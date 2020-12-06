package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import View.GameRelated.BattleSea.BattleSeaView;
import View.Menus.Menu;

import java.util.InputMismatchException;
import java.util.LinkedList;

public class BattleSeaController {
	private static Ship[] trialPlayerBoard1,
			trialPlayerBoard2;

	private static BattleSeaController battleSeaController;

	public static BattleSeaController getInstance () {
		if (battleSeaController == null)
			battleSeaController = new BattleSeaController();
		return battleSeaController;
	}

	public void chooseBetween5RandomlyGeneratedBoards () {
		LinkedList<Ship[]> fiveRandBoards = BattleSea.get5RandBoards();

		displayAll5RandomBoards(fiveRandBoards);

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

		displayBoard(chosenBoard);
		setTrialPlayerBoard(chosenBoard);
	}

	public void finalizeTrialBoard () {

		((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getListOfPlayers().get(getCurrentlyEditingTrialBoardNum() - 1))
				.finalizeBoard(getCurrentlyEditingTrialBoard());

		resetTrialPlayerBoards();
	}

	public void displayRandomlyGeneratedBoard () {
		displayBoard(BattleSea.getRandBoard());
	}

	public void displayCurrentPlayerBoard () {
		displayBoard(((PlayerBattleSea) GameController.getInstance().getCurrentGame().getTurnPlayer()).getShips(), true);
	}

	public void displayOpponentBoard () {
		displayBoard(((PlayerBattleSea) GameController.getInstance().getCurrentGame().getTurnPlayer()).getShips(), false);
	}

	public void displayBoard (Ship[] board) {
		StringBuilder boardStrBldr = new StringBuilder();

		// TODO OTOTOTOTOTOTOOTOTOTOTOTO

		BattleSeaView.getInstance().displayBoard(boardStrBldr);
	}

	public void displayBoard (LinkedList<Ship> ships, boolean boardIsForCurrentPlayer) {
		StringBuilder boardStrBldr = new StringBuilder();

		// TODO OTOTOTOTOTOTOOTOTOTOTOTO

		BattleSeaView.getInstance().displayBoard(boardStrBldr);
	}

	public void displayAll5RandomBoards (LinkedList<Ship[]> board) {
		LinkedList<StringBuilder> boardStrBldrs = new LinkedList<>();

		// TODO OTOTOTOTOTOTOOTOTOTOTOTO

		BattleSeaView.getInstance().displayAll5RandomBoards(boardStrBldrs);
	}

	public void displayTrialBoard () {
		if (getCurrentlyEditingTrialBoard() == null) return;

		BattleSeaController.getInstance().displayBoard(getCurrentlyEditingTrialBoard());
	}

	public void setTrialPlayerBoard (Ship[] trialPlayerBoard) {
		if (trialPlayerBoard1 == null) trialPlayerBoard1 = trialPlayerBoard;
		else if (trialPlayerBoard2 == null) trialPlayerBoard2 = trialPlayerBoard;
	}

	public Ship[] getCurrentlyEditingTrialBoard () {

		if (trialPlayerBoard1 != null)
			return trialPlayerBoard1;

		else if (trialPlayerBoard2 != null)
			return trialPlayerBoard2;

		return null;
	}

	private int getCurrentlyEditingTrialBoardNum () {
		if (trialPlayerBoard1 == null && trialPlayerBoard2 == null)
			return 0;
		if (trialPlayerBoard1 != null)
			return 1;
		if (trialPlayerBoard2 != null)
			return 2;
		return 0;
	}

	public void resetTrialPlayerBoards () {
		trialPlayerBoard1 = null; trialPlayerBoard2 = null;
	}

	public Ship[] getTrialPlayerBoard1 () {
		return trialPlayerBoard1;
	}

	public Ship[] getTrialPlayerBoard2 () {
		return trialPlayerBoard2;
	}
}
