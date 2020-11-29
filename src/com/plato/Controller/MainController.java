package plato.Controller;

import plato.View.Menus.Menu;

public class MainController {
	public static void main (String[] args) {
		while (true) {
		}
	}
	
	public static void writeToJSONFiles () {
		// TODO: 11/29/2020 AD  
	}

	public static void tryToExitProgram () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			MainController.writeToJSONFiles();
			System.exit(1);
		}
	}
}

