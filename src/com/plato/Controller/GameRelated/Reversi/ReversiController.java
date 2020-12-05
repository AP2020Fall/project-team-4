package Controller.GameRelated.Reversi;

import Controller.GameRelated.GameController;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.Reversi.ReversiView;
import View.Menus.Menu;

public class ReversiController {
	private static ReversiController reversiController;

	public static ReversiController getInstance () {
		if (reversiController == null)
			reversiController = new ReversiController();
		return reversiController;
	}

	public void nextTurn () {
		try {
			if (GameController.getInstance().getCurrentGame().gameEnded())
				GameController.getInstance().getCurrentGame().concludeGame();

			if (!((Reversi) GameController.getInstance().getCurrentGame()).hasPlayerMoved() && ((Reversi) GameController.getInstance().getCurrentGame()).canPlayerPlaceAnyDisks())
				throw new HasntMadeMoveInCurrentTurnException();
		} catch (HasntMadeMoveInCurrentTurnException e) {
			Menu.printErrorMessage(e.getMessage());
			return;
		}
		GameController.getInstance().getCurrentGame().nextTurn();
	}

	public void placeDisk () {

		PlayerReversi currentPlayer = ((PlayerReversi) GameController.getInstance().getCurrentGame().getTurnPlayer());

		String Xstr, Ystr; int x, y;
		while (true) {
			try {
				System.out.print("X [/cancel to cancel filling form: "); Xstr = Menu.getInputLine();
				System.out.print("Y [/cancel to cancel filling form: "); Ystr = Menu.getInputLine();

				if (Xstr.toLowerCase().trim().equals("/cancel") || Ystr.toLowerCase().trim().equals("/cancel")) return;

				x = Integer.parseInt(Xstr); y = Integer.parseInt(Ystr);

				if (!Reversi.checkCoordinates(x) || !Reversi.checkCoordinates(y))
					throw new InvalidCoordinateException();

				if (!((Reversi) GameController.getInstance().getCurrentGame()).canPlayerPlaceDiskHere(x, y))
					throw new CantPlaceDiskHere();
				break;
			} catch (InvalidCoordinateException | CantPlaceDiskHere e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		((Reversi) GameController.getInstance().getCurrentGame()).placeDisk(x, y);
	}

	public void displayAvailableCoords () {
		ReversiView.getInstance().displayAvailableCoords(((Reversi) GameController.getInstance().getCurrentGame()).getAvailableCoordinates());
	}

	public void displayGrid () {
		ReversiView.getInstance().displayGrid(((Reversi) GameController.getInstance().getCurrentGame()).getBoard());
	}

	public void displayPrevMoves () {
		ReversiView.getInstance().displayMoveHistory(((Reversi) GameController.getInstance().getCurrentGame()).getMoves());
	}

	private static class HasntMadeMoveInCurrentTurnException extends Exception {
		public HasntMadeMoveInCurrentTurnException () {
			super(""); // todo
		}
	}

	private static class InvalidCoordinateException extends Exception {
		public InvalidCoordinateException () {
			super("Coordinate must be inside the table");
		}
	}

	private static class CantPlaceDiskHere extends Exception {
		public CantPlaceDiskHere () {
			super("You cannot place the disk on this coordinates");
		}
	}
}
