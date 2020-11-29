package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Menu {
	private final String menuTitle;
	private Menu parent;
	private HashMap<Integer, Menu> childMenus = new HashMap<>();
	protected boolean inMenu = false;

	private static LinkedList<Menu> menus = new LinkedList<>();

	protected Menu (String menuTitle) {
		this.menuTitle = menuTitle;
	}

	private static void initMenus () {
		menus.add(new RegisterMenu());
	}

	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();
		if (canBack()) options.add("Back");
		if (canGoToAccMenu()) options.add("View Account Menu");
		options.add("Exit");

		return options;
	}

	public boolean canBack () {
		return true;
	}
	
	public void back () {
		parent.enter();
	}

	public boolean canGoToAccMenu () {
		return !(this instanceof AccountMenu || this instanceof LoginRegisterMenu || this instanceof RegisterMenu);
	}

	public void enter () {
		getMenuIn().inMenu = false;
		inMenu = true;
	}

	public static Menu getMenuIn () {
		return menus.stream().filter(menu -> menu.inMenu).findAny().get();
	}

	public void setParent (Menu parent) {
		this.parent = parent;
		parent.childMenus.put(parent.childMenus.size()+1, this);
	}

	public boolean isFormType () {
		return false;
	}
}
