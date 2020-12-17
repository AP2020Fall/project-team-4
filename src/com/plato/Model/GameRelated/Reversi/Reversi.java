package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

enum Direction {
	UP(0, -1),
	UP_RIGHT(1, -1),
	RIGHT(1, 0),
	DOWN_RIGHT(1, 1),
	DOWN(0, 1),
	DOWN_LEFT(-1, 1),
	LEFT(-1, 0),
	UP_LEFT(-1, -1);

	private final int deltaX, deltaY;

	Direction (int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX () {
		return deltaX;
	}

	public int getDeltaY () {
		return deltaY;
	}
}

public class Reversi extends Game {
	private static String reversiDetails;
	protected ArrayList<PlayerReversi> listOfPlayers = new ArrayList<>();

	private final String[][] board = new String[8][8];
	private final LinkedList<String> moves = new LinkedList<>();

	public Reversi (ArrayList<Gamer> gamers) {
		super();
		details = reversiDetails;

		Collections.shuffle(gamers);
		listOfPlayers.add(new PlayerReversi(gamers.get(0), "b"));
		listOfPlayers.add(new PlayerReversi(gamers.get(1), "w"));
	}

	public static boolean checkCoordinates (int number) {
		return number >= 1 && number <= 8;
	}

	public static void setDetailsForReversi (String details) {
		reversiDetails = details;
		getAllGames().stream()
				.filter(game -> game instanceof Reversi)
				.forEach(game -> game.setDetailsForIndividualGame(reversiDetails));
	}

	public static String getReversiDetails () {
		return reversiDetails;
	}

	public static LinkedList<Reversi> getAllReversiGames () {
		return getAllGames().stream()
				.filter(game -> game instanceof Reversi)
				.map(game -> ((Reversi) game))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * only used for gson, to add all the extracted reversi games
	 */
	public static void setAllGames (LinkedList<Reversi> allGames) {
		Game.getAllGames().addAll(allGames);
	}

	/**
	 * should be called after next turn
	 * if board is full or atleast one of them is 0 return new ArrayList()
	 * otherwise check every blank coordinate and if player can place there add to arraylist and return the arraylist in the end
	 *
	 * @return all x and y's in result are 1<={x or y}<=8
	 */
	public ArrayList<String> getAvailableCoordinates () {
		ArrayList<String> availableCoordinates = new ArrayList<>();
		String color = ((PlayerReversi) getTurnPlayer()).getColor();
		String otherColor = (color.equals("b")) ? "w" : "b";
		if (!isBoardFull()) {
			for (int y = 0; y < 8; y++)
				for (int x = 0; x < 8; x++)
					if (board[y][x].equals(color))
						for (Direction dir : Direction.values())
							if (checkCoordinates(y + dir.getDeltaY() + 1) && checkCoordinates(x + dir.getDeltaX() + 1)) {
								if (board[y + dir.getDeltaY()][x + dir.getDeltaX()].equals(otherColor)) {
									for (int i = y + dir.getDeltaY(), j = x + dir.getDeltaX(); checkCoordinates(i + 1) && checkCoordinates(j + 1); i += dir.getDeltaY(), j += dir.getDeltaX()) {
										if (board[i][j].equals("-")) {
											availableCoordinates.add(i + 1 + "," + (j + 1));
											break;
										}
										else if (board[i][j].equals(color)) break;
									}
								}
							}
		}
		return availableCoordinates;
	}

	/**
	 * @param x from 0 to 7 inc
	 * @param y from 0 to 7 inc
	 */
	public void placeDisk (int x, int y) {
		if (canPlayerPlaceAnyDisks()) {
			if (canPlayerPlaceDiskHere(x, y)) {
				checkDirections(x, y);
				// converting y and x to 1-8 system
				y++;
				x++;
				if (getTurnNum() == 0) {
					board[y - 1][x - 1] = "b";
					addMove(x, y, "black");
				}
				else {
					board[y - 1][x - 1] = "w";
					addMove(x, y, "white");
				}
			}
		}
	}

	/**
	 * checks for available coordinates
	 *
	 * @return true if there are possible coordinates and false if there are no coordinates available
	 */
	public boolean canPlayerPlaceAnyDisks () {
		return getAvailableCoordinates().size() != 0;
	}

	/**
	 * @param x 0<=x<=7
	 * @param y 0<=y<=7
	 * @return true if atleast one disk changes color in any direction (not the check directions method)
	 */
	public boolean canPlayerPlaceDiskHere (int x, int y) {
		return getAvailableCoordinates().contains(String.format("%d,%d", y + 1, x + 1));
	}

	/**
	 * @param x         start 0<=x<=7
	 * @param y         start 0<=y<=7
	 * @param direction direction to proceed in
	 * @return checks current color at x,y
	 * checks if there are disks with opposite color next to it
	 * returns true if after disks with oppoite color there is a same color disk in given direction
	 */
	private boolean doesAnyDiskChangeColor (int x, int y, Direction direction) {
		String color = ((PlayerReversi) getTurnPlayer()).getColor();
		String otherColor = (color.equals("b")) ? "w" : "b";

		for (Direction dir : Direction.values())
			if (dir.equals(direction))
				if (checkCoordinates(x + dir.getDeltaX() + 1) && checkCoordinates(y + dir.getDeltaY() + 1))
					if (board[y + dir.getDeltaY()][x + dir.getDeltaX()].equals(otherColor))
						for (int i = y + dir.getDeltaY(), j = x + dir.getDeltaX(); checkCoordinates(i + 1) && checkCoordinates(j + 1); i += dir.getDeltaY(), j += dir.getDeltaX())
							if (board[i][j].equals(color))
								return true;

		return false;
	}

	/**
	 * @param startx from 0 to 7
	 * @param starty from 0 to 7
	 * @param destx  from 0 to 7
	 * @param desty  from 0 to 7
	 *               moves from startx,starty in the given direction and if any disks should change color call method again but one step forward
	 */
	private void changeColor (int startx, int starty, int destx, int desty, Direction direction) {
		board[desty][destx] = ((PlayerReversi) getTurnPlayer()).getColor();
		if (doesAnyDiskChangeColor(destx, desty, direction))
			changeColor(destx, desty, destx + direction.getDeltaX(), desty + direction.getDeltaY(), direction);
	}

	/**
	 * gets called after placeDisk() to check for color changes
	 */
	private void checkDirections (int x, int y) {
		for (Direction dir : Direction.values()) {
			if (doesAnyDiskChangeColor(x, y, dir)) {
				changeColor(x, y, x + dir.getDeltaX(), y + dir.getDeltaY(), dir);
			}
		}
	}

	public void addMove (int x, int y, String color) {
		moves.addLast(color + " placed disk in coordinate (" + x + "," + y + ")");
	}

	public LinkedList<String> getMoves () {
		return moves;
	}

	private boolean isBoardFull () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].equals("-")) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * should be called before changing turns
	 */
	public boolean hasPlayerMoved () {
		if (moves.size() == 0) return false;
		else if (!canPlayerPlaceAnyDisks()) return true;
		else {
			String color = moves.getLast().substring(0, 1);

			return color.equals(((PlayerReversi) getPlayer(getTurnGamer())).getColor());
		}
	}

