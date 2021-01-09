package Controller.Menus;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleSeaEditBoardPageController implements Initializable {
	private static Stage stage;
	public Button generate1RandBoardButton;
	public ProgressIndicator timer2, timer1;
	public Label username2, username1;
	public Circle turnIndicator2, turnIndicator1;
	public Circle pfp2, pfp1;
	public GridPane board;
	public GridPane board5, board4, board3, board2, board1; // random boards
	public GridPane genRandBoardWindow;

	public static void setStage (Stage stage) {
		BattleSeaEditBoardPageController.stage = stage;
		BattleSeaEditBoardPageController.stage.setOnCloseRequest(e -> BattleSeaEditBoardPageController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

		generate1RandBoardButton.fire();
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public void doneEditing (ActionEvent actionEvent) {
		// TODO: 1/9/2021 AD
	}

	public void generate5RandBoards (ActionEvent actionEvent) {
		// TODO: 1/9/2021 AD
	}

	public void generate1RandBoard (ActionEvent actionEvent) {
		// TODO: 1/9/2021 AD
	}

	public void closeGen5RandBoard (ActionEvent actionEvent) {
		genRandBoardWindow.setVisible(false);
	}
}
