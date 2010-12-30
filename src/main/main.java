/**
 * 
 */
package main;

import java.io.IOException;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import javax.swing.text.MaskFormatter;

import Actors.Broker;
import Stomp.Client;
import Stomp.Command;
import Stomp.Message;

/**
 * @author tom
 *
 */
public class main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws LoginException 
	 */
	public static void main(String[] args) throws LoginException, IOException {
		Broker broker=new Broker("tom",0.1,"localhost", 61613);
		broker.connectToStockExcange();
		broker.message(null,"connected tom");
		broker.message(null,"connected client1 tom");
		broker.message(null,"connected client2 tom");
		broker.message(null,"connected client3 tom");
		broker.message(null,"connected client4 tom");
		broker.message(null,"sellOrder client1 tom 11 stockName1 101");
		broker.message(null,"sellOrder client2 tom 12 stockName2 102");
		broker.message(null,"sellOrder client3 tom 13 stockName3 103");
		broker.message(null,"sellOrder client4 tom 14 stockName4 104");
		broker.message(null,"buyOrder client1 tom 15 stockName5 105");
		broker.message(null,"buyOrder client2 tom 16 stockName6 106");
		broker.message(null,"buyOrder client3 tom 17 stockName7 107");
		broker.message(null,"buyOrder client4 tom 18 stockName8 108");
		broker.message(null,"disconnect client1");
		broker.message(null,"closeDay client2 1");
		broker.message(null,"closeDay client3 1");
		broker.message(null,"closeDay client4 1");
		broker.message(null,"deal client3 tom client4 aviahd stockName5 10 100");
		broker.message(null,"deal client2 aviahd client4 tom stockName2 10 200");
		broker.message(null,"newDay 2");
		broker.message(null,"connected client1 tom");
		broker.message(null,"connected client2 tom");
		broker.message(null,"connected client3 tom");
		broker.message(null,"connected client4 tom");
		broker.message(null,"connected client5 tom");
		System.out.println(broker.getCash());
		broker.message(null,"sellOrder client1 tom 11 stockName1 101");
		broker.message(null,"sellOrder client2 tom 12 stockName2 102");
		broker.message(null,"sellOrder client3 tom 13 stockName3 103");
		broker.message(null,"sellOrder client4 tom 14 stockName4 104");
		broker.message(null,"buyOrder client1 tom 15 stockName5 105");
		broker.message(null,"buyOrder client2 tom 16 stockName6 106");
		broker.message(null,"buyOrder client3 tom 17 stockName7 107");
		broker.message(null,"buyOrder client4 tom 18 stockName8 108");
		broker.message(null,"closeDay client2 1");
		broker.message(null,"closeDay client3 1");
		broker.message(null,"closeDay client4 1");
		broker.message(null,"closeDay client1 1");
		broker.message(null,"deal client3 tom client4 aviahd stockName5 10 10");
		broker.message(null,"deal client2 aviahd client4 tom stockName2 50 10");
		System.out.println(broker.getCash());

	}
}
//		Client client= new Client("localhost",61613,"login","pass");
////		HashMap h = new HashMap();
////		String b="";
////		client.receive(Command.CONNECTED,null,null);
//		//System.out.println(h.get("session").toString());
//		client.subscribe("/queue/test_pleasework");
//		client.send("/queue/test_pleasework", "hello test\n");
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Message message=client.getNext();
////		client.receive(Command.MESSAGE,h,b);
//		System.out.println(message.body());
////		client.receive(Command.CONNECTED, h, b)
//	}

