package plato.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import netscape.javascript.JSObject;
import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.*;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.Menus.Menu;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class MainController {
	private static MainController mainController;

	public static MainController getInstance () {
		if (mainController == null)
			mainController = new MainController();
		return mainController;
	}

	public static void main (String[] args) {
		getInstance().readFromJsonFiles();

		if (!Admin.adminHasBeenCreated())
			Menu.addMenu("1");

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

	private void writeToJSONFiles ()  {
		try {
			// Account.json
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Account.json"));
			writer.write(new Gson().toJson(Account.getAccounts()));
			writer.flush();

			// AdminGameReco.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/AdminGameReco.json"));
			writer.write(new Gson().toJson(AdminGameReco.getRecommendations()));
			writer.flush();

			// Event.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Event.json"));
			writer.write(new Gson().toJson(Event.getEvents()));
			writer.flush();

			// FriendRequest.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/FriendRequest.json"));
			writer.write(new Gson().toJson(FriendRequest.getAllfriendRequests()));
			writer.flush();

			// Message.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/AccountRelated/Message.json"));
			writer.write(new Gson().toJson(Message.getAllMessages()));
			writer.flush();

			// Game.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/Game.json"));
			writer.write(new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(Game.getAllGames()));
			writer.flush();

			// BattleSea.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/BattleSea/BattleSea.json"));
			writer.write(new Gson().toJson(BattleSea.getScoreboard()));
			writer.flush();

			// Reversi.json
			writer = new BufferedWriter(new FileWriter("src/Resources/JSONs/GameRelated/Reversi/Reversi.json"));
			writer.write(new Gson().toJson(Reversi.getScoreboard()));
			writer.flush();


		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void readFromJsonFiles () {
	}

	public void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			mainController.writeToJSONFiles();
			System.exit(1);
		}
	}

	private static class InvalidInputException extends Exception {
		public InvalidInputException () {
			super("Invalid Input");
		}
	}
}