package Model.GameRelated;

import Model.AccountRelated.Gamer;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Model.GameRelated.Game.getAllFinishedGames;

public class GameLog {
	/**
	 * @param gamer the gamer to which we want points earned in game
	 * @param gameName the game that we want the points gamer earned in
	 * @return based on the predetermined scoring system of said game, count the points from the list of all games.
	 * 				don't use ingame points. just the points determined for win, loss or draw.
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPoints (Gamer gamer, String gameName) {
		// TODO: 11/29/2020 AD
		return 0;
	}

	/**
	 * @return number of times said gamer played said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPlayedCount (Gamer gamer, String gameName) {
		int count = 0;
		for(Game game : getAllFinishedGames()){
			if(game.getGameName().equals(gameName))
			    if(game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
			    	count++;
		}
		return count;
	}

	/**
	 * @return number of times said game was played by anyone
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getPlayedCount (String gameName) {
		int count = 0;
		for(Game game : getAllFinishedGames()){
			if(game.getGameName().equals(gameName)) count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer won said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getWinCount (Gamer gamer, String gameName) {
		int count = 0;
		for(Game game : getAllFinishedGames()){
			if(game.getGameName().equals(gameName) && game.getWinner().equals(gamer))
				count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer lost said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getLossCount (Gamer gamer, String gameName) {
		int count = 0;
		for(Game game : getAllFinishedGames()){
			if(game.getGameName().equals(gameName))
				if(game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
					if(!game.getWinner().equals(gamer))
						count++;
		}
		return count;
	}

	/**
	 * @return number of times said gamer tied said game
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static Integer getDrawCount (Gamer gamer, String gameName) {
		int count = 0;
		for(Game game : getAllFinishedGames()) {
			if (game.getGameName().equals(gameName))
				if(game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
					if(game.getConclusion().equals(GameConclusion.DRAW))
						count++;
		}
		return count;
	}

	/**
	 * @return a list of all the gamers that played said game
	 */
	public static LinkedList<Gamer> getAllGamersWhoPlayedGame (String gameName) {
		LinkedList<Gamer> listOfGamers = new LinkedList<>();
		for(Game game : getAllFinishedGames()){
			if(game.getGameName().equals(gameName)){
				listOfGamers.add(game.getListOfPlayers().get(0).getGamer());
			listOfGamers.add(game.getListOfPlayers().get(1).getGamer());}
		}
		return listOfGamers;
	}

	/**
	 * @return a LinkedList<String> of all finished games of said type(battlesea or reversi)
	 * 			-Be sure to use to use the finished games list instead of all of them
	 */
	public static LinkedList<String> getGameHistory (String gameName) {
		LinkedList<Game> gamesHistory = Game.getAllFinishedGames().stream()
				.sorted(Comparator.comparing(Game::getDateGameEnded).reversed())
				.collect(Collectors.toCollection(LinkedList::new));

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
		LinkedList<Game> gamesReversed = getAllFinishedGames();
		Collections.reverse(getAllFinishedGames());
		for(Game game : gamesReversed){
			if(game.getListOfPlayers().get(0).getGamer().equals(gamer) || game.getListOfPlayers().get(1).getGamer().equals(gamer))
				return game.getGameName();
		}
		return null;
	}
}
