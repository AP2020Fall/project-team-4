package Controller.Menus;

import javafx.stage.Stage;

public class GameLogController {
	private static Stage stage;
	private static String gameName;

	public static void setGameName (String gameName) {
		GameLogController.gameName = gameName;
	}

	public static void setStage (Stage stage) {
		GameLogController.stage = stage;
		GameLogController.stage.setOnCloseRequest(e -> {
			GameLogController.stage = null;
			gameName = "";
		});
	}
}
