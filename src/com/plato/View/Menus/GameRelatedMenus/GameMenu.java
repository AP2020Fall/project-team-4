package plato.View.Menus.GameRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class GameMenu extends Menu {
	private static String gameName = "";

	public GameMenu (Class game) {
		super("Game Menu");
		gameName = game.getSimpleName();
	}

	@Override
	public void back () {
		super.back();
		gameName = "";
	}

	@Override
	public LinkedList<String> getOptions () {
		// TODO: 11/29/2020 AD
		return null;
	}

	public static String getGameName () {
		return gameName;
	}
}
