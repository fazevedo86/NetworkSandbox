import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import unicast.UnicastDatagramClient;


public class UDPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Which host shall we connect to?");
		String targetHost = UDPClient.readInput();
		System.out.println("And on what port?");
		int targetPort = new Integer(UDPClient.readInput());

		try {
			UnicastDatagramClient client = new UnicastDatagramClient(targetHost, targetPort);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String readInput()
	{
		BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
		String inputText = null;
		
		try {
			inputText = inputBuffer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inputText;
	}

}
