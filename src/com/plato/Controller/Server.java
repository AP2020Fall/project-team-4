package Controller;

import Controller.AccountRelated.AccountController;
import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.BattleSea.ShipController;
import Controller.GameRelated.Reversi.ReversiController;
import Controller.Menus.*;
import Model.GameRelated.BattleSea.Ship;
import View.Client;
import View.GameRelated.BattleSea.BattleSeaView;
import javafx.scene.layout.GridPane;
import Controller.AccountRelated.AccountController;

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
            switch (receivedInfo[0]){
                case"canThrowBomb":
                    int x = Integer.parseInt(receivedInfo[1]);
                    int y = Integer.parseInt(receivedInfo[2]);
                    if (BombController.getInstance().canThrowBomb(x,y))
                        dataOutputStream.writeUTF("true");
                    else dataOutputStream.writeUTF("false");
                    break;
                case"throwBomb":
                     x = Integer.parseInt(receivedInfo[1]);
                    y = Integer.parseInt(receivedInfo[2]);
                   BombController.getInstance().throwBomb(x,y);
                   break;

                case"displayBoard":
                    BattleSeaView.getInstance().displayBoard(BattleSeaController.getInstance().getBoardAsStringBuilder(BattleSeaEditBoardPageController.getInstance().getCurrentBoard()));
                    break;

//                case"moveShip":
//                    //"moveShip_"+ (newX+1) + "_" + (newY+1))
//                    x = Integer.parseInt(receivedInfo[1]);
//                    y = Integer.parseInt(receivedInfo[2]);
//                  ShipController.getInstance().moveShip(BattleSeaEditBoardPageController.getInstance().getCurrentBoard(),ship,x,y);
//                    break;
                case"getCurrentAccLoggedIn":
                    AccountController.getInstance().getCurrentAccLoggedIn();
                    break;
                case "logOut" :
                    AccountController.getInstance().logout();
                    break;

            }
        }catch (IOException | BombController.CoordinateAlreadyBombedException | ReversiController.PlayerHasAlreadyPlacedDiskException e){
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