	//empties board , blank space is shown with -
	public void emptyBoard () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// 4 starting disks
				if (i == 3 && j == 3) {
					board[i][j] = "w";
				}
				else if (i == 3 && j == 4) {
					board[i][j] = "b";
				}
				else if (i == 4 && j == 3) {
					board[i][j] = "b";
				}
				else if (i == 4 && j == 4) {
					board[i][j] = "w";
				}
				else {
					board[i][j] = "-";
				}
			}
		}
	}

	/**
	 * @return true if board is full or one of the players has 0 disks, etc. in general true if game has ended :O
	 */
	@Override
	public boolean gameEnded () {
		if (getNumberOfBlack() == 0) return true;
		else if (getNumberOfWhite() == 0) return true;
		else return isBoardFull();
	}

	@Override
	public Gamer getWinner () {
		if (getNumberOfBlack() > getNumberOfWhite())
			return (listOfPlayers.get(0).getColor().equals("b")) ? listOfPlayers.get(0).getGamer() : listOfPlayers.get(1).getGamer();
		else if (getNumberOfBlack() < getNumberOfWhite())
			return (listOfPlayers.get(0).getColor().equals("b")) ? listOfPlayers.get(1).getGamer() : listOfPlayers.get(0).getGamer();
		else return null;

	}

	public int getNumberOfBlack () {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].equals("b")) count++;
			}
		}
		return count;
	}

	public int getNumberOfWhite () {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].equals("w")) count++;
			}
		}
		return count;
	}

	/**
	 * @return getNumberOfBlack if playerNum=1
	 * getNumberOfWhite if playerNum=2
	 */
	public int getInGameScore (int playerNum) {
		switch (playerNum) {
			case 1 -> {
				return getNumberOfBlack();
			}
			case 2 -> {
				return getNumberOfWhite();
			}
		}
		return -1; // should never happen
	}

	public String[][] getBoard () {
		return board;
	}

	public ArrayList<PlayerReversi> getListOfReversiPlayers () {
		if (listOfPlayers == null)
			listOfPlayers = new ArrayList<>();
		return listOfPlayers;
	}
}