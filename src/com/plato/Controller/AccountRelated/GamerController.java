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
		LinkedList<Gamer> allGamerAccounts = (LinkedList<Gamer>) Gamer.getGamers().stream()
				.sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toList());

		GamerView.getInstance().displayAllUsernames(new LinkedList<>() {{
			for (Gamer gamerAccount : allGamerAccounts) {
				add(gamerAccount.getUsername());
			}
		}});
		// todo enter submenu to be able to view a certain account's personal info
	}

	public void displayFriendsUsernames () {
		LinkedList<Gamer> playersFriendsAccounts = (LinkedList<Gamer>) ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn())
				.getFrnds().stream().sorted(Comparator.comparing(Account::getUsername))
				.collect(Collectors.toList());

		GamerView.getInstance().displayFriendsUsernames(new LinkedList<>() {{
			for (Gamer friendAccount : playersFriendsAccounts)
				add(friendAccount.getUsername());
		}});
		// todo enter submenu to be able to view a certain friend's account's peronal info or remove friend
	}

	public void removeFriend () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (!((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).frndExists(username))
					throw new FriendDoesntExistException();
				break;
			} catch (FriendDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).removeFrnd(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFrnd(username));
	}

	public void displayFriendPersonalInfo () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

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

	private static class FriendDoesntExistException extends Exception {
		public FriendDoesntExistException () {
			super("You don't have a friend with this username.");
		}
	}
}
