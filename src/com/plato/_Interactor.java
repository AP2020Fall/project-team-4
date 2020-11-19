package plato;

import plato.AccountRelated.Account;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Interactor {

	private static Menu currentMenu = Menu.REGISTER_LOGIN_MENU; // FIXME: initialize
	private static Account accInUse = null;

	// io stuff
	private static String command;
	private static Scanner scanner = new Scanner(System.in);

	public static void main (String[] args) {

		while (scanner.hasNext()) {
			command = scanner.nextLine().trim();

			// TODO bunch of ifs to check input
		}
	}

	public static Matcher getMatcher (String text, String regex) {
		return Pattern.compile(regex).matcher(text);
	}

	private static void gotoAccMenuCommand () {/*todo*/}

	private static void backCommand () {/*todo*/}

	private static void viewPersonalInfoCommand () {/*todo*/}

	private static void changePWCommand () {/*todo*/}

	private static void editAccFieldCommand () {/*todo*/}

	private static void viewStatsCommand () {/*todo*/}

	private static void viewGamingHistoryCommand () {/*todo*/}

	private static void gameStatsForGameCommand () {/*todo*/}

	private static void logoutCommand () {/*todo*/}

	private static void showPtsCommand () {/*todo*/}

	private static void viewFavGamesCommand () {/*todo*/}

	private static void viewAdminMsgsCommand () {/*todo*/}

	private static void lastGamePlayedCommand () {/*todo*/}

	private static void viewAdminRecosCommand () {/*todo*/}

	private static void chooseAdminRecoCommand () {/*todo*/}

	private static void sendFrndReqFromMainMenuCommand () {/*todo*/}

	private static void addEventCommand () {/*todo*/}

	private static void viewEventsCommand () {/*todo*/}

	private static void editEventCommand () {/*todo*/}

	private static void removeEventCommand () {/*todo*/}

	private static void addRecoCommand () {/*todo*/}

	private static void viewRecosCommand () {/*todo*/}

	private static void removeRecoCommand () {/*todo*/}

	private static void viewAccsCommand () {/*todo*/}

	private static void viewAccCommand () {/*todo*/}

	private static void openGameCommand () {/*todo*/}

	private static void showFrndsCommand () {/*todo*/}

	private static void removeFrndCommand () {/*todo*/}

	private static void viewFrndCommand () {/*todo*/}

	private static void sendFrndReqFromFrndMenuCommand () {/*todo*/}

	private static void showFrndReqsCommand () {/*todo*/}

	private static void acceptFrndReqCommand () {/*todo*/}

	private static void declineFrndReqCommand () {/*todo*/}

	private static void registerCommand () {/*todo*/}

	private static void deleteAccCommand () {/*todo*/}

	private static void loginCommand () {/*todo*/}

	private static void scoreBoardCommand () {/*todo*/}

	private static void howToPlayCommand () {/*todo*/}

	private static void showLogCommand () {/*todo*/}

	private static void showWinsCommand () {/*todo*/}

	private static void showGameCountCommand () {/*todo*/}

	private static void addToFavesCommand () {/*todo*/}

	private static void playCommand () {/*todo*/}

	private static void showPtsGainedCommand () {/*todo*/}
//	 static final String
//			// for account menu
//			gotoAccMenu = "View account menu",
//			back = "back",
//			viewPersonalInfo = "View personal info",
//			changePW = "Change password <current_pass>, <new_pass>",
//			editAccField = "Edit <field>, <new_value>",
//			viewStats = "View plato statistics",
//			viewGamingHistory = "Games history",
//			gameStatsForGame = "GameRelated.Game statistics <game_name>",
//			logout = "Logout",
//	// for gamer main menu
//	showPts = "Show Points",
//			viewFavGames = "View favorite games",
//			viewAdminMsgs = "View platobot’s messages",
//			lastGamePlayed = "View last played",
//			viewAdminRecos = "View admin’s suggestions",
//			chooseAdminReco = "Choose suggested game",
//			sendFrndReqFromMainMenu = "Add friend",
//	// for admin main menu
//	addEvent = "Add event <game_name>, <start_date>, <end_date>, <score>",
//			viewEvents = "View events",
//			editEvent = "Edit event <event_id>, <field>, <new_value>",
//			removeEvent = "Remove event <event_id>",
//			addReco = "Add suggestion <username>, <game_name>",
//			viewRecos = "View suggestions",
//			removeReco = "Remove suggestion <suggestion_id>",
//			viewAccs = "View users",
//			viewAcc = "View user profile <username>",
//	// for games menu
//	openGame = "Open <game_name>",
//	// for friends menu
//	showFrnds = "Show friends",
//			removeFrnd = "Remove <username>",
//			viewFrnd = "View user profile <username>",
//			sendFrndReqFromFrndMenu = "Add <username>",
//			showFrndReqs = "Show friend requests",
//			acceptFrndReq = "Accept <username>",
//			declineFrndReq = "Decline <username>",
//	// for register-login menu
//	register = "Register < username, password>",
//			deleteAcc = "￼Delete <username>",
//			login = "Login <username>",
//	// for game menu
//	scoreBoard = "Show scoreboard",
//			howToPlay = "Details",
//			showLog = "Show log",
//			showWins = "Show wins count",
//			showGameCount = "Show played count",
//			addToFaves = "Add to favorites",
//			play = "Run game",
//			showPtsGained = "Show points";
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
	 * @return true if we are on our first menu
	 */
	public static boolean canBack () {
		return menuHistory.size() != 0;
	}

	public LinkedList<String> getMenuOptions () {
		StringBuilder result = new StringBuilder();
		if (this != ACC_MENU) {
			result.append("""
					1. View account menu
					""");
		}
		switch (this) {
			case ACC_MENU -> result.append(// fixme is changing password and editing fields a submenu for viewing personal info
					""" 
							1. View personal info
							2.￼View plato statistics
							3. Games history
							4. Game statistics <game_name>
							5. Logout
							""");

			case MAIN_MENU_G -> result.append("""
					2.
					""");
/*
			case MAIN_MENU_A -> result.append("""
					2.
					""");

			case REGISTER_LOGIN_MENU -> result.append("""
					2.
					""");

			case FRIENDS_PAGE -> result.append("""
					2.
					""");

			case GAMES_MENU -> result.append("""
					2.
					""");

			case GAME_MENU -> result.append("""
					2.
					""");
 */
		}

		result.append(
				String.format("Please enter a number from the menu%s: ",
						(canBack() ? " or \"back\" to go back" : "")));
		return new LinkedList<>(); // FIXME: 11/19/2020 AD
	}

}

