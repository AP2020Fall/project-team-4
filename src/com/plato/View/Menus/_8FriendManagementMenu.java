package View.Menus;

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
	public void enter () {
		super.enter();
		// todo go display a list of friend usernames and if a user is clicked display friend profile
		// todo also have the option to unfriend the selected user
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
