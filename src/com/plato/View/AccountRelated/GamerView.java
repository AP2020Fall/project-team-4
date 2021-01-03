package View.AccountRelated;

import java.util.LinkedList;

public class GamerView {
	private static GamerView gamerView;

	public static GamerView getInstance () {
		if (gamerView == null)
			gamerView = new GamerView();
		return gamerView;
	}

	public void displayPlatoStats (int daysSinceReg, int frndCount, int win, int loss, int draw, int pts) {
//		Menu.println("Plato age: %dd".formatted(daysSinceReg));
//		Menu.println("Number of Friends: " + frndCount);
//		Menu.println("Game Stats:");
//		Menu.println("\tPoints: %d, W: %d, L: %d, D: %d".formatted(pts, win, loss, draw));
	}

	public void displayAllUsernames (LinkedList<String> gamersUsernames) {
		System.out.println("Gamer usernames' list: ");
		gamersUsernames.forEach(gamer -> System.out.printf("\t%s%n", gamer));
	}

	public void displayUserProfileToAdmin (String username, String email) {
		System.out.printf("Username: %s\t\tEmail: %s%n", username, email);
	}

	public void displayFaveGamesForGamer (LinkedList<String> faveGames) {
		System.out.print("Your favorite games: ");
		if (faveGames.size() == 0) {
			System.out.println("-");
			return;
		}
		String list = faveGames.getFirst();
		if (faveGames.size() > 1) list += ", %s".formatted(faveGames.get(1));
		System.out.println(list);
	}

	public void displayFriendsUsernames (LinkedList<String> friendsUsernameList) {
		if (friendsUsernameList.size() == 0) {
			System.out.println("You currently don't have any friends.");
			return;
		}

		System.out.println("Friends' usernames list: ");
		friendsUsernameList.forEach(friend -> System.out.printf("\t%s%n", friend));
	}

	public void displayFriendPersonalInfo (String frndUsername, String firstName, String frndLastName, int frndDaysSinceRegistration,
										   LinkedList<String> frndFaveGames,
										   int frndBattleseaPoints, int frndBattleseaWinCount, int frndBattleseaDrawCount, int frndBattleseaLossCount,
										   int frndReversiPoints, int frndReversiWinCount, int frndReversiDrawCount, int frndReversiLossCount) {
		System.out.printf("Username: %s%n", frndUsername);
		System.out.printf("First name: %s\tLastname: %s%n", firstName, frndLastName);
		System.out.printf("Days since registration: %dd\t", frndDaysSinceRegistration);

		System.out.print("Favourite games: ");
		String faveGameList = "";
		switch (frndFaveGames.size()) {
			case 0 -> faveGameList = "-";
			case 1 -> faveGameList = frndFaveGames.getFirst();
			case 2 -> faveGameList = "%s, %s".formatted(frndFaveGames.get(0), frndFaveGames.get(1));
		}
		System.out.println(faveGameList);

		System.out.printf("Overall Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				frndBattleseaPoints + frndReversiPoints,
				frndBattleseaWinCount + frndReversiWinCount,
				frndBattleseaDrawCount + frndReversiDrawCount,
				frndBattleseaLossCount + frndReversiLossCount
		);
		System.out.printf("\t> BattleSea Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				frndBattleseaPoints,
				frndBattleseaWinCount,
				frndBattleseaDrawCount,
				frndBattleseaLossCount
		);
		System.out.printf("\t> Reversi Stats:\tScore: %d  Wins: %d  Draws: %d  Losses: %d%n",
				frndReversiPoints,
				frndReversiWinCount,
				frndReversiDrawCount,
				frndReversiLossCount
		);
	}
}
