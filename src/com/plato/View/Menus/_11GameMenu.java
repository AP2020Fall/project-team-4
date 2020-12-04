package plato.View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _11GameMenu extends Menu {
	private static String gameName = "";

	protected _11GameMenu () {
		super("Game Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Show scoreboard",
				"Show points",
				"Details",
				"Show log of game",
				"Show wins count",
				"Show played count",
				"Add to favorites",
				"Run Game"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void back () {
		super.back();
		gameName = "";
	}

	public static String getGameName () {
		return gameName;
	}

	@Override
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return true;
	}
}
