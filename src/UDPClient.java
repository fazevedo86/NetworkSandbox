import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import unicast.UnicastDatagramClient;
import utils.ui;


public class UDPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Which host shall we connect to?");
		String targetHost = ui.readInput();
		System.out.println("And on what port?");
		int targetPort = new Integer(ui.readInput());

		try {
			UnicastDatagramClient client = new UnicastDatagramClient(targetHost, targetPort);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
