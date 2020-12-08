package Controller.GameRelated.Reversi;

import Controller.GameRelated.GameController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameView;
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
			if (GameController.getInstance().getCurrentGameInSession().gameEnded()) {
				GameController.getInstance().getCurrentGameInSession().concludeGame();
				return;
			}

			if (!((Reversi) GameController.getInstance().getCurrentGameInSession()).hasPlayerMoved() && ((Reversi) GameController.getInstance().getCurrentGameInSession()).canPlayerPlaceAnyDisks())
				throw new HasntMadeMoveInCurrentTurnException();

		} catch (HasntMadeMoveInCurrentTurnException e) {
			Menu.printErrorMessage(e.getMessage());
			return;
		}
		GameController.getInstance().getCurrentGameInSession().nextTurn();
	}

	public void placeDisk () {

		PlayerReversi currentPlayer = ((PlayerReversi) GameController.getInstance().getCurrentGameInSession().getTurnPlayer());

		String Xstr, Ystr; int x, y;
		while (true) {
			try {
				Menu.printAskingForInput("X [/c to cancel]: "); Xstr = Menu.getInputLine();
				Menu.printAskingForInput("Y [/c to cancel]: "); Ystr = Menu.getInputLine();

				if (Xstr.toLowerCase().trim().equals("/c") || Ystr.toLowerCase().trim().equals("/c")) return;

				x = Integer.parseInt(Xstr); y = Integer.parseInt(Ystr);

				if (!Reversi.checkCoordinates(x) || !Reversi.checkCoordinates(y))
					throw new InvalidCoordinateException();

				if (!((Reversi) GameController.getInstance().getCurrentGameInSession()).canPlayerPlaceDiskHere(x, y))
					throw new CantPlaceDiskHere();
				break;
			} catch (InvalidCoordinateException | CantPlaceDiskHere e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		((Reversi) GameController.getInstance().getCurrentGameInSession()).placeDisk(x, y);
	}

	public void displayAvailableCoords () {
		ReversiView.getInstance().displayAvailableCoords(((Reversi) GameController.getInstance().getCurrentGameInSession()).getAvailableCoordinates());
	}

	public void displayGrid () {
		ReversiView.getInstance().displayGrid(((Reversi) GameController.getInstance().getCurrentGameInSession()).getBoard());
	}

	public void displayPrevMoves () {
		ReversiView.getInstance().displayMoveHistory(((Reversi) GameController.getInstance().getCurrentGameInSession()).getMoves());
	}

	public void displayInGameScores () {
		Gamer player1Gamer = GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(0).getGamer(),
				player2Gamer = GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(1).getGamer();

		GameView.getInstance().displayInGameScores(
				player1Gamer.getUsername(),
				player2Gamer.getUsername(),
				((Reversi) GameController.getInstance().getCurrentGameInSession()).getInGameScore(1),
				((Reversi) GameController.getInstance().getCurrentGameInSession()).getInGameScore(2)
		);
	}

	private static class HasntMadeMoveInCurrentTurnException extends Exception {
		public HasntMadeMoveInCurrentTurnException () {
			super("In your turn you should place the disk");
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
