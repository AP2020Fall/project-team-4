package plato.View.AccountRelated;

import plato.Model.AccountRelated.Gamer;

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
		String list = faveGames.getFirst();
		if (faveGames.size() > 1) list += ", %s".formatted(faveGames.get(1));
		System.out.println(list);
	}
}
