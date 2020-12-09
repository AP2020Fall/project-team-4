package Model.GameRelated;

import Model.AccountRelated.Gamer;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Model.GameRelated.Game.getAllFinishedGames;

public class GameLog {


	public static LinkedList<Game> getListOfGames (String gameName) {
		LinkedList<Game> listOfGames = new LinkedList<>();
		for (Game game : getAllFinishedGames())
			if (!game.getConclusion().equals(GameConclusion.IN_SESSION) && game.getGameName().equals(gameName))
				listOfGames.add(game);
		return listOfGames;
	}

	/**
	 * @param gamer    the gamer to which we want points earned in game
	 * @param gameName the game that we want the points gamer earned in
	 * @return based on the predetermined scoring system of said game, count the points from the list of all games.
	 * don't use ingame points. just the points determined for win, loss or draw.
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPoints (Gamer gamer, String gameName) {
		return getDrawCount(gamer, gameName) * GameConclusion.DRAW.getWinnerPoints()
				+ getWinCount(gamer, gameName) * GameConclusion.PLAYER1_WIN.getWinnerPoints()
				+ getLossCount(gamer, gameName) * GameConclusion.PLAYER2_WIN.getLoserPoints();
	}

	/**
	 * @return number of times said gamer played said game
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPlayedCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
				count++;
		}
		return count;
	}

	/**
	 * @return number of times said game was played by anyone
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPlayedCount (String gameName) {
		return getListOfGames(gameName).size();
	}


	/**
	 * @return number of times said gamer won said game
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getWinCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getConclusion().equals(GameConclusion.PLAYER1_WIN) || game.getConclusion().equals(GameConclusion.PLAYER2_WIN))
				if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
					if (game.getWinner().equals(gamer))
						count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer lost said game
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getLossCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getConclusion().equals(GameConclusion.PLAYER1_WIN) || game.getConclusion().equals(GameConclusion.PLAYER2_WIN))
				if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
					if (!game.getWinner().equals(gamer))
						count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer tied said game
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getDrawCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
				if (game.getConclusion().equals(GameConclusion.DRAW))
					count++;
		}
		return count;
	}

	/**
	 * @return a list of all the gamers that played said game
	 */
	public static LinkedList<Gamer> getAllGamersWhoPlayedGame (String gameName) {
		LinkedList<Gamer> listOfGamers = new LinkedList<>();
		for (Game game : getListOfGames(gameName)) {
			listOfGamers.add(game.getListOfPlayers().get(0).getGamer());
			listOfGamers.add(game.getListOfPlayers().get(1).getGamer());
		}
		return listOfGamers;
	}

	/**
	 * @return a LinkedList<String> of all finished games of said type(battlesea or reversi)
	 * -Be sure to use to use the finished games list instead of all of them
	 */
	public static LinkedList<Game> getGameHistory (String gameName) {
		return Game.getAllFinishedGames().stream()
				.filter(game -> game.getGameName().equalsIgnoreCase(gameName))
				.sorted(Comparator.comparing(Game::getDateGameEnded).reversed())
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * @param gamer
	 * @return list of all the games gamer has played
	 */
	public static LinkedList<Game> getGameHistory (Gamer gamer) {
		LinkedList<Game> gamesHistory = new LinkedList<>();
		for (Game game : getAllFinishedGames()) {
			if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
				gamesHistory.add(game);
		}
		return gamesHistory;
	}

	/**
	 * every 2 games is one level so level = playedCount/2
	 *
	 * @param gameName
	 * @return
	 */
	public static String getGameStatic (String gameName, Gamer gamer) {
		return "Level:" + (int) getPlayedCount(gamer, gameName) / 2 +
				"played" + getPlayedCount(gamer, gameName) + "times" +
				"number of wins: " + getWinCount(gamer, gameName) +
				"number of loss: " + getLossCount(gamer, gameName);

	}

	/**
	 * @return the last finished game said player participated in
	 */
	public static String getLastGamePlayed (Gamer gamer) {
		LinkedList<Game> gamesReversed = getAllFinishedGames();
		Collections.reverse(getAllFinishedGames());
		for (Game game : gamesReversed) {
			if (game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
				return game.getGameName();
		}
		return null;
	}
}
