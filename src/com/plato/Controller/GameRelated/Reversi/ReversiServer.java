package Controller.GameRelated.Reversi;

import Model.AccountRelated.Gamer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ReversiServer {

    private static ServerSocket serverSocket;

    static class ReversiClientHandler extends Thread{
        private Socket clientSocket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private ReversiServerImp server;
        private Gamer gamer;

        public ReversiClientHandler(Socket clientSocket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, ReversiServerImp server) {
            this.clientSocket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.server = server;
        }

        @Override
        public void run(){
            handleClient();
        }

        private void handleClient(){}
    }

    static class ReversiServerImp{

        private static Socket clientSocket;

        public void runReversiServer(){
            try {
                serverSocket = new ServerSocket(6666);
                clientSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                new ReversiClientHandler(clientSocket , dataInputStream , dataOutputStream , this);
            } catch (IOException e) {
                System.out.println("cannot connect to server!");
            }
        }

    }
}
