package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Reversi extends Game {
	private static String reversiDetails;

	private String[][] board = new String[8][8];
	private LinkedList<String> moves = new LinkedList<>();

	public Reversi (ArrayList<Gamer> gamers) {
		super(gamers);
		details = reversiDetails;
	}

	/**
	 * if board is full or atleast one of them is 0 return new ArrayList()
	 * otherwise check every blank coordinate and if player can place there add to arraylist and return the arraylist in the end
	 */
	public ArrayList<String> getAvailableCoordinates () {
		// TODO: 11/28/2020 AD  
		return null;
	}

	/**
	 * @param x from 0 to 7 inc
	 * @param y from 0 to 7 inc
	 */
	public void placeDisk (int x, int y) {
		if (canPlayerPlaceAnyDisks()) {
			if (canPlayerPlaceDiskHere(x, y)) {
				checkDirections(x, y);
				if (getTurnNum() == 0) {addMove(x, y, "black");}
				else {addMove(x, y, "white");}
			}
		}
	}

	/**
	 * checks for available coordinates
	 * @return true if there are possible coordinates and false if there are no coordinates available
	 */
	public boolean canPlayerPlaceAnyDisks(){
		return getAvailableCoordinates().size() != 0;
	}

	/**
	 * @return true if x,y is blank "-"
	 * @return true if atleast one disk changes color in any direction (not the check directions method)
	 */
	public boolean canPlayerPlaceDiskHere (int x, int y) { // fixme only checking if it is an available coordinate is good. the detailed check should be done in getAvailableCoordinates
		if (board[y][x].equals("-")) return true;
		else if(canPlayerPlaceAnyDisks()) return true;
		else return isBoardFull();
	}

	/**
	 * @param x   start 0<=x<=7
	 * @param y   start 0<=y<=7
	 * @param dir
	 * @return checks current color at x,y
	 * checks if there are disks with opposite color next to it
	 * returns true if after disks with oppoite color there is a same color disk in given direction
	 */
	private boolean doesAnyDiskChangeColor (int x, int y, Direction dir){
		switch (dir){
			case UP:
				break;
			case UP_RIGHT:
				break;
			case RIGHT:
				break;
			case DOWN_RIGHT:
				break;
			case DOWN:
				break;
			case DOWN_LEFT:
				break;
			case LEFT:
				break;
			case UP_LEFT:
				break;
		}
	return new Boolean(null); // fixme sorry gives error when trying to run
	} //todo

	/**
	 * @param startx from 0 to 7
	 * @param starty from 0 to 7
	 * @param destx  from 0 to 7
	 * @param desty  from 0 to 7
	 *               moves from startx,starty in the given direction and if any disks should change color call method again but one step forward
	 */
	private void changeColor (int startx, int starty, int destx, int desty, Direction direction) {
		board[desty][destx] = ((PlayerReversi) getPlayer(getTurnGamer())).getColor();
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
				if (board[i][j].equals("-")) {return false;}
			}
		}
		return true;
	}

	/**
	 * should be called before changing turns
	 */
	public boolean hasPlayerMoved(){
		String color = moves.getLast().substring(0,1);
		if(color.equals(((PlayerReversi) getPlayer(getTurnGamer())).getColor())) return true;
		else return false;}

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

	/**
	 * @return true if board is full or one of the players has 0 disks, etc. in general true if game has ended :O
	 */
	@Override
	public boolean gameEnded () { // fixme is true if getAvailableCoordinates list size =0
		if(getNumberOfBlack()==0) return true;
		else if(getNumberOfWhite()==0) return true;
		else return isBoardFull();
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

	private int getNumberOfBlack(){
		int count = 0;
		//TODO : straem for 2d array
		for(int i=0 ; i<8 ; i++){
			for(int j=0 ; j<8 ; j++){
				if(board[i][j].equals("b")) count++;
			}
		}
		return count;
	}

	private int getNumberOfWhite(){
		int count = 0;
		//TODO : straem for 2d array didnt find anything sorry
		for(int i=0 ; i<8 ; i++){
			for(int j=0 ; j<8 ; j++){
				if(board[i][j].equals("w")) count++;
			}
		}
		return count;
	}

	public static boolean checkCoordinates (int number) {
		return number >= 1 && number <= 8;
	}

	public static void setDetailsForReversi (String details) {
		reversiDetails = details;
		getAllGames().stream()
				.filter(game -> game instanceof Reversi)
				.forEach(game -> game.setDetails(reversiDetails));
	}

	public static String getReversiDetails () {
		return reversiDetails;
	}

	public String[][] getBoard () {
		return board;
	}

	public static ArrayList<Reversi> getAllReversiGames () {
		return (ArrayList<Reversi>) getAllGames().stream()
				.filter(game -> game instanceof Reversi)
				.map(game -> ((Reversi) game))
				.collect(Collectors.toList());
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