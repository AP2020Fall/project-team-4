package Controller;

import Controller.AccountRelated.*;
import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.BattleSea.ShipController;
import Controller.GameRelated.GameController;
import Controller.GameRelated.Reversi.ReversiController;
import Controller.Menus.*;
import Model.AccountRelated.Account;
import Model.AccountRelated.Event;
import Model.GameRelated.BattleSea.Ship;
import Model.GameRelated.Game;
import View.Client;
import View.GameRelated.BattleSea.BattleSeaView;
import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
                    Account account = AccountController.getInstance().getCurrentAccLoggedIn();
                    dataOutputStream.writeUTF(new Gson().toJson(account));
                    dataOutputStream.flush();
                    break;

                case "logOut" :
                    AccountController.getInstance().logout();
                    break;

                case "displayAvaiableCoords" :
                    ReversiController.getInstance().displayAvailableCoords();
                    break;

                case "placeDisk":
                    x = Integer.parseInt(receivedInfo[1]);
                    y = Integer.parseInt(receivedInfo[2]);
                    ReversiController.getInstance().placeDisk(x,y);
                    break;

                case "moveShip":

                case "ReversinextTurn" :
                    ReversiController.getInstance().nextTurn();
                    break;
                case "ReversiDisplayPrevMoves" :
                    ReversiController.getInstance().displayPrevMoves();
                    break;
                case "editAccField" :
                    AccountController.getInstance().editAccField (receivedInfo[1],receivedInfo[2]);
                    break;
                case "displayLogOfGame" :
                    Controller.GameRelated.GameLogController.getInstance().displayLogOfGame(receivedInfo[1]);
                    break;

                case "changePWCommand" :
                    AccountController.getInstance().changePWCommand(receivedInfo[1],receivedInfo[2]);
                    break;
                case "runGame" :
                    GameController.getInstance().runGame(receivedInfo[1] , receivedInfo[2]);
                    break;
                case "getAccount":
                    account = Account.getAccount(receivedInfo[1]);
                    dataOutputStream.writeUTF(new Gson().toJson(account));
                    dataOutputStream.flush();
                    break;
                case "login" :
                    AccountController.getInstance().login(receivedInfo[1] , receivedInfo[2] , receivedInfo[3].equals("true"));
                    break;
                case "register" :
//                    public void register (Image pfp, String username, String password, String firstName, String lastName, String email, String phoneNum, double initMoney)
                    Image pfp = null;
                    double initMoney = Double.parseDouble(receivedInfo[8]);
                    if(receivedInfo[1].equals("null")){pfp=null;}
                    AccountController.getInstance().register(pfp,receivedInfo[2],receivedInfo[3],receivedInfo[4],receivedInfo[5],receivedInfo[6],receivedInfo[7],initMoney);
                    break;
                case "sendMsg" :
                    MessageController.getInstance().sendMsg(UserProfileForAdminController.getGamer(), receivedInfo[1]);
                    break;
                case "removeFriend" :
                 GamerController.getInstance().removeFriend(receivedInfo[1]);

                    break;
                case "participateInEvent" :
                    EventController.getInstance().participateInEvent(receivedInfo[1]);
                    break;

                case "stopParticipateInEvent" :
                    EventController.getInstance().stopParticipatingInEvent(receivedInfo[1]);
                    break;
                case "getCurrentGameInSession":
                    Game game = GameController.getInstance().getCurrentGameInSession();
                    dataOutputStream.writeUTF(new Gson().toJson(game));
                    dataOutputStream.flush();
                    break;
                case "deleteAccount":
                    AccountController.getInstance().deleteAccount(receivedInfo[1], receivedInfo[2]);
                    break;
                case "removeEvent":
                    Event.removeEvent(receivedInfo[1]);
                    break;
                case "editEvent":
                    EventController.getInstance().editEvent(EventCreateOrEditPageController.getEvent(), receivedInfo[1], receivedInfo[2]);
                    break;
                case "createEvent_" :
                    //String title, String gameName, String picUrl, LocalDate start, LocalDate end, double eventPrize, String details
                    double prize = Double.parseDouble(receivedInfo[4]);
                    EventController.getInstance().createEvent(receivedInfo[1],receivedInfo[2],receivedInfo[3],EventCreateOrEditPageController.getEvent().getStart(), EventCreateOrEditPageController.getEvent().getEnd(),prize,receivedInfo[5]);
                    break;
                case "sendFrndRequest":
                    FriendRequestController.getInstance().sendFrndRequest(receivedInfo[1]);
                    break;
                case "declineFriendReq" :
                    FriendRequestController.getInstance().declineFriendReq(receivedInfo[1]);
                    break;
                case "acceptFriendReq" :
                    FriendRequestController.getInstance().acceptFriendReq(receivedInfo[1]);
                    break;
            }
        }catch (IOException | BombController.CoordinateAlreadyBombedException | ReversiController.PlayerHasAlreadyPlacedDiskException | EventController.GameNameCantBeEmptyException e){
            System.out.println("connection closed!");
            e.printStackTrace();
        } catch (MainController.InvalidFormatException e) {
            e.printStackTrace();
        } catch (AccountController.AccountWithUsernameAlreadyExistsException e) {
            e.printStackTrace();

        } catch (AccountController.PaswordIncorrectException e) {
            e.printStackTrace();
        } catch (AccountController.NoAccountExistsWithUsernameException e) {
            e.printStackTrace();
        } catch (GameController.CantPlayWithYourselfException e) {
            e.printStackTrace();
        } catch (GameController.CantPlayWithAdminException e) {
            e.printStackTrace();
        } catch (MessageController.EmptyMessageException e) {
            e.printStackTrace();
        } catch (AccountController.AdminAccountCantBeDeletedException e) {
            e.printStackTrace();
        } catch (EventController.StartDateTimeIsAfterEndException e) {
            e.printStackTrace();
        } catch (EventController.EndDateTimeHasAlreadyPassedException e) {
            e.printStackTrace();
        } catch (EventController.StartDateTimeHasAlreadyPassedException e) {
            e.printStackTrace();
        } catch (EventController.CantEditInSessionEventException e) {
            e.printStackTrace();
        }
    }

        private void clientDisconnected() throws IOException{
        socket.close();
        System.out.println("connection closed!");
        }
 //   }
}

