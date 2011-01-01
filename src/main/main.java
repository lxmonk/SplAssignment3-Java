/**
 * 
 */
package main;

import java.io.IOException;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import javax.swing.text.MaskFormatter;

import Actors.Broker;
import Actors.StockExchange;
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
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws LoginException, IOException, InterruptedException {
		StockExchange stockExchange=new StockExchange("localhost", 61613);
		Broker broker=new Broker("tom",0.1,"localhost", 61613);
		broker.connectToStockExcange();
		stockExchange.startNewDay();
		stockExchange.message(null,"connect client2 ","/topic/cConnect");
		stockExchange.message(null,"connect client3 ","/topic/cConnect");
		stockExchange.message(null,"connect client4 ","/topic/cConnect");
		stockExchange.message(null,"closeDay tom 0",null);
		Thread.sleep(1000);
		broker.message(null,"sellOrder client1 tom 10 stockName1 101",null);
		broker.message(null,"sellOrder client2 tom 12 stockName1 102",null);
		broker.message(null,"sellOrder client3 tom 13 stockName1 103",null);
		broker.message(null,"sellOrder client4 tom 14 stockName1 104",null);
		broker.message(null,"buyOrder client1 tom 15 stockName1 105",null);
		broker.message(null,"buyOrder client2 tom 16 stockName1 106",null);
		broker.message(null,"buyOrder client3 tom 17 stockName1 107",null);
		broker.message(null,"buyOrder client4 tom 18 stockName1 108",null);
		stockExchange.message(null,"disconnect client1",null);
		broker.message(null,"closeDay client2 1",null);
		broker.message(null,"closeDay client3 1",null);
		broker.message(null,"closeDay client4 1",null);
//		broker.message(null,"deal client3 tom client4 aviahd stockName5 10 100",null);
//		broker.message(null,"deal client2 aviahd client4 tom stockName2 10 200",null);
//		broker.message(null,"newDay 2",null);
//		stockExchange.message(null,"closeDay tom 1",null);
		//Thread.sleep(1000);
		System.out.println(broker.getCash());
		broker.message(null,"sellOrder client2 tom 12 stockName2 102",null);
		broker.message(null,"sellOrder client3 tom 13 stockName2 103",null);
		broker.message(null,"sellOrder client4 tom 14 stockName2 104",null);
		broker.message(null,"buyOrder client2 tom 16 stockName2 106",null);
		broker.message(null,"buyOrder client3 tom 17 stockName1 107",null);
		broker.message(null,"buyOrder client4 tom 18 stockName1 108",null);
		broker.message(null,"closeDay client2 2",null);
		broker.message(null,"closeDay client3 2",null);
		broker.message(null,"closeDay client4 2",null);
//		broker.message(null,"deal client3 tom client4 aviahd stockName5 10 10",null);
//		broker.message(null,"deal client2 aviahd client4 tom stockName2 50 10",null);
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

