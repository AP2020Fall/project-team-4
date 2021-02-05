package Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;


public class Client {
	public static String host = "127.0.0.1";
	public static int port = 2222;
	private static Client client;
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	public Client () {
		Client.client = this;
	}

	public static void main (String[] args) {
		new Client().connect();
		System.out.println("running the program...");
		MainController.main(args);
	}

	public static Client getClient () {
		return client;
	}

	//    public static void main (String[] args) throws IOException {
//        System.out.println("Client : client started");
//        try {
//            Scanner scanner = new Scanner(System.in);
//            InetAddress ip = InetAddress.getByName("localhost");
//            Socket socket = new Socket(ip, 5056);
//            System.out.println("Client : Connecting to Server...");
//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            launch(args);
////            while (true) {
////                String message = scanner.nextLine();
////                dataOutputStream.writeUTF(message);
////                if (message.equals("Exit")) {
////                    System.out.println("Closing this connection : " + socket);
////                    socket.close();
////                    System.out.println("Connection closed");
////                    break;
////                }
////            }
////            scanner.close();
//  //          dataOutputStream.close();
//        } catch (Exception e) {
//            System.out.println("Client : connecting to server failed due to exception");
//            e.printStackTrace();
//        }
//    }

	public DataInputStream getDataInputStream () {
		return dataInputStream;
	}

	public DataOutputStream getDataOutputStream () {
		return dataOutputStream;
	}

	private void connect () {
		try {
			socket = new Socket(host, port);
			dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			System.out.println("Successfully connected to server!");
		} catch (IOException e) {
			System.out.println("client couldnt connect to server!");
			e.printStackTrace();
		}
	}
}
