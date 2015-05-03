package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class Contributor extends Thread {

	protected InetAddress mcastGroup = null;
	protected int dstPort = -1;
	protected DatagramSocket srvSocket = null;
	protected AtomicBoolean srvRunning;
	
	public Contributor(String mcastGroupIP, int remotePort) throws UnknownHostException {
		if(remotePort < 1){
			throw new UnknownHostException(mcastGroupIP + ":" + remotePort);
		} else {
			this.mcastGroup = InetAddress.getByName(mcastGroupIP);
			this.dstPort = remotePort;
			this.srvRunning.set(false);
		}
	}
	
	public synchronized boolean startServer() {
		if(this.mcastGroup != null && this.srvRunning.compareAndSet(false, true)) {
			// Create the socket
			try {
				this.srvSocket = new DatagramSocket();
				System.out.println("Started server on " + this.srvSocket.getInetAddress() + ":" + this.srvSocket.getPort());
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
		if(!this.isServerRunning() || !this.startServer()){
			return;
		}
		
		DatagramPacket mcastPacket;
		byte[] outputBuffer;
		
		while(this.isServerRunning()){
			// Packet content
			outputBuffer = new Date().toString().getBytes();
			
			// Create the packet
			mcastPacket = new DatagramPacket(outputBuffer, outputBuffer.length, this.mcastGroup, this.dstPort);
			
			// Sent the packet
			try {
				this.srvSocket.send(mcastPacket);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			// Wait for it...
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
		this.srvSocket.close();
	}
	
}
