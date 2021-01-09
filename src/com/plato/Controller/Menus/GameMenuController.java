package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GameMenuController {
	private static Stage stage;
	private static String gameName;
	public GridPane gameInfo;
	public Label username2Error;
	public TextField username2;

	public static void setStage (Stage stage) {
		GameMenuController.stage = stage;
		GameMenuController.stage.setOnCloseRequest(e -> {
			GameMenuController.stage = null;
			gameName = "";
		});
	}

	public static void setGameName (String gameName) {
		GameMenuController.gameName = gameName;
	}

	public void startBattleSea () {
		try {
			Stage battleSeaStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaEditBoardPage.fxml").toURI().toURL()),
					"BattleSea",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			BattleSeaEditBoardPageController.setStage(battleSeaStage);
			battleSeaStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startReversi () {
		// TODO: 1/9/2021 AD
	}

	public void newGame (ActionEvent actionEvent) {
		gameInfo.setVisible(true);
	}

	public void gameInfoGiveDone (ActionEvent actionEvent) {
		try {
			GameController.getInstance().runGame(username2.getText(), gameName);
		} catch (MainController.InvalidFormatException | GameController.CantPlayWithYourselfException | GameController.CantPlayWithAdminException | AccountController.NoAccountExistsWithUsernameException e) {
			username2Error.setText(e.getMessage());
			return;
		}
		switch (gameName) {
			case "BattleSea" -> startBattleSea();
			case "Reversi" -> startReversi();
		}
	}
}
