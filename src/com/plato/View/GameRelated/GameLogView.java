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

	public void displayLogOfGame (String gameName, int allPlayedCount, LinkedList<String> gameHistory) {
		System.out.printf("%s:%n", gameName);
		System.out.printf("%tPlay count: %d%n", allPlayedCount);
		gameHistory.forEach(entry -> System.out.println("\t\t" + entry));
	}

	public void displayAllPointsOfPlayer (int points) {
		System.out.printf("You have earned %d points from all you gameplay%n", points);
	}

	public void displayLastGamePlayed (String gameName) {
		System.out.printf("Last game you played was %s%n", gameName);
	}
}
