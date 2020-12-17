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
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return true;
	}
}
