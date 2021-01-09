package Controller.Menus;

import Controller.GameRelated.GameController;
import Controller.MainController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BattleSeaEditBoardPageController implements Initializable {
	private static Stage stage;
	public Button generate1RandBoardButton;
	public ImageView pfp2, pfp1;
	public ProgressIndicator timer2, timer1;
	public Label username2, username1;
	public Circle turnIndicator2, turnIndicator1;
	public GridPane board;
	public GridPane board5, board4, board3, board2, board1; // random boards
	public GridPane genRandBoardWindow;
	private PlayerBattleSea player1, player2;
	private IntegerProperty editingTurn = new SimpleIntegerProperty(-1);

	public static void setStage (Stage stage) {
		BattleSeaEditBoardPageController.stage = stage;
		BattleSeaEditBoardPageController.stage.setOnCloseRequest(e -> BattleSeaEditBoardPageController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		player1 = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(0);
		player2 = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(1);
		// setting the pfp's
		{
			pfp1.setImage(new Image(player1.getGamer().getPfpUrl()));
			pfp1.setSmooth(true);
			pfp1.setClip(new Circle(pfp1.getBoundsInLocal().getCenterX(), pfp1.getBoundsInLocal().getCenterY(), 50));

			pfp2.setImage(new Image(player2.getGamer().getPfpUrl()));
			pfp2.setSmooth(true);
			pfp2.setClip(new Circle(pfp2.getBoundsInLocal().getCenterX(), pfp2.getBoundsInLocal().getCenterY(), 50));
		}

		editingTurn.addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() == 1) {
				timer1.setVisible(true);
				timer2.setVisible(false);

				turnIndicator1.setVisible(true);
				turnIndicator2.setVisible(false);

				generate1RandBoardButton.fire();
			}
			else if (newValue.intValue() == 2) {
				timer1.setVisible(false);
				timer2.setVisible(true);

				turnIndicator1.setVisible(false);
				turnIndicator2.setVisible(true);

				// todo add the ships for player1

				generate1RandBoardButton.fire();
			}

			else if (oldValue.intValue() == 2) {

				// todo add the ships for player2

				try {
					MainController.getInstance().createAndReturnNewStage(
							FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaPlayPage.fxml").toURI().toURL()),
							"BattleSea Game",
							true,
							MainController.getInstance().getPrimaryStage()
					).show();
					stage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		editingTurn.set(1);

		Timeline timer = new Timeline(new KeyFrame(Duration.minutes(1), e -> {

		}));
		timer.setOnFinished(e -> {
			if (editingTurn.intValue() == 1) {
				editingTurn.set(2);
				timer.playFromStart();
			}
			else {
				editingTurn.set(-1);
				timer.stop();
			}
		});
		timer.setCycleCount(5);
		timer.setAutoReverse(false);

		timer.play();
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
		BattleSea.getRandBoard().forEach(ship -> {
			ship.get
		});
	}

	public void closeGen5RandBoard (ActionEvent actionEvent) {
		genRandBoardWindow.setVisible(false);
	}
}
