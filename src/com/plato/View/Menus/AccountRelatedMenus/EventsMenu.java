package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class EventsMenu extends Menu {
	private final boolean isForAdmin;

	protected EventsMenu (boolean isForAdmin) {
		super("Events Menu");
		this.isForAdmin = isForAdmin;
	}
}
