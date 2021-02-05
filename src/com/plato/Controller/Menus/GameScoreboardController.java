package Controller.Menus;

import Controller.MainController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Controller.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameScoreboardController implements Initializable {
	private static Stage stage;
	private static String gameName;
	public ListView<GridPane> scoreBoard;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;
	private Object Account;

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
		// TODO: 2/3/2021
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		LinkedList<String> scoreBoardLinkedList = Game.getScoreboard(gameName);

		scoreBoard.setStyle("-fx-background-color: #003768;  " +
				"-fx-control-inner-background: #003768;");

		scoreBoardLinkedList.forEach(System.out::println);

		scoreBoard.getItems().add(generateScoreboardEntry(0, 0, 0, 0, 0, null));

		for (String scoreBoardEntry : scoreBoardLinkedList) {
			String regex = "Rank: (?<rank>[-0-9]+), {2}Username: (?<un>[!-~]+), {2}Points: (?<pts>[-0-9]+), {2}Wins: (?<w>[-0-9]+), {2}Losses: (?<l>[-0-9]+), {2}Draws: (?<d>[-0-9]+), {2}Played Count: (?<play>[-0-9]+)";
			Matcher matcher = Pattern.compile(regex).matcher(scoreBoardEntry);
			if (!matcher.matches()) break;

			int rank = Integer.parseInt(matcher.group("rank")),
					pts = Integer.parseInt(matcher.group("pts")),
					wins = Integer.parseInt(matcher.group("w")),
					losses = Integer.parseInt(matcher.group("l")),
					draws = Integer.parseInt(matcher.group("d")),
					played = Integer.parseInt(matcher.group("play"));
			String username = matcher.group("un");
			try {
				dataOutputStream.writeUTF("getAccount_" + username);
				dataOutputStream.flush();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			Gamer gamer = null;
			try {
				gamer = MainController.getInstance().getGson().fromJson(dataInputStream.readUTF() , Gamer.class);
			} catch (IOException exception) {
				exception.printStackTrace();
			}

			scoreBoard.getItems().add(generateScoreboardEntry(rank, pts, wins, losses, draws, gamer));
		}
	}

	@NotNull
	private GridPane generateScoreboardEntry (int rank, int pts, int wins, int losses, int draws, Gamer gamer) {
		return new GridPane() {{
			// setting up gridpane
			setHgap(7.5);
			setMaxWidth(575);

			for (int i = 0; i < 5; i++)
				getColumnConstraints().add(new ColumnConstraints() {{
					setHalignment(HPos.CENTER);
				}});
			getRowConstraints().add(new RowConstraints() {{
				setValignment(VPos.CENTER);
				setMinHeight(100);
				setMaxHeight(getMinHeight());
			}});

			// adding gamer rank
			getChildren().add(new Label("#" + rank) {{
				setAlignment(Pos.CENTER);
				setTextFill(Color.valueOf("#0097ff"));
				setFont(Font.font("American Typewriter", 21));
				setEffect(new Glow());
				setMinWidth(75);
				setMaxWidth(getMinWidth());
				setColumnIndex(this, 0);
				if (gamer == null) {
					setText("Rank");
					setFont(Font.font(getFont().getFamily(), 20));
					setTextFill(Color.WHITE);
				}
			}});

			// adding gamer pfp
			if (gamer != null)
				getChildren().add(new ImageView() {{
					setImage(new Image(gamer.getPfpUrl()));
					setPickOnBounds(true);
					setPreserveRatio(true);
					setMinWidth(150);
					setMaxWidth(getMinWidth());
					setViewport(new Rectangle2D(0, (getImage().getHeight() - getImage().getWidth() / 2) / 2, getImage().getWidth(), getImage().getWidth() / 2));
					setFitWidth(150);
					setColumnIndex(this, 1);
				}});

			// adding gamer username

			getChildren().add(new Label() {{
				setTextFill(Color.valueOf("#eeff00"));
				setFont(Font.font("American Typewriter", 30));
				setEffect(new Glow());
				setMinWidth(225);
				setMaxWidth(getMinWidth());
				setColumnIndex(this, 2);

				if (gamer == null) {
					setText("Gamer");
					setFont(Font.font(getFont().getFamily(), 20));
					setTextFill(Color.WHITE);
					setMinWidth(390);
					setMaxWidth(getMinWidth());
					setAlignment(Pos.CENTER);
					setColumnIndex(this, 1);
					setColumnSpan(this, 2);
				}
				else {
					setText(gamer.getUsername());
				}
			}});

			// adding gamer points
			getChildren().add(new Label() {{
				setText(String.valueOf(pts));
				setAlignment(Pos.CENTER);
				setTextFill(Color.valueOf("#ffa300"));
				setFont(Font.font("American Typewriter", 21));
				setEffect(new Glow());
				setMinWidth(75);
				setMaxWidth(getMinWidth());
				setColumnIndex(this, 3);
				if (gamer == null) {
					setText("Pts");
					setFont(Font.font(getFont().getFamily(), 20));
					setTextFill(Color.WHITE);
				}
			}});

			// adding gamer stats "W/D/L"
			getChildren().add(new Label("%d/%d/%d".formatted(wins, draws, losses)) {{
				setAlignment(Pos.CENTER);
				setTextFill(Color.valueOf("#ffa300"));
				setFont(Font.font("American Typewriter", 21));
				setEffect(new Glow());
				setMinWidth(75);
				setMaxWidth(getMinWidth());
				setColumnIndex(this, 4);
				if (gamer == null) {
					setText("W/D/L");
					setFont(Font.font(getFont().getFamily(), 20));
					setTextFill(Color.WHITE);
				}
			}});
		}};
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
