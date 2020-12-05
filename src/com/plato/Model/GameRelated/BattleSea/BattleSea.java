package Model.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BattleSea extends Game {
	private static String battleseaDetails;

	private static final ArrayList<String> arrangement = new ArrayList<>(Arrays.asList(
			"2 1 2", // l s n
			"3 1 1",
			"4 1 1",
			"5 1 1",
			"5 2 1"
	));

	public BattleSea (ArrayList<Gamer> gamers) {
		super(gamers);
		details = battleseaDetails;
	}

	/**
	 * @return true if board is full or one of the players has 0 disks, etc. in general true if game has ended
	 */
	@Override
	public boolean gameEnded () {
		return false;
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

		for (String s : arrangement) {

			for (int i = 0; i < Integer.parseInt(s.split(" ")[2]); ) {
				Random random = new Random(System.currentTimeMillis());

				Ship ship = new Ship(random.nextInt(10) + 1, random.nextInt(10) + 1
						, random.nextInt(2) > 0,
						Integer.parseInt(s.split(" ")[0]), // l size
						Integer.parseInt(s.split(" ")[1])); // s size

				if (ship.isShipPosValid(board, ship.getLeftMostX(), ship.getTopMostY(), ship.isVertical())) {
					board[counter.getAndIncrement()] = ship;
					i++;
				}
			}
		}

		return board;
	}

	/**
	 1. list all finished games of this type
	 2. get a list of all the players that played game with GameLog.getAllWhoPlayedGame
	 3. use gamelog methods to determine how many times each player won, lost or tied and also all the points he/she earned and number of times they played it
	 4. sort as follows -> (n:نزولی - s:صعودی)
	 5. 			n - points , n - wins , s - loss , s - playCount , n - draws , abc - username(not case sensitive)
	 6. format all this shite in string form and return	the string linkedlist result
	 */
	public static LinkedList<String> getScoreboard () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public static void setDetailsForBattlesea (String details) {
		battleseaDetails = details;
		getAllGames().stream()
				.filter(game -> game instanceof Reversi)
				.forEach(game -> game.setDetails(battleseaDetails));
	}

	public static String getBattleseaDetails () {
		return battleseaDetails;
	}

	public static boolean checkCoordinates (int number) {
		return number <= 10 && number >= 1;
	}

	public static ArrayList<BattleSea> getAllBattleSeaGames () {
		return (ArrayList<BattleSea>) getAllGames().stream()
				.filter(game -> game instanceof BattleSea)
				.map(game -> ((BattleSea) game))
				.collect(Collectors.toList());
	}
}