/**
 * 
 */
package Actors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import javax.security.auth.login.LoginException;
import javax.swing.text.html.HTMLDocument.Iterator;

import Stomp.Client;
import Stomp.Listener;

/**
 * @author tom
 *
 */
public class StockExchange implements Listener {
	static final int N=4; // max number of clients per broker
	Client _stockExchangeStompClient;
	Map<String,Company> _companies;
	TreeMap<String,StockExchangeBroker> _brokers;
	double _cash;
	int _numOfClosedBrockers;
	Set<String> _newBrokers;
	Set<String> _newClients;
	int _day;

	public StockExchange(String server, int port, String login, String pass) throws LoginException, IOException {
		_stockExchangeStompClient = new Client(server,port,login,pass);
		_companies= new HashMap<String, Company>();
		_brokers= new TreeMap<String, StockExchangeBroker>(); 

		_cash=0;
		_numOfClosedBrockers=0;
		_day=0;
		_newBrokers= new HashSet<String>();
		_newClients= new HashSet<String>();
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
		body=body.replace("\n","");
		Vector<String> parts = new Vector<String>();
		for (String s : body.split(" "))
			parts.add(s);
		// Broker connected 
		if ((origin.equals("/topic/bConnect")) && (parts.elementAt(0).equals("connect")) && (parts.size() == 2)) {
			connectBroker(parts.elementAt(1));
			return;
		}
		// Client connected
		if ((origin.equals("/topic/cConnect")) && (parts.elementAt(0).equals("connect")) && (parts.size() == 2)) {
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
		//_brokers.get(brokerName).closeDay();
		_numOfClosedBrockers++;
		if(_numOfClosedBrockers == _brokers.size())
			endTheDay();
	}

	private void endTheDay() {
		computeDeals();
		updatePrices();
		connectNewBrokers();
		connectNewClients();
		startNewDay();
	}

	private void startNewDay() {
		_stockExchangeStompClient.send("/topic/calendar", "newDay "+_day+"\n");
		
	}

	private void connectNewClients() {
		for(String client : _newClients) {
			for(StockExchangeBroker broker : _brokers.values()) {
				if (broker.getNumOfClients() == N) {
					_stockExchangeStompClient.send("/topic/Connected","connectFailed "+client+"\n");
				} else {
					broker.incClientNum();
					_stockExchangeStompClient.send("/topic/Connected","connected "+client+" "+broker.getName() +"\n");
				}
				_newClients.remove(client);
			}
		}
	}

	private void connectNewBrokers() {
		for(String broker : _newBrokers) {
			_brokers.put(broker, new StockExchangeBroker(broker));
			_stockExchangeStompClient.send("/topic/bConnected", "connected "+broker+"\n");			
			_newBrokers.remove(broker);			
		}
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
	}

	private void connectBroker(String brokerName) {
		_newBrokers.add(brokerName);
	}
}
