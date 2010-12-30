/**
 * 
 */
package Actors;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.login.LoginException;

import Stomp.Client;
import Stomp.Listener;

/**
 * @author tom
 *
 */

public class Broker implements Listener {
	Client _brokerStompClient;	// Stomp Client
	final int N=4;				 //Max number of clients the broker can deal with
	final String _name;
	final float _commission;
	float _cash;
	int _day;
	Map<String,TradingClient> _clients;

	boolean _connected;
	int _numClosedCliends;


	public Broker(String name,float commision,int day,String server, int port, String login, String pass) throws LoginException, IOException {
		_name=name;
		_commission=commision;
		_cash=0;
		_connected=false;
		_day=day;
		_clients = new HashMap<String, TradingClient>();
		_numClosedCliends=0;
		_brokerStompClient = new Client(server,port,login,pass);
	}

	public Broker(String name,float commision,int day,String server, int port) throws LoginException, IOException {
		this(name,commision,day,server,port,"login","pass");
	}

	public void connectToStockExcange() {
		//connect to StockExcange	
		_brokerStompClient.send("/topic/bConnect","connect "+ _name +"\n");
		//listen to the StockExcange connection acknowledge
		_brokerStompClient.subscribe("/topic/bConnected", this);   				
		
	}

	/* (non-Javadoc)
	 * @see Stomp.Listener#message(java.util.Map, java.lang.String)
	 */
	@Override
	public void message(Map headers, String body) {
		Vector<String> parts = new Vector<String>();
		for (String s : body.split(" "))
			parts.add(s);
		// connected to stockExcange
		if ((parts.elementAt(0).equals("connected")) && 
				 (parts.size() == 2) && 
				(parts.elementAt(1).equals(_name))) { 
			connectedToStockExcange();
			return;
		}
		if (_connected) {
			// client connected to broker
			if ((parts.elementAt(0).equals("connected")) && (parts.size() == 3) && (parts.elementAt(2).equals(_name))) {
				clientConnected(parts.elementAt(1));
				return;
			}
			// client disconnected
			if ((parts.elementAt(0).equals("disconnect"))  && (parts.size() == 2)) {
				clientDisconnected(parts.elementAt(1));
				return;
			}
			// new day
			if ((parts.elementAt(0).equals("newDay")) && (parts.size() == 2)) {
				newDay(Integer.parseInt(parts.elementAt(1)));
				return;
			} 
			// the buyer in a deal
			if ((parts.elementAt(0).equals("deal")) && (parts.size() == 8) && (parts.elementAt(2).equals(_name)))  {
				deal("dealBought",parts.elementAt(1),parts.elementAt(5),Integer.parseInt(parts.elementAt(6)),Integer.parseInt(parts.elementAt(7)));
				return;
			}
			// the seller in a deal
			if ((parts.elementAt(0).equals("deal")) && (parts.size() == 8) && (parts.elementAt(4).equals(_name)))  {
				deal("dealSold",parts.elementAt(3),parts.elementAt(5),Integer.parseInt(parts.elementAt(6)),Integer.parseInt(parts.elementAt(7)));
				return;
			} 
			// buyOrder
			if ((parts.elementAt(0).equals("buyOrder"))  && (parts.size() == 6) && (parts.elementAt(2).equals(_name))) {
				buyOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(4),parts.elementAt(5));
				return;
			}
			// sellOrder
			if ((parts.elementAt(0).equals("sellOrder"))  && (parts.size() == 6) && (parts.elementAt(2).equals(_name))) {
				sellOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(4),parts.elementAt(5));
				return;
			}
			// close day
			if ((parts.elementAt(0).equals("closeDay")) && (parts.size() == 3)) {
				ClientclosedDay(parts.elementAt(1),Integer.parseInt(parts.elementAt(2)));
				return;
			} 
			
		}
	} 
		

	private void deal(String type,String clientName,String stockName,int amount,int price) {
		_cash+=(price*amount)/_commission;
		_brokerStompClient.send("/topic/cDeals-"+clientName,
				type+" "+clientName+" "+stockName+" "+amount+" "+price+" "+_commission);
	}

	public void connectedToStockExcange() {
		_brokerStompClient.subscribe("/topic/cConnected", this);
		_brokerStompClient.subscribe("/topic/cDisconnected", this);
		_brokerStompClient.subscribe("/topic/bOrders-"+_name, this);
		_brokerStompClient.subscribe("/topic/bDeals-"+_name, this);
		_brokerStompClient.subscribe("/topic/calendar", this);
		_connected=true;
	}

	private void ClientclosedDay(String clientName, int day) {
		_clients.get(clientName).closeDay();
		_numClosedCliends++;
		if (_clients.size() == _numClosedCliends) 
			brokerCloseDay();
	}

	private void brokerCloseDay() {
		for (TradingClient client : _clients.values()) {
			for(StockOrder order : client.getBuyOrders())
				_brokerStompClient.send("/topic/Orders",order.toString());
			for(StockOrder order : client.getSellOrders())
				_brokerStompClient.send("/topic/Orders",order.toString());
		}
		_brokerStompClient.send("/topic/orders", "closeDay "+_name +_day +"\n");		
	}

	private void sellOrder(String clientName, String shares,String stockName, String price) {
		StockOrder order= new StockOrder("sellOrder",Integer.parseInt(shares),stockName,Integer.parseInt(price));
		_clients.get(clientName).addSellOrder(order);
	}

	private void buyOrder(String clientName, String shares,String stockName, String price) {
		StockOrder order= new StockOrder("buyOrder",Integer.parseInt(shares),stockName,Integer.parseInt(price));
		_clients.get(clientName).addBuyOrder(order);
	}

	private void newDay(int day) {
		_clients.clear();
		_numClosedCliends=0;
		_day=day;
	}

	private void clientDisconnected(String clientName) {
		_clients.remove(clientName);
	}

	private void clientConnected(String clientName) {
		if (_clients.size() < N)
			_clients.put(clientName, new TradingClient());
	}
	
	public float getCash() {
		return _cash;
	}
	
}
