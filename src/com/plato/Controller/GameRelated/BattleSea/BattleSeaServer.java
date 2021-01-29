package Controller.GameRelated.BattleSea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleSeaServer {

    private static ServerSocket serverSocket;

    static class BattleSeaClientHandler{
        private Socket clientSocket;
        private  DataInputStream dataInputStream;
        private  DataOutputStream dataOutputStream;
        private BattleSeaServerImp server;


    }

    static class BattleSeaServerImp{

        private static Socket clientSocket;

        public void runBattleSeaServer(){
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

