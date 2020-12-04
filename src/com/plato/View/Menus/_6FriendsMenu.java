package plato.View.Menus;

import plato.View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class _6FriendsMenu extends Menu {
	protected _6FriendsMenu () {
		super("Friends Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Show friends",
				"Send Friend Request",
				"Show Friend Requests"
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
