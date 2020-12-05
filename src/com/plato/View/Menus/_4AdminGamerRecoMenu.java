package View.Menus;

import View.Menus.Menu;

import java.util.LinkedList;

public class _4AdminGamerRecoMenu extends Menu {
	private final boolean isForAdmin;

	protected _4AdminGamerRecoMenu (boolean isForAdmin) {
		super("Game Suggestions Menu");
		this.isForAdmin = isForAdmin;
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();

		if (!isForAdmin) // for gamer account
			options.add("Choose suggested game");

		else // for admin account
			options.add("Remove suggestion");

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
