package Controller.Menus;

import Controller.GameRelated.GameController;
import Controller.GameRelated.Reversi.ReversiController;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReversiGameController implements Initializable {
	private static Stage stage;
	private static Reversi currentGame;
	public Circle turnIndicatorW, turnIndicatorB;
	public ImageView pfpW, pfpB;
	public Label usernameW, usernameB;
	public Button confirmMoveBtn, showMovesBtn;
	public TilePane board;
	private PlayerReversi player1, player2;

	public static void setStage (Stage stage) {
		ReversiGameController.stage = stage;
		ReversiGameController.stage.setOnCloseRequest(e -> {
			ReversiGameController.stage = null;
			currentGame = null;
			GameController.getInstance().setCurrentGameInSession(null);
		});
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		currentGame = (Reversi) GameController.getInstance().getCurrentGameInSession();

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

		updateAvailableCoordinates();

		updateBoard();
	}

	public void updateAvailableCoordinates () {
		ReversiController.getInstance().displayAvailableCoords();

		String[][] currentGameBoard = currentGame.getBoard();
		for (int y = 0, currentGameBoardLength = currentGameBoard.length; y < currentGameBoardLength; y++)
			for (int x = 0, rowLength = currentGameBoard[y].length; x < rowLength; x++) {
				Label cell = (Label) board.getChildren().get(x + y * 8);

				if (currentGameBoard[y][x].equals("-")) {
					if (!currentGame.hasPlayerMoved().get() && currentGame.getAvailableCoordinates().contains("%d,%d".formatted(y + 1, x + 1)))
						cell.setStyle("-fx-background-color: #6f9434;" +
								"  -fx-background-size: 25;" +
								"  -fx-background-position: center;" +
								"  -fx-background-repeat: no-repeat;" +
								"  -fx-background-image: url('https://i.imgur.com/aVt01yG.png');");
					else
						cell.setStyle("-fx-background-color: #6f9434;");
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
				cell.setStyle("-fx-background-color: #6f9434;" +
						"  -fx-background-size: 60;" +
						"  -fx-background-position: center;" +
						"  -fx-background-repeat: no-repeat;");

				switch (currentGameBoard[y][x]) {
					case "w" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/8djkzwC.png');");
					case "b" -> cell.setStyle(cell.getStyle() +
							"  -fx-background-image: url('https://i.imgur.com/vKt2BwI.png');");
					case "-" -> updateAvailableCoordinates();
					default -> throw new IllegalStateException("Unexpected value: " + cell);
				}
			}
	}

	public void closeGame (ActionEvent actionEvent) {
		stage.close();
	}

	public void putMarkIfPossible (MouseEvent mouseEvent) {
		int index = board.getChildren().indexOf(mouseEvent.getSource());

		try {
			ReversiController.getInstance().placeDisk(getXFrom1(index), getYFrom1(index));

			confirmMoveBtn.setVisible(true);

			updateBoard();

//			board.getChildren().stream()
//					.map(node -> ((Label) node))
//					.forEach(cell -> {
//						if (currentGame.hasPlayerMoved() && (!cell.getStyle().contains("8djkzwC.png") ||!cell.getStyle().contains("vKt2BwI.png"))) {
//							cell.setStyle("-fx-background-color: #6f9434;");
//						}
//					});
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
		y = (y == 9 ? 8 : y);
		return y;
	}

	public void confirmMove (ActionEvent actionEvent) {
		// TODO: 1/15/2021 AD
	}

	public void showMoves (ActionEvent actionEvent) {
		// TODO: 1/16/2021 AD
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
}
