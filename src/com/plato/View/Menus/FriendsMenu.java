package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public class FriendsMenu extends Menu {
	protected FriendsMenu (Menu parent, HashMap<Integer, Menu> childMenus) {
		super("Friends Menu", childMenus);
	}

	@Override
	public LinkedList<String> getOptions () {
		// TODO: 11/28/2020 AD
	}
}
