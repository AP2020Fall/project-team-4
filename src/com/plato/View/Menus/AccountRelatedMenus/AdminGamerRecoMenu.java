package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class AdminGamerRecoMenu extends Menu {
	private final boolean isForAdmin;

	protected AdminGamerRecoMenu (boolean isForAdmin) {
		super("Game Suggestions Menu");
		this.isForAdmin = isForAdmin;
	}
}
