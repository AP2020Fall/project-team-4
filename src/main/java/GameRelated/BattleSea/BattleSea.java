package GameRelated.BattleSea;

import AccountRelated.Gamer;
import GameRelated.Game;

import java.util.ArrayList;

public class BattleSea extends Game {
	private static String details;
	private static ArrayList<String> scoreboard = new ArrayList<>();

	public BattleSea (ArrayList<Gamer> players) {
		super(players);
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
		// TODO: 11/13/2020 AD
		return "";
	}

}
