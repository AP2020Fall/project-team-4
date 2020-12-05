package View.Menus;

import View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class _8FriendManagementMenu extends Menu {
	protected _8FriendManagementMenu () {
		super("Friend Management Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"View friend profile",
				"Remove Friend"
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
