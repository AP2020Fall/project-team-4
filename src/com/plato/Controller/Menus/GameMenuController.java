package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController implements Initializable {
	private static String gameName;
	public GridPane gameInfo;
	public Label username2Error;
	public Label gameTitle;
	public ImageView gameIcon;
	public TextField username2;



	public static void setStage (Stage stage) {
		stage.setTitle(gameName);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameTitle.setText(gameName);
		if(gameName.equals("Reversi")){

		}
		else if(gameName.equals("BattleSea")){}
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
			//stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startReversi () {
		try{
		Stage reversiStage= MainController.getInstance().createAndReturnNewStage(
				FXMLLoader.load(new File("src/com/plato/View/Menus/ReversiGame.fxml").toURI().toURL()),
				"Reversi",
				true,
				MainController.getInstance().getPrimaryStage()
		);
		ReversiGameController.setStage();
		reversiStage.show();
		//stage.close();
	} catch (IOException e){
			e.printStackTrace();
		}
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
