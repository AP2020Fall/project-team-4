package Controller.Menus;

public class GamesMenuController {
	private static boolean isForFaveGames;

	public static void setIsForFaveGames (boolean forFaveGames) { // todo when fxml is called this method should be called
		isForFaveGames = forFaveGames;
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

}
