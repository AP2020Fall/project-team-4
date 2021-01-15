package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController implements Initializable {
	private static String gameName;
	private static Stage stage;
	public Label gameTitle;
	public TextField username2;
	public MenuButton timeLimitMenu;
	public Label timeLimitAskLabel;
	public Label username2Error;
	public GridPane newGamePropertyWindow;
	public Button addToFaveGamesBtn;
	public GridPane mainGridPane;

	public static void setStage (Stage stage) {
		GameMenuController.stage = stage;
		GameMenuController.stage.setOnCloseRequest(e -> GameMenuController.stage = null);
	}

	public static void setGameName (String gameName) {
		GameMenuController.gameName = gameName;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		if (gameName.equals("Reversi")) {
			//pane.getChildren().subList(212 , 438).clear();
		}
		else if (gameName.equals("BattleSea")) {}
	}

	public void newGame (ActionEvent actionEvent) {
//		//gameInfo.setVisible(true);
//		Label title = new Label(gameName);
//		title.setFont(new Font("Bauhaus 93", 24));
//		VBox vBox = new VBox(title);
//		Scene scene = new Scene(vBox);
//		Stage stage = new Stage();
//		stage.setScene(scene);
//		stage.setTitle("New Game of " + gameName);
//		stage.setWidth(400);
//		stage.setHeight(450);
//		stage.initModality(Modality.WINDOW_MODAL);
//		TextField secondPlayer = new TextField();
//		stage.show();

		
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
		try {
			Stage reversiStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/ReversiGame.fxml").toURI().toURL()),
					"Reversi",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			ReversiGameController.setStage(reversiStage);
			reversiStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void dontStartGame (ActionEvent actionEvent) {
	}

	public void startGame (ActionEvent actionEvent) {
	}

	public void closeGame (ActionEvent actionEvent) {
	}

	public void addToFaveGames (ActionEvent actionEvent) {
	}

	public void displayScoreboard (ActionEvent actionEvent) {
	}

	public void setTime (ActionEvent actionEvent) {
	}

	public void displayLogOfGame (ActionEvent actionEvent) {
	}
}
