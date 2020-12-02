package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Gamer;
import plato.View.AccountRelated.AccountView;
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

		GamerView.displayAllUsernames(allGamerAccounts);
		// todo enter submenu to be able to view a certain account's personal info
	}

	public static void displayFriendsUsernames () {
		LinkedList<Gamer> playersFriendsAccounts = (LinkedList<Gamer>) ((Gamer) AccountController.getCurrentAccLoggedIn())
				.getFrnds().stream().sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toList());

		GamerView.displayFriendsUsernames(playersFriendsAccounts);
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

		GamerView.displayFriendPersonalInfo(((Gamer) AccountController.getCurrentAccLoggedIn()).getFrnd(username));
	}

	private static class FriendDoesntExistException extends Exception {
		public FriendDoesntExistException () {
			super("You don't have a friend with this username.");
		}
	}
}
