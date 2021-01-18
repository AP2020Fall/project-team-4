package Controller.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;

import java.util.LinkedList;

public class BattleSeaController {
	private static BattleSeaController battleSeaController;

	public static BattleSeaController getInstance () {
		if (battleSeaController == null)
			battleSeaController = new BattleSeaController();
		return battleSeaController;
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
}