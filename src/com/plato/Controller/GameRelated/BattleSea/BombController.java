package plato.Controller.GameRelated.BattleSea;

import plato.Controller.GameRelated.GameController;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.BattleSea.Bomb;
import plato.Model.GameRelated.BattleSea.PlayerBattleSea;
import plato.View.GameRelated.BattleSea.BombView;
import plato.View.Menus.Menu;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class BombController {
	private static BombController bombController;

	public static BombController getInstance () {
		if (bombController == null)
			bombController = new BombController();
		return bombController;
	}

	public void throwBomb () {
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

				if (currentPlayer.hasBeenBombedBefore(x, y))
					throw new CoordinateAlreadyBombedException();
				break;
			} catch (InvalidCoordinateException | CoordinateAlreadyBombedException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		currentPlayer.throwBomb(x, y);
	}

	public void displayAllCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown()));
	}

	public void displayAllOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown()));
	}

	public void displayAllSuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(true)));
	}

	public void displayAllSuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(true)));
	}

	public void displayAllUnsuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(false)));
	}

	public void displayAllUnsuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(false)));
	}

	private LinkedList<String> getBombXYs (LinkedList<Bomb> bombs) {
		return (LinkedList<String>) bombs.stream()
				.map(bomb -> "%d %d".formatted(bomb.getX(), bomb.getY()))
				.collect(Collectors.toList());
	}

	public static class InvalidCoordinateException extends Exception {
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