package Controller.Menus;


import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;


	public GamesMenuController(ActionEvent actionEvent, MouseEvent mouseEvent) {
		this.actionEvent = actionEvent;
		this.mouseEvent = mouseEvent;
	}

	public GamesMenuController() {
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

		Gamer currentLoggedIn = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn());

		if (isForFaveGames) {
			if (!currentLoggedIn.getFaveGames().contains("BattleSea"))
				gridpane.getChildren().remove(battlseaButton);


			if (!currentLoggedIn.getFaveGames().contains("Reversi"))
				gridpane.getChildren().remove(reversiButton);

		}
	}

	public void closeStage () {
		stage.close();
	}

	public void closeStageWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("GamesMenu.closeStage");
	}

	public void mouseIsOver () {
		((Label) getMouseEvent().getSource()).setOpacity(0.8);
	}

	public void mouseIsOverWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("GamesMenu.mouseIsOver");
	}

	public void mouseIsOut () {
		((ImageView) getMouseEvent().getSource()).setOpacity(1);
	}

	public void mouseIsOutWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("GamesMenu.mouseIsOut");
	}
}