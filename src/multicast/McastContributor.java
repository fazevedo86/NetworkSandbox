package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import utils.ui;

public class McastContributor extends Thread {

	protected InetAddress mcastGroup = null;
	protected int dstPort = -1;
	protected MulticastSocket srvSocket = null;
	protected AtomicBoolean srvRunning = null;
	
	public McastContributor(String mcastGroupIP, int remotePort) throws UnknownHostException {
		if(remotePort < 1){
			throw new UnknownHostException(mcastGroupIP + ":" + remotePort);
		} else {
			this.mcastGroup = InetAddress.getByName(mcastGroupIP);
			this.dstPort = remotePort;
			this.srvRunning = new AtomicBoolean(false);
		}
	}
	
	public synchronized boolean startServer() {
		if(this.mcastGroup != null && this.mcastGroup.isMulticastAddress() && this.srvRunning.compareAndSet(false, true)) {
			// Create the socket
			try {
				this.srvSocket = new MulticastSocket();
				this.srvSocket.setTimeToLive(255); // broadest scope
				System.out.println("Started contributing to multicast group " + this.mcastGroup.getHostAddress() + ":" + this.dstPort);
			} catch ( IOException e) {
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
		
		DatagramPacket mcastPacket;
		byte[] outputBuffer;
		String msgContent;
		
		while(this.isServerRunning()){
			// Packet content
			//outputBuffer = new Date().toString().getBytes();
			System.out.print("Text to be sent to the mcast group: ");
			msgContent = ui.readInput();
			outputBuffer = msgContent.getBytes();
			
			// Create the packet
			mcastPacket = new DatagramPacket(outputBuffer, outputBuffer.length, this.mcastGroup, this.dstPort);
			
			// Sent the packet
			try {
				this.srvSocket.send(mcastPacket);
				System.out.println("Sent message: " + msgContent);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			// Wait for it...
//			try {
//				sleep(5000);
//			} catch (InterruptedException e) {
//				System.out.println(e.getMessage());
//			}
		}
		
		this.srvSocket.close();
	}
	
}
