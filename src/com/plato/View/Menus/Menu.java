package plato.View.Menus;

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

	private static void initMenus () {
		// TODO: 11/29/2020 AD
	}

	public static void displayAreYouSureMessage () {
		System.out.print("Are you sure?[y/n]  ");
	}

	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();
		if (canBack()) options.add("Back");
		if (canGoToAccMenu()) options.add("View Account Menu");
		options.add("Exit program");

		return options;
	}

	public void back () {
		parent.enter();
	}

	public abstract boolean canBack ();

	public abstract boolean canGoToAccMenu ();

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

	public static void printErrorMessage (String message) {
		System.err.println(message);
	}
}
