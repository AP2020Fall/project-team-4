package View.GameRelated.BattleSea;

import Controller.MainController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.nio.file.Path;
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

	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) {
		primaryStage.show();
		this.primaryStage = primaryStage;
		initGameMenuView();
	}

	public void initGameMenuView () {
		mainPane = new AnchorPane() {{
			double wh = Screen.getPrimary().getBounds().getHeight();
			setMinSize(wh, wh);
			setMaxSize(wh, wh);

			ImageView bg = new ImageView() {{
				setImage(new Image("https://www.flaticon.com/free-icon/fingerprint_565512"));
				setLayoutX(0);
				setLayoutY(0);
				setPreserveRatio(true);
				setSmooth(true);
				setFitWidth(wh);
				setFitHeight(wh);
			}};

			HBox buttons = new HBox() {{
				setAlignment(Pos.CENTER);

				Button startGame = new Button();

				try {
					System.out.println(getClass().getResource(String.valueOf(Path.of("src/com/plato/View/GameRelated/BattleSea").relativize(Path.of("src/com/plato/View/GameRelated/BattleSea/Button.fxml")))));
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				getChildren().add(startGame);
			}};

			getChildren().addAll(bg, buttons);
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
//		Menu.println("You have %ds remaining.".formatted(seconds));
	}

	public void displayOutOfTimeMessage (String otherPlayer) {
//		Menu.println("%nSorry you ran out of time. Now it's %s's turn.".formatted(otherPlayer));
	}
}
