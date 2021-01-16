package Controller.GameRelated.Reversi;

import Controller.GameRelated.GameController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
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

	public void placeDisk (int x, int y) throws PlayerHasAlreadyPlacedDiskException {
		Reversi currentGame = (Reversi) GameController.getInstance().getCurrentGameInSession();

		if (currentGame.hasPlayerMoved().get())
			throw new PlayerHasAlreadyPlacedDiskException();

		if (!currentGame.canPlayerPlaceAnyDisks()) {
			nextTurn();
			return;
		}

		currentGame.placeDisk(x - 1, y - 1);
	}

	public void nextTurn () {
		if (GameController.getInstance().getCurrentGameInSession().gameEnded()) {
			Game currentGame = GameController.getInstance().getCurrentGameInSession();
			GameController.getInstance().getCurrentGameInSession().concludeGame();
			GameController.getInstance().displayGameConclusion(currentGame);
			return;
		}

		GameController.getInstance().getCurrentGameInSession().nextTurn();
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

	public class PlayerHasAlreadyPlacedDiskException extends Exception {
		public PlayerHasAlreadyPlacedDiskException () {
			super("You have already made your move this turn");
		}
	}
}
