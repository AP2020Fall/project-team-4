package IndependentClasses;

import AccountRelated.Account;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Interactor {

	// regexes fixme
	 static final String
			// for account menu
			gotoAccMenu = "View account menu",
			back = "back", // todo
			viewPersonalInfo = "View personal info",
			changePW = "Change password <current_pass>, <new_pass>",
			editAccField = "Edit <field>, <new_value>",
			viewStats = "View plato statistics",
			viewGamingHistory = "Games history",
			gameStatsForGame = "GameRelated.Game statistics <game_name>",
			logout = "Logout",
	// for gamer main menu
	showPts = "Show Points",
			viewFavGames = "View favorite games",
			viewAdminMsgs = "View platobot’s messages",
			lastGamePlayed = "View last played",
			viewAdminRecos = "View admin’s suggestions",
			chooseAdminReco = "Choose suggested game",
			sendFrndReqFromMainMenu = "Add friend",
	// for admin main menu
	addEvent = "Add event <game_name>, <start_date>, <end_date>, <score>",
			viewEvents = "View events",
			editEvent = "Edit event <event_id>, <field>, <new_value>",
			removeEvent = "Remove event <event_id>",
			addReco = "Add suggestion <username>, <game_name>",
			viewRecos = "View suggestions",
			removeReco = "Remove suggestion <suggestion_id>",
			viewAccs = "View users",
			viewAcc = "View user profile <username>",
	// for games menu
	openGame = "Open <game_name>",
	// for friends menu
	showFrnds = "Show friends",
			removeFrnd = "Remove <username>",
			viewFrnd = "View user profile <username>",
			sendFrndReqFromFrndMenu = "Add <username>",
			showFrndReqs = "Show friend requests",
			acceptFrndReq = "Accept <username>",
			declineFrndReq = "Decline <username>",
	// for register-login menu
	register = "Register < username, password>",
	deleteAcc = "￼Delete <username>",
			login = "Login <username>",
	// for game menu
	scoreBoard = "Show scoreboard",
			howToPlay = "Details",
			showLog = "Show log", // todo
			showWins = "Show wins count", // todo
			showGameCount = "Show played count", // todo
			addToFaves = "Add to favorites", // todo
			play = "Run game", // todo
			showPtsGained = "Show points"; // todo

	private static Menu currentMenu = Menu.REGISTER_LOGIN_MENU; // FIXME: initialize
	private static Account accInUse = null;

	// io stuff
	private static String command;
	private static Scanner scanner = new Scanner(System.in);

	public static void main (String[] args) {

		while (scanner.hasNext()) {
			command = scanner.nextLine().trim();

			if (command.matches(gotoAccMenu)) gotoAccMenuCommand();
			switch (currentMenu) {

			}

			// TODO bunch of ifs to check input
		}
	}

	private static void gotoAccMenuCommand () {
		if (accInUse == null)
			System.out.println();
	}

	private static Matcher getMatcher (String text, String regex) {
		return Pattern.compile(regex).matcher(text);
	}
}

enum Menu {
	ACC_MENU,
	MAIN_MENU_G,
	MAIN_MENU_A,
	GAMES_MENU,
	FRIENDS_PAGE,
	REGISTER_LOGIN_MENU,
	GAME_MENU; // fixme might need adding one for each game
	private static final LinkedList<Menu> menuHistory = new LinkedList<>();

	public void newMenu () {
		menuHistory.addLast(this);
	}

	public static Menu backAndReturnBack () {
		menuHistory.removeLast();
		return menuHistory.getLast();
	}

	/**
	 *
	 * @return true if we are on our first menu
	 */
	public static boolean canBack () {
		return menuHistory.size() !=0;
	}

}

class IDGenerator {
	private static final LinkedList<String> allIDsGenerated = new LinkedList<>();

	public static String generateNext () {
		String result;
		// gets randomly generated numbers and if it hasnt been used before returns it
		do {
			result = String.format(
					"%06d", // adds zeros before the random number if it has less than 6 digits
					new Random(System.nanoTime()).nextInt((int) Math.pow(10, 6)));

		} while (allIDsGenerated.contains(result));

		allIDsGenerated.addLast(result);

		return allIDsGenerated.getLast();
	}

}