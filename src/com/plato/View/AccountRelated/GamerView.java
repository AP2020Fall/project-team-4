package plato.View.AccountRelated;

import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.GameLog;
import plato.Model.GameRelated.Reversi.Reversi;

import java.util.LinkedList;

public class GamerView {
	public static void displayAccountStats () { // TODO: add Gamer obj if have to
		// TODO: 11/28/2020 AD
	}

	public static void displayGamingHistory () { // TODO: add Gamer obj if have to
		// TODO: 11/16/2020 AD
	}

	public static void displayGameStats (String gameName) { // TODO: add Gamer obj if have to
		// TODO: 11/16/2020 AD
	}

	public static void displayAllUsernames (LinkedList<Gamer> gamers) {
		System.out.println("Gamer usernames' list: ");
		gamers.forEach(gamer -> System.out.printf("%t%s%n", gamer.getUsername()));
	}

	public static void displayFaveGamesForGamer (LinkedList<String> faveGames) {
		System.out.print("Your favorite games: ");
		if (faveGames.size() == 0) {
			System.out.println("-");
			return;
		}
		String list = faveGames.getFirst();
		if (faveGames.size() > 1) list += ", %s".formatted(faveGames.get(1));
		System.out.println(list);
	}

	public static void displayFriendsUsernames (LinkedList<Gamer> friendsList) {
		System.out.println("Friends' usernames list: ");
		friendsList.forEach(friend -> System.out.printf("\t%s%n", friend.getUsername()));
	}

	public static void displayFriendPersonalInfo (Gamer frnd) {
		System.out.printf("Username: %s%n", frnd.getUsername());
		System.out.printf("First name: %s\tLastname: %s%n", frnd.getFirstName(), frnd.getLastName());
		System.out.printf("Days since registration: %dd\t", frnd.getDaysSinceRegistration());

		System.out.print("Favourite games: ");
		String faveGameList = "";
		switch (frnd.getFaveGames().size()) {
			case 0 -> faveGameList = "-";
			case 1 -> faveGameList = frnd.getFaveGames().getFirst();
			case 2 -> faveGameList = "%s, %s".formatted(frnd.getFaveGames().get(0), frnd.getFaveGames().get(1));
		}
		System.out.println(faveGameList);

		System.out.printf("Overall Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				GameLog.getPoints(frnd, BattleSea.class.getSimpleName()) + GameLog.getPoints(frnd, Reversi.class.getSimpleName()),        // pts
				GameLog.getWinCount(frnd, BattleSea.class.getSimpleName()) + GameLog.getWinCount(frnd, Reversi.class.getSimpleName()),    // wins
				GameLog.getDrawCount(frnd, BattleSea.class.getSimpleName()) + GameLog.getDrawCount(frnd, Reversi.class.getSimpleName()),// draws
				GameLog.getLossCount(frnd, BattleSea.class.getSimpleName()) + GameLog.getLossCount(frnd, Reversi.class.getSimpleName()) // losses
		);
		System.out.printf("\t> BattleSea Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				GameLog.getPoints(frnd, BattleSea.class.getSimpleName()),    // pts
				GameLog.getWinCount(frnd, BattleSea.class.getSimpleName()),    // wins
				GameLog.getDrawCount(frnd, BattleSea.class.getSimpleName()),// draws
				GameLog.getLossCount(frnd, BattleSea.class.getSimpleName()) // losses
		);
		System.out.printf("\t> Reversi Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				GameLog.getPoints(frnd, Reversi.class.getSimpleName()),    // pts
				GameLog.getWinCount(frnd, Reversi.class.getSimpleName()),    // wins
				GameLog.getDrawCount(frnd, Reversi.class.getSimpleName()),    // draws
				GameLog.getLossCount(frnd, Reversi.class.getSimpleName())    // losses
		);
	}
}
