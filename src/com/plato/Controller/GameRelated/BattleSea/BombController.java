package plato.Controller.GameRelated.BattleSea;

import plato.Controller.GameRelated.GameController;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.BattleSea.PlayerBattleSea;
import plato.View.GameRelated.BattleSea.BombView;
import plato.View.Menus.Menu;

public class BombController {
	public static void throwBomb () {
		PlayerBattleSea currentPlayer = ((PlayerBattleSea) GameController.getCurrentGame().getTurnPlayer());

		String Xstr, Ystr; int x, y;
		while (true) {
			try {
				System.out.print("X [/cancel to cancel filling form: "); Xstr = Menu.getInputLine();
				System.out.print("Y [/cancel to cancel filling form: "); Ystr = Menu.getInputLine();

				if (Xstr.toLowerCase().trim().equals("/cancel") || Ystr.toLowerCase().trim().equals("/cancel")) return;

				x = Integer.parseInt(Xstr); y = Integer.parseInt(Ystr);

				if (!BattleSea.checkCoordinates(x) || !BattleSea.checkCoordinates(y))
					throw new InvalidCoordinateException();

				if (currentPlayer.hasBeenBombedBefore(x,y))
					throw new CoordinateAlreadyBombedException();
				break;
			} catch (InvalidCoordinateException | CoordinateAlreadyBombedException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		currentPlayer.throwBomb(x,y);
	}

	public static void displayAllCurrentPlayerBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown());
	}

	public static void displayAllOpponentBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown());
	}

	public static void displayAllSuccessCurrentPlayerBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(true));
	}

	public static void displayAllSuccessOpponentBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(true));
	}

	public static void displayAllUnsuccessCurrentPlayerBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(false));
	}

	public static void displayAllUnsuccessOpponentBombs () {
		BombView.displayBombs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(false));
	}

	private static class InvalidCoordinateException extends Exception {
		public InvalidCoordinateException () {
			super("Coordinate must be inside the grid");
		}
	}

	private static class CoordinateAlreadyBombedException extends Exception {
		public CoordinateAlreadyBombedException () {
			super("Selected x,y has been already boomed");
		}
	}
}
