package Controller;

import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;

import java.io.*;
import java.net.Socket;

import static Controller.MyGson.getGson;


public class Client {
	public static String host = "127.0.0.1";
	public static int port = 6666;
	private static Client client;
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private String token;

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

	public String getToken () {
		return token;
	}

	public void setToken (String token) {
		this.token = token;
	}

	public Account getCurrentAccLoggedIn () {
		if (token == null)
			return null;
		try {
			dataOutputStream.writeUTF("getCurrentAccLoggedIn_" + token);
			dataOutputStream.flush();
			boolean gamerOrAdmin = dataInputStream.readBoolean();

			return getGson().fromJson(dataInputStream.readUTF(), gamerOrAdmin ? Gamer.class : Admin.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	public void clientDisconnected () throws IOException {
		socket.close();
		System.out.println("connection closed!");
	}
}
