package plato.Controller;

import plato.View.Menus.Menu;

public class MainController {
	private static MainController mainController;

	public static MainController getInstance () {
		if (mainController == null)
			mainController = new MainController();
		return mainController;
	}

	public static void main (String[] args) {
		while (true) {
		}
	}

	public void writeToJSONFiles () {
		// TODO: 11/29/2020 AD  
	}

	public void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			mainController.writeToJSONFiles();
			System.exit(1);
		}
	}
}