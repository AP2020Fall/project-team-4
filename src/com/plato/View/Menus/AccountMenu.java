package plato.View.Menus;

import java.util.LinkedList;

public class AccountMenu extends Menu{
	private Menu entry;

	protected AccountMenu () {
		super("Account Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();

		options.add("View personal info");
		options.add("View plato statistics");
		options.add("Games history");
		options.add("Game statistics <game_name>");
		options.add("Logout");

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		entry = getMenuIn(); // in case of back from account menu go to its entry menu aka menu that was used to enter account menu.
		super.enter();
	}
}
