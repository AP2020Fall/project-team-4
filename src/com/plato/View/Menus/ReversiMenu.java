package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public class ReversiMenu extends Menu {
	protected ReversiMenu (Menu parent, HashMap<Integer, Menu> childMenus) {
		super("Reversi Gameplay Menu", childMenus);
	}

	@Override
	public LinkedList<String> getOptions () {
		// TODO: 11/28/2020 AD
	}
}
