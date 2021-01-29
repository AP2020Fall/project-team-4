package Controller.GameRelated.Reversi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ReversiServer {

    private static ServerSocket serverSocket;

    static class ReversiClientHandler{
        private Socket clientSocket;
        private static DataInputStream dataInputStream;
        private static DataOutputStream dataOutputStream;
        private ReversiServerImp server;


    }

    static class ReversiServerImp{

        private static Socket clientSocket;

        public void runReversiServer(){
            try {
                serverSocket = new ServerSocket(6666);
                clientSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            } catch (IOException e) {
                System.out.println("cannot connect to server!");
            }
        }

    }
}
