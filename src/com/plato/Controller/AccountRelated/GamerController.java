package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.GameLog;
import View.AccountRelated.GamerView;
import View.Menus.Menu;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GamerController {
	private static GamerController gamerController;

	public static GamerController getInstance () {
		if (gamerController == null)
			gamerController = new GamerController();
		return gamerController;
	}

	public void displayFaveGamesForGamer () {
		GamerView.getInstance().displayFaveGamesForGamer(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFaveGames());
	}

	public void displayAllUsernames () {
		LinkedList<Gamer> allGamerAccounts = Gamer.getGamers().stream()
				.sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toCollection(LinkedList::new));

		GamerView.getInstance().displayAllUsernames(new LinkedList<>() {{
			for (Gamer gamerAccount : allGamerAccounts) {
				add(gamerAccount.getUsername());
			}
		}});
	}

	public void displayFriendsUsernames () {
		LinkedList<String> playersFriends = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn())
				.getFrnds().stream().sorted()
				.collect(Collectors.toCollection(LinkedList::new));

		GamerView.getInstance().displayFriendsUsernames(new LinkedList<>() {{
			for (String friendUN : playersFriends)
				add(friendUN);
		}});
		if (playersFriends.size() > 0)
			MainController.enterAppropriateMenu();
	}

	public void displayUserProfileToAdmin () {
		String username;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(username))
					throw new AccountController.NoAccountExistsWithUsernameException();
				break;
			} catch (AccountController.NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Gamer user = ((Gamer) Account.getAccount(username));

		GamerView.getInstance().displayUserProfileToAdmin(
				user.getUsername(),
				user.getEmail()
		);
	}

	public void removeFriend () {
		String username;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).frndExists(username))
					throw new FriendDoesntExistException();
				break;
			} catch (FriendDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).removeFrnd(
				((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFrnd(username)
		);
	}

	public void displayFriendPersonalInfo () {
		String username;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).frndExists(username))
					throw new FriendDoesntExistException();
				break;
			} catch (FriendDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Gamer frnd = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFrnd(username);
		GamerView.getInstance().displayFriendPersonalInfo(
				frnd.getUsername(), frnd.getFirstName(), frnd.getLastName(), frnd.getDaysSinceRegistration(),
				frnd.getFaveGames(),
				GameLog.getPoints(frnd, "BattleSea"), GameLog.getWinCount(frnd, "BattleSea"),
				GameLog.getDrawCount(frnd, "BattleSea"), GameLog.getLossCount(frnd, "BattleSea"),
				GameLog.getPoints(frnd, "Reversi"), GameLog.getWinCount(frnd, "Reversi"),
				GameLog.getDrawCount(frnd, "Reversi"), GameLog.getLossCount(frnd, "Reversi")
		);
	}

	public void displayAccountStats () {
		Gamer gamer = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn());

		GamerView.getInstance().displayPlatoStats(
				gamer.getDaysSinceRegistration(),
				gamer.getFrnds().size(),
				GameLog.getWinCount(gamer, "BattleSea") + GameLog.getWinCount(gamer, "Reversi"),
				GameLog.getLossCount(gamer, "BattleSea") + GameLog.getLossCount(gamer, "Reversi"),
				GameLog.getDrawCount(gamer, "BattleSea") + GameLog.getDrawCount(gamer, "Reversi"),
				GameLog.getPoints(gamer, "BattleSea") + GameLog.getPoints(gamer, "Reversi")
		);
	}

	private static class FriendDoesntExistException extends Exception {
		public FriendDoesntExistException () {
			super("You don't have a friend with this username.");
		}
	}
}
