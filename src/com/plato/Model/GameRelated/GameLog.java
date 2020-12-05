package Model.GameRelated;

import Model.AccountRelated.Gamer;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GameLog {
	/**
	 * @param gamer the gamer to which we want points earned in game
	 * @param gameName the game that we want the points gamer earned in
	 * @return based on the predetermined scoring system of said game, count the points from the list of all games.
	 * 				don't use ingame points. just the points determined for win, loss or draw.
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getPoints (Gamer gamer, String gameName) {
		// TODO: 11/29/2020 AD
		return 0;
	}

	/**
	 * @return number of times said gamer played said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getPlayedCount (Gamer gamer, String gameName) {
		// TODO: 11/29/2020 AD
		return 0;
	}

	/**
	 * @return number of times said game was played by anyone
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getPlayedCount (String gameName) {
		// TODO: 11/29/2020 AD
		return 0;
	}

	/**
	 * @return number of times said gamer won said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getWinCount (Gamer gamer, String gameName) {
		// TODO: 11/29/2020 AD
		return 0;
	}

	/**
	 * @return number of times said gamer lost said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getLossCount (Gamer gamer, String gameName) {
		// TODO: 11/30/2020 AD  
		return 0;
	}

	/**
	 * @return number of times said gamer tied said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static int getDrawCount (Gamer gamer, String gameName) {
		// TODO: 11/30/2020 AD
		return 0;
	}

	/**
	 * @return a list of all the gamers that played said game
	 */
	public static LinkedList<Gamer> getAllGamersWhoPlayedGame (String gameName) {
		// TODO: 11/30/2020 AD
		return null;
	}

	/**
	 * @return a LinkedList<String> of all finished games of said type(battlesea or reversi)
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static LinkedList<String> getGameHistory (String gameName) {
		LinkedList<Game> gamesHistory = (LinkedList<Game>) Game.getAllFinishedGames().stream()
				.sorted(Comparator.comparing(Game::getDateGameEnded).reversed())
				.collect(Collectors.toList());

		LinkedList<String> gamesHitoryAsStrings = new LinkedList<>();

		gamesHistory.forEach(game ->
				gamesHitoryAsStrings.add("%s, %s %d-%d %s".formatted(
						game.getDateGameEnded().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd")),
						game.getListOfPlayers().get(0).getUsername(),
						game.getScores()[0],
						game.getScores()[1],
						game.getListOfPlayers().get(1).getUsername()
				))
		);

		return gamesHitoryAsStrings;
	}

	/**
	 * @return the last finished game said player participated in
	 */
	public static String getLastGamePlayed (Gamer gamer) {
		// TODO: 12/1/2020 AD
		return null;
	}
}
