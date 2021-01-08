package Controller.Menus;

import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
		// for admin
		if (!gamerOrAdmin) {
			buttons.getChildren().subList(3, 10).clear();
		}
		// for gamer
		else {
			buttons.getChildren().subList(0, 3).clear();
		}
		pane.setMinWidth(775);
		pane.setMaxWidth(1550);
		pane.setMinHeight(600);
		pane.setMaxHeight(pane.getMinHeight());

		Stage stage = MainController.getInstance().getPrimaryStage();
		stage.setMinHeight(600 + 195 + 30);
		stage.setMaxHeight(stage.getMinHeight());
		stage.setHeight(stage.getMinHeight());

		buttons.getChildren().forEach(button -> {
			button.setOnMouseEntered(e -> button.setOpacity(0.8));
			button.setOnMouseExited(e -> button.setOpacity(1));
		});

		buttons.getChildren().stream()
				.map(node -> ((Button) node))
				.filter(button -> button.getText().equals("Account"))
				.forEach(Button::fire);
	}

	public void eventsTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.eventsTab");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/EventsTab.fxml").toURI().toURL()));
			EventsTabController.setGamerOrAdmin(gamerOrAdmin);
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void usersTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.usersTab");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/UsersTab.fxml").toURI().toURL()));
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void accountPage (ActionEvent actionEvent) {
		System.out.println("MainMenuController.accountPage");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/AccountPage.fxml").toURI().toURL()));
			AccountPageController.setGamerOrAdmin(gamerOrAdmin);
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gamingHistoryPage (ActionEvent actionEvent) {
		System.out.println("MainMenuController.gamingHistoryPage");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/GamingHistoryTab.fxml").toURI().toURL()));
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gamesTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.gamesTab");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/GamesMenu.fxml").toURI().toURL()));
			GamesMenuController.setIsForFaveGames(false);
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void faveGamesTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.faveGamesTab");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/GamesMenu.fxml").toURI().toURL()));
			GamesMenuController.setIsForFaveGames(true);
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void friendsPage (ActionEvent actionEvent) {
		System.out.println("MainMenuController.friendsPage");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/FriendsTab.fxml").toURI().toURL()));
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void messagesTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.messagesTab");
		try {
			pane.getChildren().clear();
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/AdminMsgs.fxml").toURI().toURL()));
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
