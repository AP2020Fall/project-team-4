package Controller.Menus;

import Controller.MainController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameConclusionWindowController implements Initializable {
	private static Stage stage;
	private static Game game;
	public Label conclusion;
	public ImageView pfp1, pfp2;
	public Label username1, username2;
	public Label score1, score2;
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;

	public GameConclusionWindowController() {
		this.actionEvent = null;
		this.mouseEvent = null;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public static void setGame (Game game) {
		GameConclusionWindowController.game = game;
	}

	public static void setStage (Stage stage) {
		GameConclusionWindowController.stage = stage;
		GameConclusionWindowController.stage.setOnCloseRequest(e -> {
			GameConclusionWindowController.stage = null;
			game = null;
		});
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		//noinspection ConstantConditions
		switch (game.getConclusion()) {
			case DRAW -> conclusion.setText("draw".toUpperCase());
			case PLAYER1_WIN,PLAYER2_WIN -> conclusion.setText("you win".toUpperCase());
			case IN_SESSION -> {}
			default -> throw new IllegalStateException("Unexpected value: " + game.getConclusion());
		}
		Gamer gamer1 = game.getListOfPlayers().get(0).getGamer(),
				gamer2 = game.getListOfPlayers().get(1).getGamer();
		pfp1.setImage(new Image(gamer1.getPfpUrl()));
		pfp2.setImage(new Image(gamer2.getPfpUrl()));

		username1.setText(gamer1.getUsername());
		username2.setText(gamer2.getUsername());

		score1.setText(String.valueOf(game.getInGameScore(1)));
		score2.setText(String.valueOf(game.getInGameScore(2)));
	}

	public static void closeStage () {
		stage.close();
	}

	public void closeStageWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("GameConclusionWindow.closeStage");
	}

	public void mouseIsOver () {
		((Label) getMouseEvent().getSource()).setOpacity(0.8);
	}

	public void mouseIsOverWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("GameConclusionWindow.mouseIsOver");
	}

	public void mouseIsOut () {
		((ImageView) getMouseEvent().getSource()).setOpacity(1);
	}

	public void mouseIsOutWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("GameConclusionWindow.mouseIsOut");
	}
}
