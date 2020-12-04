package plato.Controller.GameRelated.BattleSea;

import plato.Controller.GameRelated.GameController;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.BattleSea.PlayerBattleSea;
import plato.Model.GameRelated.BattleSea.Ship;
import plato.View.GameRelated.BattleSea.ShipView;
import plato.View.Menus.Menu;

import java.util.InputMismatchException;
import java.util.LinkedList;

public class ShipController {
	private static ShipController shipController;

	public static ShipController getInstance () {
		if (shipController == null)
			shipController = new ShipController();
		return shipController;
	}

	/**
	 * @param playernum if 1 => use trialboard1 otherwise use trialboard2
	 */
	public  void editShipCoords (int playernum) {
		Ship[] ships = (playernum == 1 ? BattleSeaController.getInstance().getTrialPlayerBoard1() : BattleSeaController.getInstance().getTrialPlayerBoard2());

		ShipView.getInstance().displayShipsWithNamesForEditing(new LinkedList<>() {{
			for (Ship ship : ships)
				add("%d %d".formatted(ship.getLeftMostX(), ship.getTopMostY()));

		}});

		String shipName; Ship chosenShip;
		while (true) {
			System.out.print("Choose ship[/cancel to cancel filling form]: "); shipName = Menu.getInputLine();

			if (shipName.toLowerCase().trim().equals("/cancel")) return;

			if (shipName.matches("[A-F]")) {
				chosenShip = ships["ABCDEF".indexOf(shipName)];
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

	/**
	 * @param playernum if 1 => use trialboard1 otherwise use trialboard2
	 */
	public  void rotateShip (int playernum) {
		Ship[] ships = (playernum == 1 ? BattleSeaController.getInstance().getTrialPlayerBoard1() : BattleSeaController.getInstance().getTrialPlayerBoard2());

		ShipView.getInstance().displayShipsWithNamesForEditing(new LinkedList<>() {{
			for (Ship ship : ships)
				add("%d %d".formatted(ship.getLeftMostX(), ship.getTopMostY()));

		}});

		String shipName; Ship chosenShip;
		while (true) {
			System.out.print("Choose ship[/cancel to cancel filling form]: "); shipName = Menu.getInputLine();

			if (shipName.toLowerCase().trim().equals("/cancel")) return;

			if (shipName.matches("[A-F]")) {
				chosenShip = ships["ABCDEF".indexOf(shipName)];
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

	public  void editingBoardDone () {
		((PlayerBattleSea) GameController.getInstance().getCurrentGame().getListOfPlayers().get(0)).finalizeBoard(BattleSeaController.getInstance().getTrialPlayerBoard1());
		((PlayerBattleSea) GameController.getInstance().getCurrentGame().getListOfPlayers().get(1)).finalizeBoard(BattleSeaController.getInstance().getTrialPlayerBoard2());
		BattleSeaController.getInstance().resetTrialPlayerBoards();
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
