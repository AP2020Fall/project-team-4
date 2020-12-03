package plato.Controller.GameRelated.Reversi;

import plato.Controller.GameRelated.GameController;
import plato.Model.GameRelated.Reversi.PlayerReversi;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.GameRelated.Reversi.ReversiView;
import plato.View.Menus.Menu;

public class ReversiController {
	private static ReversiController reversiController;

	public static ReversiController getInstance () {
		if (reversiController == null)
			reversiController = new ReversiController();
		return reversiController;
	}

	public void nextTurn () {
		try {
			if (GameController.getCurrentGame().gameEnded())
				GameController.getCurrentGame().concludeGame();

			if (!((Reversi) GameController.getCurrentGame()).hasPlayerMoved() && ((Reversi) GameController.getCurrentGame()).canPlayerPlaceAnyDisks())
				throw new HasntMadeMoveInCurrentTurnException();
		} catch (HasntMadeMoveInCurrentTurnException e) {
			Menu.printErrorMessage(e.getMessage());
			return;
		}
		GameController.getCurrentGame().nextTurn();
	}

	public void placeDisk () {

		PlayerReversi currentPlayer = ((PlayerReversi) GameController.getCurrentGame().getTurnPlayer());

		String Xstr, Ystr; int x, y;
		while (true) {
			try {
				System.out.print("X [/cancel to cancel filling form: "); Xstr = Menu.getInputLine();
				System.out.print("Y [/cancel to cancel filling form: "); Ystr = Menu.getInputLine();

				if (Xstr.toLowerCase().trim().equals("/cancel") || Ystr.toLowerCase().trim().equals("/cancel")) return;

				x = Integer.parseInt(Xstr); y = Integer.parseInt(Ystr);

				if (!Reversi.checkCoordinates(x) || !Reversi.checkCoordinates(y))
					throw new InvalidCoordinateException();

				if (!((Reversi) GameController.getCurrentGame()).canPlayerPlaceDiskHere(x, y))
					throw new CantPlaceDiskHere();
				break;
			} catch (InvalidCoordinateException | CantPlaceDiskHere e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		((Reversi) GameController.getCurrentGame()).placeDisk(x, y);
	}

	public void displayAvailableCoords () {
		ReversiView.getInstance().displayAvailableCoords(((Reversi) GameController.getCurrentGame()).getAvailableCoordinates());
	}

	public void displayGrid () {
		ReversiView.getInstance().displayGrid(((Reversi) GameController.getCurrentGame()).getBoard());
	}

	public void displayPrevMoves () {
		ReversiView.getInstance().displayMoveHistory(((Reversi) GameController.getCurrentGame()).getMoves());
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
