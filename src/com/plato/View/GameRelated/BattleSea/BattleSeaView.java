package plato.View.GameRelated.BattleSea;

import java.util.LinkedList;

public class BattleSeaView {
	private static BattleSeaView battleSeaView;

	public static BattleSeaView getInstance () {
		if (battleSeaView == null)
			battleSeaView = new BattleSeaView();
		return battleSeaView;
	}

	public void displayBoard (StringBuilder board) {
		// TODO TOTOOTOTOTOOT
	}

	public void displayAll5RandomBoards (LinkedList<StringBuilder> boards) {
		// TODO TOOTOTOTOOTOT
	}
}
