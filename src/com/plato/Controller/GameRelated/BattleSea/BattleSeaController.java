package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import Model.GameRelated.Player;
import View.GameRelated.BattleSea.BattleSeaView;
import View.Menus.Color;
import View.Menus.Menu;

import java.util.*;

public class BattleSeaController {
	private static LinkedList<Ship> trialPlayerBoard1;
	private static LinkedList<Ship> trialPlayerBoard2;

	private static boolean inTheMiddleOfMenu = false;

	private static BattleSeaController battleSeaController;

	private TurnTimerTask turnTimerTask = new TurnTimerTask();
	private Timer turnTimer = new Timer();

	public static boolean isInTheMiddleOfMenu () {
		return inTheMiddleOfMenu;
	}

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

		int boardChoice;
		while (true)
			try {
				Menu.print("Your board choice: "); boardChoice = Integer.parseInt(Menu.getInputLine());

				if (boardChoice < 1 || boardChoice > 5)
					throw new InputMismatchException();
				break;
			} catch (InputMismatchException e) {
				Menu.printErrorMessage("Invalid input.");
			}

		LinkedList<Ship> chosenBoard = fiveRandBoards.get(boardChoice - 1);

		BattleSeaView.getInstance().displayBoard(
				this.getBoardAsStringBuilder(chosenBoard)
		);
		setTrialPlayerBoard(chosenBoard);
	}

	public void finalizeTrialBoard () {
		((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getListOfPlayers().get(getCurrentlyEditingTrialBoardNum() - 1))
				.finalizeBoard(getCurrentlyEditingTrialBoard());

		resetTrialPlayerBoards();
	}

	public void displayRemainingTime () {
		BattleSeaView.getInstance().displayRemainingTime(turnTimerTask.getSecondsRemaining());
	}

	public void displayRandomlyGeneratedBoard () {
		LinkedList<Ship> randBoard = BattleSea.getRandBoard();
		BattleSeaView.getInstance().displayBoard(
				this.getBoardAsStringBuilder(randBoard)
		);
		setTrialPlayerBoard(randBoard);
	}

	public void displayCurrentPlayerBoard () {
		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(true)
		);
	}

	public void displayOpponentBoard () {
		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(false)
		);
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

	public StringBuilder getBoardAsStringBuilder (boolean boardIsForCurrentPlayer) {
		StringBuilder boardStrBldr = new StringBuilder();

		BattleSea currentGame = (BattleSea) GameController.getInstance().getCurrentGameInSession();
		PlayerBattleSea curentPlayer = ((PlayerBattleSea) currentGame.getTurnPlayer()),
				currentOpponent = (PlayerBattleSea) currentGame.getOpponentOf(curentPlayer);

		Menu.println("%s%s board%s".formatted(Color.BLACK_BRIGHT.getVal(), (boardIsForCurrentPlayer ? "Your" : "Opponent's"), Color.RESET.getVal()));

		for (int y = 0; y <= 10; y++) {
			boardStrBldr.append("\t| ");
			for (int x = 0; x <= 10; x++) {

				if (x == 0 && y == 0) boardStrBldr.append(" ");
				else if (y == 0) boardStrBldr.append(x);
				else if (x == 0) boardStrBldr.append(y);
				else {
					String symbol = " ";

					// if there was a destroyed ship here
					int finalX = x;
					int finalY = y;
					if (Ship.getAllCoords(currentOpponent.getShips(true)).stream()
							.anyMatch(coord -> finalX == coord[0] && finalY == coord[1])) {
						symbol = Color.RED_BOLD.getVal() + "*";
					}

					// if there was a successful bomb but not a destroyed ship here
					else if (curentPlayer
							.getBombsThrown(true).stream()
							.anyMatch(bomb -> bomb.getX() == finalX && bomb.getY() == finalY)) {
						symbol = Color.YELLOW_BOLD.getVal() + "+";
					}

					// if there was an unsuccessful bomb here
					else if (curentPlayer
							.getBombsThrown(false).stream()
							.anyMatch(bomb -> bomb.getX() == finalX && bomb.getY() == finalY)) {
						symbol = Color.GREEN_BOLD.getVal() + "-";
					}

					// if there is a (partly) healthy ship here -> only for currentplayer's board
					else if (boardIsForCurrentPlayer && Ship.getAllCoords(currentOpponent.getShips(true)).stream()
							.anyMatch(coord -> finalX == coord[0] && finalY == coord[1])) {
						symbol = Color.BLUE.getVal() + "#";
					}

					boardStrBldr.append(symbol + Color.RESET.getVal());
				}
				boardStrBldr.append(((x == 0 && y == 10) || (x == 10 && y == 0)) ? "" : " ");

				if (x != 10)
					boardStrBldr.append("|");
			}
			boardStrBldr.append("|\n");
		}

		return boardStrBldr;
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

	public void displayTrialBoard () {
		if (getCurrentlyEditingTrialBoard() == null) return;

		BattleSeaView.getInstance().displayBoard(
				getBoardAsStringBuilder(getCurrentlyEditingTrialBoard())
		);
	}

	public void setTrialPlayerBoard (LinkedList<Ship> trialPlayerBoard) {
		ArrayList<Player> players = GameController.getInstance().getCurrentGameInSession().getListOfPlayers();

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

	public void initTurnTimerStuff () {
		turnTimer.scheduleAtFixedRate(turnTimerTask, 1 * 1000, 1 * 1000);
	}

	public TurnTimerTask getTurnTimerTask () {
		return turnTimerTask;
	}

	public static void setInTheMiddleOfMenu (boolean inTheMiddleOfMenu) {
		BattleSeaController.inTheMiddleOfMenu = inTheMiddleOfMenu;
	}

	static class TurnTimerTask extends TimerTask {
		private final int MAX_SECONDS = 30;
		private int secondsRemaining = MAX_SECONDS;
		private String command = "";

		@Override
		public void run () {
			secondsRemaining--;

			if (command.equals("bomb"))
				resetTimer();

			if (secondsRemaining == -1) {
				if (command.equals(""))
					BattleSeaView.getInstance().displayOutOfTimeMessage(GameController.getInstance().getCurrentGameInSession().getTurnGamer().getUsername());
				resetTimer();
			}
		}

		public void resetTimer () {
			secondsRemaining = MAX_SECONDS;
			command = "";
			GameController.getInstance().getCurrentGameInSession().nextTurn();
			BattleSeaController.inTheMiddleOfMenu = false;
		}

		public int getSecondsRemaining () {
			return secondsRemaining;
		}

		public void bomb () {
			this.command = "bomb";
		}
	}
}