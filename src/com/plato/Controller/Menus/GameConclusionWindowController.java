package Controller.Menus;

import Model.GameRelated.Game;
import javafx.stage.Stage;

public class GameConclusionWindowController {
	private static Stage stage;
	private static Game game;

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
}
