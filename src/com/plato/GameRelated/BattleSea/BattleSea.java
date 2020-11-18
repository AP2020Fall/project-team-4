package plato.GameRelated.BattleSea;

import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.*;

public class BattleSea extends Game {
	private static LinkedList<PlayerBattleSea> players = new LinkedList<>();

	private static String details;
	private static ArrayList<String> scoreboard = new ArrayList<>();

	public BattleSea (ArrayList<Gamer> gamers) {
		super(gamers);
		players = new LinkedList<>();
		players.addLast(new PlayerBattleSea(gamers.get(0)));
		players.addLast(new PlayerBattleSea(gamers.get(1)));
	}

	@Override
	public void setScores () {
		// TODO: 11/13/2020 AD
	}

	@Override
	public void concludeGame () {
		// TODO: 11/13/2020 AD
		// set end time
		// set Conclusion
		// remove game from gamesInSession
		// add to logs
		// add to scoreboard
	}

	@Override
	public boolean gameEnded () {
		return false;
	}

	@Override
	public String[][] getBoard () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public static void addToScoreBoard () {
		// TODO: 11/13/2020 AD
	}

	public static ArrayList<String> getScoreboard () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public static void setDetails (String details) {
		BattleSea.details = details;
	}

	public static String getDetails () {
		return details;
	}

	@Override
	public String getGameName () {
		return "BattleSea";
	}

	public static LinkedList<Ship[]> get5RandBoards () {
		// TODO: 11/17/2020 AD
		return null;
	}

	public static Ship[] getRandBoard () {
		// TODO: 11/17/2020 AD
		return null;
	}

	public static LinkedList<PlayerBattleSea> getPlayers () {
		return players;
	}
}