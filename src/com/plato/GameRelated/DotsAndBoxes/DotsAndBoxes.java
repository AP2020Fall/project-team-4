package plato.GameRelated.DotsAndBoxes;

import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;

public class DotsAndBoxes extends Game {
	private static String details;
	private static ArrayList<String> scoreboard = new ArrayList<>();

	private static String[][] board = new String[][]{
			{"   ", "(1)", " ", "(2)", " ", "(3)", " ", "(4)", " ", "(5)", " ", "(6)", " ", "(7)", " ", "(8)"},
			{"(1)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(2)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(3)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(4)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(5)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(6)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(7)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			{"    ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " ", "   ", " " },
			{"(8)", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O ", " ", " O "},
			};

	public DotsAndBoxes (ArrayList<Gamer> players) {
		super(players);
	}

	@Override
	public void setScores () {
		// TODO: 11/13/2020 AD
	}

	@Override
	public void executeGame () {
		// TODO: 11/14/2020 AD
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

	public static void addToScoreBoard () {
		// TODO: 11/13/2020 AD
	}

	public static ArrayList<String> getScoreboard () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public static void setDetails (String details) {
		DotsAndBoxes.details = details;
	}

	public static String getDetails () {
		return details;
	}

	@Override
	public String getGameName () {
		// TODO: 11/13/2020 AD
		return "";
	}

	public static StringBuilder getBoardAsSB () {
		StringBuilder result = new StringBuilder();

		for (String[] row : board) {
			for (String s : row)
				result.append(s);
			result.append("\n");
		}

		return result;
	}

	@Override
	public String[][] getBoard () {
		return board;
	}
}
