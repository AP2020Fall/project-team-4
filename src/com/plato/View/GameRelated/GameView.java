package plato.View.GameRelated;

import java.util.LinkedList;

public class GameView {
	public static void displayTurn () {
		// TODO: 11/28/2020 AD  
	}

	public static void displayPtsPlayerGainedFromGame (int points, String gameName) {
		System.out.println("You have earned %d points from %s.".formatted(points, gameName));
	}

	public static void displaySuccessfulFaveGameAdditionMessage (String gameName) {
		System.out.printf("%s was added to your fave games successfully%n", gameName);
	}

	public static void displayGameHowToPlay (String gameDetails) {
		System.out.println(gameDetails);
	}

	public static void displayScoreboardOfGame (String gameName, LinkedList<String> scoreBoard) {
		System.out.printf("%s Scoreboard:%n", gameName);
		scoreBoard.forEach(System.out::println);
	}
}
