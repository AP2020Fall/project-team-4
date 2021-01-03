package Controller.GameRelated.Reversi;

import Controller.GameRelated.GameController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameView;
import View.GameRelated.Reversi.ReversiView;

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

			if (!((Reversi) GameController.getInstance().getCurrentGameInSession()).hasPlayerMoved() &&
					((Reversi) GameController.getInstance().getCurrentGameInSession()).canPlayerPlaceAnyDisks())
				throw new HasntMadeMoveInCurrentTurnException();

		} catch (HasntMadeMoveInCurrentTurnException e) {
//			Menu.printErrorMessage(e.getMessage());
			return;
		}
		GameController.getInstance().getCurrentGameInSession().nextTurn();
	}

	public void placeDisk () {
		String Xstr = "", Ystr="";
		int x, y;
		if (((Reversi) GameController.getInstance().getCurrentGameInSession()).hasPlayerMoved())
			try {
				throw new PlayerHasAlreadyPlacedDiskException();
			} catch (PlayerHasAlreadyPlacedDiskException e) {
//				Menu.printErrorMessage(e.getMessage());
				return;
			}

		if (!((Reversi) GameController.getInstance().getCurrentGameInSession()).canPlayerPlaceAnyDisks()) {
			nextTurn();
			return;
		}

		while (true)
			try {
//				Menu.printAskingForInput("X [/c to cancel]: ");
//				Xstr = Menu.getInputLine();

				if (Xstr.trim().equalsIgnoreCase("/c")) return;

				x = Integer.parseInt(Xstr);
				if (!Reversi.checkCoordinates(x))
					throw new InvalidCoordinateException();

//				Menu.printAskingForInput("Y [/c to cancel]: ");
//				Ystr = Menu.getInputLine();

				if (Ystr.trim().equalsIgnoreCase("/c")) return;

				y = Integer.parseInt(Ystr);
				if (!Reversi.checkCoordinates(y))
					throw new InvalidCoordinateException();

				if (!((Reversi) GameController.getInstance().getCurrentGameInSession())
						.canPlayerPlaceDiskHere(x - 1, y - 1))
					throw new CantPlaceDiskHereException();
				break;
			} catch (InvalidCoordinateException | CantPlaceDiskHereException e) {
//				Menu.printErrorMessage(e.getMessage());
			}


		((Reversi) GameController.getInstance().getCurrentGameInSession()).placeDisk(x - 1, y - 1);
	}

	public void displayAvailableCoords () {
		ReversiView.getInstance().displayAvailableCoords(
				((Reversi) GameController.getInstance().getCurrentGameInSession())
						.getAvailableCoordinates());
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
				GameController.getInstance().getCurrentGameInSession().getInGameScore(1),
				GameController.getInstance().getCurrentGameInSession().getInGameScore(2)
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

	private static class CantPlaceDiskHereException extends Exception {
		public CantPlaceDiskHereException () {
			super("You cannot place the disk on this coordinates");
		}
	}

	private class PlayerHasAlreadyPlacedDiskException extends Exception {
		public PlayerHasAlreadyPlacedDiskException () {
			super("You have already made your move this turn");
		}
	}
}
