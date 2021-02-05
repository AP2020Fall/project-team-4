package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Gamer;
import Controller.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MainController.playButtonClickSound;

public class MainMenuController implements Initializable {
	private static boolean gamerOrAdmin;
	public HBox buttons;
	public GridPane pane;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;

	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		MainMenuController.gamerOrAdmin = gamerOrAdmin;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
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
		pane.setMinHeight(750);
		pane.setMaxHeight(pane.getMinHeight());

		Stage stage = MainController.getInstance().getPrimaryStage();
		stage.setMinWidth(pane.getMinWidth());
		stage.setMaxWidth(pane.getMaxWidth());
		stage.setMinHeight(750 + 195 + 30);
		stage.setMaxHeight(stage.getMinHeight());
		stage.setHeight(stage.getMinHeight());

		stage.setWidth(gamerOrAdmin ? stage.getMaxWidth() : stage.getMinWidth());

		buttons.getChildren().stream()
				.map(node -> ((Button) node))
				.filter(button -> button.getText().equals("Account"))
				.forEach(Button::fire);

//		AudioClip bm = new AudioClip(Paths.get("src/com/Resources/Sounds/WhereIsMyMind.wav").toUri().toString());
//		bm.setCycleCount(AudioClip.INDEFINITE);
//		bm.play(0.3);
	}

	public void eventsTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.eventsTab");
		playButtonClickSound();
		try {
			pane.getChildren().clear();
			EventsTabController.setGamerOrAdmin(gamerOrAdmin);
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/EventsTab.fxml").toURI().toURL()));
			MainController.getInstance().getPrimaryStage().setMaxHeight(MainController.getInstance().getPrimaryStage().getMinHeight());
			if (!gamerOrAdmin) {
				MainController.getInstance().getPrimaryStage().setMinWidth(1010);
				MainController.getInstance().getPrimaryStage().setMaxWidth(MainController.getInstance().getPrimaryStage().getMinWidth());
			}
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
			pane.getChildren().get(0).setLayoutY(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void usersTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.usersTab");
		playButtonClickSound();
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
		playButtonClickSound();
		try {
			pane.getChildren().clear();
			AccountPageController.setGamerOrAdmin(gamerOrAdmin);
			pane.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/AccountPage.fxml").toURI().toURL()));
			GridPane.setValignment(pane.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(pane.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gamingHistoryPage (ActionEvent actionEvent) {
		System.out.println("MainMenuController.gamingHistoryPage");
		playButtonClickSound();
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
		playButtonClickSound();
		try {
			GamesMenuController.setIsForFaveGames(false);
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GamesMenu.fxml").toURI().toURL()),
					"Games Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			GamesMenuController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void faveGamesTab (ActionEvent actionEvent) {
		System.out.println("MainMenuController.faveGamesTab");
		playButtonClickSound();
		try {
			GamesMenuController.setIsForFaveGames(true);
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GamesMenu.fxml").toURI().toURL()),
					"Games Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			GamesMenuController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void friendsPage (ActionEvent actionEvent) {
		System.out.println("MainMenuController.friendsPage");
		playButtonClickSound();
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
		playButtonClickSound();
		try {
			AdminMsgsController.setGamer(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/AdminMsgs.fxml").toURI().toURL()),
					"Plato bot messages",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			AdminMsgsController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(1);
	}
}
