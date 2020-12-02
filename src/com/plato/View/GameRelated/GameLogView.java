package plato.View.GameRelated;

import plato.Model.AccountRelated.Gamer;

import java.util.LinkedList;

public class GameLogView {
	public static void displayPtsPlayerEarnedFromGame(int points, String gameName) {
		System.out.printf("You have earned %d points from playing %s%n", points, gameName);
	}
	public static void displayCountForPlayerPlayingGame (int count, String gameName) {
		System.out.printf("You have played %s %d times until now%n", gameName, count);
	}
	public static void displayCountForPlayerWinningGame (int count, String gameName) {
		System.out.printf("You have won %s %d times until now%n", gameName, count);
	}

	public static void displayLogOfGame (String gameName, int allPlayedCount, LinkedList<String> gameHistory) {
		System.out.printf("%s:%n", gameName);
		System.out.printf("%tPlay count: %d%n", allPlayedCount);
		gameHistory.forEach(entry -> System.out.println("\t\t" + entry));
	}

	public static void displayAllPointsOfPlayer (int points) {
		System.out.printf("You have earned %d points from all you gameplay%n", points);
	}

	public static void displayLastGamePlayed (String gameName) {
		System.out.printf("Last game you played was %s%n", gameName);
	}
}
