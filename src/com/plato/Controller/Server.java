package Controller;

import Controller.AccountRelated.*;
import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.GameController;
import Controller.GameRelated.GameLogController;
import Controller.GameRelated.Reversi.ReversiController;
import Controller.Menus.BattleSeaEditBoardPageController;
import Controller.Menus.EventCreateOrEditPageController;
import Controller.Menus.UserProfileForAdminController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import View.GameRelated.BattleSea.BattleSeaView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;

import static Controller.MyGson.*;
import static Controller.Server.generateTokenForUser;
import static Model.AccountRelated.Account.addAccount;
import static Model.AccountRelated.Gamer.getGamers;
import static java.lang.Boolean.parseBoolean;


public class Server {
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static SecureRandom secureRandom = new SecureRandom();
	private static Base64.Encoder base64Encoder = Base64.getUrlEncoder();

	public static Socket getSocket () {
		return socket;
	}

	public static void main (String[] args) throws IOException {
//		MainController.getInstance().deserializeEverything();
		System.out.println("Server : server started");
		serverSocket = new ServerSocket(Client.port);
		while (true) {
			System.out.println("Server : Waiting for client...");
			socket = serverSocket.accept();
			System.out.println("Server : a client has connected");
			ClientHandler clientHandler = new ClientHandler(socket);
			clientHandler.start();
		}
	}

	public static ServerSocket getServerSocket () {
		return serverSocket;
	}

	public static String generateTokenForUser (String username) {
		while (true) {
			byte[] randomBytes = new byte[24];
			secureRandom.nextBytes(randomBytes);
			String token = base64Encoder.encodeToString(randomBytes);
			token = token.replaceAll("_", "-");
			return token;
		}
	}
}


class ClientHandler extends Thread {
	private static LinkedList<ClientHandler> clients = new LinkedList<>();
	private final Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private Account account;
	private String token;


	//constructor
	public ClientHandler (Socket socket) throws IOException {
		this.socket = socket;
		this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		clients.addLast(this);
	}

	public static LinkedList<ClientHandler> getClients () {
		return clients;
	}

	public static ClientHandler getClientHandler (String token) {
		return clients.stream()
				.filter(clientHandler -> clientHandler.token != null && clientHandler.token.equals(token))
				.findAny().get();
	}

	public static boolean clientHandlerExists (String token) {
		return clients.stream().anyMatch(clientHandler -> clientHandler.token != null && clientHandler.token.equals(token));
	}

	public Account getAccount () {
		return account;
	}

	public String getToken () {
		return token;
	}

	public Socket getSocket () {
		return socket;
	}

	public DataOutputStream getDataOutputStream () {
		return dataOutputStream;
	}

	public DataInputStream getDataInputStream () {
		return dataInputStream;
	}

