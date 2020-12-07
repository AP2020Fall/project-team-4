package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.Bomb;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import View.GameRelated.BattleSea.BombView;
import View.Menus.Menu;

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
		PlayerBattleSea currentPlayer = ((PlayerBattleSea) GameController.getInstance().getCurrentGame().getTurnPlayer());

		String Xstr, Ystr; int x, y;
		while (true) {
			try {
				// for x
				System.out.print("X [/c to cancel]: ");
				Xstr = Menu.getInputLine();

				if (Xstr.trim().equalsIgnoreCase("/c")) return;

				if (!BattleSea.checkCoordinates(Integer.parseInt(Xstr)))
					throw new InvalidCoordinateException();

				// for y
				System.out.print("Y [/c to cancel]: ");
				Ystr = Menu.getInputLine();

				if (Ystr.trim().equalsIgnoreCase("/c")) return;

				if (!BattleSea.checkCoordinates(Integer.parseInt(Ystr)))
					throw new InvalidCoordinateException();

				if (currentPlayer.hasBeenBombedBefore(Integer.parseInt(Xstr), Integer.parseInt(Ystr)))
					throw new CoordinateAlreadyBombedException();

				x = Integer.parseInt(Xstr);
				y = Integer.parseInt(Ystr);
				break;
			} catch (InvalidCoordinateException | CoordinateAlreadyBombedException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		currentPlayer.throwBomb(x, y);
		BattleSeaController.getInstance().getTurnTimerTask().bomb();
	}

	public void displayAllCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown()));
	}

	public void displayAllOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown()));
	}

	public void displayAllSuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(true)));
	}

	public void displayAllSuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(true)));
	}

	public void displayAllUnsuccessCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getBombsThrown(false)));
	}

	public void displayAllUnsuccessOpponentBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getTurnPlayer())
				.getOpponentBombsThrown(false)));
	}

	private LinkedList<String> getBombXYs (LinkedList<Bomb> bombs) {
		return bombs.stream()
				.map(bomb -> "%d %d".formatted(bomb.getX(), bomb.getY()))
				.collect(Collectors.toCollection(LinkedList::new));
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
