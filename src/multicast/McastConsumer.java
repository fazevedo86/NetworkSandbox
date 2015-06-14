package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class McastConsumer extends Thread {

	private static final String LOCAL_MCAST_GROUP = "224.0.0.1";
	
	protected InetAddress localmcastGroup = null;
	protected InetAddress mcastGroup = null;
	protected MulticastSocket srvMcastSocket = null;
	protected int dstPort = -1;
	protected DatagramPacket rcvPacket = null;
	protected byte[] inputBuffer = null;
	protected AtomicBoolean consumerRunning;
	
	public McastConsumer(String mcastGroupIP, int Port) throws UnknownHostException {
		this.localmcastGroup = InetAddress.getByName(LOCAL_MCAST_GROUP);
		this.mcastGroup = InetAddress.getByName(mcastGroupIP);
		this.dstPort = Port;
		this.consumerRunning = new AtomicBoolean(false);
	}
	
	public synchronized boolean startConsumer() {
		if(this.dstPort > 1024 && this.consumerRunning.compareAndSet(false, true)) {
			try {
				this.srvMcastSocket = new MulticastSocket(this.dstPort);
				this.srvMcastSocket.joinGroup(this.mcastGroup);
				this.srvMcastSocket.joinGroup(this.localmcastGroup);
				System.out.println("Joined group " + this.mcastGroup.getHostAddress() + " on port " + this.srvMcastSocket.getLocalPort());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return this.consumerRunning.get();
	}
	
	public synchronized boolean stopConsumer() {
		return this.consumerRunning.compareAndSet(true, false);
	}
	
	public synchronized boolean isConsumerRunning() {
		return this.consumerRunning.get();
	}
	
	@Override
	public void run(){
		
		if(!this.isConsumerRunning() && !this.startConsumer()){
			return;
		}
		
		while(this.isConsumerRunning()){
			this.inputBuffer = new byte[1500];
			this.rcvPacket = new DatagramPacket(this.inputBuffer, this.inputBuffer.length);
			try {
				this.srvMcastSocket.receive(this.rcvPacket);
				System.out.println("Got a new packet from " + this.rcvPacket.getAddress() + ": " + new String(this.rcvPacket.getData(),0,this.rcvPacket.getLength()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		try {
			this.srvMcastSocket.leaveGroup(this.mcastGroup);
			this.srvMcastSocket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
