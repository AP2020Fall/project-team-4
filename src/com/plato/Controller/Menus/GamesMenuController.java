package Controller.Menus;


import Controller.MainController;
import Model.AccountRelated.Gamer;
import Controller.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GamesMenuController implements Initializable {
	private static Stage stage;
	private static boolean isForFaveGames;
	public Button battlseaButton;
	public Button reversiButton;
	public GridPane gridpane;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;

	public static void setIsForFaveGames (boolean forFaveGames) {
		isForFaveGames = forFaveGames;
	}

	public static void setStage (Stage stage) {
		GamesMenuController.stage = stage;
		GamesMenuController.stage.setOnCloseRequest(e -> GamesMenuController.stage = null);
	}

	public void battleSeaMainMenu (ActionEvent actionEvent) {
		try {
			GameMenuController.setGameName("BattleSea");
			Stage battleSeaMainMenu = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameMenu.fxml").toURI().toURL()),
					"BattleSea Main Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);

			Label gameTitle = new Label("Battle Sea");
			gameTitle.setVisible(true);


			GameMenuController.setStage(battleSeaMainMenu);
			battleSeaMainMenu.show();
			stage.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}


	public void closeStage (ActionEvent actionEvent) {
		stage.close();

	}

	public void reversiMainMenu (ActionEvent actionEvent) {

		try {

			GameMenuController.setGameName("Reversi");

			Stage reversiMainMenu = MainController.getInstance().createAndReturnNewStage(

					FXMLLoader.load(new File("src/com/plato/View/Menus/GameMenu.fxml").toURI().toURL()),

					"Reversi Main Menu",

					true,

					MainController.getInstance().getPrimaryStage()
			);

			GameMenuController.setStage(reversiMainMenu);

			reversiMainMenu.show();

			stage.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@Override

	public void initialize (URL location, ResourceBundle resources) {

		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		try {
			dataOutputStream.writeUTF("getCurrentAccLoggedIn");
			dataOutputStream.flush();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		Gamer currentLoggedIn = null;
		try {
			currentLoggedIn = MainController.getInstance().getGson().fromJson(dataInputStream.readUTF() , Gamer.class);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		if (isForFaveGames) {
			if (!currentLoggedIn.getFaveGames().contains("BattleSea"))
				gridpane.getChildren().remove(battlseaButton);


			if (!currentLoggedIn.getFaveGames().contains("Reversi"))
				gridpane.getChildren().remove(reversiButton);

		}
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(1);
	}
}