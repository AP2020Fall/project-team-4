package plato.GameRelated.BattleSea;

import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleSea extends Game {
	private static LinkedList<PlayerBattleSea> players = new LinkedList<>();

	private static String details;
	private static ArrayList<String> scoreboard = new ArrayList<>();

	private static final ArrayList<String> arrangement = new ArrayList<>(Arrays.asList(
			"size l 2 s 1 count 2",
			"size l 3 s 1 count 1",
			"size l 4 s 1 count 1",
			"size l 5 s 1 count 1",
			"size l 5 s 2 count 1"
	));

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

	public static LinkedList<Ship[]> get5RandBoards () {
		LinkedList<Ship[]> boards = new LinkedList<>();

		for (int i = 0; i < 5; ) {
			Ship[] board = getRandBoard();

			if (boards.contains(board)) continue;

			boards.addLast(board);
			i++;
		}

		return boards;
	}

	public static Ship[] getRandBoard () {
		Ship[] board = new Ship[arrangement.size() + 1];
		AtomicInteger counter = new AtomicInteger(0);

		final String arrangmentREGEX = "size l (<l>\\d) s (<s>\\d) count (<n>\\d)";

		for (String s : arrangement) {
			Matcher m = Pattern.compile(arrangmentREGEX).matcher(s); m.find();

			for (int i = 0; i < Integer.parseInt(m.group("n")); ) {
				Random random = new Random(System.currentTimeMillis());
				Ship ship = new Ship(random.nextInt(10) + 1, random.nextInt(10) + 1
						, random.nextInt(2) > 0,
						Integer.parseInt(m.group("l")), Integer.parseInt(m.group("s")));
				if (ship.isShipPosValid(board, ship.getLeftMostX(), ship.getTopMostY(), ship.isVertical())) {
					board[counter.getAndIncrement()] = ship;
					i++;
				}
			}
		}

		return board;
	}

	public static LinkedList<PlayerBattleSea> getPlayers () {
		return players;
	}
}





















