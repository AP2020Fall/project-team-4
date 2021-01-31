package Controller.Menus;

import Controller.MainController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.GameLog;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
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
				Image image = MainController.getImageFromFile("src/com/Resources/Images/battleseaBackground.png");
				gamePicture.setImage(image);
				gamePicture.setViewport(new Rectangle2D(image.getWidth() / 3, image.getHeight() / 3,
						image.getWidth() * 2 / 3, image.getWidth() * 2 / 3 / 4));
			}
			case "Reversi" -> {
				Image image = MainController.getImageFromFile("src/com/Resources/Images/reversiBackground.png");
				gamePicture.setImage(image);
				gamePicture.setViewport(new Rectangle2D(0, image.getHeight() / 3,
						image.getWidth(), image.getWidth() / 4));
			}
		}
		gamePicture.setFitWidth(400);
		gamePicture.setFitHeight(100);

		Controller.GameRelated.GameLogController.getInstance().displayLogOfGame(gameName);

		gameLogList.setStyle("-fx-background-color: #003768;  " +
				"-fx-control-inner-background: #003768;");

		LinkedList<Game> gameHistory = GameLog.getGameHistory(gameName);

		for (int i = 0; i < gameHistory.size(); i++) {
			Game game = gameHistory.get(i);
			gameLogList.getItems().add(
					generateGameLogEntry(
							new Gamer[]{
									game.getListOfPlayers().get(0).getGamer(),
									game.getListOfPlayers().get(1).getGamer()
							},
							new int[]{
									game.getInGameScore(1),
									game.getInGameScore(2)
							},
							game.getDateGameEnded(),
							i == 0 || !gameHistory.get(i).getDateGameEnded().toLocalDate().equals(gameHistory.get(i - 1).getDateGameEnded().toLocalDate()
							)
					));
		}

		gameLogList.getItems().forEach(gridPane -> gridPane.setAlignment(Pos.CENTER));
	}

	private GridPane generateGameLogEntry (Gamer[] gamers,
										   int[] scores,
										   LocalDateTime endDateTime,
										   boolean showDate) {
		return new GridPane() {{
			setHgap(5);
			setVgap(5);
			setMinSize(400, 200);
			setMaxSize(getMinWidth(), getMinHeight());
			setPadding(new Insets(0, 15, 0, 15));

			for (int i = 0; i < 3; i++) {
				int finalI = i;
				getColumnConstraints().add(new ColumnConstraints() {{
					setHalignment(HPos.CENTER);
					setPercentWidth((finalI == 2) ? 25 : 37.5);
				}});
			}
			for (int i = 0; i < 5; i++)
				getRowConstraints().add(new RowConstraints() {{
					setValignment(VPos.CENTER);
				}});

			// player pfp's
			for (int i = 0; i < gamers.length; i++) {
				int finalI = i;
				getChildren().add(new ImageView() {{
					setImage(new Image(gamers[finalI].getPfpUrl()));
					setFitWidth(75);
					setFitHeight(75);
					setPickOnBounds(true);
					setPreserveRatio(true);

					setColumnIndex(this, finalI);
					setRowIndex(this, 1);
					setRowSpan(this, 2);
				}});
			}

			// player usernames
			for (int i = 0; i < gamers.length; i++) {
				int finalI = i;
				getChildren().add(new Label() {{
					setText(gamers[finalI].getUsername());
					setTextFill(Color.WHITE);
					setFont(Font.font("Arial", 17));
					setEffect(new Glow());
					setColumnIndex(this, finalI);
					setRowIndex(this, 3);
				}});
			}

			// player scores
			for (int i = 0; i < scores.length; i++) {
				int finalI = i;
				getChildren().add(new Label() {{
					setText(String.valueOf(scores[finalI]));
					setTextFill(Color.valueOf("#fbff00"));
					setFont(Font.font("American Typewriter", 34));
					setEffect(new Glow());

					setColumnIndex(this, finalI);
					setRowIndex(this, 4);
				}});
			}

			// time game ended
			getChildren().add(new Label() {{
				setText(endDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("H:mm a")));
				setAlignment(Pos.CENTER_RIGHT);
				setTextFill(Color.valueOf("#9bafff"));
				setFont(Font.font("Apple Braille Outline 6 Dot", 15));
				setEffect(new Glow());
				setMargin(this, new Insets(0, 10, 0, 0));
				setHalignment(this, HPos.RIGHT);
				setMinWidth(50);
				setColumnIndex(this, 2);
				setRowIndex(this, 1);
			}});

			// date game ended
			getChildren().add(new HBox() {{
				setAlignment(Pos.CENTER);
				setMinHeight(30);
				setSpacing(10);

				for (int i = 0; i < 2; i++)
					getChildren().add(new Line() {{
						setStartX(-100);
						setStroke(Color.valueOf("#5c7dff"));
						setStrokeWidth(2);
					}});

				getChildren().add(1, new Label() {{
					setText(endDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
					setTextFill(Color.WHITE);
					setFont(Font.font("Apple Braille Outline 8 Dot", 15));
					setAlignment(Pos.CENTER);
					setMinSize(100, 25);
					setStyle("-fx-background-color: #5c7dff; -fx-background-radius: 3;");
					setEffect(new Glow());
				}});

				setColumnSpan(this, 3);
			}});
			if (!showDate) {
				getChildren().removeIf(node -> node instanceof HBox);
				getRowConstraints().remove(0);
			}
		}};
	}

	public static void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public void closeStageWrite(ActionEvent actionEvent){
		MainController.write("GameLog.closeStage");
	}

	public static void mouseIsOver (MouseEvent mouseEvent) {
		((Label) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOverWrite(MouseEvent mouseEvent){
		MainController.write("GameLog.mouseIsOver");
	}

	public static void mouseIsOut (MouseEvent mouseEvent) {
		((ImageView) mouseEvent.getSource()).setOpacity(1);
	}

	public void mouseIsOutWrite(MouseEvent mouseEvent){
		MainController.write("GameLog.mouseIsOut");
	}
}
