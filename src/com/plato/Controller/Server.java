package Controller;

import Controller.Menus.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class Server implements Runnable{
    private int port;
    private String host;

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        System.out.println("server started");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5056);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true) {
            Socket socket = null;

            try {

                socket = serverSocket.accept();

                System.out.println("A new client has connected : " + socket);


                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                DataOutputStream dataOutputStream =new DataOutputStream(socket.getOutputStream());

                // System.out.println("Assigning new thread for this client");

                Thread thread = new ClientHandler(socket, dataInputStream);

                thread.start();

            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("socket closed due to exception");
                e.printStackTrace();
            }
        }
    }
}


class ClientHandler extends Thread {

    final Socket socket;
    final DataInputStream dataInputStream;


    public ClientHandler(Socket socket, DataInputStream dataInputStream1) {
        this.socket = socket;
        this. dataInputStream= dataInputStream1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String received = this.dataInputStream.readUTF();
                System.out.println(received);
                String className = received.split("\\.")[0];
                String methodName = received.split("\\.")[1];
                switch (className){
                    case"AccountPage":
                        AccountPageController accountPageController = new AccountPageController();
                        if (methodName.equals("logout"))
                            accountPageController.logout();

                        else if (methodName.equals("uploadPfp"))
                            accountPageController.uploadPfp();

                        else if (methodName.equals("editPassword"))
                            accountPageController.editPassword();

                        else if (methodName.equals("displayPersonInfo"))
                            accountPageController.displayPersonalInfo();

                        else if (methodName.equals("changeDropDownMenuVisibility"))
                            accountPageController.changeDropDownMenuVisibility();

                        else if (methodName.equals("mouseIsOut"))
                            accountPageController.mouseIsOut();

                        else if (methodName.equals(("mouseIsOver")))
                            accountPageController.mouseIsOut();

                        else if (methodName.equals("openReversiMainMenu"))
                            accountPageController.openReversiMainMenu();

                        else if (methodName.equals("openBattlSeaMainMenu"))
                            accountPageController.openBattleSeaMainMenu();
                     break;

                    case "BattleSea5RandBoard":
                        BattleSea5RandBoardsController battleSea5RandBoardsController = new BattleSea5RandBoardsController();
                        if (methodName.equals("mouseIsOver"))
                            battleSea5RandBoardsController.mouseIsOut();

                        else  if (methodName.equals(("mouseIsOut")))
                            battleSea5RandBoardsController.mouseIsOut();

                        else if (methodName.equals("closeGen5RandBoard"))
                        battleSea5RandBoardsController.closeGen5RandBoard();

                         else if (methodName.equals("selectBoard"))
                            battleSea5RandBoardsController.selectBoard();
                         break;

                    case"AdminMsgsController":
                        AdminMsgsController adminMsgsController = new AdminMsgsController();
                        if (methodName.equals("closeStage")) adminMsgsController.closeStage();
                     break;


                    case "GameConclusionWindow":
                        GameConclusionWindowController gameConclusionWindowController = new GameConclusionWindowController();
                        if(methodName.equals("closeStage")) GameConclusionWindowController.closeStage();
                        else if(methodName.equals("mouseIsOver")) gameConclusionWindowController.mouseIsOver();
                        else if(methodName.equals("mouseIsOut")) gameConclusionWindowController.mouseIsOut();

                    case "GameLog":
                        GameLogController gameLogController = new GameLogController();
                        if(methodName.equals("closeStage")) gameLogController.closeStage();
                        else if(methodName.equals("mouseIsOver")) gameLogController.mouseIsOver();
                        else if(methodName.equals("mouseIsOut")) gameLogController.mouseIsOut();

                    case "GameMenu":
                        GameMenuController gameMenuController = new GameMenuController();
                        if(methodName.equals("newGame")) gameMenuController.newGame();
                        else if(methodName.equals("dontStartGame")) gameMenuController.dontStartGame();
                        else if(methodName.equals("startGame")) gameMenuController.startGame();
                        else if(methodName.equals("changeFaveStatus")) gameMenuController.changeFaveStatus();
                        else if(methodName.equals("closeGame")) gameMenuController.closeGame();
                        else if(methodName.equals("displayScoreboard")) gameMenuController.displayScoreboard();
                        else if(methodName.equals("setTime")) gameMenuController.setTime();
                        else if(methodName.equals("displayLogOfGame")) gameMenuController.displayLogOfGame();
                        else if(methodName.equals("mouseIsOver"))gameMenuController.mouseIsOver();
                        else if(methodName.equals("mouseIsOut")) gameMenuController.mouseIsOut();
                        break;
                    case "GameScoreboard":
                        GameScoreboardController gameScoreboardController = new GameScoreboardController();
                         if(methodName.equals("closeStage")) gameScoreboardController.closeStage();
                         else if(methodName.equals("mouseIsOver")) gameScoreboardController.mouseIsOver();
                         else if(methodName.equals("mouseIsOut")) gameScoreboardController.mouseIsOut();
                        break;
                    case "GamesMenu":
                    case "GamingHistoryTab":
                    case "LoginMenu":
                    case "MainMenu":
                    case "MessageTemplate":
                    case "RegisterForm":
                    case "RegisterMenu":
                    case "ReversiGame":
                    case "SendFriendRequestPage":
                    case "UserProfileForAdmin":
                    case "UsersTab":
                }
//                System.out.println("Client " + this.socket + " sends exit...");
//                System.out.println("Closing this connection.");
//                this.socket.close();
//                System.out.println("Connection closed");
            } catch (IOException exception) {
                exception.printStackTrace();
                continue;
            }

            try {

                this.dataInputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
    }
}

