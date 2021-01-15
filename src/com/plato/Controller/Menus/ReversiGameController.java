package Controller.Menus;

import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReversiGameController implements Initializable {
	private static Stage stage;
	private static Reversi currentGame;
	public Circle turnIndicatorW;
	public ImageView pfpW;
	public ImageView pfpB;
	public Circle turnIndicatorB;
	public Label usernameW;
	public Label usernameB;
	public Button confirmMoveBtn;
	private PlayerReversi player1, player2;

	public static void setStage (Stage stage) {
		ReversiGameController.stage = stage;
		ReversiGameController.stage.setOnCloseRequest(e -> {
			ReversiGameController.stage = null;
			currentGame = null;
		});
	}

	public static void setCurrentGame (Reversi currentGame) {
		ReversiGameController.currentGame = currentGame;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		player1 = (PlayerReversi) currentGame.getListOfPlayers().get(0);
		player2 = (PlayerReversi) currentGame.getListOfPlayers().get(1);

		//setting profile pictures
		{
		}

		// setting the usernames
		{
		}
	}

	public void closeGame (ActionEvent actionEvent) {
		// TODO: 1/15/2021 AD
	}

	public void putMarkIfPossible (MouseEvent mouseEvent) {
		// TODO: 1/15/2021 AD
	}

	public void confirmMove (ActionEvent actionEvent) {
		// TODO: 1/15/2021 AD
	}
}
