import java.net.SocketException;
import java.net.UnknownHostException;

import unicast.UnicastDatagramServer;
import utils.ui;


public class UDPServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int srvPort = -1;
//		String srvAddress = null;
		
		try {
//			System.out.println("Which IP shall we bind to?");
//			srvAddress = ui.readInput();
			
			System.out.println("And on what port?");
			srvPort = new Integer(ui.readInput());
			
//			UnicastDatagramServer server = new UnicastDatagramServer(srvAddress, srvPort);
			UnicastDatagramServer server = new UnicastDatagramServer(srvPort);
			
			// Start processing the received packets
			server.start();
			
		} catch (SocketException | UnknownHostException e) {
			System.out.println(e.getStackTrace());
		}
		
	}

}
