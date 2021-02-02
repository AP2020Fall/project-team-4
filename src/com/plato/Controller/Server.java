package Controller;

import Controller.GameRelated.Reversi.ReversiController;
import Controller.Menus.*;
import View.Client;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.Base64;


public class Server{
    private final int port;
    private static ServerSocket serverSocket;
    private static Socket socket = null;
    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    //constructor
    public Server(int port, String host) {
        this.port = port;
    }

    public String generateTokenForUser(String username) {
        while (true) {
            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            String token = base64Encoder.encodeToString(randomBytes);
            return token;
        }
    }

    public static void main(String[] args) throws IOException{
        System.out.println("Server : server started");
        serverSocket = new ServerSocket(5056);
        while (true) {
            System.out.println("Server : Waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Server : a client has connected");
            new ClientHandler(socket).start();
        }
    }
}


class ClientHandler extends Thread {

    final Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    //constructor
    public ClientHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        System.out.println("Server : Waiting for client to send request");
        try{
      //  while(received != null) {
            //receive from client
            String received = dataInputStream.readUTF();
            String[] receivedInfo = received.split("_");
            switch (receivedInfo[0]){}
        }catch (IOException e){
            System.out.println("connection closed!");
            e.printStackTrace();
        }
        }

        private void clientDisconnected() throws IOException{
        socket.close();
        System.out.println("connection closed!");
        }
 //   }
}

