package plato;

import plato.AccountRelated.Account;
import plato.AccountRelated.Admin;
import plato.AccountRelated.Gamer;

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

		//noinspection InfiniteLoopStatement
		while (true) {
			System.out.println(currentMenu.getMenuOptions());
			command = scanner.nextLine().trim();

			guideToCommandMethod();
		}
	}

	private static void guideToCommandMethod () {/*todo*/}

	private static Matcher getMatcher (String text, String regex) {
		return Pattern.compile(regex).matcher(text);
	}

	private static void gotoAccMenuCommand () {/*todo*/}

	private static void backCommand () {/*todo*/}

	private static void viewPersonalInfoCommand () {/*fixme*/
		System.out.println(accInUse.getPersonalInfo());
	}

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

	private static void registerCommand () {/*fixme test*/

		// trying to get username
		//		if account already exists try asking for username again
		String username = null;
		do {
			if (username != null)
				System.out.println("An Account already exists with this username.");

			System.out.print("Username: "); username = scanner.nextLine();

		} while (Account.accountExists(username));

		// trying to ask for password and full name
		System.out.print("Password: "); String password = scanner.nextLine();
		System.out.print("First Name: "); String firstName = scanner.nextLine();
		System.out.print("Last Name: "); String lastName = scanner.nextLine();

		// trying to get email
		//		if email format is invalid try asking for it again
		String email = null;
		do {
			if (email != null)
				System.out.println("Email Format is invalid.");

			System.out.print("Email Address: "); email = scanner.nextLine();

		} while (Account.isEmailOK(email));

		// trying to get phoneNum
		//		if phoneNumber format is invalid try asking for it again
		String phoneNum = null;
		do {
			if (phoneNum != null)
				System.out.println("Phone Format is invalid.");

			System.out.print("Phone Number: "); phoneNum = scanner.nextLine();

		} while (Account.isPhoneNumOK(phoneNum));

		// if admin account hasnt already been created make admin account
		// 		otherwise ask for initial money amount and make a gamer account
		if (!Admin.adminHasBeenCreated())
			new Admin(firstName, lastName, username, password, email, phoneNum);
		else {
			// trying to get initial balance
			//		if input is not a number or is negative try asking for it again
			double initMoney;
			while (true) {
				System.out.print("Initial Balance: ");
				try {
					initMoney = Double.parseDouble(scanner.nextLine());

					// initial balance cant be a negative amount
					if (initMoney < 0)
						System.out.println("Initial Balance must be a positive number.");

					else
						break;

				} catch (NumberFormatException e) {
					System.out.println("Initial Balance must be a number.");
				}
			}

			new Gamer(firstName, lastName, username, password, email, phoneNum, initMoney);
		}
	}

	private static void deleteAccCommand () {/*fixme test*/

		// trying to get username
		//		if account doesnt exists try asking for username again
		String username = null;
		do {
			if (username != null)
				System.out.println("No account exists with this username.");

			System.out.print("Username: "); username = scanner.nextLine();

		} while (!Account.accountExists(username));

		// trying to get password
		//		if password is incorrect try asking for username again
		String password = null;
		do {
			if (password != null)
				System.out.println("Password incorrect.");

			System.out.print("Username: "); password = scanner.nextLine();

		} while (!Account.getAccount(username).isPasswordCorrect(password));

		Account.removeAccount(username);
		if (accInUse.getUsername().equals(username))
			logoutCommand();
	}

	private static void loginCommand () {/*fixme test*/

		// trying to get username
		//		if account doesnt exists try asking for username again
		String username = null;
		do {
			if (username != null)
				System.out.println("No account exists with this username.");

			System.out.print("Username: "); username = scanner.nextLine();

		} while (!Account.accountExists(username));

		// trying to get password
		//		if password is incorrect try asking for username again
		String password = null;
		do {
			if (password != null)
				System.out.println("Password incorrect.");

			System.out.print("Username: "); password = scanner.nextLine();

		} while (!Account.getAccount(username).isPasswordCorrect(password));

		accInUse = Account.getAccount(username);
	}

	private static void scoreBoardCommand () {/*todo*/}

	private static void howToPlayCommand () {/*todo*/}

	private static void showLogCommand () {/*todo*/}

	private static void showWinsCommand () {/*todo*/}

	private static void showGameCountCommand () {/*todo*/}

	private static void addToFavesCommand () {/*todo*/}

	private static void playCommand () {/*todo*/}

	private static void showPtsGainedCommand () {/*todo*/}

	private static void endProgramCommand () {
		System.out.print("Are you sure? [y/n]");

		if (scanner.nextLine().toLowerCase().equals("y")) {
			writeToJSONFiles();
			System.exit(1);
		}
	}

	private static void writeToJSONFiles () {/*todo*/}

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
	 * @return false if we are on our first menu
	 */
	public static boolean canBack () {
		return menuHistory.size() != 0;
	}

	public LinkedList<String> getMenuOptions () {
		LinkedList<String> result = new LinkedList<>();

		if (this != ACC_MENU) {
			result.add("View account menu");
		}
		switch (this) {
			case ACC_MENU -> {// fixme is changing password and editing fields a submenu for viewing personal info
				result.add("View personal info");
				result.add("View plato statistics");
				result.add("Games history");
				result.add("Game statistics <game_name>");
				result.add("Logout");
			}

			case MAIN_MENU_G -> result.add("");

			case MAIN_MENU_A -> result.add("");

			case REGISTER_LOGIN_MENU -> {
				// user has to go to register menu from login menu after first registery
				if (Admin.adminHasBeenCreated())
					result.add("Register < username, password>");

				result.add(""); // FIXME: 11/19/2020 AD
			}

			case FRIENDS_PAGE -> result.add("");

			case GAMES_MENU -> result.add("");

			case GAME_MENU -> result.add("");
		}

		if (canBack())
			result.add("back");

		return result;
	}

}

