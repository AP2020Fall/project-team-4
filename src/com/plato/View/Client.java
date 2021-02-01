package View;
import java.io.*;
import java.net.*;
import java.util.Scanner;



public class Client {


    public static void main (String[] args) throws IOException {
        System.out.println("client started");
        try {
            Scanner scanner = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 5056);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String message = scanner.nextLine();
                dataOutputStream.writeUTF(message);
                if (message.equals("Exit")) {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }
            }
            scanner.close();
            dataOutputStream.close();
        } catch (Exception e) {
            System.out.println("connecting to server failed due to exception");
            e.printStackTrace();
        }
    }
}
