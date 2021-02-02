package Controller;

import Controller.Menus.*;
import Model.GameRelated.Reversi.Reversi;
import View.Client;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class Server{
    private final int port;
    private static ServerSocket serverSocket;
    private static Socket socket = null;


    //constructor
    public Server(int port, String host) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException{
        System.out.println("Server : server started");
        serverSocket = new ServerSocket(5056);

        //running loop for getting client requests
        while (true) {
            System.out.println("Server : Waiting for client...");

            try {

                Client.main(args);

                socket = serverSocket.accept();

                System.out.println("Server : A new client has connected : " + socket);

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                DataOutputStream dataOutputStream =new DataOutputStream(socket.getOutputStream());

                System.out.println("Server : Assigning new thread for this client");

                Thread thread = new ClientHandler(socket, dataInputStream);

                thread.start();

            //    thread.run();

                MainController.startingMainController(args);

            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.out.println("Server : socket closed due to exception");
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
        System.out.println("Server : Waiting for client to send request");
        String received = null;
        try {
            received = this.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(received != null) {
            //receive from client
            System.out.println(received);
            String className = received.split("\\.")[0];
            String methodName = received.split("\\.")[1];
            switch (className){
                case"AccountPage":
                    AccountPageController accountPageController = new AccountPageController();
                    switch (methodName) {
                        case "logout" -> accountPageController.logout();
                        case "uploadPfp" -> accountPageController.uploadPfp();
                        case "editPassword" -> accountPageController.editPassword();
                        case "displayPersonInfo" -> accountPageController.displayPersonalInfo();
                        case "changeDropDownMenuVisibility" -> accountPageController.changeDropDownMenuVisibility();
                        case "mouseIsOut" -> accountPageController.mouseIsOut();
                        case ("mouseIsOver") -> accountPageController.mouseIsOut();
                        case "openReversiMainMenu" -> accountPageController.openReversiMainMenu();
                        case "openBattleSeaMainMenu" -> accountPageController.openBattleSeaMainMenu();
                    }
                 break;

                case "BattleSea5RandBoard":
                    BattleSea5RandBoardsController battleSea5RandBoardsController = new BattleSea5RandBoardsController();
                    switch (methodName) {
                        case "mouseIsOver" -> battleSea5RandBoardsController.mouseIsOut();
                        case ("mouseIsOut") -> battleSea5RandBoardsController.mouseIsOut();
                        case "closeGen5RandBoard" -> battleSea5RandBoardsController.closeGen5RandBoard();
                        case "selectBoard" -> battleSea5RandBoardsController.selectBoard();
                    }
                     break;

                case"AdminMsgs":
                    AdminMsgsController adminMsgsController = new AdminMsgsController();
                    if (methodName.equals("closeStage"))
                        adminMsgsController.closeStage();
                 break;

                case"BattleSeaEditBoardPage":

                    BattleSeaEditBoardPageController battleSeaEditBoardPageController = new BattleSeaEditBoardPageController();
                    switch (methodName) {
                        case "closeStage" -> battleSeaEditBoardPageController.closeStage();
                        case "doneEditing" -> battleSeaEditBoardPageController.doneEditing();
                        case "generate5RandBoard" -> battleSeaEditBoardPageController.generate5RandBoards();
                        case "generate1RandBoard" -> battleSeaEditBoardPageController.generate1RandBoard();
                        case "rotateShip" -> battleSeaEditBoardPageController.rotateShip();
                        case "mouseIsOver" -> battleSeaEditBoardPageController.mouseIsOver();
                        case "mouseIsOut" -> battleSeaEditBoardPageController.mouseIsOut();
                        case "moveShipIfPossible" -> battleSeaEditBoardPageController.moveShipIfPossible();
                    }

                    break;

                case "BattleSeaPlayPage":
                    BattleSeaPlayPageController battleSeaPlayPageController = new BattleSeaPlayPageController();

                    switch (methodName) {
                        case "closeGame" -> battleSeaPlayPageController.closeGame();
                        case "mouseIsOver" -> battleSeaPlayPageController.mouseIsOver();
                        case "mouseIsOut" -> battleSeaPlayPageController.mouseIsOver();
                    }

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

                    switch (methodName) {
                        case "closeStage" -> displayPersonalAccInfoController.closeStage();
                        case "editFirstName" -> displayPersonalAccInfoController.editFirstName();
                        case "editLastName" -> displayPersonalAccInfoController.editLastName();
                        case "editUsername" -> displayPersonalAccInfoController.editUsername();
                        case "editMail" -> displayPersonalAccInfoController.editEmail();
                        case "editPhonNum" -> displayPersonalAccInfoController.editPhoneNum();
                        case "confirmAllEdits" -> displayPersonalAccInfoController.confirmAllEdits();
                        case "uploadPfp" -> displayPersonalAccInfoController.uploadPfp();
                    }

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
                    switch (methodName) {
                        case "uploadImg" -> eventCreateOrEditPageController.uploadImg();
                        case "editTitle" -> eventCreateOrEditPageController.editTitle();
                        case "editGame" -> eventCreateOrEditPageController.editGame();
                        case "editStartDate" -> eventCreateOrEditPageController.editStartDate();
                        case "editEndDate" -> eventCreateOrEditPageController.editEndDate();
                        case "editCoin" -> eventCreateOrEditPageController.editCoins();
                        case "editDetails" -> eventCreateOrEditPageController.editDetails();
                        case "removeEvent" -> eventCreateOrEditPageController.removeEvent();
                        case "revertEdit" -> eventCreateOrEditPageController.revertEdits();
                        case "confirmEdits" -> eventCreateOrEditPageController.confirmEdits();
                        case "closeStage" -> eventCreateOrEditPageController.closeStage();
                        case "createEvent" -> eventCreateOrEditPageController.createEvent();
                        case "openEventSettings" -> eventCreateOrEditPageController.openEventSettings();
                    }

                    break;

                case "EventSettings":
                    EventSettingsController eventSettingsController = new EventSettingsController();
                    switch (methodName) {
                        case "isLoginTime" -> eventSettingsController.isLoginTimes();
                        case "resetPage" -> eventSettingsController.resetPage();
                        case "closeStage" -> eventSettingsController.closeStage();
                        case "isMVPInGame" -> eventSettingsController.isMVPInGame();
                        case "isPlayTime" -> eventSettingsController.isPlayTimes();
                        case "isWinTime" -> eventSettingsController.isWinTimes();
                    }

                    break;

                case"FriendsTab":
                    FriendsTabController friendsTabController = new FriendsTabController();

                    if (methodName.equals("displayFrndRequests"))
                        friendsTabController.displayFrndRequests();

                    else if (methodName.equals("updateFrndsList"))
                        friendsTabController.updateFrndsList();

                    break;

                case "EventsTabController":
                    EventsTabController eventsTabController = new EventsTabController();

                    if (methodName.equals("mouseIsOut"))
                        eventsTabController.mouseIsOut();

                    else if (methodName.equals("mouseIsOver"))
                        eventsTabController.mouseIsOver();

                    break;

                case "FriendRequestManagementPage":
                    FriendRequestManagementPageController friendRequestManagementPageController = new FriendRequestManagementPageController();

                    switch (methodName) {
                        case "sendFriendReq" -> friendRequestManagementPageController.sendFriendReq();
                        case "closeStage" -> friendRequestManagementPageController.closeStage();
                        case "closeFriendReqSendingWindow" -> friendRequestManagementPageController.closeFriendReqSendingWindow();
                        case "mouseIsOver" -> friendRequestManagementPageController.mouseIsOver();
                        case "mouseIsOut" -> friendRequestManagementPageController.mouseIsOut();
                    }

                    break;


                case "GameConclusionWindow":
                    GameConclusionWindowController gameConclusionWindowController = new GameConclusionWindowController();
                    switch (methodName) {
                        case "closeStage" -> gameConclusionWindowController.closeStage();
                        case "mouseIsOver" -> gameConclusionWindowController.mouseIsOver();
                        case "mouseIsOut" -> gameConclusionWindowController.mouseIsOut();
                    }

                case "GameLog":
                    GameLogController gameLogController = new GameLogController();
                    switch (methodName) {
                        case "closeStage" -> gameLogController.closeStage();
                        case "mouseIsOver" -> gameLogController.mouseIsOver();
                        case "mouseIsOut" -> gameLogController.mouseIsOut();
                    }

                case "GameMenu":
                    GameMenuController gameMenuController = new GameMenuController();
                    switch (methodName) {
                        case "newGame" -> gameMenuController.newGame();
                        case "dontStartGame" -> gameMenuController.dontStartGame();
                        case "startGame" -> gameMenuController.startGame();
                        case "changeFaveStatus" -> gameMenuController.changeFaveStatus();
                        case "closeGame" -> gameMenuController.closeGame();
                        case "displayScoreboard" -> gameMenuController.displayScoreboard();
                        case "setTime" -> gameMenuController.setTime();
                        case "displayLogOfGame" -> gameMenuController.displayLogOfGame();
                        case "mouseIsOver" -> gameMenuController.mouseIsOver();
                        case "mouseIsOut" -> gameMenuController.mouseIsOut();
                    }
                    break;
                case "GameScoreboard":
                    GameScoreboardController gameScoreboardController = new GameScoreboardController();
                    switch (methodName) {
                        case "closeStage" -> gameScoreboardController.closeStage();
                        case "mouseIsOver" -> gameScoreboardController.mouseIsOver();
                        case "mouseIsOut" -> gameScoreboardController.mouseIsOut();
                    }
                    break;
                case "GamesMenu":
                    GamesMenuController gamesMenuController = new GamesMenuController();
                    switch (methodName) {
                        case "closeStage" -> gamesMenuController.closeStage();
                        case "mouseIsOver" -> gamesMenuController.mouseIsOver();
                        case "mouseIsOut" -> gamesMenuController.mouseIsOut();
                        case "battleSeaMainMenu" -> gamesMenuController.battleSeaMainMenu();
                        case "reversiMainMenu" -> gamesMenuController.reversiMainMenu();
                    }
                    break;
                case "GamingHistoryTab":
                    GamingHistoryTabController gamingHistoryTabController = new GamingHistoryTabController();
                    if(methodName.equals("updateListOfGames")) gamingHistoryTabController.updateListOfGames();
                    break;
                case "LoginMenu":
                    LoginMenuController loginMenuController = new LoginMenuController();
                    switch (methodName) {
                        case "login" -> loginMenuController.login();
                        case "signUp" -> loginMenuController.signUp();
                        case "deleteAccount" -> loginMenuController.deleteAccount();
                    }
                case "MainMenu":
                    MainMenuController mainMenuController = new MainMenuController();
                    switch (methodName) {
                        case "eventsTab" -> mainMenuController.eventsTab();
                        case "usersTab" -> mainMenuController.usersTab();
                        case "accountPage" -> mainMenuController.accountPage();
                        case "gamingHistoryPage" -> mainMenuController.gamingHistoryPage();
                        case "gamesTab" -> mainMenuController.gamesTab();
                        case "faveGamesTab" -> mainMenuController.faveGamesTab();
                        case "friendsPage" -> mainMenuController.friendsPage();
                        case "messagesTab" -> mainMenuController.messagesTab();
                        case "mouseIsOut" -> mainMenuController.mouseIsOut();
                        case "mouseIsOver" -> mainMenuController.mouseIsOver();
                    }
                    break;
                case "RegisterForm":
                    RegisterFormController registerFormController = new RegisterFormController();
                    switch (methodName) {
                        case "signUp" -> registerFormController.signUp();
                        case "uploadPfp" -> registerFormController.uploadPfp();
                        case "closeStage" -> registerFormController.closeStage();
                        case "mouseIsOver" -> registerFormController.mouseIsOver();
                        case "mouseIsOut" -> registerFormController.mouseIsOut();
                    }
                    break;
                case "RegisterMenu":
                    RegisterMenuController registerMenuController = new RegisterMenuController();
                    registerMenuController.signUp();
                    break;
                case "ReversiGame":
                    ReversiGameController reversiGameController = new ReversiGameController();
                    switch (methodName) {
                        case "putMarkIfPossible" -> reversiGameController.putMarkIfPossible();
                        case "confirmMove" -> reversiGameController.confirmMove();
                        case "showMoves" -> reversiGameController.showMoves();
                        case "mouseIsOut" -> reversiGameController.mouseIsOut();
                        case "mouseIsOver" -> reversiGameController.mouseIsOver();
                        case "closeGame" -> reversiGameController.closeGame();
                        case "closeMoveHistory" -> reversiGameController.closeMoveHistory();
                    }
                    break;
                case "UserProfileForAdmin":
                    UserProfileForAdminController userProfileForAdminController = new UserProfileForAdminController();
                    switch (methodName) {
                        case "sendMsg" -> userProfileForAdminController.sendMsg();
                        case "sendMessageDone" -> userProfileForAdminController.sendMessageDone();
                        case "cancelSending" -> userProfileForAdminController.cancelSendingMsg();
                        case "mouseIsOver" -> userProfileForAdminController.mouseIsOver();
                        case "mouseIsOut" -> userProfileForAdminController.mouseIsOut();
                    }
                    break;
            }
//                System.out.println("Client " + this.socket + " sends exit...");
//                System.out.println("Closing this connection.");
//                this.socket.close();
//                System.out.println("Connection closed");

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

