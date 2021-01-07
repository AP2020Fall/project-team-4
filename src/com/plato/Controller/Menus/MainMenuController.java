package Controller.Menus;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
	private static boolean gamerOrAdmin;
	public HBox buttons;
	public Pane pane;

	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		MainMenuController.gamerOrAdmin = gamerOrAdmin;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		if (!gamerOrAdmin)
			buttons.getChildren().subList(3, 10).clear();
		else
			buttons.getChildren().subList(0, 3).clear();
	}

	public void eventsTabA (ActionEvent actionEvent) {
	}

	public void usersTab (ActionEvent actionEvent) {
	}

	public void accountPage (ActionEvent actionEvent) {
	}

	public void eventsTabG (ActionEvent actionEvent) {
	}

	public void gamingHistoryPage (ActionEvent actionEvent) {
	}

	public void gamesTab (ActionEvent actionEvent) {
	}

	public void faveGamesTab (ActionEvent actionEvent) {
	}

	public void friendsPage (ActionEvent actionEvent) {
	}

	public void accountPageG (ActionEvent actionEvent) {
	}

	public void messagesTab (ActionEvent actionEvent) {
	}
}
