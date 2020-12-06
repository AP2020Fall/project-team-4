package View.GameRelated.BattleSea;

import View.Menus.Menu;

import java.util.LinkedList;

public class BattleSeaView {
	private static BattleSeaView battleSeaView;

	public static BattleSeaView getInstance () {
		if (battleSeaView == null)
			battleSeaView = new BattleSeaView();
		return battleSeaView;
	}

	public void displayBoard (StringBuilder board) {
		System.out.println(board.toString());
	}

	public void displayAll5RandomBoards (LinkedList<StringBuilder> boards) {
		// TODO TOOTOTOTOOTOT
	}

	public void displayRemainingTime (int seconds) {
		Menu.println("You have %ds remaining.".formatted(seconds));
	}

	public void displayOutOfTimeMessage (String otherPlayer) {
		Menu.println("Sorry you ran out of time. Now it's %s's turn.".formatted(otherPlayer));
	}
}
