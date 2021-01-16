package Controller.Menus;

import javafx.stage.Stage;

public class GameScoreboardController {
	private static Stage stage;
	private static String gameName;

	public static void setStage (Stage stage) {
		GameScoreboardController.stage = stage;
		GameScoreboardController.stage.setOnCloseRequest(e -> {
			GameScoreboardController.stage = null;
			gameName = "";
		});
	}

	public static void setGameName (String gameName) {
		GameScoreboardController.gameName = gameName;
	}
}
