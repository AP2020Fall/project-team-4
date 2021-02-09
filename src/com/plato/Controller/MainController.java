package Controller;

import Controller.AccountRelated.AccountController;
import Controller.Menus.LoginMenuController;
import Controller.Menus.MainMenuController;
import Controller.Menus.RegisterMenuController;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class MainController extends Application {
	public static Socket clientSocket;
	private static MainController mainController;
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	public Stage primaryStage;

	public static void main (String[] args) {
		launch(args);
	}

	public static MainController getInstance () {
		if (mainController == null)
			mainController = new MainController();
		return mainController;
	}

//	public static void write (String message) {
//		try {
//			InetAddress ip = InetAddress.getByName("localhost");
//			Socket socket = new Socket(ip, Client.port);
//			clientSocket = new Socket(ip, Client.port);
//			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//			System.out.println(dataInputStream.readUTF());
//			dataOutputStream.writeUTF(message);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

	public static Image getImageFromFile (String address) {
		try {
			return new Image(String.valueOf(new File(address).toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static URL getImageUrlFromFile (String address) {
		try {
			return new File(address).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void openUploadPfpWindow (Stage parentStage, ImageView imageViewToUpdate) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"));
		try {
			File image = fileChooser.showOpenDialog(parentStage);
			Path from = Paths.get(image.toURI()),
					to = Paths.get("src/com/Resources/ProfilePics/" + image.getName());
			Files.copy(from, to);
			imageViewToUpdate.setImage(new Image(String.valueOf(image.toURI().toURL())));
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}
//
//	private void dealWithInput (int command) {
//	LinkedList<String> menuOpts = new LinkedList<>();
//		//menuOpts = Menu.getMenuIn().getOptions();
//
//		command--; // to use for accessing menuOpts indexes
//	String commandOption = menuOpts.get(command).trim();
//
//switch (commandOption) {
//			case "Exit program" -> saveEverything();
//		case "Back" -> {
//			//Menu.getMenuIn().back();
//
//
//			}
//			case "Go to Account Menu", "Go to Games Menu", "Go to Friends Menu",
//					"Open Reversi Game Menu", "Open BattleSea Game Menu" -> {
//				enterAppropriateMenu();
//
//			}
//
//			// register-login menu
//		case "Register Gamer", "Register Admin" -> AccountController.getClient().register();
//		case "Login" -> AccountController.getClient().login();
//			case "Delete Account" -> AccountController.getClient().deleteAccount();
//
//			// main menu
//			case "Show Points" -> {
//				if (Menu.getMenuIn() instanceof _3MainMenu)
//
//					GameLogController.getClient().displayAllPointsOfPlayer();
//	if (Menu.getMenuIn() instanceof _11GameMenu)
//					GameLogController.getClient().displayPtsLoggedInPlayerEarnedFromGame();
//			}
//			case "View Favorite games" -> GamerController.getClient().displayFaveGamesForGamer();
//			case "View Platobot’s messages" -> MessageController.getClient().displayAdminMessages();case "View last played" -> GameLogController.getClient().displayLastGamePlayed();
//			case "View admin’s suggestions" -> AdminGameRecoController.getClient().displayAdminsRecosToPlayer();
//			case "View Events" -> {
//				EventController.getClient().displayInSessionEvents();
//				enterAppropriateMenu();
//		}
//		case "Add Event" -> EventController.getClient().createEvent();
//			case "Add suggestion" -> AdminGameRecoController.getClient().giveRecommendationToGamer();
//			case "View suggestions" -> {
//				AdminGameRecoController.getClient().displayAllAdminRecos();
//				enterAppropriateMenu();
//			}
//			case "Send message" -> MessageController.getClient().sendMsg();
//		case "Edit Details of BattleSea", "Edit Details of Reversi" -> {
//				String gameName = commandOption.split(" ")[commandOption.split(" ").length - 1];
//				GameController.getClient().editDetails(gameName);
//			}
//			case "View Users" -> {
//				GamerController.getClient().displayAllUsernames();
//				enterAppropriateMenu();
//			}
//
//			// suggestions menu
//			case "Choose suggested game" -> AdminGameRecoController.getClient().chooseRecoGame();
//			case "Remove suggestion" -> AdminGameRecoController.getClient().removeReco();
//
//			// events menu
//			case "View event info" -> EventController.getClient().displayEventInfo();
//			case "Participate in event" -> EventController.getClient().participateInEvent();
//			case "Show Events participating in" -> EventController.getClient().displayInSessionEventsParticipatingIn();
//			case "Stop participating in Event" -> EventController.getClient().stopParticipatingInEvent();
//		case "Edit Event" -> EventController.getClient().editEvent();
//			case "Remove Event" -> EventController.getClient().removeEvent();
//
//			// friend menu
//	         case "Show friends" -> GamerController.getClient().displayFriendsUsernames();
//			case "Send Friend Request" -> FriendRequestController.getClient().sendFrndRequest();case "Show Friend Requests" -> FriendRequestController.getClient().displayFrndReqsPlayerGotten();
//
//			// gamer user management menu
//			case "View user profile" -> GamerController.getClient().displayUserProfileToAdmin();
//
//			// friend management menu
//            case "View friend profile" -> GamerController.getClient().displayFriendPersonalInfo();
//			case "Remove Friend" -> GamerController.getClient().removeFriend();
//
//		// friend request management menu
//
//	case "Accept Friend Request" -> FriendRequestController.getClient().acceptFriendReq();
//			case "Decline Friend Request" -> FriendRequestController.getClient().declineFriendReq();
//
//		// game menu
//			case "Show scoreboard" -> GameController.getClient().displayScoreboardOfGame();
//			case "Details" -> GameController.getClient().displayGameHowToPlay();
//			case "Show log of game" -> GameLogController.getClient().displayLogOfGame();
//			case "Show wins count" -> GameLogController.getClient().displayWinCountOfGameByLoggedInPlayer();
//			case "Show played count" -> GameLogController.getClient().displayPlayedCountOfGameByLoggedInPlayer();
//			case "Add to favorites" -> GameController.getClient().addFaveGame();
//			case "Continue previous games" -> GameController.getClient().displayPrevGamesAndChooseToContinue();
//			case "Run Game" -> GameController.getClient().runGame();
//
//			// gameplay battlesea menu
//				//	phase 1
//			case "Generate a Random Board" -> BattleSeaController.getClient().displayRandomlyGeneratedBoard();
//		case "Choose between 5 randomly generated boards" -> BattleSeaController.getClient().chooseBetween5RandomlyGeneratedBoards();
//			case "Display on-trial Board" -> BattleSeaController.getClient().displayTrialBoard();
//			case "Move ship" -> ShipController.getClient().moveShip();
//			case "Change ship direction" -> ShipController.getClient().rotateShip();
//			case "Finalize Board" -> {
//				BattleSeaController.getClient().finalizeTrialBoard();
//
//				if (((BattleSea) GameController.getClient().getCurrentGameInSession()).canStartBombing()) {
//					((_12_1GameplayBattleSeaMenu) Menu.getMenuIn()).nextPhase();
//					BattleSeaController.getClient().initTurnTimerStuff();
//				}
//			}
//				//	phase 2
//		case "Boom (Throw Bomb)" -> BombController.getClient().throwBomb();
//			case "Time?" -> BattleSeaController.getClient().displayRemainingTime();
//			case "Whose turn?", "Whose turn now?" -> GameController.getClient().displayTurn();
//			case "Display all my ships" -> ShipController.getClient().displayAllShipsOfCurrentPlayer();
//			case "Display all my booms" -> BombController.getClient().displayAllCurrentPlayerBombs();
//			case "Display all my opponent’s booms" -> BombController.getClient().displayAllOpponentBombs();
//			case "Display all my correct booms" -> BombController.getClient().displayAllSuccessCurrentPlayerBombs();
//			case "Display all my opponent’s correct booms" -> BombController.getClient().displayAllSuccessOpponentBombs();
//			case "Display all my incorrect booms" -> BombController.getClient().displayAllUnsuccessCurrentPlayerBombs();
//			case "Display all my opponent’s incorrect booms" -> BombController.getClient().displayAllUnsuccessOpponentBombs();
//			case "Display all my boomed ships" -> ShipController.getClient().displayDestroyedShipsOfCurrentPlayer();
//			case "Display all my opponent’s boomed ships" -> ShipController.getClient().displayDestroyedShipsOfOpponent();
//			case "Display all my unboomed ships" -> ShipController.getClient().displayHealthyShipsOfCurrentPlayer();
//			case "Display my board" -> BattleSeaController.getClient().displayCurrentPlayerBoard();
//			case "Display my opponent’s board" -> BattleSeaController.getClient().displayOpponentBoard();
//			//gameplay reversi menu
//			case "Place Disk" -> ReversiController.getClient().placeDisk();
//			case "Next Turn" -> ReversiController.getClient().nextTurn();
//			case "Display available coordinates" -> ReversiController.getClient().displayAvailableCoords();
//			case "Display Board (Grid)" -> ReversiController.getClient().displayGrid();
//			case "Display disks" -> ReversiController.getClient().displayPrevMoves();
//			case "Display scores" -> ReversiController.getClient().displayInGameScores();
//			case "Display final result" -> GameController.getClient().displayGameConclusion(GameController.getClient().getCurrentGameInSession());
//
//	//	 user editing menu
//			case "Change password" -> AccountController.getClient().changePWCommand();
//			case "Edit Personal info" -> AccountController.getClient().editAccFieldCommand();
//
//	//	 account menu
//			case "View personal info (w/ money)", "View personal info (w/o money)" -> {
//				AccountController.getClient().displayPersonalInfo();
//				enterAppropriateMenu();
//			}
//			case "View plato statistics" -> GamerController.getClient().displayAccountStats();
//			case "View Gaming History" -> GameLogController.getClient().displayFullGamingHistoryOfGamer();
//			case "View Gaming History in BattleSea" -> GameLogController.getClient().displayGamingHistoryOfGamerInGame("BattleSea");
//			case "View Gaming History in Reversi" -> GameLogController.getClient().displayGamingHistoryOfGamerInGame("Reversi");
//			case "View my BattleSea statistics" -> GameLogController.getClient().displayPlayerStatsInGame("BattleSea");
//			case "View my Reversi statistics" -> GameLogController.getClient().displayPlayerStatsInGame("Reversi");
//			case "Logout" -> {
//				AccountController.getClient().logout();
//			//	Menu.getMenu("2").enter();
//			}
//		}
//
//		saveEverything();
//	}

	public static void playButtonClickSound () {
		new AudioClip(Paths.get("src/com/Resources/Sounds/button.wav").toUri().toString()).play(0.2);
	}

	@Override
	public void start (Stage primaryStage) {
		getInstance().primaryStage = primaryStage;
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> saveEverything());
		DayPassController.getInstance().start();

		String path;

		if (!Admin.adminHasBeenCreated()) {
			RegisterMenuController.setStage(primaryStage);
			path = "src/com/plato/View/Menus/RegisterMenu.fxml";
		}

		else if (AccountController.getInstance().getCurrentAccLoggedIn() == null) {
			LoginMenuController.setStage(primaryStage);
			path = "src/com/plato/View/Menus/LoginMenu.fxml";
		}
		else {
			MainMenuController.setGamerOrAdmin(AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer);
			LoginMenuController.setStage(primaryStage);
			path = "src/com/plato/View/Menus/MainMenu.fxml";
		}

		try {
			primaryStage.setScene(new Scene(FXMLLoader.load(new File(path).toURI().toURL())));
		} catch (IOException e) {
			e.printStackTrace();
		}

		primaryStage.show();
	}

	public void saveEverything () {
		try {
//			if (Client.getClient() != null)
//				serializeEverything();
			Client.getClient().clientDisconnected();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a new stage on top of the previous stage
	 * .show() needs to be used outside in case further changes are needed
	 */
	public Stage createAndReturnNewStage (Parent root, String title, boolean newOrReplace, Stage ownerStage) {
		if (newOrReplace) {
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(ownerStage);
			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.setTitle(title);
			return stage;
		}
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setTitle(title);
		primaryStage.setOnCloseRequest(e -> saveEverything());

		return primaryStage;
	}

	public Stage getPrimaryStage () {
		return primaryStage;
	}

	public static class InvalidInputException extends Exception {
		public InvalidInputException () {
			super("Invalid Input");
		}
	}

	public static class InvalidFormatException extends Exception {
		public InvalidFormatException (String field) {
			super(field + " format is invalid");
		}
	}

	public static class SuccessfulOperationException extends Throwable {
		public SuccessfulOperationException () {
			super();
		}
	}

	public static class DayPassController extends Thread {
		private static DayPassController dayPassController = new DayPassController();
		private int lastDayUpdated;

		public static DayPassController getInstance () {
			if (dayPassController == null)
				dayPassController = new DayPassController();
			return dayPassController;
		}

		@Override
		public void run () {
			int today = LocalDate.now().getDayOfMonth();
			if (today != lastDayUpdated) {
				// todo do things that should be done everyday
				Event.dealWOverdueEvents();

				lastDayUpdated = LocalDate.now().getDayOfMonth();
			}
		}
	}
}