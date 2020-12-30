package View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _10GamesMenu extends Menu {
	protected _10GamesMenu () {
		super("Games Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Open BattleSea Game Menu",
				"Open Reversi Game Menu"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		super.enter();
		// todo display games list
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
