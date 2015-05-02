package unicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UnicastDatagramClient {

	InetAddress dstIP = null;
	int dstPort = -1;
	DatagramSocket socket = null;
	DatagramPacket responsePacket = null;
	byte[] outputBuffer, inputBuffer = null;
	
	public UnicastDatagramClient(String IP, int Port) throws SocketException, UnknownHostException {
		this.socket = new DatagramSocket();
		this.dstIP = InetAddress.getByName(IP);
		this.dstPort = Port;
		this.inputBuffer = new byte[1500];
	}
	
	public String sendMessage(String msg) throws IOException{
		// Send the packet
		this.outputBuffer = msg.getBytes();
		this.socket.send(new DatagramPacket(this.outputBuffer, this.outputBuffer.length, this.dstIP, this.dstPort));
		
		// DEBUG
		System.out.println("Packet sent containing: " + msg);
		
		// Get the response
		this.responsePacket = new DatagramPacket(this.inputBuffer, this.inputBuffer.length);
		this.socket.receive(responsePacket);
		
		// DEBUG
		System.out.println("Packet received containing: " + new String(this.responsePacket.getData(),0,this.responsePacket.getLength()));
		
		// Return the response
		return new String(this.responsePacket.getData(),0,this.responsePacket.getLength());
	}
}
