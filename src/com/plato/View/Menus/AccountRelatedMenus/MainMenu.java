package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class MainMenu extends Menu {
	private final boolean isForAdmin;

	protected MainMenu (boolean isForAdmin) {
		super("(%s) Main Menu".formatted(isForAdmin ? "Admin" : "Gamer"));
		this.isForAdmin = isForAdmin;
	}
}
