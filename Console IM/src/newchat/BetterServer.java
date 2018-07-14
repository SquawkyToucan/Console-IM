package newchat;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BetterServer {
	// Member variables
	Socket socket;

	// Constructor
	BetterServer(Socket client) {
		socket = client;
	}

	// Main, which starts the program creates the Server
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(3000);
		try {
			while (true) {
				// Accepts first user, starts server, stops listening for new clients
				Socket s = listener.accept();
				new BetterServer(s).run();
				break;
			}
		} catch (Exception e) {
			// Nothing needs to be done
		}
	}

	// Run, creates the chat
	@SuppressWarnings("resource")
	public void run() {
		try {
			// Gets information from the client
			Scanner in = new Scanner(socket.getInputStream());
			// Sends information to the client
			PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
			// Gets information from the host
			Scanner console = new Scanner(System.in);
			// Thread for receiving information
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
								sender.println(console.nextLine());
							}
						} catch (IndexOutOfBoundsException e) {
							// Nothing was there at all...
						}
					}
				}
			});
			// Start the two threads, beginning it
			getter.start();
			giver.start();
		} catch (Exception e) {
			// This exception would be important to know about, so I'll notify the console.
			System.err.println("The program failed in some way to create the Scanners, Threads, and/or PrintWriters, so the program wouldn't have been able to run.");
		}
	}
}
