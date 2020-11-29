package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public class AdminMainMenu extends Menu {
	protected AdminMainMenu (Menu parent, HashMap<Integer, Menu> childMenus) {
		super("(Admin) Main Menu", childMenus);
	}

	@Override
	public LinkedList<String> getOptions () {
		// TODO: 11/28/2020 AD
	}
}
