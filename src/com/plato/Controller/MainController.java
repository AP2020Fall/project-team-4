package Controller;

import Controller.AccountRelated.AccountController;
import Controller.Menus.LoginMenuController;
import Controller.Menus.MainMenuController;
import Controller.Menus.RegisterMenuController;
import Controller.TypeAdapters.Account.AccountAdapter;
import Model.AccountRelated.*;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainController extends Application {
	public static Socket clientSocket;
	private static MainController mainController;
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	public Stage primaryStage;
	private Gson gson;

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
			initGsonAndItsBuilder();
			if (Client.getClient() != null)
				serializeEverything();
			Client.getClient().clientDisconnected();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deserializeEverything () throws IOException {
		initGsonAndItsBuilder();
		Admin.setAdmin(getAdmin());
		Gamer.setGamers(getGamers());
		AccountController.getInstance().setCurrentAccLoggedIn(getSavedLoginInfo());
		AdminGameReco.setRecommendations(getAdminGameRecos());
		Event.setEvents(getEvents());
		FriendRequest.setAllfriendRequests(getFrndReqs());
		Message.setAllMessages(getMsgs());
		BattleSea.setAllGames(getBattleseaGames());
		Reversi.setAllGames(getReversiGames());
		IDGenerator.setAllIDsGenerated(getIDGenerator());
		try {
			String battleSeaDetails = Game.getAllGames().stream().filter(game -> game instanceof BattleSea).findFirst().get().getDetails();
			BattleSea.setDetailsForBattlesea(battleSeaDetails);

			String reversiDetails = Game.getAllGames().stream().filter(game -> game instanceof Reversi).findFirst().get().getDetails();
			Reversi.setDetailsForReversi(reversiDetails);
		} catch (NoSuchElementException | NullPointerException e) {
			//e.printStackTrace();
		}
	}

	public LinkedList<String> getIDGenerator () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/IDGenerator.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<String>>() {
				}.getType());
			return null;
		}
	}

	public LinkedList<Reversi> getReversiGames () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/GameRelated/Reversi.json"))) {

			while (reader.ready())
				json.append(reader.readLine());


			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<Reversi>>() {
				}.getType());
		}
		return null;
	}

	public LinkedList<BattleSea> getBattleseaGames () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/GameRelated/BattleSea.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<BattleSea>>() {
				}.getType());
		}
		return null;
	}

	public LinkedList<Message> getMsgs () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Message.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<Message>>() {
				}.getType());
		}
		return null;
	}

	public LinkedList<FriendRequest> getFrndReqs () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/FriendRequest.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<FriendRequest>>() {
				}.getType());
		}
		return null;
	}

	public LinkedList<Event> getEvents () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Event.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<Event>>() {
				}.getType());
		}
		return null;
	}

	public LinkedList<AdminGameReco> getAdminGameRecos () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {

			while (reader.ready())
				json.append(reader.readLine());


			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<AdminGameReco>>() {
				}.getType());
		}
		return null;
	}

	public Account getSavedLoginInfo () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/SavedLoginInfo.json"))) {
			boolean forAdmin = false;
			if (reader.ready()) forAdmin = reader.readLine().equalsIgnoreCase("a");

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2) {
				AccountController.getInstance().setSaveLoginInfo(true);
				return gson.fromJson(json.toString(), forAdmin ? Admin.class : Gamer.class);
			}
		}
		return null;
	}

	public LinkedList<Gamer> getGamers () throws IOException {
		StringBuilder json = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Gamer.json"))) {

			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), new TypeToken<LinkedList<Gamer>>() {
				}.getType());
		}
		return null;
	}

	public Admin getAdmin () throws IOException {
		StringBuilder json = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Admin.json"))) {
			while (reader.ready())
				json.append(reader.readLine());

			if (json.length() > 2)
				return gson.fromJson(json.toString(), (Type) Admin.class);
		}
		return null;
	}

	private void initGsonAndItsBuilder () {
		gsonBuilder.setDateFormat("yyyy-MMM-dd HH:mm:ss");
//		gsonBuilder.setPrettyPrinting();
		gsonBuilder.serializeNulls();
//		gsonBuilder.registerTypeAdapter(Account.class, new AccountAdapter());
		gson = FxGson.addFxSupport(gsonBuilder)
				.create();
	}

	public void serializeEverything () throws IOException {
		serializeSavedLoginInfo();
		serializeAdmin();
		serializeGamers();
		serializeAdminGameRecos();
		serializeEvents();
		serializeFrndReqs();
		serializeMsgs();
		serializeBattleSeaGames();
		serializeReversiGames();
		serializeIDGenerator();
	}

	public void serializeIDGenerator () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/IDGenerator.json")) {
			printWriter.print("");
		}
		if (IDGenerator.getAllIDsGenerated().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/IDGenerator.json"))) {

				writer.write(gson.toJson(IDGenerator.getAllIDsGenerated()));
			}
	}

	public void serializeReversiGames () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/GameRelated/Reversi.json")) {
			printWriter.print("");
		}
		if (Reversi.getAllReversiGames().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/GameRelated/Reversi.json"))) {

				writer.write(gson.toJson(Reversi.getAllReversiGames()));
			}
	}

	public void serializeBattleSeaGames () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/GameRelated/BattleSea.json")) {
			printWriter.print("");
		}
		if (BattleSea.getAllBattleSeaGames().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/GameRelated/BattleSea.json"))) {

				writer.write(gson.toJson(BattleSea.getAllBattleSeaGames()));
			}
	}

	public void serializeMsgs () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/Message.json")) {
			printWriter.print("");
		}
		if (Message.getAllMessages().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Message.json"))) {

				writer.write(gson.toJson(Message.getAllMessages()));
			}
	}

	public void serializeFrndReqs () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/FriendRequest.json")) {
			printWriter.print("");
		}
		if (FriendRequest.getAllfriendRequests().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/FriendRequest.json"))) {

				writer.write(gson.toJson(FriendRequest.getAllfriendRequests()));
			}
	}

	public void serializeEvents () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/Event.json")) {
			printWriter.print("");
		}
		if (Event.getAllEvents().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Event.json"))) {

				writer.write(gson.toJson(Event.getAllEvents()));
			}
	}

	public void serializeAdminGameRecos () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/AdminGameReco.json")) {
			printWriter.print("");
		}
		if (AdminGameReco.getRecommendations().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {

				writer.write(gson.toJson(AdminGameReco.getRecommendations()));
			}
	}

	public void serializeGamers () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/Gamer.json")) {
			printWriter.print("");
		}
		if (Gamer.getGamers().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Gamer.json"))) {

				writer.write(gson.toJson(Gamer.getGamers()));
			}
	}

	public void serializeAdmin () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/AccountRelated/Admin.json")) {
			printWriter.print("");
		}
		if (Admin.adminHasBeenCreated())
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Admin.json"))) {

				writer.write(gson.toJson(Admin.getAdmin()));
			}
	}

	public void serializeSavedLoginInfo () throws IOException {
		try (PrintWriter printWriter = new PrintWriter("src/com/Resources/JSONs/SavedLoginInfo.json")) {
			printWriter.print("");
		}
		if (AccountController.getInstance().getCurrentAccLoggedIn() != null) // if is logged-in
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/SavedLoginInfo.json"))) {
				if (AccountController.getInstance().saveLoginInfo()) { // skip if said no to remember me
					writer.write((AccountController.getInstance().getCurrentAccLoggedIn() instanceof Admin ? "a" : "g") + "\n");
					writer.write(gson.toJson(AccountController.getInstance().getCurrentAccLoggedIn()));
				}
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

	public Gson getGson () {
		initGsonAndItsBuilder();
		return gson;
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