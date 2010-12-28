/**
 * 
 */
package main;

import java.io.IOException;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import javax.swing.text.MaskFormatter;

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
		Client client= new Client("localhost",61613,"login","pass");
//		HashMap h = new HashMap();
//		String b="";
//		client.receive(Command.CONNECTED,null,null);
		//System.out.println(h.get("session").toString());
		client.subscribe("/queue/test_pleasework");
		client.send("/queue/test_pleasework", "hello test\n");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message=client.getNext();
//		client.receive(Command.MESSAGE,h,b);
		System.out.println(message.body());
//		client.receive(Command.CONNECTED, h, b)
	}

}
