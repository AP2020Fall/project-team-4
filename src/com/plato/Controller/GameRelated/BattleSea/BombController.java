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

	public void throwBomb () {
		PlayerBattleSea currentPlayer = ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getTurnPlayer());

		String Xstr = "", Ystr = "";
		int x, y;
		while (true) {
			try {
				// for x
//				Menu.printAskingForInput("X [/c to cancel]: ");
//				Xstr = Menu.getInputLine().trim();

				if (Xstr.equalsIgnoreCase("/c")) return;

				if (!BattleSea.checkCoordinates(Integer.parseInt(Xstr)))
					throw new InvalidCoordinateException();

				// for y
//				Menu.printAskingForInput("Y [/c to cancel]: ");
//				Ystr = Menu.getInputLine().trim();

				if (Ystr.equalsIgnoreCase("/c")) return;

				if (!BattleSea.checkCoordinates(Integer.parseInt(Ystr)))
					throw new InvalidCoordinateException();

				if (currentPlayer.hasBeenBombedBefore(Integer.parseInt(Xstr), Integer.parseInt(Ystr)))
					throw new CoordinateAlreadyBombedException();

				x = Integer.parseInt(Xstr);
				y = Integer.parseInt(Ystr);
				break;
			} catch (NumberFormatException e) {
//				Menu.println("You can only use numbers from 1 to 8 inclusive");
			} catch (InvalidCoordinateException | CoordinateAlreadyBombedException e) {
//				Menu.printErrorMessage(e.getMessage());
			}
		}

		currentPlayer.throwBomb(x, y);
		BattleSeaController.getInstance().getTurnTimerTask().bomb();
		if (((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getTurnPlayer())
				.getBombsThrown().getLast().wasSuccessful()) {
//			Menu.printSuccessfulOperation("Bomb successful");
//			Menu.println("You can bomb one more time.");
		}
		else {
			BattleSeaController.getInstance().getTurnTimerTask().bomb();
//			Menu.printSuccessfulOperation("Bomb unsuccessful");
//			Menu.println("Next!!");
		}
	}

	public void displayAllCurrentPlayerBombs () {
		BombView.getInstance().displayBombs(getBombXYs(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getTurnPlayer())
				.getBombsThrown()));
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
