package View.Menus;

import java.util.Collections;
import java.util.LinkedList;

public class _7GamerUserManagementMenu extends Menu {
	protected _7GamerUserManagementMenu () {
		super("User Management Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Collections.singletonList(
				"View user profile"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		super.enter();
		// todo go display a list of gamer usernames and if a user is clicked display user info
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
