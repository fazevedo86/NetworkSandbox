import java.io.IOException;

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
			
			System.out.println("Connection established...");
			
			while(true){
				System.out.println(client.sendMessage(ui.readInput()));
			}

		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		
	}
}
