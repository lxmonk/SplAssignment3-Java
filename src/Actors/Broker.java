/**
 * 
 */
package Actors;

import java.io.IOException;
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
	Client _brokerStompClient;
	final int N=4; //Max number of clients the broker can deal with
	final String _name;
	final float _commission;
	float _cash;
	String _day;
	Map<String,ClientOrders> _clients;

	boolean _connected;


	Broker(String name,float commision,String day,String server, int port, String login, String pass) throws LoginException, IOException {
		_name=name;
		_commission=commision;
		_cash=0;
		_connected=false;
		_day=day;
		_clients = new Map<String, ClientOrders>();
		_brokerStompClient = new Client(server,port,login,pass);
	}

	Broker(String name,float commision,String day,String server, int port) throws LoginException, IOException {
		this(name,commision,day,server,port,"login","pass");
	}

	void connectToStockExcange() {
		_brokerStompClient.send("/topic/bConnect","connect "+ _name +"\n");	//connect to StockExcange	
		_brokerStompClient.subscribe("/topic/bConnected", this); //listen to the StockExcange connection acknowledge  				
		
	}

	/* (non-Javadoc)
	 * @see Stomp.Listener#message(java.util.Map, java.lang.String)
	 */
	@Override
	public void message(Map headers, String body) {
		Vector<String> parts = new Vector<String>();
		for (String s : body.substring(1).split(" "))
			parts.add(s);
		// connected to stockExcange
		if ((parts.elementAt(0) == "connected") && (parts.size() == 2) && (parts.elementAt(1) == _name)) { 
			connectedToStockExcange();
			return;
		}
		if (_connected) {
			// client connected to broker
			if ((parts.elementAt(0) == "connected") && (parts.size() == 3) && (parts.elementAt(2) == _name)) {
				clientConnected(parts.elementAt(1));
				return;
			}
			// client disconnected
			if ((parts.elementAt(0) == "disconnect")  && (parts.size() == 2)) {
				clientDisconnected(parts.elementAt(1));
				return;
			}
			// new day
			if ((parts.elementAt(0) == "newDay") && (parts.size() == 2)) {
				newDay(parts.elementAt(1));
				return;
			} 
			// deal
			if ((parts.elementAt(0) == "deal") && (parts.size() == 8) && ((parts.elementAt(2) == _name) || (parts.elementAt(4) == _name)))  {
				deal(parts);
				return;
			} 
			// buyOrder
			if ((parts.elementAt(0) == "buyOrder")  && (parts.size() == 6) && (parts.elementAt(2) == _name)) {
				buyOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(4),parts.elementAt(5));
				return;
			}
			// sellOrder
			if ((parts.elementAt(0) == "sellOrder")  && (parts.size() == 6) && (parts.elementAt(2) == _name)) {
				sellOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(4),parts.elementAt(5));
				return;
			}
			// close day
			if ((parts.elementAt(0) == "closeDay") && (parts.size() == 2)) {
				closeDay(parts.elementAt(1),parts.elementAt(2));
				return;
			} 
			
		}
	} 
		
	void connectedToStockExcange() {
		_brokerStompClient.subscribe("/topic/cConnected", this);
		_brokerStompClient.subscribe("/topic/cDisconnected", this);
		_brokerStompClient.subscribe("/topic/bOrders-"+_name, this);
		_brokerStompClient.subscribe("/topic/bDeals-"+_name, this);
		_brokerStompClient.subscribe("/topic/calendar", this);
		_connected=true;
	}

	private void closeDay(String clientName, String day) {
		// TODO Auto-generated method stub
		
	}

	private void sellOrder(String clientName, String shares,String stockName, String price) {
		StockOrder order= new StockOrder("sellOrder",Integer.parseInt(shares),stockName,Integer.parseInt(price));
		_clients.get(clientName).addSellOrder(order)
	}

	private void buyOrder(String clientName, String shares,String stockName, String price) {
		StockOrder order= new StockOrder("buyOrder",Integer.parseInt(shares),stockName,Integer.parseInt(price));
		_clients.get(clientName).addBuyOrder(order)
	}

	private void newDay(String day) {
		_day=day; // TODO: check if day is implemented correctly
	}

	private void clientDisconnected(String clientName) {
		_clients.remove(clientName);
	}

	private void clientConnected(String clientName) {
		_clients.put(clientName, new ClientOrders());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
