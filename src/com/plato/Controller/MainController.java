package Controller;

import Controller.AccountRelated.*;
import Controller.GameRelated.GameController;
import Controller.GameRelated.GameLogController;
import Controller.GameRelated.Reversi.ReversiController;
import Model.AccountRelated.*;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;
import View.Menus.Menu;
import View.Menus._11GameMenu;
import View.Menus._3MainMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainController {
	private static MainController mainController;
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;

	public static MainController getInstance () {
		if (mainController == null)
			mainController = new MainController();
		return mainController;
	}

	public static void main (String[] args) {
		try {
			getInstance().deserialize();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!Admin.adminHasBeenCreated())
			Menu.addMenu("1");
		else
			Menu.addMenu("2");

		while (true) {

			Menu.getMenuIn().displayMenu();

			int command;
			try {
				String comStr = Menu.getInputLine();

				if (!comStr.matches("[0-9]+"))
					throw new InvalidInputException();

				command = Integer.parseInt(comStr);

				if (command < 1 || command > Menu.getMenuIn().getOptions().size())
					throw new InvalidInputException();

				getInstance().dealWithInput(command);

//				Menu.getMenuIn().displayMenu();

			} catch (InvalidInputException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}
	}

	private void dealWithInput (int command) {
		LinkedList<String> menuOpts = Menu.getMenuIn().getOptions();

		command--; // to use for accessing menuOpts indexes
		String commandOption = menuOpts.get(command).toLowerCase();

		switch (commandOption) {
			case "exit program" -> tryToExitProgram();
			case "back" -> Menu.getMenuIn().back();
			case "go to account menu" -> {
				String menuId = "14" + (AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer ? "G" : "A");
				Menu.addMenu(menuId);
				Menu.getMenu(menuId).enter();
			}

			// main menu
			case "register" -> AccountController.getInstance().register();
			case "login" -> AccountController.getInstance().login();
			case "delete account" -> AccountController.getInstance().deleteAccount();
			case "show points" -> {
				if (Menu.getMenuIn() instanceof _3MainMenu)
					GameLogController.getInstance().displayAllPointsOfPlayer();
				if (Menu.getMenuIn() instanceof _11GameMenu)
					GameLogController.getInstance().displayPtsLoggedInPlayerEarnedFromGame();
			}
			case "view favorite games" -> GamerController.getInstance().displayFaveGamesForGamer();
			case "view platobot's messages" -> MessageController.getInstance().displayAdminMessages();
			case "view last played" -> GameLogController.getInstance().displayLastGamePlayed();
			case "view admin's suggestions" -> AdminGameRecoController.getInstance().displayAdminsRecosToPlayer();
			case "go to friends menu" -> Menu.getMenuIn().getChildMenus().get(6).enter();
			case "view events" -> {
				EventController.getInstance().displayInSessionEvents();

				if (!((_3MainMenu) Menu.getMenuIn()).isForAdmin())
					Menu.getMenuIn().getChildMenus().get(7).enter();
				else
					Menu.getMenuIn().getChildMenus().get(2).enter();
			}
			case "add event" -> EventController.getInstance().createEvent();
			case "add suggestions" -> AdminGameRecoController.getInstance().addReco();
			case "view suggestions" -> {
				AdminGameRecoController.getInstance().displayAllAdminRecos();
				Menu.getMenuIn().getChildMenus().get(4).enter();
			}
			case "send message" -> MessageController.getInstance().sendMsg();
			case "view users" -> {
				GamerController.getInstance().displayAllUsernames();
				Menu.getMenuIn().getChildMenus().get(6).enter();
			}

			// suggestions menu
			case "choose suggested game" -> AdminGameRecoController.getInstance().displayAdminsRecosToPlayer();
			case "remove suggestion" -> AdminGameRecoController.getInstance().removeReco();

			// events menu
			case "view event info" -> EventController.getInstance().displayEventInfo();
			case "participate in event" -> EventController.getInstance().participateInEvent();
			case "Show Events participating in" -> EventController.getInstance().displayInSessionEventsParticipatingIn();
			case "Stop participating in Event" -> EventController.getInstance().stopParticipatingInEvent();
			case "edit event" -> EventController.getInstance().editEvent();
			case "remove event" -> EventController.getInstance().removeEvent();

			// friend menu
			case "show friends" -> GamerController.getInstance().displayFriendsUsernames();
			case "send friend request" -> FriendRequestController.getInstance().sendFrndRequest();
			case "show friend requests" -> FriendRequestController.getInstance().displayFrndReqsPlayerGotten();

			// gamer user management menu
			case "view user profile" -> GamerController.getInstance().displayUserProfileToAdmin();

			// friend management menu
			case "view friend profile" -> GamerController.getInstance().displayFriendPersonalInfo();
			case "remove friend" -> GamerController.getInstance().removeFriend();

			// friend request management menu
			case "accept friend request" -> FriendRequestController.getInstance().acceptFriendReq();
			case "decline friend request" -> FriendRequestController.getInstance().declineFriendReq();

			// games menu
			case "open battlesea game menu" -> Menu.getMenuIn().getChildMenus().get(1).enter();
			case "open reversi game menu" -> Menu.getMenuIn().getChildMenus().get(2).enter();

			// game menu
			case "show scoreboard" -> GameController.getInstance().displayScoreboardOfGame();
			case "details" -> GameController.getInstance().displayGameHowToPlay();
			case "show log of game" -> GameLogController.getInstance().displayLogOfGame();
			case "show wins count" -> GameLogController.getInstance().displayWinCountOfGameByLoggedInPlayer();
			case "show played count" -> GameLogController.getInstance().displayPlayedCountOfGameByLoggedInPlayer();
			case "add to favorites" -> GameController.getInstance().addGameToFavesOfLoggedInGamer();
			case "run game" -> GameController.getInstance().runGame();

			// gameplay battlesea menu
			// TODO: 12/5/2020 AD

			// gameplay reversi menu
			case "place disks" -> ReversiController.getInstance().placeDisk();
			case "next turn" -> ReversiController.getInstance().nextTurn();
			case "whose turn now?" -> GameController.getInstance().displayTurn();
			case "display available coordinates" -> ReversiController.getInstance().displayAvailableCoords();
			case "display board (grid)" -> ReversiController.getInstance().displayGrid();
			case "display disks" -> ReversiController.getInstance().displayPrevMoves();
			case "display scores" -> GameController.getInstance().displayInGameScores();
			case "display final result" -> GameController.getInstance().displayGameConclusion();

			// user editing menu
			case "change password" -> AccountController.getInstance().changePWCommand();
			case "edit personal info" -> AccountController.getInstance().editAccFieldCommand();

			// account menu
			case "view personal info (w/ money)" -> {
				AccountController.getInstance().diplayPersonalInfo();
				Menu.getMenuIn().getChildMenus().get(command+1).enter();
			}
			case "view plato statistics" -> GamerController.getInstance().displayAccountStats();
			case "view gaming history" -> GamerController.getInstance().displayGamingHistory();
			case "view gaming history in battlesea" -> GamerController.getInstance().displayGamingHistory("battlesea");
			case "view gaming history in reversi" -> GamerController.getInstance().displayGamingHistory("reversi");
			case "logout" -> {
				AccountController.getInstance().logoutCommand();
				Menu.getMenuIn().getChildMenus().get(command+1).enter();
			}
		}
	}

	public void serialize () throws IOException {
		BufferedWriter writer;

		// Admin.json
		if (Admin.adminHasBeenCreated()) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Admin.json"));
			writer.write(gson.toJson(Admin.getAdmin()));
			writer.flush();
		}

		// Gamer.json
		if (Gamer.getGamers().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Gamer.json"));
			writer.write(gson.toJson(Gamer.getGamers()));
			writer.flush();
		}

		// AdminGameReco.json
		if (AdminGameReco.getRecommendations().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			writer.write(gson.toJson(AdminGameReco.getRecommendations()));
			writer.flush();
		}

		// Event.json
		if (Event.getEvents().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Event.json"));
			writer.write(gson.toJson(Event.getEvents()));
			writer.flush();
		}

		// FriendRequest.json
		if (FriendRequest.getAllfriendRequests().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			writer.write(gson.toJson(FriendRequest.getAllfriendRequests()));
			writer.flush();
		}

		// Message.json
		if (Message.getAllMessages().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Message.json"));
			writer.write(gson.toJson(Message.getAllMessages()));
			writer.flush();
		}

		// BattleSea.json
		if (BattleSea.getAllBattleSeaGames().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/BattleSea.json"));
			writer.write(gson.toJson(BattleSea.getAllBattleSeaGames()));
			writer.flush();
		}

		// Reversi.json
		if (Reversi.getAllReversiGames().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/Reversi.json"));
			writer.write(gson.toJson(Reversi.getAllReversiGames()));
			writer.flush();
		}

		// IDGenerator.json
		if (IDGenerator.getAllIDsGenerated().size() > 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/IDGenerator.json"));
			writer.write(gson.toJson(IDGenerator.getAllIDsGenerated()));
			writer.flush();
		}
	}

	public void deserialize () throws IOException {
		initGsonAndItsBuilder();
		// admins
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Admin.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Admin.setAdmin(gson.fromJson(json, (Type) Admin.class));
		}
		// gamers
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Gamer.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Gamer.setGamers(gson.fromJson(json, new TypeToken<LinkedList<Gamer>>() {
				}.getType()));
		}
		// admin game recommendations
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				AdminGameReco.setRecommendations(gson.fromJson(json, new TypeToken<LinkedList<AdminGameReco>>() {
				}.getType()));
		}
		// events
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Event.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Event.setEvents(gson.fromJson(json, new TypeToken<LinkedList<Event>>(){}.getType()));
		}
		// frnd req's
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				FriendRequest.setAllfriendRequests(gson.fromJson(json, new TypeToken<LinkedList<FriendRequest>>(){}.getType()));
		}
		// messages
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Message.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Message.setAllMessages(gson.fromJson(json,  new TypeToken<LinkedList<Message>>(){}.getType()));
		}
		// BattleSea list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/BattleSea.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Game.setAllGames(gson.fromJson(json, new TypeToken<LinkedList<BattleSea>>(){}.getType()));
		}
		// Reversi list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/Reversi.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Game.setAllGames(gson.fromJson(json, new TypeToken<LinkedList<Reversi>>(){}.getType()));
		}
		// IDGenerator list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/IDGenerator.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				IDGenerator.setAllIDsGenerated(gson.fromJson(json, new TypeToken<LinkedList<String>>(){}.getType()));
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
		gson = gsonBuilder.create();
	}

	public void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			try {
				mainController.serialize();
				AccountController.getInstance().logout();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class InvalidInputException extends Exception {
		public InvalidInputException () {
			super("Invalid Input");
		}
	}
}