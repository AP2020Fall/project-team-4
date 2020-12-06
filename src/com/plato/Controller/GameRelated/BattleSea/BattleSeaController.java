package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import Model.GameRelated.Player;
import View.GameRelated.BattleSea.BattleSeaView;
import View.Menus.Menu;

import java.util.*;

public class BattleSeaController {
	private static LinkedList<Ship> trialPlayerBoard1;
	private static LinkedList<Ship> trialPlayerBoard2;

	private static BattleSeaController battleSeaController;

	public static BattleSeaController getInstance () {
		if (battleSeaController == null)
			battleSeaController = new BattleSeaController();
		return battleSeaController;
	}

	public void chooseBetween5RandomlyGeneratedBoards () {
		LinkedList<LinkedList<Ship>> fiveRandBoards = BattleSea.get5RandBoards();

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

		LinkedList<Ship> chosenBoard = fiveRandBoards.get(boardChoice - 1);

		displayBoard(chosenBoard);
		setTrialPlayerBoard(chosenBoard);
	}

	public void finalizeTrialBoard () {
		((PlayerBattleSea) GameController.getInstance().getCurrentGame()
				.getListOfPlayers().get(getCurrentlyEditingTrialBoardNum() - 1))
				.finalizeBoard(getCurrentlyEditingTrialBoard());

		resetTrialPlayerBoards();
	}

//	public void checkForTimeOut () {
//		if (LocalTime.now().getSecond() - localTime.getSecond() > 30) {
//			GameController.getInstance().getCurrentGame().nextTurn();
//			BattleSeaView.getInstance().displayOutOfTimeMessage(GameController.getInstance().getCurrentGame().getTurnGamer().getUsername());
//			resetTimer();
//		}
//	}

	public void displayRemainingTime () {
//		BattleSeaView.getInstance().displayRemainingTime(30 - (LocalTime.now().getSecond() - localTime.getSecond()));
	}

//	public void resetTimer () {
//		localTime = LocalTime.now();
//	}

	public void displayRandomlyGeneratedBoard () {
		LinkedList<Ship> randBoard = BattleSea.getRandBoard();
		displayBoard(randBoard);
		setTrialPlayerBoard(randBoard);
	}

	public void displayCurrentPlayerBoard () {
		displayBoard(((PlayerBattleSea) GameController.getInstance().getCurrentGame().getTurnPlayer()).getShips(), true);
	}

	public void displayOpponentBoard () {
		displayBoard(((PlayerBattleSea) GameController.getInstance().getCurrentGame().getTurnPlayer()).getShips(), false);
	}

	// only used in editing phase -> no need to display bombs
	public void displayBoard (LinkedList<Ship> board) {
		StringBuilder boardStrBldr = new StringBuilder();

		for (int y = 0; y <= 10; y++) {
			boardStrBldr.append("\t| ");
			for (int x = 0; x <= 10; x++) {

				if (x == 0 && y == 0)
					boardStrBldr.append(" ");

				else if (y == 0)
					boardStrBldr.append(x);

				else if (x == 0)
					boardStrBldr.append(y);

				else {
					int finalY = y, finalX = x;

					String symbol = (Ship.getAllCoords(board).stream()
							.anyMatch(shipCoord -> shipCoord[0] == finalX && shipCoord[1] == finalY)) ? "#" : " ";

					boardStrBldr.append(symbol);
				}
				boardStrBldr.append((((x == 0 && y == 10) || (x == 10 && y == 0)) ? "" : " ") + (x != 10 ? "| " : ""));
			}
			boardStrBldr.append("|\n");
		}

		BattleSeaView.getInstance().displayBoard(boardStrBldr);
	}

	public void displayBoard (LinkedList<Ship> ships, boolean boardIsForCurrentPlayer) {
		StringBuilder boardStrBldr = new StringBuilder();

		// TODO OTOTOTOTOTOTOOTOTOTOTOTO

		BattleSeaView.getInstance().displayBoard(boardStrBldr);
	}

	public void displayAll5RandomBoards (LinkedList<LinkedList<Ship>> board) {
		LinkedList<StringBuilder> boardStrBldrs = new LinkedList<>();

		// TODO OTOTOTOTOTOTOOTOTOTOTOTO

		BattleSeaView.getInstance().displayAll5RandomBoards(boardStrBldrs);
	}

	public void displayTrialBoard () {
		if (getCurrentlyEditingTrialBoard() == null) return;

		BattleSeaController.getInstance().displayBoard(getCurrentlyEditingTrialBoard());
	}

	public void setTrialPlayerBoard (LinkedList<Ship> trialPlayerBoard) {
		ArrayList<Player> players = GameController.getInstance().getCurrentGame().getListOfPlayers();

		if (((PlayerBattleSea) players.get(0)).getShips() == null) trialPlayerBoard1 = trialPlayerBoard;
		else if (((PlayerBattleSea) players.get(1)).getShips() == null) trialPlayerBoard2 = trialPlayerBoard;
	}

	public LinkedList<Ship> getCurrentlyEditingTrialBoard () {

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

}