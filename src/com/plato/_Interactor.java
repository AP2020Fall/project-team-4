package plato;

import plato.AccountRelated.*;
import plato.GameRelated.BattleSea.BattleSea;
import plato.GameRelated.Reversi.Reversi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Interactor {

	private static Menu currentMenu = Menu.REGISTER_LOGIN_MENU; // FIXME: initialize
	private static Account accInUse = null;
	private static Class gameMenuName = null;

	// io stuff
	private static int command;
	private static Scanner scanner = new Scanner(System.in);

	public static void main (String[] args) {

		while (true) {
			System.out.println(currentMenu.getNumerizedMenuOptions());
			command = Integer.parseInt(scanner.nextLine().trim());

			guideToCommandMethod();

			for (int i = 0; i < 3; i++) System.out.println();
		}
	}

	private static void guideToCommandMethod () {/*todo*/
		if (command == currentMenu.getMenuOptions().size())
			endProgramCommand();

		else if (currentMenu.canGoToAccMenu() && command == currentMenu.getMenuOptions().size() - 1)
			gotoAccMenuCommand();

		else if (Menu.canBack()) {
			if (currentMenu.canGoToAccMenu() && command == currentMenu.getMenuOptions().size() - 2)
				backCommand();
			else if (!currentMenu.canGoToAccMenu() && command == currentMenu.getMenuOptions().size() - 1)
				backCommand();
		}

		switch (currentMenu) {
			case REGISTER_LOGIN_MENU -> {
				if (command == 1) registerCommand();
				if (Admin.adminHasBeenCreated()) {
					if (command == 2) loginCommand();
					else if (command == 3) deleteAccCommand();
				}
			}

			case MAIN_MENU_G -> {
				if (!Menu.isInASubMenu())
					switch (command) {
						case 1 -> showPtsCommand();
						case 2 -> viewFavGamesCommand();
						case 3 -> viewAdminMsgsCommand();
						case 4 -> lastGamePlayedCommand();
						case 5 -> viewAdminRecosCommand();
						case 6 -> sendFrndReqFromMainMenuCommand();
					}
				else if (command == 1)
					chooseAdminRecoCommand();
			}

			case MAIN_MENU_A -> {
				if (!Menu.isInASubMenu())
					switch (command) {
						case 1 -> addEventCommand();
						case 2 -> viewEventsCommand();
						case 3 -> addRecoCommand();
						case 4 -> viewAllRecosCommand();
						case 5 -> sendMsgCommand();
						case 6 -> viewAccsCommand();
					}
				else {
					switch (Menu.getSubMenuNumber()) {
						case 1 -> {
							switch (command) {
								case 1 -> editEventCommand();
								case 2 -> removeEventCommand();
							}
						}
						case 2 -> { if (command == 1) removeRecoCommand(); }
						case 3 -> { if (command == 1) viewAccCommand(); }
					}
				}
			}

			case FRIENDS_PAGE -> {
				if (!Menu.isInASubMenu())
					switch (command) {
						case 1 -> showFrndsCommand();
						case 2 -> sendFrndReqFromFrndMenuCommand();
						case 3 -> showFrndReqsCommand();
					}
				else
					switch (Menu.getSubMenuNumber()) {
						case 1 -> {
							switch (command) {
								case 1 -> removeFrndCommand();
								case 2 -> viewFrndCommand();
							}
						}
						case 2 -> {
							switch (command) {
								case 1 -> acceptFrndReqCommand();
								case 2 -> declineFrndReqCommand();
							}
						}
					}

			}

			case ACC_MENU -> {
				if (!Menu.isInASubMenu())
					switch (command) {
						case 1 -> viewAccCommand();
						case 2 -> viewStatsCommand();
						case 3 -> viewGamingHistoryCommand();
						case 4 -> gameStatsForGameCommand();
						case 5 -> logoutCommand();
					}
				else {
					switch (command) {
						case 1 -> changePWCommand();
						case 2 -> editAccFieldCommand();
					}
				}
			}

			case GAMES_MENU -> {
				if (command == 1)
					openGameCommand();
			}
		}
	}

	private static Matcher getMatcher (String text, String regex) {
		return Pattern.compile(regex).matcher(text);
	}

	private static void gotoAccMenuCommand () {/*fixme test*/
		currentMenu = Menu.gotoMenu(Menu.ACC_MENU);
	}

	private static void backCommand () {/*fixme test*/
		if (currentMenu == Menu.GAME_MENU)
			gameMenuName = null;
		currentMenu = Menu.backAndReturnBack();
	}

	private static void viewPersonalInfoCommand () {/*fixme test*/
		System.out.println(accInUse.getPersonalInfo());
	}

	private static void changePWCommand () {/*fixme test*/
		String oldPW = null;
		do {
			if (oldPW != null)
				System.out.println("Password incorrect.");

			System.out.print("Old password: "); oldPW = scanner.nextLine();

		} while (!accInUse.isPasswordCorrect(oldPW));

		System.out.print("New password: "); String newPW = scanner.nextLine();

		accInUse.editField("password", newPW);
	}

	private static void editAccFieldCommand () {/*fixme test*/
		LinkedList<String> availableFields = (LinkedList<String>) Arrays.asList(new String[]{
				"First Name",
				"Last Name",
				"Username",
				"Email",
				"Phone Number"});
		System.out.println("Choose field to edit:");
		for (String field : availableFields) {
			System.out.printf("%d. %s%n", availableFields.indexOf(field) + 1, field);
		}

		command = Integer.parseInt(scanner.nextLine().trim());

		switch (command) {
			case 1 -> {
				System.out.print("New First name: "); accInUse.editField("first name", scanner.nextLine());
				System.out.println("First name changed successfully.");
			}
			case 2 -> {
				System.out.print("New Last name: "); accInUse.editField("last name", scanner.nextLine());
				System.out.println("Last name changed successfully.");
			}
			case 3 -> {
				String newUsername = null;
				do {
					if (newUsername != null)
						System.out.println("An Account already exists with this newUsername.");

					System.out.print("New Username: "); newUsername = scanner.nextLine();

				} while (Account.accountExists(newUsername));

				accInUse.editField("username", scanner.nextLine());
				System.out.println("Username changed successfully.");
			}
			case 4 -> {
				String newEmail = null;
				do {
					if (newEmail != null)
						System.out.println("Email Format is invalid.");

					System.out.print("New email address: "); newEmail = scanner.nextLine();

				} while (!Account.isEmailOK(newEmail));
				accInUse.editField("email", newEmail);
				System.out.println("Email changed successfully.");
			}
			case 5 -> {
				String newPhoneNum = null;
				do {
					if (newPhoneNum != null)
						System.out.println("Phone Format is invalid.");

					System.out.print("New phone Number: "); newPhoneNum = scanner.nextLine();

				} while (!Account.isPhoneNumOK(newPhoneNum));
				accInUse.editField("phone num", newPhoneNum);
				System.out.println("Phone number changed successfully.");
			}
			default -> System.out.println("Invalid field.");
		}

	}

	private static void viewStatsCommand () {/*todo*/}

	private static void viewGamingHistoryCommand () {/*todo*/}

	private static void gameStatsForGameCommand () {/*todo*/}

	private static void logoutCommand () {/*fixme test*/
		if (accInUse instanceof Gamer) { // fixme check if this is necessary
			accInUse = null;
			currentMenu = Menu.REGISTER_LOGIN_MENU;
			gameMenuName = null;
		}
	}

	private static void showPtsCommand () {/*todo*/}

	private static void viewFavGamesCommand () {/*fixme test*/

		AtomicInteger count = new AtomicInteger(1);
		for (String faveGameName : ((Gamer) accInUse).getFaveGames()) {
			System.out.printf("%d. %s%n", count.getAndIncrement(), faveGameName);
		}
	}

	private static void viewAdminMsgsCommand () {/*fixme test*/
		Message.getAllMessages().sort(Comparator.comparing(Message::getDateTime)); // fixme fix sort if necessary also try reversed
		for (Message message : Message.getAllMessages())
			System.out.printf("  >%s %s%n",
					message.getDateTime().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
					message.getText()
			);
	}

	private static void lastGamePlayedCommand () {/*fixme test*/
		System.out.println(((Gamer) accInUse).getLastPlayedGame() + " was the last game you played.");
	}

	private static void viewAdminRecosCommand () {/*fixme test*/
		AtomicInteger count = new AtomicInteger(1);
		for (AdminGameReco gameReco : AdminGameReco.getRecommendations((Gamer) accInUse)) {
			System.out.printf("%d. %s%n", count.getAndIncrement(), gameReco.getGameName());
		}
		Menu.inSubMenu(1);
	}

	private static void chooseAdminRecoCommand () {/*fixme test*/
		gameMenuName = AdminGameReco.getRecommendations().get(command - 1).getClass();
		Menu.outOfSubMenu();
	}

	private static void sendFrndReqFromMainMenuCommand () {/*todo*/}

	private static void addEventCommand () {/*fixme test*/

		System.out.printf("1. %s%n", BattleSea.class.getSimpleName());
		System.out.printf("2. %s%n", Reversi.class.getSimpleName());
		int game = -1;
		do {
			if (game != -1) System.out.println("Invalid input.");
			System.out.print("Choose Game: "); game = Integer.parseInt(scanner.nextLine());
		} while (game != 1 && game != 2);

		String gameName = "";
		switch (game) {
			case 1 -> gameName = BattleSea.class.getSimpleName();
			case 2 -> gameName = Reversi.class.getSimpleName();
		}

		LocalDate start;
		while (true) {
			try {
				System.out.print("Start Date[d-MMM-yyyy]: ");
				start = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));
				if (!start.isBefore(LocalDate.now()))
					break;
				else
					System.out.println("Start date has already passed.");
			} catch (DateTimeParseException e) {
				System.out.println("Invalid start date format.");
			}
		}

		LocalDate end;
		while (true) {
			try {
				System.out.print("End Date[d-MMM-yyyy]: ");
				end = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));
				if (end.isAfter(start)) // fixme check for end.isEqual(start) if needed
					break;
				else
					System.out.println("End date must be after start date."); // fixme "or the same day as" if necessary
			} catch (DateTimeParseException e) {
				System.out.println("Invalid end date format.");
			}
		}

		double eventPrize;
		while (true) {
			try {
				System.out.print("Event Prize: "); eventPrize = Double.parseDouble(scanner.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid format.");
			}
		}

		new Event(gameName, eventPrize, start, end);
	}

	private static void viewEventsCommand () {/*todo*/
		Menu.inSubMenu(1);
	}

	private static void editEventCommand () {/*todo*/
		Menu.outOfSubMenu();
	}

	private static void removeEventCommand () {/*todo*/
		Menu.outOfSubMenu();
	}

	private static void addRecoCommand () {/*todo*/}

	private static void viewAllRecosCommand () {/*todo*/
		Menu.inSubMenu(2);
	}

	private static void removeRecoCommand () {/*todo*/
		Menu.outOfSubMenu();
	}

	private static void sendMsgCommand () {/*fixme test*/
		String text;

		gettingMsgLoop:
		while (true) {
			text = scanner.nextLine();

			while (true) {
				System.out.print("Is this all good?[y/n] "); String yn = scanner.nextLine().trim();

				if (yn.equalsIgnoreCase("y"))
					break gettingMsgLoop;
				else if (yn.equalsIgnoreCase("n"))
					break;
			}
		}
		Admin.sendMsg(text);
	}

	private static void viewAccsCommand () {/*todo*/
		Menu.inSubMenu(3);
	}

	private static void viewAccCommand () {/*todo*/
		Menu.outOfSubMenu();
	}

	private static void openGameCommand () {/*fixme test*/

		System.out.printf("1. %s%n", BattleSea.class.getSimpleName());
		System.out.printf("2. %s%n", Reversi.class.getSimpleName());

		System.out.print("Choose Game: "); int game = Integer.parseInt(scanner.nextLine());

		switch (game) {
			case 1 -> gameMenuName = BattleSea.class;
			case 2 -> gameMenuName = Reversi.class;
		}

		currentMenu = Menu.gotoMenu(Menu.GAME_MENU);
	}

	private static void showFrndsCommand () {/*fixme test*/
		for (Gamer frnd : ((Gamer) accInUse).getFrnds())
			System.out.printf("%t%s%n", frnd.getUsername());
		Menu.inSubMenu(1);
	}

	private static void removeFrndCommand () {/*fixme test*/
		String username = null;

		do {
			if (username != null)
				System.out.println("You don't have a friend with this username.");

			System.out.print("Username: "); username = scanner.nextLine();

		} while (!((Gamer) accInUse).frndExists(username));

		((Gamer) accInUse).removeFrnd(((Gamer) accInUse).getFrnd(username));

		Menu.outOfSubMenu();
	}

	private static void viewFrndCommand () {/*todo*/}

	private static void sendFrndReqFromFrndMenuCommand () {/*fixme test*/
		String usernameTo = null;

		do {
			if (usernameTo != null) {
				if (((Gamer) accInUse).frndExists(usernameTo))
					System.out.println("You already are friends with " + usernameTo);
				else if (!(Account.accountExists(usernameTo) && Account.getAccount(usernameTo) instanceof Gamer))
					System.out.println("Account doesn't exist.");
			}

			System.out.print("Username: "); usernameTo = scanner.nextLine();

		} while (((Gamer) accInUse).frndExists(usernameTo) ||
				!(Account.accountExists(usernameTo) && Account.getAccount(usernameTo) instanceof Gamer)); // fixme forgot a condition add if remmebered

		((Gamer) accInUse).sendFrndReq(usernameTo);
	}

	private static void showFrndReqsCommand () {/*fixme test*/
		for (FriendRequest friendRequest : ((Gamer) accInUse).getFriendRequestsGotten()) {
			System.out.println("\t" + friendRequest.getFrom().getUsername());
		}

		Menu.inSubMenu(2);
	}

	private static void acceptFrndReqCommand () {/*fixme test*/
		String usernameFrom = null;

		do {
			if (usernameFrom != null)
				System.out.println("You don't have a friend with this username.");

			System.out.print("Username: "); usernameFrom = scanner.nextLine();

		} while (!FriendRequest.frndReqExists(usernameFrom));

		FriendRequest.getFriendReq(((Gamer) Account.getAccount(usernameFrom)), ((Gamer) accInUse))
				.conclude(true);

		Menu.outOfSubMenu();
	}

	private static void declineFrndReqCommand () {/*fixme test*/
		String usernameFrom = null;

		do {
			if (usernameFrom != null)
				System.out.println("You don't have a friend with this username.");

			System.out.print("Username: "); usernameFrom = scanner.nextLine();

		} while (!FriendRequest.frndReqExists(usernameFrom));

		FriendRequest.getFriendReq(((Gamer) Account.getAccount(usernameFrom)), ((Gamer) accInUse))
				.conclude(false);

		Menu.outOfSubMenu();
	}

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

		} while (!Account.isEmailOK(email));

		// trying to get phoneNum
		//		if phoneNumber format is invalid try asking for it again
		String phoneNum = null;
		do {
			if (phoneNum != null)
				System.out.println("Phone Format is invalid.");

			System.out.print("Phone Number: "); phoneNum = scanner.nextLine();

		} while (!Account.isPhoneNumOK(phoneNum));

		// if admin account hasnt already been created make admin account
		// 		otherwise ask for initial money amount and make a gamer account
		if (!Admin.adminHasBeenCreated()) {
			new Admin(firstName, lastName, username, password, email, phoneNum);
			System.out.println("Admin account created successfully.");
		}
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
			System.out.println("Gamer account created successfully.");
		}
	}

	private static void deleteAccCommand () {/*fixme test*/

		// trying to get username
		//		if account doesnt exists try asking for username again
		String username = null;
		do {
			if (username != null) {
				if (!Account.accountExists(username))
					System.out.println("No account exists with this username.");
				else if (Account.getAccount(username) instanceof Admin)
					System.out.println("Admin accounts can't be deleted.");
			}

			System.out.print("Username: "); username = scanner.nextLine();

		} while (!(Account.accountExists(username) && (Account.getAccount(username) instanceof Gamer)));

		// trying to get password
		//		if password is incorrect try asking for username again
		String password = null;
		do {
			if (password != null)
				System.out.println("Password incorrect.");

			System.out.print("Password: "); password = scanner.nextLine();

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

			System.out.print("Password: "); password = scanner.nextLine();

		} while (!Account.getAccount(username).isPasswordCorrect(password));

		accInUse = Account.getAccount(username);

		if (accInUse instanceof Gamer)
			currentMenu = Menu.gotoMenu(Menu.MAIN_MENU_G);
		else
			currentMenu = Menu.gotoMenu(Menu.MAIN_MENU_A);
	}

	private static void scoreBoardCommand () {/*todo*/}

	private static void howToPlayCommand () {/*fixme test*/

		if (BattleSea.class.equals(gameMenuName)) {
			System.out.println(BattleSea.getDetails());
		}
		else if (Reversi.class.equals(gameMenuName)) {
			System.out.println(Reversi.getDetails());
		}
	}

	private static void showLogCommand () {/*todo*/}

	private static void showWinsCommand () {/*todo*/}

	private static void showGameCountCommand () {/*todo*/}

	private static void addToFavesCommand () {/*fixme test*/
		((Gamer) accInUse).addToFaveGames(gameMenuName.getSimpleName());
	}

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
	private static Integer subMenuNumber;

	public static Menu gotoMenu (Menu menu) {
		menu.newMenu();
		return menu;
	}

	public void newMenu () {
		menuHistory.addLast(this);
	}

	public static Menu backAndReturnBack () {
		if (isInASubMenu())
			outOfSubMenu();
		else
			menuHistory.removeLast();
		return menuHistory.getLast();
	}

	/**
	 * @return false if we are on our first menu
	 */
	public static boolean canBack () {
		return menuHistory.size() != 0 || isInASubMenu();
	}

	public boolean canGoToAccMenu () {
		return (this != ACC_MENU && this != REGISTER_LOGIN_MENU);
	}

	public LinkedList<String> getMenuOptions () {
		LinkedList<String> result = new LinkedList<>();
		switch (this) {
			case ACC_MENU -> {// fixme is changing password and editing fields a submenu for viewing personal info
				result.add("View personal info");
				result.add("View plato statistics");
				result.add("Games history");
				result.add("Game statistics <game_name>");
				result.add("Logout");
			}

			case MAIN_MENU_G -> {
				if (!isInASubMenu()) {
					result.add("Show Points");
					result.add("View favorite games");
					result.add("View platobot’s messages");
					result.add("View last played");
					result.add("View admin’s suggestions");
					result.add("Add friend");
				}
				else
					result.add("Choose suggested game");
			}

			case MAIN_MENU_A -> {
				if (!isInASubMenu()) {
					result.add("Add event");
					result.add("View events");
					result.add("Add suggestion");
					result.add("View suggestions");
					result.add("Send message");
					result.add("View users");
				}
				else {
					switch (subMenuNumber) {
						case 1 -> {
							result.add("Edit event");
							result.add("Remove event");
						}
						case 2 -> result.add("Remove suggestion");
						case 3 -> result.add("View user profile");
					}
				}
			}

			case REGISTER_LOGIN_MENU -> {
				// user has to go to register menu from login menu after first registery
				//		if admin account has been created user can only register
				result.add("Register");
				if (Admin.adminHasBeenCreated()) {
					result.add("Delete");
					result.add("Login");
				}
			}

			case FRIENDS_PAGE -> {
				if (!isInASubMenu()) {
					result.add("Show friends");
					result.add("Send friend request");
					result.add("Show friend requests");
				}
				else {
					switch (subMenuNumber) {
						case 1 -> {
							result.add("Remove Friend");
							result.add("View user profile");
						}
						case 2 -> {
							result.add("Accept Request");
							result.add("Decline Request");
						}
					}
				}
			}

			case GAMES_MENU -> result.add("Open game");

			case GAME_MENU -> result.add("");
		}

		if (canBack())
			result.add("back");

		if (canGoToAccMenu()) {
			result.add("View account menu");
		}

		result.add("Exit program");

		return result;
	}

	public String getNumerizedMenuOptions () {
		StringBuilder result = new StringBuilder();
		result.append(this.toString() + ":\n");
		AtomicInteger optionNum = new AtomicInteger(1);
		getMenuOptions().forEach(option -> {
			result.append(String.format("%d. %s%n", optionNum.get(), option));
			optionNum.getAndIncrement();
		});

		return result.toString();
	}

	@Override
	public String toString () {
		switch (this) {
			case FRIENDS_PAGE -> {return "Friends Page";}
			case REGISTER_LOGIN_MENU -> {
				return (!Admin.adminHasBeenCreated() ? "Register Menu" : "Register/Login Menu");
			}
			case MAIN_MENU_A, MAIN_MENU_G -> {return "Main Menu";}
			case ACC_MENU -> {return "Account Page";}
			case GAME_MENU -> {return "Game Page";}
			case GAMES_MENU -> {return "Games Menu";}
		}
		return "";
	}

	public static void inSubMenu (Integer subMenuNumber) {
		Menu.subMenuNumber = subMenuNumber;
	}

	public static void outOfSubMenu () {
		Menu.subMenuNumber = null;
	}

	public static boolean isInASubMenu () {
		return subMenuNumber != null;
	}

	public static Integer getSubMenuNumber () {
		return subMenuNumber;
	}
}

