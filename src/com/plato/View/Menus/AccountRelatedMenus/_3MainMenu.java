package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class _3MainMenu extends Menu {
	private final boolean isForAdmin;

	protected _3MainMenu (boolean isForAdmin) {
		super("(%s) Main Menu".formatted(isForAdmin ? "Admin" : "Gamer"));
		this.isForAdmin = isForAdmin;
	}

	@Override
	public LinkedList<String> getOptions () {

		LinkedList<String> options;


		if (!isForAdmin) // for gamer account
			options = new LinkedList<>(Arrays.asList(
					"Show Points",
					"View Favorite games",
					"View Platobot’s messages",
					"View last played",
					"View admin’s suggestions",
					"Go to Friends Menu",
					"View Events"));

		else // for admin account
			options = new LinkedList<>(Arrays.asList(
					"Add Event",
					"View Events",
					"Add suggestion",
					"View suggestions",
					"Send message",
					"View Users"
			));

		options.addAll(super.getOptions());

		return options;
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
