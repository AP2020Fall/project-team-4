package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.GameLog;
import plato.View.AccountRelated.GamerView;
import plato.View.Menus.Menu;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GamerController {
	public static void displayFaveGamesForGamer () {
		GamerView.displayFaveGamesForGamer(((Gamer) AccountController.getCurrentAccLoggedIn()).getFaveGames());
	}

	public static void displayAllUsernames () {
		LinkedList<Gamer> allGamerAccounts = (LinkedList<Gamer>) Gamer.getGamers().stream()
				.sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toList());

		GamerView.displayAllUsernames(new LinkedList<>() {{
			for (Gamer gamerAccount : allGamerAccounts) {
				add(gamerAccount.getUsername());
			}
		}});
		// todo enter submenu to be able to view a certain account's personal info
	}

	public static void displayFriendsUsernames () {
		LinkedList<Gamer> playersFriendsAccounts = (LinkedList<Gamer>) ((Gamer) AccountController.getCurrentAccLoggedIn())
				.getFrnds().stream().sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toList());

		GamerView.displayFriendsUsernames(new LinkedList<>() {{
			for (Gamer friendAccount : playersFriendsAccounts)
				add(friendAccount.getUsername());
		}});
		// todo enter submenu to be able to view a certain friend's account's peronal info or remove friend
	}

	public static void removeFriend () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (!((Gamer) AccountController.getCurrentAccLoggedIn()).frndExists(username))
					throw new FriendDoesntExistException();
				break;
			} catch (FriendDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		((Gamer) AccountController.getCurrentAccLoggedIn()).removeFrnd(((Gamer) AccountController.getCurrentAccLoggedIn()).getFrnd(username));
	}

	public static void displayFriendPersonalInfo () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (!((Gamer) AccountController.getCurrentAccLoggedIn()).frndExists(username))
					throw new FriendDoesntExistException();
				break;
			} catch (FriendDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Gamer frnd = ((Gamer) AccountController.getCurrentAccLoggedIn()).getFrnd(username);
		GamerView.displayFriendPersonalInfo(
				frnd.getUsername(), frnd.getFirstName(), frnd.getLastName(), frnd.getDaysSinceRegistration(),
				frnd.getFaveGames(),
				GameLog.getPoints(frnd, "BattleSea"), GameLog.getWinCount(frnd, "BattleSea"),
				GameLog.getDrawCount(frnd, "BattleSea"), GameLog.getLossCount(frnd, "BattleSea"),
				GameLog.getPoints(frnd, "Reversi"), GameLog.getWinCount(frnd, "Reversi"),
				GameLog.getDrawCount(frnd, "Reversi"), GameLog.getLossCount(frnd, "Reversi")
		);
	}

	private static class FriendDoesntExistException extends Exception {
		public FriendDoesntExistException () {
			super("You don't have a friend with this username.");
		}
	}
}
