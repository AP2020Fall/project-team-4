package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import View.GameRelated.BattleSea.ShipView;
import View.Menus.Menu;

import java.util.InputMismatchException;
import java.util.LinkedList;

public class ShipController {
	private static ShipController shipController;

	public static ShipController getInstance () {
		if (shipController == null)
			shipController = new ShipController();
		return shipController;
	}

	public void editShipCoords () {
		LinkedList<Ship> ships = BattleSeaController.getInstance().getCurrentlyEditingTrialBoard();

		ShipView.getInstance().displayShipsWithNamesForEditing(new LinkedList<>() {{
			for (Ship ship : ships)
				add("%d %d".formatted(ship.getLeftMostX(), ship.getTopMostY()));

		}});

		String shipName; Ship chosenShip;
		while (true) {
			System.out.print("Choose ship[/cancel to cancel filling form]: "); shipName = Menu.getInputLine();

			if (shipName.toLowerCase().trim().equals("/cancel")) return;

			if (shipName.matches("[A-F]")) {
				chosenShip = ships.get("ABCDEF".indexOf(shipName));
				break;
			}

			Menu.printErrorMessage("Invalid ship name.");
		}

		String newX, newY; int newXInt, newYInt;
		while (true)
			try {
				System.out.print("New x [/cancel to cancel filling form, /s to use previous value]: "); newX = Menu.getInputLine();
				System.out.print("New y [/cancel to cancel filling form, /s to use previous value]: "); newY = Menu.getInputLine();

				if (newX.toLowerCase().trim().equals("/cancel") || newY.toLowerCase().trim().equals("/cancel")) return;

				boolean X_Same = newX.toLowerCase().trim().equals("/s"),
						Y_Same = newY.toLowerCase().trim().equals("/s");

				if (!X_Same && !BattleSea.checkCoordinates(Integer.parseInt(newX)))
					throw new InvalidCoordinateException();

				if (!Y_Same && !BattleSea.checkCoordinates(Integer.parseInt(newY)))
					throw new InvalidCoordinateException();

				newXInt = X_Same ? chosenShip.getLeftMostX() : Integer.parseInt(newX);
				newYInt = Y_Same ? chosenShip.getTopMostY() : Integer.parseInt(newY);

				if (!chosenShip.canMove(newXInt, newYInt))
					throw new InvalidCoordinateException();
				break;
			} catch (InputMismatchException e) {
				Menu.printErrorMessage("You must either enter a number or \"/s\" for both new x and new y");
			} catch (InvalidCoordinateException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		chosenShip.move(newXInt, newYInt);
	}

	public  void rotateShip () {
		LinkedList<Ship> ships = BattleSeaController.getInstance().getCurrentlyEditingTrialBoard();

		ShipView.getInstance().displayShipsWithNamesForEditing(new LinkedList<>() {{
			for (Ship ship : ships)
				add("%d %d".formatted(ship.getLeftMostX(), ship.getTopMostY()));

		}});

		String shipName; Ship chosenShip;
		while (true) {
			System.out.print("Choose ship[/cancel to cancel filling form]: "); shipName = Menu.getInputLine();

			if (shipName.toLowerCase().trim().equals("/cancel")) return;

			if (shipName.matches("[A-F]")) {
				chosenShip = ships.get("ABCDEF".indexOf(shipName));
				break;
			}

			Menu.printErrorMessage("Invalid ship name.");
		}

		try {
			if (!chosenShip.canChangeDir())
				throw new CantChangeDirException();
		} catch (CantChangeDirException e) {
			Menu.printErrorMessage(e.getMessage());
		}

		chosenShip.changeDir();
	}

	public  void displayAllShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGame()
						.getTurnPlayer())
						.getShips())
		);
	}

	public  void displayDestroyedShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGame()
						.getTurnPlayer())
						.getShips(true)));
	}

	public  void displayDestroyedShipsOfOpponent () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGame()
						.getTurnPlayer())
						.getOpponentShips(true)));
	}

	public  void displayHealthyShipsOfCurrentPlayer () {
		ShipView.getInstance().displayShips(getShipsSizes(
				((PlayerBattleSea) GameController.getInstance().getCurrentGame()
						.getTurnPlayer())
						.getShips(false)));
	}

	public  LinkedList<String> getShipsSizes (LinkedList<Ship> ships) {
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

	private static class CantChangeDirException extends Exception {
		public CantChangeDirException () {
			super("Can't change this ship's direction");
		}
	}
}
