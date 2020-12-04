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
			case "11B" -> menus.putIfAbsent(menuNumber, new _11GameMenu("BattleSea"));
			case "11R" -> menus.putIfAbsent(menuNumber, new _11GameMenu("Reversi"));
			case "11" -> menus.putIfAbsent(menuNumber, new _11GameMenu()); 	// from the admin game recos menu
																					// -> remove this and set to 11B or 11R based on users decision
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

				case 11, 12 -> {
					addMenu(i + "B");
					addMenu(i + "R");
				}

				default -> addMenu(String.valueOf(i));
			}
		addMenu("11");

		getMenu("2").addChildMenu(2, getMenu(3 + aORg));

		switch (aORg) {
			case "G" -> {
				// for menu 3
				getMenu("3G").addChildMenu(5, getMenu("4G"));
				getMenu("3G").addChildMenu(6, getMenu("6"));
				getMenu("3G").addChildMenu(7, getMenu("5G"));
				getMenu("3G").addChildMenu(8, getMenu("14G"));
				// for menu 4
				getMenu("4G").addChildMenu(1, getMenu("11"));
				getMenu("4G").addChildMenu(3, getMenu("14G"));
				// for menu 5
				getMenu("5G").addChildMenu(4, getMenu("14G"));
				// for menu 6
				getMenu("6").addChildMenu(1, getMenu("8"));
				getMenu("6").addChildMenu(3, getMenu("9"));
				getMenu("6").addChildMenu(5, getMenu("14G"));
				// for menu 8
				getMenu("8").addChildMenu(4, getMenu("14G"));
				// for menu 9
				getMenu("9").addChildMenu(4, getMenu("14G"));
				// for menu 10
				getMenu("10").addChildMenu(1, getMenu("11B"));
				getMenu("10").addChildMenu(2, getMenu("11R"));
				getMenu("10").addChildMenu(4, getMenu("14G"));
				// for menu 11
				getMenu("11B").addChildMenu(8, getMenu("12B"));
				getMenu("11R").addChildMenu(8, getMenu("12R"));
				getMenu("11B").addChildMenu(10, getMenu("14G"));
				getMenu("11R").addChildMenu(10, getMenu("14G"));
				// for menu 14
				getMenu("14G").addChildMenu(1, getMenu("13"));
				getMenu("14G").addChildMenu(6, getMenu("2"));
			}

			case "A" -> {
				// for menu 3
				getMenu("3A").addChildMenu(2, getMenu("5A"));
				getMenu("3A").addChildMenu(4, getMenu("4A"));
				getMenu("3A").addChildMenu(6, getMenu("7"));
				getMenu("3A").addChildMenu(7, getMenu("14A"));
				// for menu 4
				getMenu("4A").addChildMenu(3, getMenu("14A"));
				// for menu 5
				getMenu("5A").addChildMenu(4, getMenu("14A"));
				// for menu 7
				getMenu("7").addChildMenu(3, getMenu("14A"));
				// for menu 14
				getMenu("14A").addChildMenu(1, getMenu("13"));
				getMenu("14A").addChildMenu(2, getMenu("2"));
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
		if (canGoToAccMenu()) options.add("Go to Account Menu");
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
