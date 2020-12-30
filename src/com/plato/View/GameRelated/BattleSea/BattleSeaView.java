package View.GameRelated.BattleSea;

import Controller.MainController;
import View.Menus.Menu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.LinkedList;

public class BattleSeaView extends Application {
	private static BattleSeaView battleSeaView;
	private Stage primaryStage;
	private Pane mainPane;

	public static BattleSeaView getInstance () {
		if (battleSeaView == null)
			battleSeaView = new BattleSeaView();
		return battleSeaView;
	}

	@Override
	public void start (Stage primaryStage) {
		primaryStage.show();
		this.primaryStage = primaryStage;
		initGameMenuView();
	}

	public static void main (String[] args) {
		launch(args);
	}

	public void initGameMenuView () {
		mainPane = new AnchorPane() {{
			double wh = Screen.getPrimary().getBounds().getHeight();
			setMinSize(wh, wh); setMaxSize(wh, wh);

			ImageView bg = new ImageView() {{
				setImage(new Image("https://i.pinimg.com/originals/a9/ac/64/a9ac648a584d68eebb451a0460125462.jpg"));
				setLayoutX(0); setLayoutY(0);
				setPreserveRatio(true);
				setSmooth(true);
				setFitWidth(wh); setFitHeight(wh);
			}};

			HBox buttons = new HBox() {{
				setAlignment(Pos.CENTER);

				Button startGame = null;
				startGame = new Button();
//				startGame.getStylesheets().add(String.valueOf(getClass().getClassLoader().getResource("../../../../Resources/BattleSeaMainMenu/Button.css")))

//				System.out.println("Path.of(\"Button.fxml\") = " + Path.of("src/com/plato/View/GameRelated/BattleSea/Button.fxml"));
//				System.out.println(startGame.getStylesheets().get(0));

				try {
					System.out.println(getClass().getResource(String.valueOf(Path.of("src/com/plato/View/GameRelated/BattleSea").relativize(Path.of("src/com/plato/View/GameRelated/BattleSea/Button.fxml")))));
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				getChildren().add(startGame);

//				System.out.println(getClass().getClassLoader().getResourceAsStream("BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("/src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResourceAsStream("/Users/dorrinsotoudeh/Desktop/project-team-4/src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//
//				System.out.println(getClass().getClassLoader().getResource("BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("/src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getClassLoader().getResource("/Users/dorrinsotoudeh/Desktop/project-team-4/src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//
//				System.out.println(getClass().getResource("BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("Button.css") == null);
//				System.out.println(getClass().getResource("/Button.css") == null);
//				System.out.println(getClass().getResource("src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("/src/com/Resources/BattleSeaMainMenu/Button.css") == null);
//				System.out.println(getClass().getResource("/Users/dorrinsotoudeh/Desktop/project-team-4/src/com/Resources/BattleSeaMainMenu/Button.css") == null);

//				startGame.setMinSize(200, 100);
//				startGame.setMaxSize(200, 100);
//				startGame.setText("YESS");


//					setFont(Font.font("Marker Felt", 20));
//					setTextFill(Color.YELLOW);
//					setStyle("-fx-background-color: ORANGERED");
//					setStyle("-fx-arc-height: 20");
//					setStyle("-fx-arc-width: 20");

//					setStyle(
//							"\tbox-shadow: 3px 4px 0px 0px #8a2a21;\n" +
//							"\tbackground:linear-gradient(to bottom, #c62d1f 5%, #f24437 100%);\n" +
//							"\tbackground-color:#c62d1f;\n" +
//							"\tborder-radius:18px;\n" +
//							"\tborder:1px solid #d02718;\n" +
//							"\tdisplay:inline-block;\n" +
//							"\tcursor:pointer;\n" +
//							"\tcolor:#ffffff;\n" +
//							"\tfont-family:Arial;\n" +
//							"\tfont-size:17px;\n" +
//							"\tpadding:7px 25px;\n" +
//							"\ttext-decoration:none;\n" +
//							"\ttext-shadow:0px 1px 0px #810e05;\n" +
//							"}\n" +
//							".myButton:hover {\n" +
//							"\tbackground:linear-gradient(to bottom, #f24437 5%, #c62d1f 100%);\n" +
//							"\tbackground-color:#f24437;\n" +
//							"}\n" +
//							".myButton:active {\n" +
//							"\tposition:relative;\n" +
//							"\ttop:1px;\n"
//							);
			}};

			getChildren().addAll(bg, buttons);
		}};

		MainController.getInstance().createAndReturnNewStage(
				mainPane, "Battlesea Main Menu", true, primaryStage
		).show();
	}


	public void displayBoard (StringBuilder board) {
		System.out.println(board.toString());
	}

	public void displayAll5RandomBoards (LinkedList<StringBuilder> boards) {
		for (StringBuilder board : boards)
			displayBoard(board);
	}

	public void displayRemainingTime (int seconds) {
		Menu.println("You have %ds remaining.".formatted(seconds));
	}

	public void displayOutOfTimeMessage (String otherPlayer) {
		Menu.println("%nSorry you ran out of time. Now it's %s's turn.".formatted(otherPlayer));
	}
}
