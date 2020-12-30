package View.Menus;

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
	public void enter () {
		super.enter();
		// todo display a list of friend requests gotten and either accept or decline
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
