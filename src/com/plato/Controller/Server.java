package Controller;

import Controller.Menus.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class Server{
    private int port;
    private String host;
    private static ServerSocket serverSocket;

    //constructor
    public Server(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public static void main(String[] args) throws IOException{
        System.out.println("server started");
        serverSocket = new ServerSocket(5056);

        //running loop for getting client requests
        while (true) {
            System.out.println("Waiting for client...");
            Socket socket = null;

            try {

                socket = serverSocket.accept();

                System.out.println("A new client has connected : " + socket);

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                DataOutputStream dataOutputStream =new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                Thread thread = new ClientHandler(socket, dataInputStream);

                thread.start();

            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.out.println("socket closed due to exception");
                    e.printStackTrace();
                }
            }
        }
    }
}


class ClientHandler extends Thread {

    final Socket socket;
    final DataInputStream dataInputStream;

    //constructor
    public ClientHandler(Socket socket, DataInputStream dataInputStream1) {
        this.socket = socket;
        this. dataInputStream= dataInputStream1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                //receive from client
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

                    case"AdminMsgs":
                        AdminMsgsController adminMsgsController = new AdminMsgsController();
                        if (methodName.equals("closeStage"))
                            adminMsgsController.closeStage();
                     break;

                    case"BattleSeaEditBoardPage":

                        BattleSeaEditBoardPageController battleSeaEditBoardPageController = new BattleSeaEditBoardPageController();
                        if (methodName.equals("closeStage"))
                            battleSeaEditBoardPageController.closeStage();

                        else if (methodName.equals("doneEditing"))
                            battleSeaEditBoardPageController.doneEditing();

                        else if (methodName.equals("generate5RandBoard"))
                            battleSeaEditBoardPageController.generate5RandBoards();

                        else if (methodName.equals("generate1RandBoard"))
                            battleSeaEditBoardPageController.generate1RandBoard();

                        else if (methodName.equals("rotateShip"))
                            battleSeaEditBoardPageController.rotateShip();

                        else if (methodName.equals("mouseIsOver"))
                            battleSeaEditBoardPageController.mouseIsOver();

                        else if (methodName.equals("mouseIsOut"))
                            battleSeaEditBoardPageController.mouseIsOut();

                        else if (methodName.equals("moveShipIfPossible"))
                            battleSeaEditBoardPageController.moveShipIfPossible();

                        break;

                    case "BattleSeaPlayPage":
                        BattleSeaPlayPageController battleSeaPlayPageController = new BattleSeaPlayPageController();

                        if(methodName.equals("closeGame"))
                            battleSeaPlayPageController.closeGame();

                        else if (methodName.equals("mouseIsOver"))
                            battleSeaPlayPageController.mouseIsOver();

                        else if (methodName.equals("mouseIsOut"))
                            battleSeaPlayPageController.mouseIsOver();

                        break;

                    case "DeleteAccount":
                        DeleteAccountController deleteAccountController = new DeleteAccountController();
                        if (methodName.equals("closeStage"))
                            deleteAccountController.closeStage();

                        else if (methodName.equals("removeAccount"))
                            deleteAccountController.removeAccount();

                        break;

                    case "DisplayPersonalInfo":
                        DisplayPersonalAccInfoController displayPersonalAccInfoController = new DisplayPersonalAccInfoController();

                        if(methodName.equals("closeStage"))
                            displayPersonalAccInfoController.closeStage();

                        else if (methodName.equals("editFirstName"))
                            displayPersonalAccInfoController.editFirstName();

                        else if( methodName.equals("editLastName"))
                            displayPersonalAccInfoController.editLastName();

                        else if (methodName.equals("editUsername"))
                            displayPersonalAccInfoController.editUsername();

                        else if (methodName.equals("editMail"))
                            displayPersonalAccInfoController.editEmail();

                        else if (methodName.equals("editPhonNum"))
                            displayPersonalAccInfoController.editPhoneNum();

                        else if (methodName.equals("confirmAllEdits"))
                            displayPersonalAccInfoController.confirmAllEdits();

                        else if (methodName.equals("uploadPfp"))
                            displayPersonalAccInfoController.uploadPfp();

                        break;

                    case "EditPW":
                        EditPWController editPWController = new EditPWController();
                        if(methodName.equals("confirmPasswordEdit"))
                            editPWController.confirmPasswordEdit();

                        else if (methodName.equals("closeStage"))
                            editPWController.closeStage();

                        break;

                    case "EventCreateOrEditPage":
                        EventCreateOrEditPageController eventCreateOrEditPageController = new EventCreateOrEditPageController();
                        if (methodName.equals("uploadImg"))
                            eventCreateOrEditPageController.uploadImg();

                        else if (methodName.equals("editTitle"))
                            eventCreateOrEditPageController.editTitle();

                        else if (methodName.equals("editGame"))
                            eventCreateOrEditPageController.editGame();

                        else if (methodName.equals("editStartDate"))
                            eventCreateOrEditPageController.editStartDate();

                        else if (methodName.equals("editEndDate"))
                            eventCreateOrEditPageController.editEndDate();

                        else if (methodName.equals("editCoin"))
                            eventCreateOrEditPageController.editCoins();

                        else if (methodName.equals("editDetails"))
                            eventCreateOrEditPageController.editDetails();

                        else if (methodName.equals("removeEvent"))
                            eventCreateOrEditPageController.removeEvent();

                        else if (methodName.equals("revertEdit"))
                            eventCreateOrEditPageController.revertEdits();

                        else if (methodName.equals("confirmEdits"))
                            eventCreateOrEditPageController.confirmEdits();

                        else if(methodName.equals("closeStage"))
                            eventCreateOrEditPageController.closeStage();

                        else if (methodName.equals("createEvent"))
                            eventCreateOrEditPageController.createEvent();

                        else if (methodName.equals("openEventSettings"))
                            eventCreateOrEditPageController.openEventSettings();

                        break;

                    case "EventSettings":
                        EventSettingsController eventSettingsController = new EventSettingsController();
                        if (methodName.equals("isLoginTime"))
                            eventSettingsController.isLoginTimes();

                        else if (methodName.equals("resetPage"))
                            eventSettingsController.resetPage();

                        else if (methodName.equals("closeStage"))
                            eventSettingsController.closeStage();

                        else if (methodName.equals("isMVPInGame"))
                            eventSettingsController.isMVPInGame();

                        else if (methodName.equals("isPlayTime"))
                            eventSettingsController.isPlayTimes();

                        else if (methodName.equals("isWinTime"))
                            eventSettingsController.isWinTimes();

                        break;

                    case"FriendsTab":
                        FriendsTabController friendsTabController = new FriendsTabController();

                        if (methodName.equals("displayFrndReguests"))
                            friendsTabController.displayFrndRequests();

                        else if (methodName.equals("updateFrndsList"))
                            friendsTabController.updateFrndsList();

                        break;


                    case "GameConclusionWindow":
                        GameConclusionWindowController gameConclusionWindowController = new GameConclusionWindowController();
                        if(methodName.equals("closeStage")) gameConclusionWindowController.closeStage();
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

