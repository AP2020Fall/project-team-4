package plato.View.Menus.GameRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class GameMenu extends Menu {
	private static String gameName = "";

	protected GameMenu (Class game) {
		super("Game Menu");
		gameName = game.getSimpleName();
	}

	@Override
	public void back () {
		super.back();
		gameName = "";
	}

	public static String getGameName () {
		return gameName;
	}
}
