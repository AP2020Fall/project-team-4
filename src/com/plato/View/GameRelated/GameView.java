package View.GameRelated;

import View.Menus.Menu;

import java.util.LinkedList;

public class GameView {
	private static GameView gameView;

	public static GameView getInstance () {
		if (gameView == null)
			gameView = new GameView();
		return gameView;
	}

	public void displayTurn (String currentTurnGamerUsername) {
		Menu.println("It it currently %s's turn to play".formatted(currentTurnGamerUsername));
	}

	public void displayPtsPlayerGainedFromGame (int points, String gameName) {
		System.out.println("You have earned %d points from %s.".formatted(points, gameName));
	}

	public void displaySuccessfulFaveGameAdditionMessage (String gameName) {
		System.out.printf("%s was added to your fave games successfully%n", gameName);
	}

	public void displayGameHowToPlay (String gameDetails) {
		System.out.println(gameDetails);
	}

	public void displayScoreboardOfGame (String gameName, LinkedList<String> scoreBoard) {
		System.out.printf("%s Scoreboard:%n", gameName);
		scoreBoard.forEach(System.out::println);
	}

	public void displayGameConclusion (String conclusion, String player1Username, String player2Username, int player1IngameScore, int player2IngameScore) {
		if (conclusion.toUpperCase().equals("IS"))
			System.out.println("Game is still in session. Results are undetermined.");
		else {
			if (conclusion.toUpperCase().contains("D"))
				System.out.println("Game ended with Draw");
			else if (conclusion.toUpperCase().contains("W"))
				System.out.printf("Game ended with player %s winning.%n", (conclusion.contains("1") ? player1Username : player2Username));

			System.out.printf("Final in-game scores are -> %s %d - %d %s%n", player1Username, player1IngameScore, player2IngameScore, player2Username);
		}
	}

	public void displayInGameScores (String player1Username, String player2Username, int player1IngameScore, int player2IngameScore) {
		System.out.printf("In-game scores are -> %s %d - %d %s%n", player1Username, player1IngameScore, player2IngameScore, player2Username);
	}
}