	@Override
	public void run () {
		while (true) {
			try {
				if (dataInputStream.available() == 0) continue;
				System.out.println("Server : Waiting for client to send request");
				//receive from client
				String received = dataInputStream.readUTF();
				String[] receivedInfo = received.split("_");
				System.out.println("receivedInfo = " + Arrays.toString(receivedInfo));
				switch (receivedInfo[0]) {
					case "getAllGamers":
						dataOutputStream.writeUTF(getGson().toJson(getGamers()));
						dataOutputStream.flush();
						break;
					case "getAllReversiGames":
						dataOutputStream.writeUTF(getGson().toJson(getReversiGames()));
						dataOutputStream.flush();
						break;
					case "getMsgs" :
						dataOutputStream.writeUTF(getGson().toJson(getMsgs()));
						dataOutputStream.flush();
						break;
					case "getAllBattleSeaGames":
						dataOutputStream.writeUTF(getGson().toJson(getBattleseaGames()));
						dataOutputStream.flush();
						break;
					case "getAllFriendRequests" :
						dataOutputStream.writeUTF(getGson().toJson(getFrndReqs()));
						dataOutputStream.flush();
						break;
					case "getAdmin":
						dataOutputStream.writeUTF(getGson().toJson(getAdmin()));
						dataOutputStream.flush();
						break;
					case "adminHasBeenCreated":
						dataOutputStream.writeUTF(String.valueOf(getAdmin() != null));
						dataOutputStream.flush();
						break;
					case "canThrowBomb":
						int x = Integer.parseInt(receivedInfo[1]);
						int y = Integer.parseInt(receivedInfo[2]);
						if (BombController.getInstance().canThrowBomb(x, y))
							dataOutputStream.writeUTF("true");
						else dataOutputStream.writeUTF("false");
						break;
					case "throwBomb":
						x = Integer.parseInt(receivedInfo[1]);
						y = Integer.parseInt(receivedInfo[2]);
						BombController.getInstance().throwBomb(x, y);
						break;

					case "displayBoard":
						BattleSeaView.getInstance().displayBoard(BattleSeaController.getInstance().getBoardAsStringBuilder(BattleSeaEditBoardPageController.getInstance().getCurrentBoard()));
						break;

//                case"moveShip":
//                    //"moveShip_"+ (newX+1) + "_" + (newY+1))
//                    x = Integer.parseInt(receivedInfo[1]);
//                    y = Integer.parseInt(receivedInfo[2]);
//                  ShipController.getClient().moveShip(BattleSeaEditBoardPageController.getClient().getCurrentBoard(),ship,x,y);
//                    break;
					case "getCurrentAccLoggedIn":
						String token = receivedInfo[1];
						Account account = getClientHandler(token).account;
						dataOutputStream.writeUTF(String.valueOf(account instanceof Gamer));
						dataOutputStream.flush();
						dataOutputStream.writeUTF(getGson().toJson(account));
						dataOutputStream.flush();
						break;
					case "isAccLoggedIn":
						dataOutputStream.writeUTF(String.valueOf(clientHandlerExists(receivedInfo[1])));
						dataOutputStream.flush();
						break;

					case "logout":
						getClientHandler(receivedInfo[1]).account = null;
						getClientHandler(receivedInfo[1]).token = null;
						break;

					case "displayAvaiableCoords":
						ReversiController.getInstance().displayAvailableCoords();
						break;

					case "placeDisk":
						x = Integer.parseInt(receivedInfo[1]);
						y = Integer.parseInt(receivedInfo[2]);
						ReversiController.getInstance().placeDisk(x, y);
						break;

					//case "moveShip":
					case "ReversinextTurn":
						ReversiController.getInstance().nextTurn();
						break;
					case "ReversiDisplayPrevMoves":
						ReversiController.getInstance().displayPrevMoves();
						break;
					case "editAccField":
						AccountController.getInstance().editAccField(receivedInfo[1], receivedInfo[2]);
						break;
					case "displayLogOfGame":
						GameLogController.getInstance().displayLogOfGame(receivedInfo[1]);
						break;
					case "changePWCommand":
						AccountController.getInstance().changePWCommand(receivedInfo[1], receivedInfo[2]);
						break;
					case "runGame":
						GameController.getInstance().runGame(receivedInfo[1], receivedInfo[2]);
						break;
					case "getAccount":
						account = Account.getAccount(receivedInfo[1]);
						dataOutputStream.writeUTF(getGson().toJson(account));
						dataOutputStream.flush();
						break;
					case "login":
						boolean gamerOrAdmin = parseBoolean(receivedInfo[1]);
						this.account = getGson().fromJson(receivedInfo[2], gamerOrAdmin ? Gamer.class : Admin.class);
						this.token = generateTokenForUser(this.account.getUsername());
						dataOutputStream.writeUTF(this.token);
						dataOutputStream.flush();
						break;
					case "registerAdmin":
						double initMoney = Double.parseDouble(receivedInfo[8]);
//						(Class accType, String pfp, String firstName, String lastName, String username, String password, String email, String phoneNum, double money)
						Admin admin = (Admin) addAccount(Admin.class, "https://i.imgur.com/IIyNCG4.png", receivedInfo[2], receivedInfo[3], receivedInfo[4], receivedInfo[5], receivedInfo[6], receivedInfo[7], initMoney);
						serializeAdmin(admin);
						break;
					case "registerGamer":
						initMoney = Double.parseDouble(receivedInfo[8]);
						LinkedList<Gamer> gamers = new LinkedList<>();
//						(Class accType, String pfp, String firstName, String lastName, String username, String password, String email, String phoneNum, double money)
						gamers.add((Gamer) addAccount(Gamer.class, receivedInfo[1], receivedInfo[2], receivedInfo[3], receivedInfo[4], receivedInfo[5], receivedInfo[6], receivedInfo[7], initMoney));
						serializeGamers(gamers);
						break;
					case "sendMsg":
						MessageController.getInstance().sendMsg(UserProfileForAdminController.getGamer(), receivedInfo[1]);
						break;
					case "removeFriend":
						GamerController.getInstance().removeFriend(receivedInfo[1]);
						break;
					case "participateInEvent":
						EventController.getInstance().participateInEvent(receivedInfo[1]);
						break;

					case "stopParticipateInEvent":
						EventController.getInstance().stopParticipatingInEvent(receivedInfo[1]);
						break;
					case "getCurrentGameInSession":
						Game game = GameController.getInstance().getCurrentGameInSession();
						dataOutputStream.writeUTF(getGson().toJson(game));
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
					case "createEvent":
						//String title, String gameName, String picUrl, LocalDate start, LocalDate end, double eventPrize, String details
						double prize = Double.parseDouble(receivedInfo[4]);
						EventController.getInstance().createEvent(receivedInfo[1], receivedInfo[2], receivedInfo[3], EventCreateOrEditPageController.getEvent().getStart(), EventCreateOrEditPageController.getEvent().getEnd(), prize, receivedInfo[5]);
						break;
					case "sendFrndRequest":
						FriendRequestController.getInstance().sendFrndRequest(receivedInfo[1]);
						break;
					case "declineFriendReq":
						FriendRequestController.getInstance().declineFriendReq(receivedInfo[1]);
						break;
					case "acceptFriendReq":
						FriendRequestController.getInstance().acceptFriendReq(receivedInfo[1]);
						break;
					case "getFrnds":
						Gamer gamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
						dataOutputStream.writeUTF(getGson().toJson(gamer.getFrnds()));
						dataOutputStream.flush();
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + receivedInfo[0]);
				}
			} catch (IOException e) {
				try {
					clientDisconnected();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			} catch
			(BombController.CoordinateAlreadyBombedException | ReversiController.PlayerHasAlreadyPlacedDiskException | EventController.GameNameCantBeEmptyException | MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException | AccountController.PaswordIncorrectException | AccountController.NoAccountExistsWithUsernameException | GameController.CantPlayWithYourselfException | GameController.CantPlayWithAdminException | MessageController.EmptyMessageException | AccountController.AdminAccountCantBeDeletedException | EventController.StartDateTimeIsAfterEndException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException | EventController.CantEditInSessionEventException
							e) {
			}
		}
	}

	private void clientDisconnected () throws IOException {
		socket.close();
		System.out.println("connection closed!");
		MainController.getInstance().saveEverything();
	}
}

