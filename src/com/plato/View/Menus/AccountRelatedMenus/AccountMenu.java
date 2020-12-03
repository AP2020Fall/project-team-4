package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class AccountMenu extends Menu {
	private Menu entry;
	private final boolean isForAdmin;

	protected AccountMenu (boolean isForAdmin) {
		super("Account Menu");
		this.isForAdmin = isForAdmin;
	}

	@Override
	protected void enter () {
		entry = getMenuIn(); // in case of back from account menu go to its entry menu aka menu that was used to enter account menu.
		super.enter();
	}
}
