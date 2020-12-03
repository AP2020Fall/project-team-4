package plato.View.GameRelated.Reversi;

import java.util.ArrayList;
import java.util.LinkedList;

public class ReversiView {
	private static ReversiView reversiView;

	public static ReversiView getInstance () {
		if (reversiView == null)
			reversiView = new ReversiView();
		return reversiView;
	}

	public void displayGrid (String[][] board) {
		// TODO todoododoodod
	}

	public void displayMoveHistory (LinkedList<String> history) {
		// TODO: totoototoodoododoodoo
	}

	public void displayAvailableCoords (ArrayList<String> availableCoordinates) {
		// TODO TOODODOODOD
	}
}
