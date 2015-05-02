package unicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UnicastDatagramServer extends Thread {

	protected DatagramSocket socket = null;
	
	public UnicastDatagramServer(String localAddress, int localPort) throws SocketException, UnknownHostException {
		this.socket = new DatagramSocket(localPort, InetAddress.getByName(localAddress));
		System.out.println("Started server on " + localAddress + ":" + localPort);
	}
	
	public void run() {
		
		while(true){
			DatagramPacket responsePacket;
			DatagramPacket receivedPacket;
			InetAddress clientIP;
			int clientPort;
			byte[] inputBuffer;
			byte[] outputBuffer;
			
			// MTU for ethernet = 1500, therefore the buffer must be at least that
			inputBuffer = new byte[1500];
	
			// Prepare the structure of the packet to be received
	        receivedPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
	        
	        try {
	        	// receive the packet
				this.socket.receive(receivedPacket);
				
				// DEBUG
		        System.out.println("Received a packet containing: " + new String(receivedPacket.getData(),0,receivedPacket.getLength()));
				
				// Extract the source IP and port
	            clientIP = receivedPacket.getAddress();
	            clientPort = receivedPacket.getPort();
	            
	            // Prepare the packet to be sent
	            outputBuffer = "I heard you loud and clear!".getBytes();
	            responsePacket = new DatagramPacket(outputBuffer, outputBuffer.length, clientIP, clientPort);
	            
	            // DEBUG
		        System.out.println("Sent a packet containing: " + new String(outputBuffer,0,outputBuffer.length));
	            
	            // Packet away!
	            socket.send(responsePacket);
	            
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
		}
	}
}
