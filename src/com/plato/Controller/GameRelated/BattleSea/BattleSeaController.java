package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import Model.GameRelated.Game;
import Model.GameRelated.Player;
import View.GameRelated.BattleSea.BattleSeaView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class BattleSeaController {
	private static BattleSeaController battleSeaController;
	private final TurnTimerTask turnTimerTask = new TurnTimerTask();
	private LinkedList<Ship> trialPlayerBoard1;
	private LinkedList<Ship> trialPlayerBoard2;
	private Timer turnTimer;

	public static BattleSeaController getInstance () {
		if (battleSeaController == null)
			battleSeaController = new BattleSeaController();
		return battleSeaController;
	}

	public void chooseBetween5RandomlyGeneratedBoards () {
		LinkedList<LinkedList<Ship>> fiveRandBoards = BattleSea.get5RandBoards();
		BattleSeaView.getInstance().displayAll5RandomBoards(
				getBoardAsLinkedListOfStringBuilders(fiveRandBoards)
		);

		int boardChoice = 0;
		while (true)
			try {
//				Menu.printAskingForInput("Your board choice: ");
//				boardChoice = Integer.parseInt(Menu.getInputLine());

				if (boardChoice < 1 || boardChoice > 5)
					throw new InputMismatchException();
				break;
			} catch (InputMismatchException e) {
//				Menu.printErrorMessage("Invalid input.");
			}

		LinkedList<Ship> chosenBoard = fiveRandBoards.get(boardChoice - 1);

		BattleSeaView.getInstance().displayBoard(
				this.getBoardAsStringBuilder(chosenBoard)
		);
		setTrialPlayerBoard(chosenBoard);
		updateGamePlayMenu();
	}

	public LinkedList<StringBuilder> getBoardAsLinkedListOfStringBuilders (LinkedList<LinkedList<Ship>> fiveBoards) {
		LinkedList<StringBuilder> boardStrBldrs = new LinkedList<>();

		for (int i = 0; i < 5; i++) {
			boardStrBldrs.add(new StringBuilder());

			boardStrBldrs.getLast().append("%n%d. %n%n".formatted(i + 1));

			boardStrBldrs.getLast().append(getBoardAsStringBuilder(fiveBoards.get(i)));

			boardStrBldrs.getLast().append("\n");
		}

		return boardStrBldrs;
	}

	// only used in editing phase therefore no need to display bombs
	public StringBuilder getBoardAsStringBuilder (LinkedList<Ship> board) {
		StringBuilder boardStrBldr = new StringBuilder();

		for (int y = 0; y <= 10; y++) {
			boardStrBldr.append("\t| ");
			for (int x = 0; x <= 10; x++) {

				if (x == 0 && y == 0) boardStrBldr.append(" ");
				else if (y == 0) boardStrBldr.append(x);
				else if (x == 0) boardStrBldr.append(y);
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

		return boardStrBldr;
	}

	public void setTrialPlayerBoard (LinkedList<Ship> trialPlayerBoard) {
		LinkedList<Player> players = GameController.getInstance().getCurrentGameInSession().getListOfPlayers();

		if (((PlayerBattleSea) players.get(0)).getShips() == null)
			trialPlayerBoard1 = trialPlayerBoard;
		else if (((PlayerBattleSea) players.get(1)).getShips() == null) trialPlayerBoard2 = trialPlayerBoard;
	}

	public void updateGamePlayMenu () {
		BattleSea currentGame = ((BattleSea) GameController.getInstance().getCurrentGameInSession());

//		if (currentGame.getListOfBattleSeaPlayers().get(0).getShips() == null && trialPlayerBoard1 != null)
//			battleseaGPMenu.setTrialBoardExists(true);
//		else if (trialPlayerBoard2 != null && currentGame.getListOfBattleSeaPlayers().get(1).getShips() == null)
//			battleseaGPMenu.setTrialBoardExists(true);
//		else if (trialPlayerBoard2 != null && currentGame.getListOfBattleSeaPlayers().get(1).getShips() != null) {
//			battleseaGPMenu.nextPhase();
//			initTurnTimerStuff();
//			resetTrialPlayerBoards();
//		}
//		else
//			battleseaGPMenu.setTrialBoardExists(false);
//
//		if (currentGame.canStartBombing()) {
//			battleseaGPMenu.nextPhase();
//			initTurnTimerStuff();
//			resetTrialPlayerBoards();
//		}
	}

	public void finalizeTrialBoard () {
		((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getListOfPlayers().get(getCurrentlyEditingTrialBoardNum() - 1))
				.finalizeBoard(getCurrentlyEditingTrialBoard());

		resetTrialPlayerBoards();
		updateGamePlayMenu();
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

	public LinkedList<Ship> getCurrentlyEditingTrialBoard () {

		if (trialPlayerBoard1 != null)
			return trialPlayerBoard1;

		else if (trialPlayerBoard2 != null)
			return trialPlayerBoard2;

		return null;
	}

	public void resetTrialPlayerBoards () {
		trialPlayerBoard1 = null;
		trialPlayerBoard2 = null;
	}

	public void displayRemainingTime () {
		BattleSeaView.getInstance().displayRemainingTime(turnTimerTask.secondsRemainingProperty().intValue());
	}

	public void displayRandomlyGeneratedBoard (LinkedList<Ship> randBoard) {
		BattleSeaView.getInstance().displayBoard(
				this.getBoardAsStringBuilder(randBoard)
		);
		setTrialPlayerBoard(randBoard);
		updateGamePlayMenu();
	}

	public void displayCurrentPlayerBoard () {
		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(true)
		);
	}

	public StringBuilder getBoardAsStringBuilder (boolean boardIsForCurrentPlayer) {
		StringBuilder boardStrBldr = new StringBuilder();

		BattleSea currentGame = (BattleSea) GameController.getInstance().getCurrentGameInSession();

		PlayerBattleSea playerToShowBoardOf, opponentPlayer;
		if (boardIsForCurrentPlayer) {
			playerToShowBoardOf = ((PlayerBattleSea) currentGame.getTurnPlayer());
			opponentPlayer = (PlayerBattleSea) currentGame.getOpponentOf(playerToShowBoardOf);
		}
		else {
			opponentPlayer = ((PlayerBattleSea) currentGame.getTurnPlayer());
			playerToShowBoardOf = (PlayerBattleSea) currentGame.getOpponentOf(opponentPlayer);
		}

//		Menu.println("%s%s board%s".formatted(Color.BLACK_BRIGHT.getVal(), (boardIsForCurrentPlayer ? "Your" : "Opponent's"), Color.RESET.getVal()));

		for (int y = 0; y <= 10; y++) {
			boardStrBldr.append("\t| ");
			for (int x = 0; x <= 10; x++) {

				if (x == 0 && y == 0) boardStrBldr.append(" ");
				else if (y == 0) boardStrBldr.append(x);
				else if (x == 0) boardStrBldr.append(y);
				else {
					String symbol = " ";
					int finalX = x, finalY = y;

					// if there is a (partly) healthy ship here -> only for currentplayer's board
					if (boardIsForCurrentPlayer) {
//						if (Ship.getAllCoords(playerToShowBoardOf.getShips(false)).stream()
//								.anyMatch(coord -> finalX == coord[0] && finalY == coord[1]))
//							symbol = Color.BLUE.getVal() + "#";
					}

					// if there was a successful bomb but not a destroyed ship here
					if (opponentPlayer
							.getBombsThrown(true).stream()
							.anyMatch(bomb -> bomb.getX() == finalX && bomb.getY() == finalY)) {
//						symbol = Color.YELLOW_BOLD.getVal() + "+";
					}

					// if there was a destroyed ship here
					if (Ship.getAllCoords(playerToShowBoardOf.getShips(true)).stream()
							.anyMatch(coord -> finalX == coord[0] && finalY == coord[1])) {
//						symbol = Color.RED_BOLD.getVal() + "*";
					}

					// if there was an unsuccessful bomb here
					if (opponentPlayer
							.getBombsThrown(false).stream()
							.anyMatch(bomb -> bomb.getX() == finalX && bomb.getY() == finalY)) {
//						symbol = Color.GREEN_BOLD.getVal() + "-";
					}
//					boardStrBldr.append(symbol + Color.RESET.getVal());
				}
				boardStrBldr.append(((x == 0 && y == 10) || (x == 10 && y == 0)) ? "" : " ");

				if (x != 10)
					boardStrBldr.append("| ");
			}
			boardStrBldr.append("|\n");
		}

		return boardStrBldr;
	}

	public void displayOpponentBoard () {
		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(false)
		);
	}

	public void displayTrialBoard () {
		if (getCurrentlyEditingTrialBoard() == null) return;

		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(getCurrentlyEditingTrialBoard())
		);
	}

	public Timer getTurnTimer () {
		return turnTimer;
	}

	public void initTurnTimerStuff () {
		turnTimer = new Timer();
		turnTimer.scheduleAtFixedRate(turnTimerTask, 1000, 1000);
		turnTimerTask.resetTimer();
	}

	public TurnTimerTask getTurnTimerTask () {
		return turnTimerTask;
	}

	public void setMaxTime (int maxTime) {
		turnTimerTask.MAX_SECONDS = maxTime;
	}

	public double getTimePercentageRemaining () {
		return (((double) turnTimerTask.MAX_SECONDS) - turnTimerTask.secondsRemaining.doubleValue()) / ((double) turnTimerTask.MAX_SECONDS);
	}

	public static class TurnTimerTask extends TimerTask {
		private int MAX_SECONDS = 30;
		private IntegerProperty secondsRemaining = new SimpleIntegerProperty(MAX_SECONDS);
		private String command = "";

		@Override
		public void run () {
			secondsRemaining.set(secondsRemaining.get() - 1);
			System.out.println("secondsRemaining.get() = " + secondsRemaining.get());

			if (command.equals("bomb")) {
				resetTimer();
			}

			if (secondsRemaining.intValue() == -1) {
				BattleSea currentGame = (BattleSea) GameController.getInstance().getCurrentGameInSession();
				if (command.equals(""))
					BattleSeaView.getInstance().displayOutOfTimeMessage(
							currentGame.getOpponentOf(currentGame.getTurnPlayer()).getUsername()
					);
				resetTimer();
			}
		}

		public void resetTimer () {
			secondsRemaining.set(MAX_SECONDS);
			// if bomb wasn't successful go to next turn
			if (command.equals("bomb")) {
				if (!((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
						.getTurnPlayer())
						.getBombsThrown().getLast().wasSuccessful())
					GameController.getInstance().getCurrentGameInSession().nextTurn();
			}
			else
				GameController.getInstance().getCurrentGameInSession().nextTurn();
			command = "";
			if (GameController.getInstance().getCurrentGameInSession().gameEnded()) {
				Game currentGame = GameController.getInstance().getCurrentGameInSession();
				GameController.getInstance().getCurrentGameInSession().concludeGame();
				GameController.getInstance().displayGameConclusion(currentGame);
			}
		}

		public IntegerProperty secondsRemainingProperty () {
			return secondsRemaining;
		}

		public void bomb () {
			this.command = "bomb";
		}
	}
}