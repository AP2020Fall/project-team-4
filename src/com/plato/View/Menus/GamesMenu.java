package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public class GamesMenu extends Menu {
	protected GamesMenu (Menu parent, HashMap<Integer, Menu> childMenus) {
		super("Games Menu", childMenus);
	}

	@Override
	public LinkedList<String> getOptions () {
		// TODO: 11/28/2020 AD  
	}
}
