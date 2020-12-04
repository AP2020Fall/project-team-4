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
			if (menuOpts.get(command).toLowerCase().contains("register"))
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

	private void deserialize () throws IOException {
		initGsonAndItsBuilder();
		// gamers
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Gamer.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Gamer.setGamers(gson.fromJson(json, (Type) LinkedList.class));
		}
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
		// admin game recommendations
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				AdminGameReco.setRecommendations(gson.fromJson(json, (Type) LinkedList.class));
		}
		// events
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Event.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Event.setEvents(gson.fromJson(json, (Type) LinkedList.class));
		}
		// frnd req's
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				FriendRequest.setAllfriendRequests(gson.fromJson(json, (Type) LinkedList.class));
		}
		// messages
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/AccountRelated/Message.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Message.setAllMessages(gson.fromJson(json, (Type) LinkedList.class));
		}
		// BattleSea list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/BattleSea.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Game.setAllGames(gson.fromJson(json, (Type) LinkedList.class));
		}
		// Reversi list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/GameRelated/Reversi.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				Game.setAllGames(gson.fromJson(json, (Type) LinkedList.class));
		}
		// IDGenerator list
		{
			String json = "";
			BufferedReader reader = new BufferedReader(new FileReader("src/Resources/JSONs/IDGenerator.json"));
			while (reader.ready()) {
				json += reader.readLine();
			}

			if (json.length() > 2)
				IDGenerator.setAllIDsGenerated(gson.fromJson(json, (Type) LinkedList.class));
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