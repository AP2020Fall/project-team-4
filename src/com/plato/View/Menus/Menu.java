package plato.View.Menus;

import plato.View.Menus.AccountRelatedMenus.AccountMenu;
import plato.View.Menus.AccountRelatedMenus.LoginRegisterMenu;
import plato.View.Menus.AccountRelatedMenus.RegisterMenu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public abstract class Menu {
	private final String menuTitle;
	private Menu parent;
	private HashMap<Integer, Menu> childMenus = new HashMap<>();
	protected boolean inMenu = false;

	private static LinkedList<Menu> menus = new LinkedList<>();

	protected static final Scanner scanner = new Scanner(System.in);

	protected Menu (String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public static void displayAreYouSureMessage () {
		System.out.print("Are you sure?[y/n]  ");
	}

	private static void initMenus () {
		// TODO: 11/29/2020 AD
	}

	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();
		if (canBack()) options.add("Back");
		if (canGoToAccMenu()) options.add("View Account Menu");
		if (this instanceof LoginRegisterMenu || this instanceof RegisterMenu) options.add("Exit program");

		return options;
	}

	public boolean canBack () {
		return true;
	}

	public void back () {
		parent.enter();
	}

	public boolean canGoToAccMenu () {
		return !(this instanceof AccountMenu || this instanceof LoginRegisterMenu || this instanceof RegisterMenu || this.isFormType());
	}

	protected void enter () {
		getMenuIn().inMenu = false;
		inMenu = true;
	}

	public static Menu getMenuIn () {
		return menus.stream().filter(menu -> menu.inMenu).findAny().get();
	}

	public void setParent (Menu parent) {
		this.parent = parent;
		parent.childMenus.put(parent.childMenus.size() + 1, this);
	}

	public boolean isFormType () {
		return false;
	}

	public static Scanner getScanner () {
		return scanner;
	}

	public static String getInputLine () {
		return scanner.nextLine();
	}
}
