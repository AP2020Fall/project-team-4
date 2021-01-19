package Controller.Menus;

import Model.GameRelated.GameLog;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameLogController implements Initializable {
	private static Stage stage;
	private static String gameName;
	public ImageView gamePicture;
	public ListView<GridPane> gameLogList;
	public Label gameTitle, playedNum;

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

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		gameTitle.setText(gameName);
		playedNum.setText(playedNum.getText().replaceAll("-", String.valueOf(GameLog.getPlayedCount(gameName))));
		switch (gameName) {
			case "BattleSea" -> {
				Image image = new Image("https://i.imgur.com/7po5Ihx.jpg");
				gamePicture.setImage(image);
				gamePicture.setViewport(new Rectangle2D(image.getWidth() / 3, image.getHeight() / 3,
						image.getWidth() * 2 / 3, image.getWidth() * 2 / 3 / 4));
			}
			case "Reversi" -> {
				Image image = new Image("https://i.imgur.com/VaeApyW.png");
				gamePicture.setImage(image);
				gamePicture.setViewport(new Rectangle2D(0, image.getHeight() / 3,
						image.getWidth(), image.getWidth() / 4));
			}
		}
		gamePicture.setFitWidth(400);
		gamePicture.setFitHeight(100);
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Label) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Label) mouseEvent.getSource()).setOpacity(1);
	}

	public void closeStage (MouseEvent mouseEvent) {
		stage.close();
	}
}
