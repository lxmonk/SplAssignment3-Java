/**
 * 
 */
package Actors;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Vector;
import java.util.Map.Entry;

import javax.security.auth.login.LoginException;

import Stomp.Client;
import Stomp.Listener;

/**
 * @author tom
 *
 */
public class StockExchange implements Listener {
	Client _stockExchangeStompClient;
	Map<String,Company> _companies;
	//SortedMap<String,StockExchangeBroker> _brokers;
	List<StockExchangeBroker> _brokers;
	double _cash;
	int _numOfClosedBrockers;
	Vector<String> _newBrokers;
	Vector<String> _newClients;
	int _day;

	public StockExchange(String server, int port, String login, String pass) throws LoginException, IOException {
		_stockExchangeStompClient = new Client(server,port,login,pass);
		_companies=new HashMap<String, Company>();
		//		_brokers=new //SortedMap<String, StockExchangeBroker>();

		_cash=0;
		_numOfClosedBrockers=0;
		_day=0;
		_newBrokers= new Vector<String>();
		_newClients= new Vector<String>();
		_stockExchangeStompClient.subscribe("/topic/bConnect",this);
		_stockExchangeStompClient.subscribe("/topic/cConnect",this);
		_stockExchangeStompClient.subscribe("/topic/Orders",this);
		_stockExchangeStompClient.subscribe("/topic/cDisconnect",this);
	}

	public StockExchange(String server, int port) throws LoginException, IOException {
		this(server,port,"login","pass");
	}

	/* (non-Javadoc)
	 * @see Stomp.Listener#message(java.util.Map, java.lang.String)
	 */
	@Override
	public void message(Map headers, String body,String origin) {
		Vector<String> parts = new Vector<String>();
		for (String s : body.split(" "))
			parts.add(s);
		// Broker connected 
		if ((origin.equals("bConnect")) && (parts.elementAt(0).equals("connect")) && (parts.size() == 2)) {
			connectBroker(parts.elementAt(1));
			return;
		}
		// Client connected
		if ((origin.equals("cConnect")) && (parts.elementAt(0).equals("connect")) && (parts.size() == 2)) {
			connectClient(parts.elementAt(1));
			return;
		}
		// Client disConnected
		if ((parts.elementAt(0).equals("disconnect")) && (parts.size() == 2)) {
			return;
		}
		// Broker closeDay
		if ((parts.elementAt(0).equals("closeDay")) && (parts.size() == 3)) {
			brokerClosedTheDay(parts.elementAt(1));
			return;
		}
		// Broker passed a buyOrder
		if ((parts.elementAt(0).equals("buyOrder"))  && (parts.size() == 6)) {
			addBuyOrder(parts.elementAt(1),Integer.parseInt(parts.elementAt(3)),parts.elementAt(4),Double.parseDouble(parts.elementAt(5)));
			return;
		}
		// Broker passed a sellOrder
		if ((parts.elementAt(0).equals("sellOrder"))  && (parts.size() == 6)) {
			addSellOrder(parts.elementAt(1),Integer.parseInt(parts.elementAt(3)),parts.elementAt(4),Double.parseDouble(parts.elementAt(5)));
			return;
		}

	}

	private void brokerClosedTheDay(String brokerName) {
		_brokers.get(brokerName).closeDay();
		_numOfClosedBrockers++;
		if(_numOfClosedBrockers == _brokers.size())
			endTheDayStartTheNext();
	}

	private void endTheDayStartTheNext() {
		//TODO: make deals
		
	}

	private void addSellOrder(String clientName, int shares,String stockName, double price) {
		_companies.get(stockName).addSellOrder(shares,clientName,price);
	}

	private void addBuyOrder(String clientName, int shares,String stockName, double price) {
		_companies.get(stockName).addBuyOrder(shares,clientName,price);
	}

	//	private void findMatchForBuyOrder(String clientName, int shares,String stockName, double price){
	//		Company stockCompany=_companies.get(stockName);
	//		double flotPrice=-1;
	//		double sellPrice=-1;
	//		if (stockCompany.getNumOfFlotingShares() <= shares) 
	//			flotPrice=stockCompany.getPrice();
	//		for(StockOrder order : stockCompany.getSellOrders()) {
	//			if !(flotPrice != -1) && order.getPrice() >
	//			if (order.getAmount() <= shares) 
	//				
	//		}
	//			stockCompany.getSellOrders()
	//	}

	private void connectClient(String clientName) {
		_newClients.add(clientName);
		//TODO:	_brokers. add the client
		//	_stockExchangeStompClient.send("/topic/cConnected", "connected "+clientName+" "+brokerName+"\n");
	}

	private void connectBroker(String brokerName) {
		_newBrokers.add(brokerName);
		//		_brokers.put(brokerName,new StockExchangeBroker(brokerName));
		//_stockExchangeStompClient.send("/topic/bConnected", "connected "+brokerName+"\n");
	}

}
