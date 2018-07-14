package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
	Socket socket;
	int clientNumber = 0;

	Server(Socket socket, int client) {
		this.socket = socket;
		this.clientNumber = client;
		log("New connection with client# " + clientNumber + " at " + socket);
	}

	@SuppressWarnings({ })
	public void run() {
		try {
			Scanner in = new Scanner(socket.getInputStream()); // takes messages from the client
			Scanner niriri = new Scanner(System.in); // takes messages from the server
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // sends messages to the client
			out.println("Hello, you are client #" + clientNumber + ".");
			out.println("Enter a line with only a period to quit\n");
			//while (true) {
				try {
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							while(true) {
								receive(in);
							}
						}
					});
					Thread y = new Thread(new Runnable() {
						@Override
						public void run() {
							while(true) {
								send(out, niriri);
							}
						}
					});
					t.start();
					y.start();
				} catch (Exception e) {
					out.println("You didn't say anything...");
				}

			//}
		} catch (IOException e) {
			log("Error handling client# " + clientNumber + ": " + e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				log("Couldn't close a socket, what's going on?");
			}
			log("Connection with client# " + clientNumber + " closed");
		}

	}

	public void receive(Scanner s) {
		while (true) {
			try {
				try {
					System.out.println(s.nextLine());
				} catch (Exception e) {
				}
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
	}

	public void send(PrintWriter sender, Scanner s) {
		//while (true) {
			try {
				while(s.hasNextLine()) {
					String toTheClient = s.nextLine();
					sender.println(toTheClient);
				}
			}
			catch(IndexOutOfBoundsException e) {
				
			}
		//}

	}

	/**
	 * Logs a simple message. In this case we just write the message to the server
	 * applications standard output.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		System.out.println("The capitalization server is running.");

		// 10. Create a variable to hold a client number
		int client = 0;

		// 11. Create a SeverSocket on port 9898
		ServerSocket listener = new ServerSocket(3000);
		try {
			while (true) {
				// 12. call the accept method on your ServerSocket and store it
				// in a Socket Variable
				Socket socket = listener.accept();

				// 14. create an instance of a Server and pass it your
				// listener
				// and client number.
				Server rc = new Server(socket, client);

				// 15. Start your Reverser.
				rc.start();
			}
		} finally {
			// 16. Don't forget to close your ServerSocket!

		}
	}

	private void log(String message) {
		System.out.println(message);
	}

}