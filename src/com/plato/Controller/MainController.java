package Controller;

import Controller.AccountRelated.*;
import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.BattleSea.ShipController;
import Controller.GameRelated.GameController;
import Controller.GameRelated.GameLogController;
import Controller.GameRelated.Reversi.ReversiController;
import Model.AccountRelated.*;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;
import View.Menus.Menu;
import View.Menus._11GameMenu;
import View.Menus._12_1GameplayBattleSeaMenu;
import View.Menus._3MainMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainController {
	private static MainController mainController;
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;

	static int command;

	public static MainController getInstance () {
		if (mainController == null)
			mainController = new MainController();
		return mainController;
	}

	public static void main (String[] args) {
		DayPassController.getInstance().start();

		try {
			getInstance().deserialize();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!Admin.adminHasBeenCreated()) {
			Menu.addMenu("1");
			Menu.getMenu("1").enter();
		}
		else {
			Menu.addMenu("2");
			if (AccountController.getInstance().getCurrentAccLoggedIn() != null) {
				String aORg = AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer ? "G" : "A";
				Menu.addMenu("3" + aORg);
				Menu.getMenu("3" + aORg).enter();
			}
			else
				Menu.getMenu("2").enter();


			Menu.addMenusForAdminOrGamer(AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer ? "G" : "A");
		}

		Menu.getMenuIn().displayMenu();

		while (true) {
			if (Menu.getScanner().hasNextLine()) {
				try {
					String comStr = Menu.getInputLine();

					if (!comStr.matches("[0-9]+"))
						throw new InvalidInputException();

					command = Integer.parseInt(comStr);

					if (command < 1 || command > Menu.getMenuIn().getOptions().size())
						throw new InvalidInputException();

					getInstance().dealWithInput(command);

				} catch (InvalidInputException e) {
					Menu.printErrorMessage(e.getMessage());
				}

				Menu.getMenuIn().displayMenu();
			}
		}
	}

	private void dealWithInput (int command) {
		LinkedList<String> menuOpts = Menu.getMenuIn().getOptions();

		command--; // to use for accessing menuOpts indexes
		String commandOption = menuOpts.get(command).trim();

		switch (commandOption) {
			case "Exit program" -> tryToExitProgram();
			case "Back" -> Menu.getMenuIn().back();
			case "Go to Account Menu", "Go to Games Menu", "Go to Friends Menu",
					"Open Reversi Game Menu", "Open BattleSea Game Menu" -> enterAppropriateMenu();

			// register-login menu
			case "Register Gamer", "Register Admin" -> AccountController.getInstance().register();
			case "Login" -> AccountController.getInstance().login();
			case "Delete Account" -> AccountController.getInstance().deleteAccount();

			// main menu
			case "Show Points" -> {
				if (Menu.getMenuIn() instanceof _3MainMenu)
					GameLogController.getInstance().displayAllPointsOfPlayer();
				if (Menu.getMenuIn() instanceof _11GameMenu)
					GameLogController.getInstance().displayPtsLoggedInPlayerEarnedFromGame();
			}
			case "View Favorite games" -> GamerController.getInstance().displayFaveGamesForGamer();
			case "View Platobot’s messages" -> MessageController.getInstance().displayAdminMessages();
			case "View last played" -> GameLogController.getInstance().displayLastGamePlayed();
			case "View admin’s suggestions" ->
					AdminGameRecoController.getInstance().displayAdminsRecosToPlayer();
			case "View Events" -> {
				EventController.getInstance().displayInSessionEvents();
				enterAppropriateMenu();
			}
			case "Add Event" -> EventController.getInstance().createEvent();
			case "Add suggestion" -> AdminGameRecoController.getInstance().giveRecommendationToGamer();
			case "View suggestions" -> {
				AdminGameRecoController.getInstance().displayAllAdminRecos();
				enterAppropriateMenu();
			}
			case "Send message" -> MessageController.getInstance().sendMsg();
			case "Edit Details of BattleSea", "Edit Details of Reversi" -> {
				String gameName = commandOption.split(" ")[commandOption.split(" ").length - 1];
				GameController.getInstance().editDetails(gameName);
			}
			case "View Users" -> {
				GamerController.getInstance().displayAllUsernames();
				enterAppropriateMenu();
			}

			// suggestions menu
			case "Choose suggested game" -> AdminGameRecoController.getInstance().chooseRecoGame();
			case "Remove suggestion" -> AdminGameRecoController.getInstance().removeReco();

			// events menu
			case "View event info" -> EventController.getInstance().displayEventInfo();
			case "Participate in event" -> EventController.getInstance().participateInEvent();
			case "Show Events participating in" -> EventController.getInstance().displayInSessionEventsParticipatingIn();
			case "Stop participating in Event" -> EventController.getInstance().stopParticipatingInEvent();
			case "Edit Event" -> EventController.getInstance().editEvent();
			case "Remove Event" -> EventController.getInstance().removeEvent();

			// friend menu
			case "Show friends" -> GamerController.getInstance().displayFriendsUsernames();
			case "Send Friend Request" -> FriendRequestController.getInstance().sendFrndRequest();
			case "Show Friend Requests" -> FriendRequestController.getInstance().displayFrndReqsPlayerGotten();

			// gamer user management menu
			case "View user profile" -> GamerController.getInstance().displayUserProfileToAdmin();

			// friend management menu
			case "View friend profile" -> GamerController.getInstance().displayFriendPersonalInfo();
			case "Remove Friend" -> GamerController.getInstance().removeFriend();

			// friend request management menu
			case "Accept Friend Request" -> FriendRequestController.getInstance().acceptFriendReq();
			case "Decline Friend Request" -> FriendRequestController.getInstance().declineFriendReq();

			// game menu
			case "Show scoreboard" -> GameController.getInstance().displayScoreboardOfGame();
			case "Details" -> GameController.getInstance().displayGameHowToPlay();
			case "Show log of game" -> GameLogController.getInstance().displayLogOfGame();
			case "Show wins count" -> GameLogController.getInstance().displayWinCountOfGameByLoggedInPlayer();
			case "Show played count" -> GameLogController.getInstance().displayPlayedCountOfGameByLoggedInPlayer();
			case "Add to favorites" -> GameController.getInstance().addGameToFavesOfLoggedInGamer();
			case "Run Game" -> GameController.getInstance().runGame();

			// gameplay battlesea menu
			//		phase 1
			case "Generate a Random Board" -> BattleSeaController.getInstance().displayRandomlyGeneratedBoard();
			case "Choose between 5 randomly generated boards" -> BattleSeaController.getInstance().chooseBetween5RandomlyGeneratedBoards();
			case "Display on-trial Board" -> BattleSeaController.getInstance().displayTrialBoard();
			case "Move ship" -> ShipController.getInstance().editShipCoords();
			case "Change ship direction" -> ShipController.getInstance().rotateShip();
			case "Finalize Board" -> {
				BattleSeaController.getInstance().finalizeTrialBoard();
				if (((BattleSea) GameController.getInstance().getCurrentGameInSession()).canStartBombing()) {
					((_12_1GameplayBattleSeaMenu) Menu.getMenuIn()).nextPhase();
					BattleSeaController.getInstance().initTurnTimerStuff();
				}
			}
			//		phase 2
			case "Boom (Throw Bomb)" -> BombController.getInstance().throwBomb();
			case "Time?" -> BattleSeaController.getInstance().displayRemainingTime();
			case "Whose turn?", "Whose turn now?" -> GameController.getInstance().displayTurn();
			case "Display all my ships" -> ShipController.getInstance().displayAllShipsOfCurrentPlayer();
			case "Display all my booms" -> BombController.getInstance().displayAllCurrentPlayerBombs();
			case "Display all my opponent’s booms" -> BombController.getInstance().displayAllOpponentBombs();
			case "Display all my correct booms" -> BombController.getInstance().displayAllSuccessCurrentPlayerBombs();
			case "Display all my opponent’s correct booms" -> BombController.getInstance().displayAllSuccessOpponentBombs();
			case "Display all my incorrect booms" -> BombController.getInstance().displayAllUnsuccessCurrentPlayerBombs();
			case "Display all my opponent’s incorrect booms" -> BombController.getInstance().displayAllUnsuccessOpponentBombs();
			case "Display all my boomed ships" -> ShipController.getInstance().displayDestroyedShipsOfCurrentPlayer();
			case "Display all my opponent’s boomed ships" -> ShipController.getInstance().displayDestroyedShipsOfOpponent();
			case "Display all my unboomed ships" -> ShipController.getInstance().displayHealthyShipsOfCurrentPlayer();
			case "Display my board" -> BattleSeaController.getInstance().displayCurrentPlayerBoard();
			case "Display my opponent’s board" -> BattleSeaController.getInstance().displayOpponentBoard();

			// gameplay reversi menu
			case "Place Disk" -> ReversiController.getInstance().placeDisk();
			case "Next Turn" -> ReversiController.getInstance().nextTurn();
			case "Display available coordinates" -> ReversiController.getInstance().displayAvailableCoords();
			case "Display Board (Grid)" -> ReversiController.getInstance().displayGrid();
			case "Display disks" -> ReversiController.getInstance().displayPrevMoves();
			case "Display scores" -> ReversiController.getInstance().displayInGameScores();
			case "Display final result" -> GameController.getInstance().displayGameConclusion();

			// user editing menu
			case "Change password" -> AccountController.getInstance().changePWCommand();
			case "Edit Personal info" -> AccountController.getInstance().editAccFieldCommand();

			// account menu
			case "View personal info (w/ money)", "View personal info (w/o money)" -> {
				AccountController.getInstance().displayPersonalInfo();
				enterAppropriateMenu();
			}
			case "View plato statistics" -> GamerController.getInstance().displayAccountStats();
			case "View Gaming History" -> GameLogController.getInstance().displayFullGamingHistoryOfGamer();
			case "View Gaming History in BattleSea" -> GameLogController.getInstance().displayGamingHistoryOfGamerInGame("BattleSea");
			case "View Gaming History in Reversi" -> GameLogController.getInstance().displayGamingHistoryOfGamerInGame("Reversi");
			case "View my BattleSea statistics" -> GameLogController.getInstance().displayPlayerStatsInGame("BattleSea");
			case "View my Reversi statistics" -> GameLogController.getInstance().displayPlayerStatsInGame("Reversi");
			case "Logout" -> {
				AccountController.getInstance().logout();
				Menu.getMenu("2").enter();
			}
		}

		saveEverything();
	}

	public void serialize () throws IOException {

		// SavedLoginInfo.json
		if (AccountController.getInstance().getCurrentAccLoggedIn() != null) // if is logged-in
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/SavedLoginInfo.json"))) {
				if (AccountController.getInstance().saveLoginInfo()) { // skip if said no to remember me
					writer.write((AccountController.getInstance().getCurrentAccLoggedIn() instanceof Admin ? "a" : "g") + "\n");
					writer.write(gson.toJson(AccountController.getInstance().getCurrentAccLoggedIn()));
				}
			}
		else // if is logged-out
			try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/SavedLoginInfo.json")) {
				printWriter.print("");
			}


		// Admin.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/Admin.json")) {
			printWriter.print("");
		}
		if (Admin.adminHasBeenCreated())
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Admin.json"))) {

				writer.write(gson.toJson(Admin.getAdmin()));
			}


		// Gamer.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/Gamer.json")) {
			printWriter.print("");
		}
		if (Gamer.getGamers().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Gamer.json"))) {

				writer.write(gson.toJson(Gamer.getGamers()));
			}

		// AdminGameReco.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/AdminGameReco.json")) {
			printWriter.print("");
		}
		if (AdminGameReco.getRecommendations().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {

				writer.write(gson.toJson(AdminGameReco.getRecommendations()));
			}

		// Event.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/Event.json")) {
			printWriter.print("");
		}
		if (Event.getEvents().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Event.json"))) {

				writer.write(gson.toJson(Event.getEvents()));
			}

		// FriendRequest.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/FriendRequest.json")) {
			printWriter.print("");
		}
		if (FriendRequest.getAllfriendRequests().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/FriendRequest.json"))) {

				writer.write(gson.toJson(FriendRequest.getAllfriendRequests()));
			}

		// Message.json
		try (PrintWriter printWriter = new PrintWriter("src/Resources/JSONs/AccountRelated/Message.json")) {
			printWriter.print("");
		}
		if (Message.getAllMessages().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Message.json"))) {

				writer.write(gson.toJson(Message.getAllMessages()));
			}
