package View.GameRelated;

import View.Menus.Menu;

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
		if (!gameName.equals(""))
			Menu.println("Your Gaming History of %s".formatted(gameName));
		history.forEach(entry -> Menu.println("\t\t" + entry));
	}

	public void displayLogOfGame (String gameName, int allPlayedCount, LinkedList<String> gameHistory) {
		Menu.println("%s:".formatted(gameName));
		Menu.println("\tPlay count: %d".formatted(allPlayedCount));
		gameHistory.forEach(entry -> Menu.println("\t\t" + entry));
	}

	public void displayAllPointsOfPlayer (int points) {
		Menu.println("You have earned %d points from all you gameplay".formatted(points));
	}

	public void displayLastGamePlayed (String gameName) {
		Menu.println("Last game you played was %s".formatted(gameName));
	}
}
