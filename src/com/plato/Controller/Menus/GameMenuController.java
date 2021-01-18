package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenuController implements Initializable {
	private static String gameName;
	private static Stage stage;
	public Label gameTitle;
	public TextField username2;
	public Label timeLimitAskLabel;
	public MenuButton timeLimitMenuInStartGameMenu;
	public Label username2Error;
	public GridPane newGamePropertyWindow;
	public Button addToFaveGamesBtn;
	public GridPane mainGridPane;
	public Button timeBtn;
	public MenuButton timeLimitMenuInGameMenu;
	public GridPane timeBtnGridPane;
	private String selectedTime = "30s";

	public static void setStage (Stage stage) {
		GameMenuController.stage = stage;
		GameMenuController.stage.setOnCloseRequest(e -> GameMenuController.stage = null);
	}

	public static void setGameName (String gameName) {
		GameMenuController.gameName = gameName;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		gameTitle.setText(gameName);
		Gamer currentGamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();

		if (currentGamer.getFaveGames().contains(gameName))
			addToFaveGamesBtn.setStyle(addToFaveGamesBtn.getStyle() + "-fx-background-image:url('https://i.imgur.com/UODKjrB.png')");

		else
			addToFaveGamesBtn.setStyle(addToFaveGamesBtn.getStyle() + "-fx-background-image:url('https://i.imgur.com/z4JgICV.png')");


		switch (gameName) {
			case "BattleSea" -> {
				mainGridPane.setStyle(
						"-fx-background-image: url('https://i.imgur.com/7po5Ihx.jpg');" +
								"  -fx-background-size: 1000 600;" +
								"  -fx-background-position: right center;");

				timeLimitMenuInStartGameMenu.textProperty().addListener((observable, oldValue, newValue) -> selectedTime = newValue);
				timeLimitMenuInGameMenu.textProperty().addListener((observable, oldValue, newValue) -> selectedTime = newValue);

				// adding time options
				for (int i = 10; i < 60; i += 10) {
					timeLimitMenuInGameMenu.getItems().add(new MenuItem(i + "s") {{
						setOnAction(e -> timeLimitMenuInGameMenu.setText(getText()));
					}});
					timeLimitMenuInStartGameMenu.getItems().add(new MenuItem(i + "s") {{
						setOnAction(e -> timeLimitMenuInStartGameMenu.setText(getText()));
					}});
				}
				for (int i = 1; i <= 5; i++) {
					timeLimitMenuInGameMenu.getItems().add(new MenuItem(i + "m") {{
						setOnAction(e -> timeLimitMenuInGameMenu.setText(getText()));
					}});
					timeLimitMenuInStartGameMenu.getItems().add(new MenuItem(i + "m") {{
						setOnAction(e -> timeLimitMenuInStartGameMenu.setText(getText()));
					}});
				}
			}
			case "Reversi" -> {
				mainGridPane.getChildren().remove(timeBtnGridPane);
				newGamePropertyWindow.getChildren().removeAll(timeLimitAskLabel, timeLimitMenuInStartGameMenu);

				mainGridPane.setStyle(
						"-fx-background-image: url('https://i.imgur.com/VaeApyW.png');" +
								"  -fx-background-size: 650;" +
								"  -fx-background-position: center bottom;" +
								"  -fx-background-repeat: no-repeat;" +
								"  -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 55% , rgb(30,130,230), rgb(21,51,128));");
			}
			default -> throw new IllegalStateException("Unexpected value: " + gameName);
		}
	}

	public void newGame (ActionEvent actionEvent) {

		newGamePropertyWindow.setVisible(true);
		timeLimitMenuInStartGameMenu.setText(selectedTime);

		confirmTimeInGameMenu();
	}

	private void confirmTimeInGameMenu () {
		timeBtn.setVisible(true);
		timeLimitMenuInGameMenu.setVisible(false);
	}

	public void dontStartGame (ActionEvent actionEvent) {
		newGamePropertyWindow.setVisible(false);
	}

	public void startGame (ActionEvent actionEvent) {
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

	public void startBattleSea () {
		try {
			BattleSeaPlayPageController.setMaxTime(getMaxTimeSeconds());
			Stage battleSeaStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaEditBoardPage.fxml").toURI().toURL()),
					"BattleSea",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			BattleSeaEditBoardPageController.setStage(battleSeaStage);
			battleSeaStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getMaxTimeSeconds () {
		Matcher matcher = Pattern.compile("(?<num>[0-9]+)[m|s]").matcher(selectedTime); matcher.find();
		int time = Integer.parseInt(matcher.group("num"));
		time = selectedTime.endsWith("m") ? time * 60 : time;
		return time;
	}

	private void startReversi () {
		try {
			Stage reversiStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/ReversiGame.fxml").toURI().toURL()),
					"Reversi",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			ReversiGameController.setStage(reversiStage);
			reversiStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeFaveStatus (ActionEvent actionEvent) {
		Gamer currentGamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		AtomicReference<String> noBgImgStyle = new AtomicReference<>("");
		Arrays.asList(addToFaveGamesBtn.getStyle().split(";")).subList(0, addToFaveGamesBtn.getStyle().split(";").length - 1)
				.forEach(style -> noBgImgStyle.set("%s;%s".formatted(noBgImgStyle.get(), style)));

		if (currentGamer.getFaveGames().contains(gameName)) {
			currentGamer.removeFaveGame(gameName);
			addToFaveGamesBtn.setStyle(noBgImgStyle.get().substring(1) +
					";  -fx-background-image: url('https://i.imgur.com/z4JgICV.png');");
		}
		else {
			currentGamer.addToFaveGames(gameName);
			addToFaveGamesBtn.setStyle(noBgImgStyle.get().substring(1) +
					";  -fx-background-image: url('https://i.imgur.com/UODKjrB.png');");
		}
	}

	public void closeGame (ActionEvent actionEvent) {
		stage.close();
	}

	public void displayScoreboard (ActionEvent actionEvent) {
		confirmTimeInGameMenu();
		Game.getScoreboard(gameName).forEach(System.out::println);
		if (Game.getScoreboard(gameName).size() == 1 && !Game.getScoreboard(gameName).get(0).startsWith("Rank:"))
			return;
		try {
			GameScoreboardController.setGameName(gameName);
			Stage scoreBoardStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameScoreboard.fxml").toURI().toURL()),
					gameName + " Scoreboard",
					true,
					stage
			);
			GameScoreboardController.setStage(scoreBoardStage);
			scoreBoardStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTime (ActionEvent actionEvent) {
		timeBtn.setVisible(false);
		timeLimitMenuInGameMenu.setVisible(true);
	}

	public void displayLogOfGame (ActionEvent actionEvent) {
		confirmTimeInGameMenu();
		try {
			GameLogController.setGameName(gameName);
			Stage gameLogStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameLog.fxml").toURI().toURL()),
					gameName + " Log",
					true,
					stage
			);
			GameLogController.setStage(gameLogStage);
			gameLogStage.show();
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
