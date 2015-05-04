import java.net.UnknownHostException;

import multicast.McastConsumer;

import utils.ui;


public class McastClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int srvPort = -1;
		String srvAddress = null;
		
		try {
			System.out.println("Which Multicast Group shall we join? (should be within 239.192.0.0/14)");
			srvAddress = ui.readInput();
			
			System.out.println("And on which local port shall we listen to?");
			srvPort = new Integer(ui.readInput());
			
			McastConsumer Client = new McastConsumer(srvAddress, srvPort);
			
			// Start processing the received packets
			Client.start();
			
		} catch (UnknownHostException e) {
			System.out.println(e.getStackTrace());
		}
	}

}
