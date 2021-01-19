package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
	private final String[][] board = new String[8][8];
	private final LinkedList<String> moves = new LinkedList<>();
	protected ArrayList<PlayerReversi> listOfPlayers = new ArrayList<>();
	private BooleanProperty hasMadeMoveOfTurn = new SimpleBooleanProperty(false);
	private IntegerProperty pointsW = new SimpleIntegerProperty(0),
			pointsB = new SimpleIntegerProperty(0);

	public Reversi (ArrayList<Gamer> gamers) {
		super();
		details = reversiDetails;

		Collections.shuffle(gamers);
		listOfPlayers.add(new PlayerReversi(gamers.get(0), "b"));
		listOfPlayers.add(new PlayerReversi(gamers.get(1), "w"));
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
	 * @param x from 0 to 7 inc
	 * @param y from 0 to 7 inc
	 */
	public void placeDisk (int x, int y) {
		if (canPlayerPlaceAnyDisks())
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

		if (checkCoordinates(x + direction.getDeltaX() + 1) && checkCoordinates(y + direction.getDeltaY() + 1))
			if (board[y + direction.getDeltaY()][x + direction.getDeltaX()].equals(otherColor))
				for (int i = y + direction.getDeltaY(), j = x + direction.getDeltaX(); checkCoordinates(i + 1) && checkCoordinates(j + 1); i += direction.getDeltaY(), j += direction.getDeltaX())
					if (board[i][j].equals(color))
						return true;

		return false;
	}

	@Override
	public void nextTurn () {
		super.nextTurn();
		hasMadeMoveOfTurn.set(false);
	}

	@Override
	public Gamer getWinner () {
		if (getNumberOfBlack() > getNumberOfWhite())
			return (listOfPlayers.get(0).getColor().equals("b")) ? listOfPlayers.get(0).getGamer() : listOfPlayers.get(1).getGamer();
		else if (getNumberOfBlack() < getNumberOfWhite())
			return (listOfPlayers.get(0).getColor().equals("b")) ? listOfPlayers.get(1).getGamer() : listOfPlayers.get(0).getGamer();
		else return null;

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

	public void updatePointProperties () {
		if (pointsB.get() != getNumberOfBlack())
			pointsB.set(getNumberOfBlack());
		if (pointsW.get() != getNumberOfWhite())
			pointsW.set(getNumberOfWhite());
	}

	public IntegerProperty pointsBProperty () {
		return pointsB;
	}

	public IntegerProperty pointsWProperty () {
		return pointsW;
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

	/**
	 * should be called before changing turns
	 */
	public BooleanProperty hasPlayerMoved () {
		if (moves.size() == 0) hasMadeMoveOfTurn.set(false);
		else if (!canPlayerPlaceAnyDisks()) hasMadeMoveOfTurn.set(true);
		else {
			String color = moves.getLast().substring(0, 1);

			hasMadeMoveOfTurn.set(color.equals(((PlayerReversi) getPlayer(getTurnGamer())).getColor()));
		}
		return hasMadeMoveOfTurn;
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
//		if (!isBoardFull()) {
//			for (int y = 0; y < 8; y++)
//				for (int x = 0; x < 8; x++)
//					if (board[y][x].equals(color))
//						for (Direction dir : Direction.values())
//							if (checkCoordinates(y + dir.getDeltaY() + 1) && checkCoordinates(x + dir.getDeltaX() + 1)) {
//								if (board[y + dir.getDeltaY()][x + dir.getDeltaX()].equals(otherColor)) {
//									for (int i = y + dir.getDeltaY(), j = x + dir.getDeltaX(); checkCoordinates(i + 1) && checkCoordinates(j + 1); i += dir.getDeltaY(), j += dir.getDeltaX()) {
//										if (board[i][j].equals("-")) {
//											availableCoordinates.add(i + 1 + "," + (j + 1));
//											break;
//										}
//										else if (board[i][j].equals(color)) break;
//									}
//								}
//							}
//		}
		if (isBoardFull()) return availableCoordinates;
		if (getNumberOfWhite() == 0) return availableCoordinates;
		if (getNumberOfBlack() == 0) return availableCoordinates;

		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
//				System.out.println("board[y][x] = " + board[y + 1][x + 1]);
				if (board[y][x].equals(color))
					for (Direction dir : Direction.values()) {
//						System.out.printf("Checking in direction %s%n", dir.toString().toLowerCase());
//						System.out.printf("checking neighbour at (x,y)=(%d,%d)%n", x + dir.getDeltaX() + 1, y + dir.getDeltaY() + 1);
						if (checkCoordinates(y + dir.getDeltaY() + 1) && checkCoordinates(x + dir.getDeltaX() + 1)) {
//							System.out.println("board[" + (y + dir.getDeltaY() + 1) + "][" + (x + dir.getDeltaX() + 1) + "] = " + board[y + dir.getDeltaY()][x + dir.getDeltaX()]);
							if (board[y + dir.getDeltaY()][x + dir.getDeltaX()].equals(otherColor)) {
//								System.out.println("checking step by step");
								for (int i = y + dir.getDeltaY(), j = x + dir.getDeltaX(); checkCoordinates(i + 1) && checkCoordinates(j + 1); i += dir.getDeltaY(), j += dir.getDeltaX()) {
//									System.out.printf("board[%d][%d] = %s%n", i + 1, j + 1, board[i][j]);
									if (board[i][j].equals("-")) {
										availableCoordinates.add(i + 1 + "," + (j + 1));
//										System.out.println("added to available coordinates");
										break;
									}
									else if (board[i][j].equals(color)) {
//										System.out.println("direction " + dir.toString().toLowerCase() + " is useless");
										break;
									}
								}
							}
						}
					}
			}
		return availableCoordinates;
	}

	public static boolean checkCoordinates (int number) {
		return number >= 1 && number <= 8;
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

	public String[][] getBoard () {
		return board;
	}

	public ArrayList<PlayerReversi> getListOfReversiPlayers () {
		if (listOfPlayers == null)
			listOfPlayers = new ArrayList<>();
		return listOfPlayers;
	}
}