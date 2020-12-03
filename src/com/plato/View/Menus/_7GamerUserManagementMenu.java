package plato.View.Menus;

import plato.View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class _7GamerUserManagementMenu extends Menu {
	protected _7GamerUserManagementMenu () {
		super("User Management Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"View user profile"
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
