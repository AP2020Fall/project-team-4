package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.Bomb;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import View.GameRelated.BattleSea.BombView;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class BombController {
	private static BombController bombController;

	public static BombController getInstance () {
		if (bombController == null)
			bombController = new BombController();
		return bombController;
	}

	public void throwBomb (int x, int y) throws InvalidCoordinateException, CoordinateAlreadyBombedException {
		PlayerBattleSea currentPlayer = ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getTurnPlayer());

		if (canThrowBomb(x, y)) {
			currentPlayer.throwBomb(x, y);
			BattleSeaController.getInstance().getTurnTimerTask().bomb();

			if (!currentPlayer.getBombsThrown().getLast().wasSuccessful())
				BattleSeaController.getInstance().getTurnTimerTask().bomb();
		}
	}

	public boolean canThrowBomb (int x, int y) throws InvalidCoordinateException, CoordinateAlreadyBombedException {
		PlayerBattleSea currentPlayer = ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getTurnPlayer());

		if (!BattleSea.checkCoordinates(x))
			throw new InvalidCoordinateException();

		if (!BattleSea.checkCoordinates(y))
			throw new InvalidCoordinateException();

		if (currentPlayer.hasBeenBombedBefore(x, y))
			throw new CoordinateAlreadyBombedException();

		return true;
	}

	public void displayAllCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getBombsThrown()));
	}

	private LinkedList<String> getBombXYs (LinkedList<Bomb> bombs) {
		return bombs.stream()
				.map(bomb -> "%d %d".formatted(bomb.getX(), bomb.getY()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public void displayAllOpponentBombs () {
		BombView.getInstance().displayBombs(
				getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getOpponentBombsThrown()));
	}

	public void displayAllSuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getBombsThrown(true)));
	}

	public void displayAllSuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getOpponentBombsThrown(true)));
	}

	public void displayAllUnsuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getBombsThrown(false)));
	}

	public void displayAllUnsuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getOpponentBombsThrown(false)));
	}

	public static class InvalidCoordinateException extends Exception {
		public InvalidCoordinateException () {
			super("Coordinate must be inside the grid");
		}
	}

	public static class CoordinateAlreadyBombedException extends Exception {
		public CoordinateAlreadyBombedException () {
			super("Selected x,y has been already boomed");
		}
	}
}
