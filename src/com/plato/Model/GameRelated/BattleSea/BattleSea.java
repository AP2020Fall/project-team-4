package Model.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

public class BattleSea extends Game {
	private static String battleseaDetails;
	private final ArrayList<PlayerBattleSea> listOfPlayers = new ArrayList<>();

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
		PlayerBattleSea player1 = (PlayerBattleSea) getListOfPlayers().get(0),
				player2 = (PlayerBattleSea) getListOfPlayers().get(1);

		return player2.getShips(false).size() == 0 || player1.getShips(false).size() == 0;
	}

	@Override
	public Gamer getWinner () {
		PlayerBattleSea player1 = (PlayerBattleSea) getListOfPlayers().get(0),
				player2 = (PlayerBattleSea) getListOfPlayers().get(1);

		if (player2.getShips(false).size() == 0)
			return getOpponentOf(player2).getGamer();
		if (player1.getShips(false).size() == 0)
			return getOpponentOf(player1).getGamer();

		return null;
	}

	@Override
	public int getInGameScore (int playerNum) {
		return ((PlayerBattleSea) getListOfPlayers().get(playerNum - 1))
				.getBombsThrown(true)
				.size();
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

	public static void setDetailsForBattlesea (String details) {
		battleseaDetails = details;
		getAllGames().stream()
				.filter(game -> game instanceof BattleSea)
				.forEach(game -> game.setDetailsForIndividualGame(details));
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

	/**
	 * only used for gson, to add all the extracted battlesea games
	 */
	public static void setAllGames (LinkedList<BattleSea> allGames) {
		Game.getAllGames().addAll(allGames);
	}

	public ArrayList<PlayerBattleSea> getListOfBattleSeaPlayers () {
		return listOfPlayers;
	}
}