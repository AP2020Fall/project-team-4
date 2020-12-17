package View.GameRelated;

import View.Menus.Menu;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameView {
	private static GameView gameView;

	public static GameView getInstance () {
		if (gameView == null)
			gameView = new GameView();
		return gameView;
	}

	public void displayTurn (String gamename, boolean gameHasEnded, String currentTurnGamerUsername) {
		if (gamename.equals("Reversi") && gameHasEnded) {
			System.out.println("Game has ended.");
			return;
		}
		Menu.println("It it currently %s's turn to play".formatted(currentTurnGamerUsername));
	}

	public void displaySuccessfulFaveGameAdditionMessage (String gameName) {
		System.out.printf("%s was added to your fave games successfully%n", gameName);
	}

	public void displayGameHowToPlay (String gameDetails) {
		if (gameDetails == null || gameDetails.equals(""))
			System.out.println("Details of this game hasn't been added yet.");
		else
			System.out.println(gameDetails);
	}

	public void displayScoreboardOfGame (String gameName, LinkedList<String> scoreBoard) {
		System.out.printf("%s Scoreboard:%n", gameName);
		scoreBoard.forEach(System.out::println);
	}

	public void displayGameConclusion (String conclusion, String player1Username, String player2Username, int player1InGameScore, int player2InGameScore) {
		if (conclusion.equalsIgnoreCase("IS"))
			System.out.println("Game is still in session. Results are undetermined.");
		else {
			if (conclusion.toUpperCase().contains("D"))
				System.out.println("Game ended with Draw");
			else if (conclusion.toUpperCase().contains("W"))
				System.out.printf("Game ended with player %s winning.%n", (conclusion.contains("1") ? player1Username : player2Username));

			System.out.printf("Final in-game scores are -> %s %d - %d %s%n", player1Username, player1InGameScore, player2InGameScore, player2Username);
		}
	}

	public void displayInGameScores (String player1Username, String player2Username, int player1IngameScore, int player2IngameScore) {
		System.out.printf("In-game scores are -> %s %d - %d %s%n", player1Username, player1IngameScore, player2IngameScore, player2Username);
	}

	public void displayPrevGamesAndChooseToContinue (LinkedList<String> unfinishedGames) { // all strings in form {opponent'sUN player1 score1 score2 player2}
		if (unfinishedGames.size()==0)
			Menu.println("You don't have any unfinished games.");

		AtomicInteger counter = new AtomicInteger(1);
		unfinishedGames.forEach(unfinishedGame ->
				Menu.println("%d. Your Opponent= %s\t\t%s %s - %s %s".formatted(
						counter.getAndIncrement(),
						unfinishedGame.split(" ")[0],
						unfinishedGame.split(" ")[1], // player1
						unfinishedGame.split(" ")[2], // score1
						unfinishedGame.split(" ")[3], // player2
						unfinishedGame.split(" ")[4]  // score2
				)));
	}
}
