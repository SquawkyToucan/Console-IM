package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client {
	private Scanner in;
	private PrintWriter out;
	Scanner kb = new Scanner(System.in);
	Socket s = new Socket();
	public boolean update() {
		out.println(kb.nextLine());
		String response;
		//try {
            response = in.nextLine();
            if (response == null || response.equals("")) {
                  System.exit(0);
              }
        //} catch (Exception ex) {
            //   response = "Error: " + ex;
        //}
        System.out.println(response);

		return true;
	}
	
	public void connectToServer() throws IOException {
		String serverAddress = JOptionPane.showInputDialog(null, "Enter IP Address of the Server:", JOptionPane.QUESTION_MESSAGE);
		@SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 3000);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
		for (int i = 0; i < 3; i++) {
			System.out.println(in.nextLine() + "\n");
        }
	}

	public static void main(String[] args) throws Exception {
		Client rx = new Client();
		rx.connectToServer();
		while(rx.update()){}
	}
}