package View.GameRelated.BattleSea;

import Controller.MainController;
import View.Menus.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class BattleSeaView {
	private static BattleSeaView battleSeaView;
	private Pane mainPane;

	public static BattleSeaView getInstance () {
		if (battleSeaView == null)
			battleSeaView = new BattleSeaView();
		return battleSeaView;
	}

	public void initGameMenuView () {
		mainPane = new GridPane(){{
			setWidth(900); setHeight(900);
		}};

		Stage stage = MainController.getInstance().createAndReturnNewStage(mainPane, "Battlesea Main Menu", true);
		stage.show();
		System.out.println("stage.isShowing() = " + stage.isShowing());
	}


	public void displayBoard (StringBuilder board) {
		System.out.println(board.toString());
	}

	public void displayAll5RandomBoards (LinkedList<StringBuilder> boards) {
		for (StringBuilder board : boards)
			displayBoard(board);
	}

	public void displayRemainingTime (int seconds) {
		Menu.println("You have %ds remaining.".formatted(seconds));
	}

	public void displayOutOfTimeMessage (String otherPlayer) {
		Menu.println("%nSorry you ran out of time. Now it's %s's turn.".formatted(otherPlayer));
	}
}
