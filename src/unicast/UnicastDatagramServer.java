package unicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UnicastDatagramServer extends Thread {

	protected DatagramSocket srvSocket = null;
	protected InetAddress srvAddress = null;
	protected int srvPort = -1;
	protected AtomicBoolean srvRunning = null;
	
	public UnicastDatagramServer(String localAddress, int localPort) throws SocketException, UnknownHostException {
		this(localPort);
		this.srvAddress = InetAddress.getByName(localAddress);
	}
	
	public UnicastDatagramServer(int localPort) throws SocketException, UnknownHostException {
		if(localPort <= 1024) {
			throw new SocketException("Cannot use ports below 1024");
		} else {
			this.srvPort = localPort;
			this.srvRunning = new AtomicBoolean(false);
		}
	}
	
	public synchronized boolean startServer() {
		if(this.srvPort > 1024 && this.srvRunning.compareAndSet(false, true)) {
			// Create the socket
			try {
				if(this.srvAddress != null) {
					this.srvSocket = new DatagramSocket(this.srvPort,this.srvAddress);
					System.out.println("Started server on " + this.srvSocket.getInetAddress() + ":" + this.srvSocket.getPort());
				} else {
					this.srvSocket = new DatagramSocket(this.srvPort);
					System.out.println("Started server on all address on port" + this.srvSocket.getPort());
				}
			} catch (SocketException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return this.srvRunning.get();
	}
	
	public synchronized boolean stopServer() {
		return this.srvRunning.compareAndSet(true, false);
	}
	
	public synchronized boolean isServerRunning() {
		return this.srvRunning.get();
	}
	
	@Override
	public void run() {
		
		if(!this.isServerRunning() && !this.startServer()){
			return;
		}
		
		DatagramPacket responsePacket;
		DatagramPacket receivedPacket;
		InetAddress clientIP;
		int clientPort;
		byte[] inputBuffer;
		byte[] outputBuffer;
		
		while(this.isServerRunning()){
			
			// MTU for ethernet = 1500, therefore the buffer must be at least that
			inputBuffer = new byte[1500];
	
			// Prepare the structure of the packet to be received
	        receivedPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
	        
	        try {
	        	// receive the packet
				this.srvSocket.receive(receivedPacket);
				
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
	            srvSocket.send(responsePacket);
	            
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
		}
		this.srvSocket.close();
	}
}
