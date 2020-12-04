package plato.View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _14AccountMenu extends Menu {
	private Menu entry;
	private final boolean isForAdmin;

	protected _14AccountMenu (boolean isForAdmin) {
		super("Account Menu");
		this.isForAdmin = isForAdmin;
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		if (!isForAdmin) // for gamer account
			options = new LinkedList<>(Arrays.asList(
					"View personal info (w/ money)",
					"View plato statistics",
					"View Gaming History",
					"View Gaming History in BattleSea",
					"View Gaming History in Reversi",
					"Logout"
			));

		else // for admin account
			options = new LinkedList<>(Arrays.asList(
					"View personal info (w/o money)",
					"Logout"
			));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		entry = getMenuIn(); // in case of back from account menu go to its entry menu aka menu that was used to enter account menu.
		super.enter();
	}

	@Override
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return false;
	}
}
