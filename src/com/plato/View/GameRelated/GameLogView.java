package View.GameRelated;

import java.util.LinkedList;

public class GameLogView {
	private static GameLogView gameLogView;

	public static GameLogView getInstance () {
		if (gameLogView == null)
			gameLogView = new GameLogView();
		return gameLogView;
	}

	public void displayPtsPlayerEarnedFromGame (int points, String gameName) {
		System.out.printf("You have earned %d points from playing %s%n", points, gameName);
	}

	public void displayCountForPlayerPlayingGame (int count, String gameName) {
		System.out.printf("You have played %s %d times until now%n", gameName, count);
	}

	public void displayCountForPlayerWinningGame (int count, String gameName) {
		System.out.printf("You have won %s %d times until now%n", gameName, count);
	}

	public void displayGamingHistoryOfGamer (String gameName, LinkedList<String> history) {
//		if (history.size() == 0)
//			Menu.println("You haven't played any games until now.");
//		else {
//			switch (gameName.toLowerCase()) {
//				case "" -> Menu.println("Your complete Gaming History -> ");
//				case "reversi" -> Menu.println("Your Gaming History of Reversi -> ");
//				case "battlesea" -> Menu.println("Your Gaming History of BattleSea -> ");
//			}
//			history.forEach(entry -> Menu.println("\t\t" + entry));
//		}
	}

	public void displayLogOfGame (String gameName, int allPlayedCount, LinkedList<String> gameHistory) {
		System.out.printf("%s:%n", gameName);
		System.out.printf("\tPlay count: %d%n", allPlayedCount);
		gameHistory.forEach(entry -> System.out.println("\t\t" + entry));
	}

	public void displayAllPointsOfPlayer (int points) {
//		Menu.println("You have earned %d points from all you gameplay".formatted(points));
	}

	public void displayLastGamePlayed (String gameName) {
//		Menu.println("Last game you played was %s".formatted(gameName));
	}

	public void displayPlayerStatsInGame (String gameName, int lvl, int pts, int wins, int losses, int playCount) {
//		Menu.println("Your stats in " + gameName + " -> ");
//		Menu.println("\tLevel: %d  Points: %d  Wins: %d  Losses: %d  Play Count: %d".formatted(lvl, pts, wins, losses, playCount));
	}
}
