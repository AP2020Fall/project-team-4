package View.GameRelated.BattleSea;

import Controller.MainController;
import View.Menus.Menu;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedList;

public class BattleSeaView extends Application {
	private static BattleSeaView battleSeaView;
	private Stage primaryStage;
	private Pane mainPane;

	public static BattleSeaView getInstance () {
		if (battleSeaView == null)
			battleSeaView = new BattleSeaView();
		return battleSeaView;
	}

	@Override
	public void start (Stage primaryStage) {
		primaryStage.show();
		this.primaryStage = primaryStage;
		initGameMenuView();
	}

	public static void main (String[] args) {
		launch(args);
	}

	public void initGameMenuView () {
		mainPane = new AnchorPane() {{
			double wh = Screen.getPrimary().getBounds().getHeight();
			setMinSize(wh, wh); setMaxSize(wh, wh);

			ImageView bg = new ImageView() {{
				setImage(new Image("https://i.pinimg.com/originals/a9/ac/64/a9ac648a584d68eebb451a0460125462.jpg"));
				setLayoutX(0); setLayoutY(0);
				setPreserveRatio(true);
				setSmooth(true);
				setFitWidth(wh); setFitHeight(wh);
			}};
			getChildren().add(bg);
		}};

		MainController.getInstance().createAndReturnNewStage(
				mainPane, "Battlesea Main Menu", true, primaryStage
		).show();
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
