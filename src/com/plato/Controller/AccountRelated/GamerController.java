package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Gamer;
import plato.View.AccountRelated.GamerView;

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
}
