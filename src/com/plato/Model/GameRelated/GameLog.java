package Model.GameRelated;

import Model.AccountRelated.Gamer;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Model.GameRelated.Game.getAllFinishedGames;

public class GameLog {
	/**
	 * @return a list of all finished games
	 */
	public static LinkedList<Game> getListOfGames (String gameName) {
		LinkedList<Game> listOfGames = new LinkedList<>();
		for (Game game : getAllFinishedGames())
			if (game.getConclusion() != GameConclusion.IN_SESSION && game.getGameName().equals(gameName))
				listOfGames.add(game);
		return listOfGames;
	}

	/**
	 * @param gamer    the gamer to which we want points earned in game
	 * @param gameName the game that we want the points gamer earned in
	 * @return based on the predetermined scoring system of said game, count the points from the list of all games.
	 */
	public static Integer getPoints (Gamer gamer, String gameName) {
		return getDrawCount(gamer, gameName) * GameConclusion.DRAW.getWinnerPoints()
				+ getWinCount(gamer, gameName) * GameConclusion.PLAYER1_WIN.getWinnerPoints()
				+ getLossCount(gamer, gameName) * GameConclusion.PLAYER2_WIN.getLoserPoints();
	}

	/**
	 * @return number of times said gamer played said game
	 */
	public static Integer getPlayedCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
				count++;
		}
		return count;
	}

	/**
	 * @return number of times said game was played by anyone
	 */
	public static Integer getPlayedCount (String gameName) {
		return getListOfGames(gameName).size();
	}


	/**
	 * @return number of times said gamer won said game
	 */
	public static Integer getWinCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getConclusion().equals(GameConclusion.PLAYER1_WIN) || game.getConclusion().equals(GameConclusion.PLAYER2_WIN))
				if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
					if (game.getWinner().getUsername().equals(gamer.getUsername()))
						count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer lost said game
	 */
	public static Integer getLossCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getConclusion().equals(GameConclusion.PLAYER1_WIN) || game.getConclusion().equals(GameConclusion.PLAYER2_WIN))
				if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
					if (!game.getWinner().getUsername().equals(gamer.getUsername()))
						count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer tied said game
	 */
	public static Integer getDrawCount (Gamer gamer, String gameName) {
		int count = 0;
		for (Game game : getListOfGames(gameName)) {
			if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
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
			Gamer gamer1 = (game.getListOfPlayers().get(0).getGamer());
			Gamer gamer2 = (game.getListOfPlayers().get(1).getGamer());

			if (listOfGamers.stream().noneMatch(gamer -> gamer.getUsername().equals(gamer1.getUsername())))
				listOfGamers.add(gamer1);

			if (listOfGamers.stream().noneMatch(gamer -> gamer.getUsername().equals(gamer2.getUsername())))
				listOfGamers.add(gamer2);
		}
		return listOfGamers;
	}

	/**
	 * @return a LinkedList<String> of all finished games of said type(battlesea or reversi)
	 */
	public static LinkedList<Game> getGameHistory (String gameName) {
		return Game.getAllFinishedGames().stream()
				.filter(game -> game.getGameName().equalsIgnoreCase(gameName))
				.sorted(Comparator.comparing(Game::getDateGameEnded).reversed())
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * @return list of all the games gamer has played
	 */
	public static LinkedList<Game> getGameHistory (Gamer gamer) {
		LinkedList<Game> gamesHistory = new LinkedList<>();
		for (Game game : getAllFinishedGames()) {
			if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
				gamesHistory.add(game);
		}
		return gamesHistory;
	}

	/**
	 * every 2 games is one level so level = playedCount/2
	 */
	public static String getGameStatic (String gameName, Gamer gamer) {
		return "Level:" + getPlayedCount(gamer, gameName) / 2 +
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
			if (game.getListOfPlayers().get(0).getGamer().getUsername().equals(gamer.getUsername()) || game.getListOfPlayers().get(1).getGamer().getUsername().equals(gamer.getUsername()))
				return game.getGameName();
		}
		return null;
	}

	public static int getLevel (Gamer gamer, String gameName) {
		return getPoints(gamer, gameName) / 2;
	}
}
