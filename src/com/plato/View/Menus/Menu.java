package plato.View.Menus;

import plato.Model.AccountRelated.Admin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Menu {
	private final String menuTitle;
	private Menu parent;
	private HashMap<Integer, Menu> childMenus = new HashMap<>();
	protected boolean inMenu = false;

	private static HashMap<String, Menu> menus = new HashMap<>();

	protected static final Scanner scanner = new Scanner(System.in);

	protected Menu (String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public static void addMenu (String menuNumber) {
		switch (menuNumber) {
			case "1" -> menus.putIfAbsent(menuNumber, new _1RegisterMenu(Admin.adminHasBeenCreated()));
			case "2" -> menus.putIfAbsent(menuNumber, new _2LoginRegisterMenu(true));
			case "3G" -> menus.putIfAbsent(menuNumber, new _3MainMenu(false));
			case "3A" -> menus.putIfAbsent(menuNumber, new _3MainMenu(true));
			case "4G" -> menus.putIfAbsent(menuNumber, new _4AdminGamerRecoMenu(false));
			case "4A" -> menus.putIfAbsent(menuNumber, new _4AdminGamerRecoMenu(true));
			case "5G" -> menus.putIfAbsent(menuNumber, new _5EventsMenu(false));
			case "5A" -> menus.putIfAbsent(menuNumber, new _5EventsMenu(true));
			case "6" -> menus.putIfAbsent(menuNumber, new _6FriendsMenu());
			case "7" -> menus.putIfAbsent(menuNumber, new _7GamerUserManagementMenu());
			case "8" -> menus.putIfAbsent(menuNumber, new _8FriendManagementMenu());
			case "9" -> menus.putIfAbsent(menuNumber, new _9FriendRequestManagementMenu());
			case "10" -> menus.putIfAbsent(menuNumber, new _10GamesMenu());
			case "11" -> menus.putIfAbsent(menuNumber, new _11GameMenu());
			case "12B" -> menus.putIfAbsent(menuNumber, new _12_1GameplayBattleSeaMenu());
			case "12R" -> menus.putIfAbsent(menuNumber, new _12_2GameplayReversiMenu());
			case "13" -> menus.putIfAbsent(menuNumber, new _13UserInfoEditingMenu());
			case "14G" -> menus.putIfAbsent(menuNumber, new _14AccountMenu(false));
			case "14A" -> menus.putIfAbsent(menuNumber, new _14AccountMenu(true));
		}
	}

	public static void addMenusForAdminOrGamer (String aORg) {
		if (menus.containsKey("1"))
			menus.remove("1");

		aORg = aORg.toUpperCase();

		for (int i = 3; i <= 14; i++)
			switch (i) {
				case 3, 4, 5, 14 -> addMenu(i + aORg);

				case 12 -> {
					addMenu(i + "B");
					addMenu(i + "R");
				}

				default -> addMenu(String.valueOf(i));
			}

		getMenu("2").addChildMenu(2, getMenu("3" + aORg));

		switch (aORg) {
			case "A" -> {

			}

			case "G" -> {

			}
		}
	}

	public void addChildMenu (int index, Menu child) {
		childMenus.put(index, child);
		child.parent = this;
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

	public void displayMenu () {
		for (int i = 0; i < 2; i++) System.out.println();

		System.out.println(menuTitle + ":");

		AtomicInteger optionCounter = new AtomicInteger(1);
		getOptions().forEach(opt -> System.out.printf("%d. %s%n", optionCounter.getAndIncrement(), opt));

		System.out.println();
		System.out.print("Your choice:  ");
	}

	public void back () {
		parent.enter();
	}

	public abstract boolean canBack ();

	public abstract boolean canGoToAccMenu ();

	public void enter () {
		getMenuIn().inMenu = false;
		inMenu = true;
	}

	public static Menu getMenuIn () {
		return menus.values().stream().filter(menu -> menu.inMenu).findAny().get();
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
		System.err.flush();
	}

	public static void print(String text) {
		System.out.print(text);
		System.out.flush();
	}

	public static void println(String text) {
		System.out.println(text);
		System.out.flush();
	}

	public static Menu getMenu (String menuid) {
		return menus.get(menuid);
	}

	public String getMenuID () {
		for (Map.Entry<String, Menu> menuEntry : menus.entrySet())
			if (menuEntry.getValue().menuTitle.equals(this.menuTitle))
				return menuEntry.getKey();

		return "";
	}
}
