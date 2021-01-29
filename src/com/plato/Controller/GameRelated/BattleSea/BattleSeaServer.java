package Controller.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleSeaServer {

    private static ServerSocket serverSocket;

    static class BattleSeaClientHandler extends Thread {


        private Socket clientSocket;
        private  DataInputStream dataInputStream;
        private  DataOutputStream dataOutputStream;
        private BattleSeaServerImp server;
        private Gamer gamer;

        public BattleSeaClientHandler(Socket clientSocket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, BattleSeaServerImp server) {
            this.clientSocket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.server = server;
        }
        @Override
        public void run()
        {
            clientHandler();


        }
        private void clientHandler()
        {
            try {
                String input = "" ;
                while (true){
                    input =dataInputStream.readUTF();

                    System.out.println("client sent : "+ input);
                    if (input.startsWith("")){

                    }

                }
            }
            catch (IOException e){
                System.out.println("error");
            }
        }
    }


    static class BattleSeaServerImp{

        private static Socket clientSocket;

        public void runBattleSeaServer(){
            try {

                serverSocket = new ServerSocket(6666);

                clientSocket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                new BattleSeaClientHandler(clientSocket, dataInputStream, dataOutputStream,this );

            } catch (IOException e) {
                System.out.println("cannot connect to server!");
            }
        }

    }

}

