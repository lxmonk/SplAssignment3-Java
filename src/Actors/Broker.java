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
	//Map<String,ClientOrders> _clientNames;

	boolean _connected;


	Broker(String name,float commision,String server, int port, String login, String pass) throws LoginException, IOException {
		_name=name;
		_commission=commision;
		_cash=0;
		_connected=false;
		_brokerStompClient = new Client(server,port,login,pass);
		_brokerStompClient.send("/topic/bConnect","connect "+ _name +"\n");	//connect to StockExcange	
		_brokerStompClient.subscribe("/topic/bConnected", this); //listen to the StockExcange connection acknowledge  		
	}

	Broker(String name,float commision,String server, int port) throws LoginException, IOException {
		this(name,commision,server,port,"login","pass");
	}

	void connectedToStockExcange() {
		_brokerStompClient.subscribe("/topic/cConnected", this);
		_brokerStompClient.subscribe("/topic/cDisconnected", this);
		_brokerStompClient.subscribe("/topic/bOrders-"+_name, this);
		_brokerStompClient.subscribe("/topic/bDeals-"+_name, this);
		_brokerStompClient.subscribe("/topic/calendar", this);
		_connected=true;
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
		if ((parts.elementAt(0) == "connected") && (parts.elementAt(1) == _name)) { 
			connectedToStockExcange();
			return;
		}
		if (_connected) {
			// client connected to broker
			if ((parts.elementAt(0) == "connected") && (parts.elementAt(2) == _name)) {
				clientConnected(parts.elementAt(1));
				return;
			}
			// client disconnected
			if (parts.elementAt(0) == "disconnect") {
				clientDisconnected(parts.elementAt(1));
				return;
			}
			// new day
			if (parts.elementAt(0) == "newDay") {
				newDay(parts.elementAt(1));
				return;
			} 
			// deal
			if ((parts.elementAt(0) == "deal") && ((parts.elementAt(2) == _name) || (parts.elementAt(4) == _name)))  {
				deal(parts);
				return;
			} 
			// buyOrder
			if ((parts.elementAt(0) == "buyOrder") && (parts.elementAt(2) == _name)) {
				buyOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(3),parts.elementAt(5));
				return;
			}
			// sellOrder
			if ((parts.elementAt(0) == "sellOrder") && (parts.elementAt(2) == _name)) {
				sellOrder(parts.elementAt(1),parts.elementAt(3),parts.elementAt(3),parts.elementAt(5));
				return;
			}
			// close day
			if (parts.elementAt(0) == "closeDay") {
				closeDay(parts.elementAt(1),parts.elementAt(2));
				return;
			} 
			
		}
	} 


	private void newDay(String elementAt) {
		// TODO Auto-generated method stub
		
	}

	private void clientDisconnected(String elementAt) {
		// TODO Auto-generated method stub
		
	}

	private void clientConnected(String elementAt) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
