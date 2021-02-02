package Controller.Menus;

import Controller.GameRelated.GameController;
import Controller.GameRelated.Reversi.ReversiController;
import Controller.MainController;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ReversiGameController implements Initializable {
	private static Stage stage;
	private static Reversi currentGame;
	private static LinkedList<String> changingDisks = new LinkedList<>();
	public Circle turnIndicatorW, turnIndicatorB;
	public ImageView pfpW, pfpB;
	public Label usernameW, usernameB;
	public Button confirmMoveBtn, showMovesBtn;
	public TilePane board;
	public Label pointsW, pointsB;
	public GridPane historyGridPane;
	public ListView<GridPane> moveHistoryList;
	private PlayerReversi player1, player2;
	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public static void setStage (Stage stage) {
		ReversiGameController.stage = stage;
		ReversiGameController.stage.setOnCloseRequest(e -> {
			ReversiGameController.stage = null;
			currentGame = null;
			GameController.getInstance().setCurrentGameInSession(null);
		});
	}

	/**
	 * @param x from 0 to 7
	 * @param y from 0 to 7
	 */
	public static void addToChangingDisks (int x, int y) {
		if (!changingDisks.contains("%d %d".formatted(x, y)))
			if (!currentGame.getBoard()[y][x].equals("-"))
				changingDisks.add("%d %d".formatted(x, y));
		System.out.println("currentGame.getBoard()[" + (y + 1) + "][" + (x + 1) + "] = " + currentGame.getBoard()[y][x]);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		currentGame = (Reversi) GameController.getInstance().getCurrentGameInSession();

		currentGame.pointsBProperty().addListener(observable -> updatePoints());
		currentGame.pointsWProperty().addListener(observable -> updatePoints());

		currentGame.updatePointProperties();

		currentGame.hasPlayerMoved().addListener((observable, oldValue, newValue) -> {
			updateAvailableCoordinates();
		});

		player1 = (PlayerReversi) currentGame.getListOfPlayers().get(0); // b
		player2 = (PlayerReversi) currentGame.getListOfPlayers().get(1); // w

		updateTurnIndicators();

		usernameB.setText(player1.getUsername());
		usernameW.setText(player2.getUsername());
		pfpB.setImage(new Image(player1.getGamer().getPfpUrl()));
		pfpW.setImage(new Image(player2.getGamer().getPfpUrl()));

		confirmMoveBtn.setVisible(false);

		updateBoard();

		updateAvailableCoordinates();
	}

	private void updatePoints () {
		pointsW.setText(String.valueOf(currentGame.pointsWProperty().get()));
		pointsB.setText(String.valueOf(currentGame.pointsBProperty().get()));
	}

	public void updateAvailableCoordinates () {
		dataOutputStream.writeUTF("displayAvaiableCoords");
		ReversiController.getInstance().displayAvailableCoords();

		String[][] currentGameBoard = currentGame.getBoard();
		for (int y = 0, currentGameBoardLength = currentGameBoard.length; y < currentGameBoardLength; y++)
			for (int x = 0, rowLength = currentGameBoard[y].length; x < rowLength; x++) {
				Label cell = (Label) board.getChildren().get(x + y * 8);

				if (currentGameBoard[y][x].equals("-")) {
					if (!currentGame.hasPlayerMoved().get() && currentGame.getAvailableCoordinates().contains("%d,%d".formatted(y + 1, x + 1)))
						cell.setStyle("-fx-background-size: 25;" +
								"  -fx-background-position: center;" +
								"  -fx-background-repeat: no-repeat;" +
								"  -fx-background-image: url('https://i.imgur.com/aVt01yG.png');");
				}
			}
	}

	public void updateTurnIndicators () {
		switch (currentGame.getTurnNum()) {
			case 0 -> turnIndicatorB.setVisible(true);
			case 1 -> turnIndicatorB.setVisible(false);
			default -> throw new IllegalStateException("Unexpected value: " + currentGame.getTurnNum());
		}
		turnIndicatorW.setVisible(!turnIndicatorB.isVisible());
	}

	// show disks and available coordinattes
	public void updateBoard () {
		String[][] currentGameBoard = currentGame.getBoard();
		for (int y = 0, currentGameBoardLength = currentGameBoard.length; y < currentGameBoardLength; y++)
			for (int x = 0, rowLength = currentGameBoard[y].length; x < rowLength; x++) {
				Label cell = (Label) board.getChildren().get(x + y * 8);
				cell.setStyle("-fx-background-size: 60;" +
						"  -fx-background-position: center;" +
						"  -fx-background-repeat: no-repeat;");

				switch (currentGameBoard[y][x]) {
					case "w" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/8djkzwC.png');");
					case "b" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/vKt2BwI.png');");
					case "-" -> {}
					default -> throw new IllegalStateException("Unexpected value: " + cell);
				}
			}
	}

	public void putMarkIfPossible (MouseEvent mouseEvent) {
		int index = board.getChildren().indexOf(mouseEvent.getSource());
		try {
			dataOutputStream.writeUTF("placeDisk_" + getXFrom1(index) + "_" + getYFrom1(index));
			ReversiController.getInstance().placeDisk(getXFrom1(index), getYFrom1(index));

			showColorChanges();

			confirmMoveBtn.setVisible(true);

			currentGame.updatePointProperties();
		} catch (ReversiController.PlayerHasAlreadyPlacedDiskException e) {
			System.out.println(e.getMessage());
		}
	}

	public int getXFrom1 (int index) {
		int x = (index + 1) % 8;
		x = (x == 0 ? 8 : x);
		return x;
	}

	public int getYFrom1 (int index) {
		int y = (index + 1) / 8 + 1;
		if (getXFrom1(index) == 8) y--;
		return y;
	}

	private void showColorChanges () {
		System.out.println("ReversiGameController.showColorChanges");
		for (int i = 0; i < changingDisks.size(); i++) {
			String changingDiskCoord = changingDisks.get(i);
			int x = Integer.parseInt(changingDiskCoord.split(" ")[0]),
					y = Integer.parseInt(changingDiskCoord.split(" ")[1]);

			Label cell = (Label) board.getChildren().get(x + 8 * y);

			RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), cell);
			rotateTransition.setByAngle(180);
			rotateTransition.setCycleCount(1);
			int finalI = i;
			rotateTransition.setOnFinished(event -> {
				System.out.println("changingDiskCoord = " + (x + 1) + " " + (y + 1));
				System.out.println("index = " + x + 8 * y);

				cell.setStyle("-fx-background-size: 60;" +
						"  -fx-background-position: center;" +
						"  -fx-background-repeat: no-repeat;");

				switch (currentGame.getBoard()[y][x]) {
					case "w" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/8djkzwC.png');");
					case "b" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/vKt2BwI.png');");
					case "-" -> {}
					default -> throw new IllegalStateException("Unexpected value: " + cell);
				}

				if (finalI == changingDisks.size() - 1) {
					changingDisks.clear();
				}

				updateBoard();
			});
			rotateTransition.play();
		}
	}

	public void confirmMove (ActionEvent actionEvent) {
		ReversiController.getInstance().nextTurn();

		if (currentGame.gameHasEnded()) {
			MainController.getInstance().saveEverything();
			displayGameConclusion();
		}
		else {

			confirmMoveBtn.setVisible(false);

			updateTurnIndicators();

			updateAvailableCoordinates();

			System.out.println("Move History");
			ReversiController.getInstance().displayPrevMoves();
		}
	}

	private void displayGameConclusion () {
		try {
			GameConclusionWindowController.setGame(currentGame);
			Stage concStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameConclusionWindow.fxml").toURI().toURL()),
					"",
					true,
					stage
			);
			GameConclusionWindowController.setStage(concStage);
			concStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMoves (ActionEvent actionEvent) {
		moveHistoryList.getItems().clear();

		for (int i = 0; i < currentGame.getMoves().size(); i += 2) {
			String move = currentGame.getMoves().get(i);

			int finalI = i;
			moveHistoryList.getItems().add(new GridPane() {{
				getRowConstraints().add(new RowConstraints() {{
					setValignment(VPos.CENTER);
					setMinHeight(20);
					setMaxHeight(getMinHeight());
				}});
				for (int i = 0; i < 3; i++) {
					int finalI = i;
					getColumnConstraints().add(new ColumnConstraints() {{
						setHalignment(HPos.CENTER);
						setMinWidth(finalI == 0 ? 35 : 105);
						setMaxWidth(getMinWidth());
					}});
				}

				// number of the pair of move
				getChildren().add(new Label((finalI / 2 + 1) + ".") {{
					setTextAlignment(TextAlignment.CENTER);
					setFont(Font.font("Arial", 14));

					setColumnIndex(this, 0);
					setRowIndex(this, 0);
				}});

				// black move
				int x = Integer.parseInt(move.split(" ")[1]), y = Integer.parseInt(move.split(" ")[2]);
				String placement = Character.toString("ABCDEFGH".charAt(x - 1)) + y;
				getChildren().add(new Label(placement) {{
					setTextAlignment(TextAlignment.CENTER);
					setFont(Font.font("Arial", 14));

					setColumnIndex(this, 1);
					setRowIndex(this, 0);
				}});

				// white move if available
				getChildren().add(new Label() {{
					setTextAlignment(TextAlignment.CENTER);
					setFont(Font.font("Arial", 14));

					if (finalI + 1 < currentGame.getMoves().size()) {
						String move2 = currentGame.getMoves().get(finalI + 1);
						int x2 = Integer.parseInt(move2.split(" ")[1]), y2 = Integer.parseInt(move2.split(" ")[2]);
						String placement2 = String.valueOf("ABCDEFGH".charAt(x2 - 1)) + y2;
						setText(placement2);
					}

					setColumnIndex(this, 2);
					setRowIndex(this, 0);
				}});
			}});
		}

		historyGridPane.setVisible(true);
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(1);
	}

	public void mouseIsOverCell (MouseEvent mouseEvent) {
		// TODO: 1/16/2021 AD

//		System.out.println("x,y = " + getXFrom1(board.getChildren().indexOf(mouseEvent.getSource())) + "," + getYFrom1(board.getChildren().indexOf(mouseEvent.getSource())));
	}

	public void closeGame (ActionEvent actionEvent) {
		stage.close();
	}

	public void closeMoveHistory (ActionEvent actionEvent) {
		historyGridPane.setVisible(false);
		System.out.println("close Move History");
	}
}
