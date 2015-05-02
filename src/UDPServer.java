import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import unicast.UnicastDatagramServer;


public class UDPServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UnicastDatagramServer server = new UnicastDatagramServer(6969);
			server.start();
			
			System.out.println("Started server on " + InetAddress.getLocalHost() + ":6969");
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
