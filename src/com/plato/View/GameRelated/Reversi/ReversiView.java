package View.GameRelated.Reversi;

import java.util.ArrayList;
import java.util.LinkedList;

public class ReversiView {
	private static ReversiView reversiView;

	public static ReversiView getInstance () {
		if (reversiView == null)
			reversiView = new ReversiView();
		return reversiView;
	}

	/**
	 * displays game board
	 * along with coordinates
	 */
	public void displayGrid (String[][] board) {
		System.out.print(" ");
		for (int i = 1; i <= 8; i++) {
			if (i == 8) {System.out.println(" " + i);}
			else {System.out.print(" " + i);}
		}
		for (int i = 0; i < 8; i++) {
			System.out.print(i + 1);  //for coordinates
			for (int j = 0; j < 8; j++) {
				if (j == 7) {System.out.println(" " + board[i][j]);}
				else {System.out.print(" " + board[i][j]);}
			}
		}
	}

	/**
	 * displays all game history
	 */
	public void displayMoveHistory (LinkedList<String> moves) {
		if (moves.size() == 0) {
			System.out.println("No moves made yet.");
			return;
		}
		for (String move : moves)
		{
			System.out.println(move);
		}
	}

	public void displayAvailableCoords (ArrayList<String> availableCoordinates) {
		for (String coord : availableCoordinates)
			System.out.printf(
					"x = %d, y = %d%n",
					Integer.parseInt(coord.split(",")[1].trim()),
					Integer.parseInt(coord.split(",")[0].trim())
			);
	}
}
