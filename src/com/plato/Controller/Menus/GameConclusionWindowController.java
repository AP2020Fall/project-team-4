package Controller.Menus;

import Model.GameRelated.Game;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameConclusionWindowController {
	private static Stage stage;
	private static Game game;
	public Label conclusion;
	public ImageView pfp1;
	public ImageView pfp2;
	public Label username1;
	public Label username2;
	public Label score1;
	public Label score2;

	public static void setGame (Game game) {
		GameConclusionWindowController.game = game;
	}

	public static void setStage (Stage stage) {
		GameConclusionWindowController.stage = stage;
		GameConclusionWindowController.stage.setOnCloseRequest(e -> {
			GameConclusionWindowController.stage = null;
			game = null;
		});
	}

	public void closeStage (MouseEvent mouseEvent) {
		stage.close();
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Label) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Label) mouseEvent.getSource()).setOpacity(1);
	}
}
