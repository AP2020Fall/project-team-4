package Model.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.GameLog;
import Model.GameRelated.Reversi.Reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

public class BattleSea extends Game {
	private static String battleseaDetails;

	private static final ArrayList<String> arrangement = new ArrayList<>(Arrays.asList(
			"5 2 1", // l s n
			"5 1 1",
			"4 1 1",
			"3 1 1",
			"2 1 2"
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


	public static LinkedList<LinkedList<Ship>> get5RandBoards () {
		LinkedList<LinkedList<Ship>> boards = new LinkedList<>();

		for (int i = 0; i < 5; ) {
			LinkedList<Ship> board = getRandBoard();

			if (boards.contains(board)) continue;

			boards.addLast(board);
			i++;
		}

		return boards;
	}

	public static LinkedList<Ship> getRandBoard () {
		LinkedList<Ship> board = new LinkedList<>();

		for (String s : arrangement) {
			for (int i = 0; i < Integer.parseInt(s.split(" ")[2]); ) {
				Random random = new Random();

				int lsize = Integer.parseInt(s.split(" ")[0]),
						ssize = Integer.parseInt(s.split(" ")[1]);

				Ship ship = new Ship(random.nextInt(10) + 1, random.nextInt(10) + 1
						, random.nextInt(2) > 0,
						lsize, ssize);

				if (ship.isShipPosValid(board, ship.getLeftMostX(), ship.getTopMostY(), ship.isVertical())) {
					board.add(ship);
					i++;
				}
			}
		}

		return board;
	}

	/**
	 * 1. list all finished games of this type
	 * 2. get a list of all the players that played game with GameLog.getAllWhoPlayedGame
	 * 3. use gamelog methods to determine how many times each player won, lost or tied and also all the points he/she earned and number of times they played it
	 * 4. sort as follows -> (n:نزولی - s:صعودی)
	 * 5. 			n - points , n - wins , s - loss , s - playCount , n - draws , abc - username(not case sensitive)
	 * 6. format all this shite in string form and return the string linkedlist result
	 */
	public static LinkedList<String> getScoreboard () {
		String gameName = BattleSea.class.getSimpleName();
		LinkedList<String> scoreBoard = new LinkedList<>();
		LinkedList<Gamer> allPlayersOfBattleSea = GameLog.getAllGamersWhoPlayedGame(gameName);

		allPlayersOfBattleSea.sort((gamer1, gamer2) -> {
			int cmp;

			cmp = -GameLog.getPoints(gamer1, gameName).compareTo(GameLog.getPoints(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = -GameLog.getWinCount(gamer1, gameName).compareTo(GameLog.getWinCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = GameLog.getLossCount(gamer1, gameName).compareTo(GameLog.getLossCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = GameLog.getPlayedCount(gamer1, gameName).compareTo(GameLog.getPlayedCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = -GameLog.getDrawCount(gamer1, gameName).compareTo(GameLog.getDrawCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			return gamer1.getUsername().compareToIgnoreCase(gamer2.getUsername());
		});

		if (allPlayersOfBattleSea.size() == 0)
			scoreBoard.addLast("No one has played %s until now.".formatted(gameName));
		else
			allPlayersOfBattleSea.forEach(gamer -> {
				String username = gamer.getUsername();
				int pts = (GameLog.getPoints(gamer, gameName)),
						wins = (GameLog.getWinCount(gamer, gameName)),
						losses = (GameLog.getLossCount(gamer, gameName)),
						draws = (GameLog.getDrawCount(gamer, gameName)),
						playCount = (GameLog.getPlayedCount(gamer, gameName));

				scoreBoard.addLast("Username: %s,\tPoints: %d,\tWins: %d,\tLosses: %d,\tDraws: %d,\tPlayed Count: %d".formatted(
						username, pts, wins, losses, draws, playCount
				));
			});

		return scoreBoard;
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

	public static LinkedList<BattleSea> getAllBattleSeaGames () {
		return getAllGames().stream()
				.filter(game -> game instanceof BattleSea)
				.map(game -> ((BattleSea) game))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public boolean canStartBombing () {
		return ((PlayerBattleSea) getListOfPlayers().get(0)).getShips() != null &&
				((PlayerBattleSea) getListOfPlayers().get(1)).getShips() != null;
	}
}