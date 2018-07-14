package newchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BetterClient {
	// Member variables that will be initialized later
	Scanner in;
	PrintWriter out;
	static BetterClient client = new BetterClient();
	// Used to listen to this computer
	Scanner console = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		client.connectToServer();
	}
	
	@SuppressWarnings("resource")
	public void connectToServer() throws IOException {
		// Connection
		System.out.println("You aren't connected to a server. Please enter the IP Address of a server to continue:");
		String serverAddress = console.nextLine();
		System.out.println("Trying to connect...");
		// Socket for connection to the server
		Socket socket = new Socket(serverAddress, 3000);
		// Used to get information from the server
		in = new Scanner(socket.getInputStream());
		// Used to send information to the server
		out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("\n");
		// Begins the chat
		client.update();
	}
	
	public void update() {
		Thread getter = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if (in.hasNextLine()) {
							System.out.println(in.nextLine());
						}
					} catch (IndexOutOfBoundsException e) {
						// Nothing was there at all...
					}
				}
			}
		});
		// Thread for sending information
		Thread giver = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if (console.hasNextLine()) {
							out.println(console.nextLine());
						}
					} catch (IndexOutOfBoundsException e) {
						// Nothing was there at all...
					}
				}
			}
		});
		getter.start();
		giver.start();
	}
}