//
//		// BattleSea.json
//		if (BattleSea.getAllBattleSeaGames().size() > 0)
//			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/BattleSea.json"))) {
//
//				writer.write(gson.toJson(BattleSea.getAllBattleSeaGames()));
//			}
//
//		// Reversi.json
//		if (Reversi.getAllReversiGames().size() > 0)
//			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/Reversi.json"))) {
//
//				writer.write(gson.toJson(Reversi.getAllReversiGames()));
//			} fixme

		// IDGenerator.json
		if (IDGenerator.getAllIDsGenerated().size() > 0)
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/IDGenerator.json"))) {

				writer.write(gson.toJson(IDGenerator.getAllIDsGenerated()));
			}
	}

	public void saveEverything () {
		try {
			serialize();
			Menu.printSavedMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deserialize () throws IOException {
		initGsonAndItsBuilder();
		// admins
		{
			String json = "";

			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Admin.json"))) {
				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					Admin.setAdmin(gson.fromJson(json, (Type) Admin.class));
			}
		}
		// gamers
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Gamer.json"))) {

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					Gamer.setGamers(gson.fromJson(json, new TypeToken<LinkedList<Gamer>>() {
					}.getType()));
			}
		}
		// savedLoginInfo
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/SavedLoginInfo.json"))) {
				boolean forAdmin = false;
				if (reader.ready()) forAdmin = reader.readLine().equalsIgnoreCase("a");

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2) {
					AccountController.getInstance().setCurrentAccLoggedIn(gson.fromJson(json, (forAdmin ? Admin.class : Gamer.class)));
					AccountController.getInstance().setSaveLoginInfo(true);
				}
			}
		}
		// admin game recommendations
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {

				while (reader.ready())
					json += reader.readLine();


				if (json.length() > 2)
					AdminGameReco.setRecommendations(gson.fromJson(json, new TypeToken<LinkedList<AdminGameReco>>() {
					}.getType()));
			}
		}
		// events
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Event.json"))) {

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					Event.setEvents(gson.fromJson(json, new TypeToken<LinkedList<Event>>() {
					}.getType()));
			}
		}
		// frnd req's
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/FriendRequest.json"))) {

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					FriendRequest.setAllfriendRequests(gson.fromJson(json, new TypeToken<LinkedList<FriendRequest>>() {
					}.getType()));
			}
		}
		// messages
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Message.json"))) {

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					Message.setAllMessages(gson.fromJson(json, new TypeToken<LinkedList<Message>>() {
					}.getType()));
			}
		}
