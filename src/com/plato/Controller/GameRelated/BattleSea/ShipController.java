package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import View.GameRelated.BattleSea.ShipView;

import java.util.LinkedList;

public class ShipController {
	private static ShipController shipController;

	public static ShipController getInstance () {
		if (shipController == null)
			shipController = new ShipController();
		return shipController;
	}

	public void moveShip (LinkedList<Ship> board, Ship chosenShip, int newXInt, int newYInt) {
		try {
			if (!BattleSea.checkCoordinates((newXInt)))
				throw new InvalidCoordinateException();

			if (!BattleSea.checkCoordinates((newYInt)))
				throw new InvalidCoordinateException();

			if (!chosenShip.canMove(newXInt, newYInt))
				throw new InvalidCoordinateException();

			chosenShip.move(newXInt, newYInt);

		} catch (InvalidCoordinateException e) {}
	}

	public void rotateShip (LinkedList<Ship> board, Ship chosenShip) throws CantChangeDirException {
		if (!chosenShip.canChangeDir(board))
			throw new CantChangeDirException();

		chosenShip.changeDir();
	}

	public void displayAllShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getShips())
		);
	}

	public void displayDestroyedShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getShips(true)));
	}

	public void displayDestroyedShipsOfOpponent () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getOpponentShips(true)));
	}

	public void displayHealthyShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getShips(false)));
	}

	public LinkedList<String> getShipsSizes (LinkedList<Ship> ships) {
		return new LinkedList<>() {{
			for (Ship ship : ships)
				add("%d %d".formatted(ship.getL_SIZE(), ship.getS_SIZE()));
		}};
	}

	private static class InvalidCoordinateException extends Exception {
		public InvalidCoordinateException () {
			super("Invalid coordinate");
		}
	}

	public static class CantChangeDirException extends Exception {
		public CantChangeDirException () {
			super("Can't change this ship's direction");
		}
	}
}
