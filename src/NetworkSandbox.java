import utils.ui;


public class NetworkSandbox {

	public static void main(String[] args) {
		
		Integer choice = -1;
		
		while(choice < 1 || choice > 6){
			NetworkSandbox.printMenu();
			choice = Integer.valueOf(ui.readInput());
		}

		NetworkSandbox.processChoice(choice, args);;
		
	}

	public static void printMenu() {
		System.out.println("Which function would you like to launch?");
		System.out.println("1) Unicast UDP server");
		System.out.println("2) Unicast UDP client");
		System.out.println("3) Unicast TCP server");
		System.out.println("4) Unicast TCP client");
		System.out.println("5) Multicast server");
		System.out.println("6) Multicast client");
	}
	
	public static void processChoice(int choice, String[] args){
		switch (choice) {
		case 1:
			UDPServer.main(args);
			break;
			
		case 2:
			UDPClient.main(args);
			break;
			
		case 3:
		case 4:
			System.out.println("Function not yet implemented...");
			break;
			
		case 5:
			McastServer.main(args);
			break;

		case 6:
			McastClient.main(args);
			break;
			
		default:
			System.out.println("Invalid option selected... terminating execution");
			break;
		}
	}
	
}
