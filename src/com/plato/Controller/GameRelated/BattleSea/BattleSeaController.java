package plato.Controller.GameRelated.BattleSea;

import plato.Controller.GameRelated.GameController;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.BattleSea.PlayerBattleSea;
import plato.Model.GameRelated.BattleSea.Ship;
import plato.View.GameRelated.BattleSea.BattleSeaView;
import plato.View.Menus.Menu;

import java.util.InputMismatchException;
import java.util.LinkedList;

public class BattleSeaController {
	private static Ship[] trialPlayerBoard1,
			trialPlayerBoard2;

	public static void chooseBetween5RandomlyGeneratedBoards () {
		LinkedList<Ship[]> fiveRandBoards = BattleSea.get5RandBoards();

		BattleSeaView.displayAll5RandomBoards(fiveRandBoards);

		int boardChoice;
		while (true)
			try {
				System.out.print("Your board choice: "); boardChoice = Integer.parseInt(Menu.getInputLine());

				if (boardChoice < 1 || boardChoice > 5)
					throw new InputMismatchException();
				break;
			} catch (InputMismatchException e) {
				Menu.printErrorMessage("Invalid input.");
			}

		Ship[] chosenBoard = fiveRandBoards.get(boardChoice - 1);

		BattleSeaView.displayBoard(chosenBoard);
		setTrialPlayerBoard(chosenBoard);
	}

	public static void displayRandomlyGeneratedBoard () {
		BattleSeaView.displayBoard(BattleSea.getRandBoard());
	}

	public static void displayCurrentPlayerBoard () {
		BattleSeaView.displayBoard(((PlayerBattleSea) GameController.getCurrentGame().getTurnPlayer()).getShips(), true);
	}

	public static void displayOpponentBoard () {
		BattleSeaView.displayBoard(((PlayerBattleSea) GameController.getCurrentGame().getTurnPlayer()).getShips(), false);
	}

	public static void setTrialPlayerBoard (Ship[] trialPlayerBoard) {
		if (trialPlayerBoard1 == null) trialPlayerBoard1 = trialPlayerBoard;
		else if (trialPlayerBoard2 == null) trialPlayerBoard2 = trialPlayerBoard;
	}

	public static void resetTrialPlayerBoards () {
		trialPlayerBoard1 = null; trialPlayerBoard2 = null;
	}

	public static Ship[] getTrialPlayerBoard1 () {
		return trialPlayerBoard1;
	}

	public static Ship[] getTrialPlayerBoard2 () {
		return trialPlayerBoard2;
	}
}
