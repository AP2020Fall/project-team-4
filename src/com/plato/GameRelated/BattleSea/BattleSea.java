package plato.GameRelated.BattleSea;

import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BattleSea extends Game {
	private static String details;
	private static ArrayList<String> scoreboard = new ArrayList<>();
	private static LinkedList<Player> players = new LinkedList<>();

	private static final int SHIPS_COUNT = 6;

	public BattleSea (ArrayList<Gamer> gamers) {
		super(gamers);
		players.addLast(new Player(gamers.get(0)));
		players.addLast(new Player(gamers.get(1)));
	}

	@Override
	public void setScores () {
		// TODO: 11/13/2020 AD
	}

	@Override
	public void executeGame () {
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

}

class Player {
	private final Gamer gamer;
	private int score = 0;

	public Player (Gamer gamer) {
		this.gamer = gamer;
	}
}

enum Ship {

}