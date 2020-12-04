package plato.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.*;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.Menus.Menu;

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

		if (menuOpts.get(command).equalsIgnoreCase("exit program")) tryToExitProgram();

		else if (menuOpts.get(command).equalsIgnoreCase("back")) Menu.getMenuIn().back();

		else if (menuOpts.get(command).toLowerCase().contains("go to account menu")) {
			String menuId = "14" + (AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer ? "G" : "A");
			Menu.addMenu(menuId);
			Menu.getMenuIn().getMenu(menuId).enter();
		}

		else {
			if (menuOpts.get(command).equalsIgnoreCase("register admin"))
				AccountController.getInstance().register();

			if (menuOpts.get(command).equalsIgnoreCase("login"))
				AccountController.getInstance().login();
		}
	}

	private void serialize () throws IOException {
		BufferedWriter writer;

		// Admin.json
		if (Admin.adminHasBeenCreated()) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Admin.json"));
			writer.write(gson.toJson(Admin.getAdmin()));
			writer.flush();
		}

		// Gamer.json
		if (Gamer.getGamers().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Gamer.json"));
			writer.write(gson.toJson(Gamer.getGamers()));
			writer.flush();
		}

		// AdminGameReco.json
		if (AdminGameReco.getRecommendations().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			writer.write(gson.toJson(AdminGameReco.getRecommendations()));
			writer.flush();
		}

		// Event.json
		if (Event.getEvents().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Event.json"));
			writer.write(gson.toJson(Event.getEvents()));
			writer.flush();
		}

		// FriendRequest.json
		if (FriendRequest.getAllfriendRequests().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			writer.write(gson.toJson(FriendRequest.getAllfriendRequests()));
			writer.flush();
		}

		// Message.json
		if (Message.getAllMessages().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Message.json"));
			writer.write(gson.toJson(Message.getAllMessages()));
			writer.flush();
		}

		// BattleSeaGameList.json
		if (BattleSea.getAllBattleSeaGames().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/BattleSeaGameList.json"));
			writer.write(gson.toJson(BattleSea.getAllBattleSeaGames()));
			writer.flush();
		}

		// ReversiGameList.json
		if (Reversi.getAllReversiGames().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/ReversiGameList.json"));
			writer.write(gson.toJson(Reversi.getAllReversiGames()));
			writer.flush();
		}

		// BattleSeaScoreboard.json
		if (BattleSea.getScoreboard().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/BattleSea/BattleSeaScoreboard.json"));
			writer.write(gson.toJson(BattleSea.getScoreboard()));
			writer.flush();
		}

		// ReversiScoreboard.json
		if (Reversi.getScoreboard().size() != 0) {
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/Reversi/ReversiScoreboard.json"));
			writer.write(gson.toJson(Reversi.getScoreboard()));
			writer.flush();
		}
	}

	private void deserialize () throws IOException {
		initGsonAndItsBuilder();
		// gamers
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Gamer.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Gamer.setGamers(gson.fromJson(json, (Type) LinkedList.class));
		}
		// admins
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Admin.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Admin.setAdmin(gson.fromJson(json, (Type) Admin.class));
		}
		// admin game recommendations
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				AdminGameReco.setRecommendations(gson.fromJson(json, (Type) LinkedList.class));
		}
		// events
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Event.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Event.setEvents(gson.fromJson(json, (Type) LinkedList.class));
		}
		// frnd req's
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				FriendRequest.setAllfriendRequests(gson.fromJson(json, (Type) LinkedList.class));
		}
		// messages
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Message.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Message.setAllMessages(gson.fromJson(json, (Type) LinkedList.class));
		}
		// game list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/BattleSeaGameList.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Game.setAllGames(gson.fromJson(json, (Type) LinkedList.class));
		}
		// battlesea scoreboard
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/BattleSea/BattleSeaScoreboard.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				BattleSea.setScoreboard(gson.fromJson(json, (Type) LinkedList.class));
		}
		// reversi scoreboard
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/Reversi/ReversiScoreboard.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() != 0)
				Reversi.setScoreboard(gson.fromJson(json, (Type) LinkedList.class));
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
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gson = gsonBuilder.create();
	}

	public void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			try {
				mainController.serialize();
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