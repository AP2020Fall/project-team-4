package Controller.Menus;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EventSettingsController {
	private static boolean isForCreateOrEdit;
	private static Stage stage;
	public MenuButton minReqMenu, gameMenu;
	public GridPane gridPane;

	public static void setStage (Stage stage) {
		EventSettingsController.stage = stage;
		EventSettingsController.stage.setOnCloseRequest(e -> EventSettingsController.stage = null);
	}

	public static void setIsForCreateOrEdit (boolean isForCreateOrEdit) {
		EventSettingsController.isForCreateOrEdit = isForCreateOrEdit;
	}

	public void isLoginTimes (ActionEvent actionEvent) {
		gridPane.getChildren().remove(gameMenu);
		gridPane.getRowConstraints().remove(1);
		resetPage(actionEvent);
	}

	public void resetPage (ActionEvent actionEvent) {
		minReqMenu.setText("Minimum Requirement");
		gameMenu.setText("Game name");
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}
}
