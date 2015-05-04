import java.net.UnknownHostException;
import multicast.McastContributor;
import utils.ui;


public class McastServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int dstPort = -1;
		String mcastAddress = null;
		
		try {
			System.out.println("To which Multicast Group shall we contribute? (should be within 239.192.0.0/14)");
			mcastAddress = ui.readInput();
			
			System.out.println("And on which port will the consumers be listening?");
			dstPort = new Integer(ui.readInput());
			
			McastContributor server = new McastContributor(mcastAddress, dstPort);
			
			// Start processing sending packets
			server.start();
			
		} catch (UnknownHostException e) {
			System.out.println(e.getStackTrace());
		}
	}

}
