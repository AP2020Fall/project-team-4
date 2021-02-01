package Controller;

import Controller.Menus.AccountPageController;
import Controller.Menus.GameConclusionWindowController;
import Controller.Menus.GameLogController;
import Controller.Menus.GameMenuController;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class Server {
    public static void run () throws IOException {
        System.out.println("server started");
        ServerSocket serverSocket = new ServerSocket(5056);


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
                socket.close();
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
                        if (methodName.equals("logout")) {
                            accountPageController.logout();
                            break;
                        }
                        else if (methodName.equals("uploadPfp")){
                            accountPageController.uploadPfp();
                            break;
                        }
                        else if (methodName.equals("editPassword")){
                            accountPageController.editPassword();
                            break;}
                        else if (methodName.equals("displayPersonInfo")){
                            accountPageController.displayPersonalInfo();
                            break;
                        }
                        else if (methodName.equals("changeDropDownMenuVisibility")){
                            accountPageController.changeDropDownMenuVisibility();
                            break;}
                        else if (methodName.equals("mouseIsOut")) {
                            accountPageController.mouseIsOut();
                            break;}
                        else if (methodName.equals(("mouseIsOver"))){
                            accountPageController.mouseIsOut();
                            break;}
                        else if (methodName.equals("openReversiMainMenu")){
                            accountPageController.openReversiMainMenu();
                            break;}
                        else if (methodName.equals("openBattlSeaMainMenu")){
                            accountPageController.openBattleSeaMainMenu();
                            break;
                        }

                    case "GameConclusionWindow":
                        GameConclusionWindowController obj = new GameConclusionWindowController();
                        if(methodName.equals("closeStage")) {
                            GameConclusionWindowController.closeStage();
                            break;}
                        else if(methodName.equals("mouseIsOver")){
                            obj.mouseIsOver();
                            break;}
                        else if(methodName.equals("mouseIsOut")){
                            obj.mouseIsOut();
                            break;
                        }
                    case "GameLog":
                        GameLogController gameLogController = new GameLogController();
                        if(methodName.equals("closeStage")){
                            gameLogController.closeStage();
                            break;
                        }
                        else if(methodName.equals("mouseIsOver")){
                            gameLogController.mouseIsOver();
                            break;
                        }
                        else if(methodName.equals("mouseIsOut")){
                            gameLogController.mouseIsOut();
                            break;
                        }
                    case "GameMenu":
                        GameMenuController gameMenuController = new GameMenuController();
                        if(methodName.equals("newGame")){
                            gameMenuController.newGame();
                            break;
                        }
                        else if(methodName.equals("dontStartGame")){
                            gameMenuController.dontStartGame();
                            break;
                        }
                        else if(methodName.equals("startGame")){
                            gameMenuController.startGame();
                            break;
                        }
                        else if(methodName.equals("changeFaveStatus")){
                            gameMenuController.changeFaveStatus();
                            break;
                        }
                        else if(methodName.equals("closeGame")){
                            gameMenuController.closeGame();
                            break;
                        }
                        else if(methodName.equals("displayScoreboard")){
                            gameMenuController.displayScoreboard();
                            break;
                        }
                        else if(methodName.equals("setTime")){
                            gameMenuController.setTime();
                            break;
                        }
                        else if(methodName.equals("displayLogOfGame")){
                            gameMenuController.displayLogOfGame();
                            break;
                        }
                        else if(methodName.equals("mouseIsOver")){
                            gameMenuController.mouseIsOver();
                            break;
                        }
                        else if(methodName.equals("mouseIsOut")){
                            gameMenuController.mouseIsOut();
                            break;
                        }
                        break;
                    case "GameScoreboard":
                        // if(){}
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

