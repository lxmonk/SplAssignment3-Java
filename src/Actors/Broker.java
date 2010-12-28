/**
 * 
 */
package Actors;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	final float _cash;
	Map<String> _clientNames;
	
	
	Broker(String server, int port, String login, String pass) throws LoginException, IOException {
		_brokerStompClient = new Client(server,port,login,pass);
	}
	
	Broker(String server, int port) throws LoginException, IOException {
		this(server,port,"login","pass");
	}
			

	/* (non-Javadoc)
	 * @see Stomp.Listener#message(java.util.Map, java.lang.String)
	 */
	@Override
	public void message(Map headers, String body) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
