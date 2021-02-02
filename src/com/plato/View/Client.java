package View;
import Controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static javafx.application.Application.launch;


public class Client {


    public static void main (String[] args) throws IOException {
        System.out.println("Client : client started");
        try {
            Scanner scanner = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 5056);
            System.out.println("Client : Connecting to Server...");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            while (true) {
//                String message = scanner.nextLine();
//                dataOutputStream.writeUTF(message);
//                if (message.equals("Exit")) {
//                    System.out.println("Closing this connection : " + socket);
//                    socket.close();
//                    System.out.println("Connection closed");
//                    break;
//                }
//            }
//            scanner.close();
  //          dataOutputStream.close();
        } catch (Exception e) {
            System.out.println("Client : connecting to server failed due to exception");
            e.printStackTrace();
        }
    }

}
