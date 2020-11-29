package plato.Model.GameRelated.Reversi;

import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.PlayerBattleSea;
import plato.Model.GameRelated.Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Reversi extends Game {
	private static String details;

	private static String[][] board = new String[8][8];
	private static ArrayList<String> scoreboard = new ArrayList<>(); // FIXME: remove if necessary because history of all games is there
	private ArrayList<String> moves = new ArrayList<>();
	private int numberOfWhite = 2;
	private int numberOfBlack = 2;

	public Reversi (ArrayList<Gamer> gamers) {
		super(gamers);
	}

	public ArrayList<String> getAvailableCoordinates () {
		// TODO: 11/28/2020 AD  
		return null;
	}

	public void placeDisk (int x, int y) {
		if (canPlayerPlaceAnyDisks()) {
			if (canPlayerPlaceDiskHere(x, y)) {
				checkDirections(x, y);
				if (getTurnNum() == 0) {addMove(x, y, "black");}
				else {addMove(x, y, "white");}
			}
		}
	}

	//is called after next turn
	private boolean canPlayerPlaceAnyDisks () {return true;}

	private boolean canPlayerPlaceDiskHere (int x, int y) {return true;}

	private boolean doesAnyDiskChangeColor (int x, int y, Direction dir) {return true;}

	private boolean doesAnyDiskChangeColor (int x, int y) {return true;}


	//gets start and destination and changes the color of in between disks
	private void changeColor (int startx, int starty, int destx, int desty) {
		if (startx >= destx) {
			if (starty >= desty) {
				for (int i = destx; i < startx - 1; i++) {
					for (int j = starty; j < desty - 1; i++) {
						board[i][j] = ((PlayerReversi) getPlayer(getTurnGamer())).getColor();
					}
				}
			}
			else {}
		}
		else {
			if (starty >= desty) {}
			else {}
		}
	}

	private void checkDirections (int x, int y) {
		for (Direction dir : Direction.values()) {
			if (doesAnyDiskChangeColor(x, y, dir)) {changeColor(x, y, x + dir.getDeltaX(), y + dir.getDeltaY());}
		}

	}

	public void addMove (int x, int y, String color) {
		moves.add(color + " placed disk in coordinate (" + x + "," + y + ")");
	}

	public ArrayList<String> getMoves () {
		return moves;
	}

	private boolean isBoardFull () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].equals("-")) {return false;}
			}
		}
		return true;
	}

	private boolean hasPlayerMoved () {return true;}

	public static String getDetails () {
		return details;
	}

	public static void setDetails (String details) {
		Reversi.details = details;
	}

	//empties board , blank space is shown with -
	public void emptyBoard () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// 4 starting disks
				if (i == 3 && j == 3) {board[i][j] = "w";}
				else if (i == 3 && j == 4) {board[i][j] = "b";}
				else if (i == 4 && j == 3) {board[i][j] = "b";}
				else if (i == 4 && j == 4) {board[i][j] = "w";}
				else {board[i][j] = "-";}
			}
		}
	}


	@Override
	public void setScores () {
		// TODO: 11/28/2020 AD
	}

	@Override
	public void concludeGame () {
		// set end time
		// set Conclusion
		// add to scoreboard fixme maybe not?
	}

	/**
	 * @return true if board is full or one of the players has 0 disks, etc. in general true if game has ended
	 */
	@Override
	public boolean gameEnded () {
		return false;
	}

	public static String[][] getBoard () {
		return board;
	}
}

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