//		// BattleSea list
//		{
//			String json = "";
//			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/BattleSea.json"))) {
//
//				while (reader.ready())
//					json += reader.readLine();
//
//				if (json.length() > 2)
//					Game.setAllGames(gson.fromJson(json, new TypeToken<LinkedList<BattleSea>>() {
//					}.getType()));
//			}
//		}
//		// Reversi list
//		{
//			String json = "";
//			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/Reversi.json"))) {
//
//				while (reader.ready())
//					json += reader.readLine();
//
//
//				if (json.length() > 2)
//					Game.setAllGames(gson.fromJson(json, new TypeToken<LinkedList<Reversi>>() {
//					}.getType()));
//			}
//		}fixme
		// IDGenerator list
		{
			String json = "";
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/IDGenerator.json"))) {

				while (reader.ready())
					json += reader.readLine();

				if (json.length() > 2)
					IDGenerator.setAllIDsGenerated(gson.fromJson(json, new TypeToken<LinkedList<String>>() {
					}.getType()));
			}
		}

		try {
			String battleSeaDetails = Game.getAllGames().stream().filter(game -> game instanceof BattleSea).findFirst().get().getDetails();
			BattleSea.setDetailsForBattlesea(battleSeaDetails);

			String reversiDetails = Game.getAllGames().stream().filter(game -> game instanceof Reversi).findFirst().get().getDetails();
			Reversi.setDetailsForReversi(reversiDetails);
		} catch (NoSuchElementException | NullPointerException e) {
			return;
		}
	}

	private void initGsonAndItsBuilder () {
		gsonBuilder.setDateFormat("yyyy-MMM-dd HH:mm:ss");
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	public void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().equalsIgnoreCase("y")) {
			try {
				mainController.serialize();
				AccountController.getInstance().logout();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class InvalidInputException extends Exception {
		public InvalidInputException () {
			super("Invalid Input");
		}
	}

	private static class DayPassController extends Thread {
		private int lastDayUpdated;

		private static DayPassController dayPassController = new DayPassController();

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

	public static int getCommand () {
		return command;
	}

	public static void enterAppropriateMenu () {
		Menu.getMenuIn().getChildMenus().get(command).enter();
	}
}