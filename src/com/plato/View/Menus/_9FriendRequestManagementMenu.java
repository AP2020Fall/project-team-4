package View.Menus;

import View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class _9FriendRequestManagementMenu extends Menu {
	protected _9FriendRequestManagementMenu () {
		super("Friend Request Management Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Accept Friend Request",
				"Decline Friend Request"
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
