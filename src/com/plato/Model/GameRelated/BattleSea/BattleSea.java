package Model.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;

import java.util.*;
import java.util.stream.Collectors;

public class BattleSea extends Game {
	private static final ArrayList<String> arrangement = new ArrayList<>(Arrays.asList(
			"5 2 1", // l s n
			"5 1 1",
			"4 1 1",
			"3 1 1",
			"2 1 2"
	));
	private static String battleseaDetails;
	private ArrayList<PlayerBattleSea> listOfPlayers = new ArrayList<>();

	public BattleSea (ArrayList<Gamer> gamers) {
		super();
		details = battleseaDetails;

		Collections.shuffle(gamers);
		listOfPlayers.add(new PlayerBattleSea(gamers.get(0)));
		listOfPlayers.add(new PlayerBattleSea(gamers.get(1)));

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

	public static void setDetailsForBattlesea (String details) { // fixme wont save if no games exist and we edit and restart program
		battleseaDetails = details;
		getAllGames().stream()
				.filter(game -> game instanceof BattleSea)
				.forEach(game -> game.setDetailsForIndividualGame(details));
	}

	public static String getBattleseaDetails () {
		return battleseaDetails;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean checkCoordinates (int number) {
		return number <= 10 && number >= 1;
	}

	public static LinkedList<BattleSea> getAllBattleSeaGames () {
		return getAllGames().stream()
				.filter(game -> game instanceof BattleSea)
				.map(game -> ((BattleSea) game))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * only used for gson, to add all the extracted battlesea games
	 */
	public static void setAllGames (LinkedList<BattleSea> allGames) {
		Game.getAllGames().addAll(allGames);
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
		// player1 win
		if (getInGameScore(1) > getInGameScore(2))
			return listOfPlayers.get(0).getGamer();

		// player2win
		if (getInGameScore(1) < getInGameScore(2))
			return listOfPlayers.get(1).getGamer();

		return null; // draw
	}

	@Override
	public int getInGameScore (int playerNum) {
		return ((PlayerBattleSea) getListOfPlayers().get(playerNum - 1))
				.getBombsThrown(true)
				.size();
	}

	public boolean canStartBombing () {
		return listOfPlayers.get(0).getShips() != null &&
				listOfPlayers.get(1).getShips() != null &&
				listOfPlayers.get(0).getShips().size() != 0 &&
				listOfPlayers.get(1).getShips().size() != 0;
	}

	public ArrayList<PlayerBattleSea> getListOfBattleSeaPlayers () {
		if (listOfPlayers == null)
			listOfPlayers = new ArrayList<>();
		return listOfPlayers;
	}
}