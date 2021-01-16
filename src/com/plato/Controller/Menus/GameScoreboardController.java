package Controller.Menus;

import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameScoreboardController implements Initializable {
	private static Stage stage;
	private static String gameName;
	public ListView<GridPane> scoreBoard;

	public static void setGameName (String gameName) {
		GameScoreboardController.gameName = gameName;
	}

	public static Stage getStage () {
		return stage;
	}

	public static void setStage (Stage stage) {
		GameScoreboardController.stage = stage;
		GameScoreboardController.stage.setOnCloseRequest(e -> {
			GameScoreboardController.stage = null;
			gameName = "";
		});
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		LinkedList<String> scoreBoardLinkedList = Game.getScoreboard(gameName);

		scoreBoardLinkedList.forEach(System.out::println);
		for (String scoreBoardEntry : scoreBoardLinkedList) {
			String regex = "Rank: (?<rank>[0-9]+),\tUsername: (?<un>[!-~]+),\tPoints: (?<pts>[0-9]+),\tWins: (?<w>[0-9]+),\tLosses: (?<l>[0-9]+),\tDraws: (?<d>[0-9]+),\tPlayed Count: (?<play>[0-9]+)";
			Matcher matcher = Pattern.compile(regex).matcher(scoreBoardEntry);
			if (!matcher.matches()) break;
			matcher.find();

			int rank = Integer.parseInt(matcher.group("rank")),
					pts = Integer.parseInt(matcher.group("pts")),
					wins = Integer.parseInt(matcher.group("w")),
					losses = Integer.parseInt(matcher.group("l")),
					draws = Integer.parseInt(matcher.group("d")),
					played = Integer.parseInt(matcher.group("play"));
			String username = matcher.group("un");
			Gamer gamer = (Gamer) Account.getAccount(username);

			scoreBoard.getItems().add(new GridPane() {{
				// setting up gridpane
				setHgap(7.5);
				setMaxHeight(75);
				setMinHeight(getMaxHeight());
				for (int i = 0; i < 5; i++)
					getColumnConstraints().add(new ColumnConstraints() {{
						setHalignment(HPos.CENTER);
					}});
				getRowConstraints().add(new RowConstraints() {{
					setValignment(VPos.CENTER);
				}});

				// adding gamer rank
				getChildren().add(new Label("#" + rank) {{
					setAlignment(Pos.CENTER);
					setTextFill(Color.valueOf("#0097ff"));
					setFont(Font.font("American Typewriter", 21));
					setEffect(new Glow());
					setColumnIndex(this, 0);
				}});

				// adding gamer pfp
				getChildren().add(new ImageView() {{
					setImage(new Image(gamer.getPfpUrl()));
					setFitHeight(75);
					setFitWidth(150);
					setPickOnBounds(true);
					setPreserveRatio(true);
					setColumnIndex(this, 1);
				}});

				// adding gamer username
				getChildren().add(new Label(gamer.getUsername()) {{
					setTextFill(Color.valueOf("#eeff00"));
					setFont(Font.font("American Typewriter", 30));
					setEffect(new Glow());
					setColumnIndex(this, 2);
				}});

				// adding gamer points
				getChildren().add(new Label() {{
					setText(pts + "pts");
					setAlignment(Pos.CENTER);
					setTextFill(Color.valueOf("#ffa300"));
					setFont(Font.font("American Typewriter", 21));
					setEffect(new Glow());
					setColumnIndex(this, 3);
				}});

				// adding gamer stats "W/D/L"
				getChildren().add(new Label("%d/%d/%d".formatted(wins, draws, losses)) {{
					setAlignment(Pos.CENTER);
					setTextFill(Color.valueOf("#ffa300"));
					setFont(Font.font("American Typewriter", 21));
					setEffect(new Glow());
					setColumnIndex(this, 4);
				}});
			}});
		}
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(1);
	}
}
