package Controller.Menus;

import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.GameController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.Bomb;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleSeaPlayPageController implements Initializable {
	private static Stage stage;
	private static BattleSea currentGame;
	public ImageView pfp1, pfp2;
	public Circle turnIndicator1, turnIndicator2;
	public Label username1, username2;
	public ProgressIndicator timer1, timer2;
	public GridPane opponentBoardGridpane, yourBoardGridpane;
	public TilePane clickableOpponentBoardTilePane;
	private PlayerBattleSea player1, player2;

	public static void setStage (Stage stage) {
		BattleSeaPlayPageController.stage = stage;
		BattleSeaPlayPageController.stage.setOnCloseRequest(e -> {
			BattleSeaPlayPageController.stage = null;
			currentGame = null;
		});
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		currentGame = (BattleSea) GameController.getInstance().getCurrentGameInSession();

		setShipLabelIDs();

		player1 = (PlayerBattleSea) currentGame.getListOfPlayers().get(0);
		player2 = (PlayerBattleSea) currentGame.getListOfPlayers().get(1);

		username1.setText(player1.getUsername());
		username2.setText(player2.getUsername());
		pfp1.setImage(new Image(player1.getGamer().getPfpUrl()));
		pfp2.setImage(new Image(player2.getGamer().getPfpUrl()));

		updateTurnIndicators();
		updateAllPage();
	}

	private void updateTurnIndicators () {
		switch (currentGame.getTurnNum()) {
			case 0 -> turnIndicator1.setVisible(true);
			case 1 -> turnIndicator1.setVisible(false);
			default -> throw new IllegalStateException("Unexpected value: " + currentGame.getTurnNum());
		}
		turnIndicator2.setVisible(!turnIndicator1.isVisible());
		timer1.setVisible(turnIndicator1.isVisible());
		timer2.setVisible(turnIndicator2.isVisible());
	}

	public void updateOpponentBoard () {
		PlayerBattleSea currentPlayer = (PlayerBattleSea) currentGame.getTurnPlayer();
		LinkedList<Ship> opponentBoard = currentPlayer.getOpponentShips();

		setBoard(opponentBoard, opponentBoardGridpane);

		// show only completely destroyed ships
		for (Ship ship : opponentBoard)
			getShipView(opponentBoardGridpane, ship).setVisible(ship.isDestroyed(currentPlayer));

		updateBombThrowability();

		updateBombs(currentPlayer.getBombsThrown(), opponentBoardGridpane);
	}

	public void updateYourBoard () {
		PlayerBattleSea currentPlayer = (PlayerBattleSea) currentGame.getTurnPlayer();
		LinkedList<Ship> yourBoard = currentPlayer.getShips();

		setBoard(yourBoard, yourBoardGridpane);

		updateBombs(currentPlayer.getOpponentBombsThrown(), yourBoardGridpane);
	}

	public void setBoard (LinkedList<Ship> board, GridPane boardToShowShipsIn) {
		AtomicBoolean firstSmallShipIsMoved = new AtomicBoolean(false);

		board.forEach(ship -> {
			String size = ship.getL_SIZE() + "_" + ship.getS_SIZE();
			if (size.equals("2_1")) {
				if (!firstSmallShipIsMoved.get()) {
					size += "_1";
					firstSmallShipIsMoved.set(true);
				}
				else
					size += "_2";
			}
			String finalSize = size;
			Label shipToMove = boardToShowShipsIn.getChildren().stream()
					.filter(node -> node.getId().contains(finalSize))
					.map(node -> ((Label) node))
					.findAny().get();

			shipToMove.setRotate(ship.isVertical() ? 0 : 90);

			GridPane.setColumnIndex(shipToMove, ship.getLeftMostX() - 1);
			GridPane.setRowIndex(shipToMove, ship.getTopMostY() - 1);

			GridPane.setColumnSpan(shipToMove, ship.isVertical() ? ship.getS_SIZE() : ship.getL_SIZE());
			GridPane.setRowSpan(shipToMove, ship.isVertical() ? ship.getL_SIZE() : ship.getS_SIZE());
		});
	}

	private Label getShipView (GridPane board, Ship ship) {
		return board.getChildren().stream()
				.filter(node -> node instanceof Label)
				.map(node -> ((Label) node))
				.filter(ship1 -> GridPane.getColumnIndex(ship1) + 1 == ship.getLeftMostX() && GridPane.getRowIndex(ship1) + 1 == ship.getTopMostY())
				.findAny().get();
	}

	private void updateBombs (LinkedList<Bomb> bombs, GridPane boardToShowBombsIn) {
		boardToShowBombsIn.getChildren()
				.removeIf(node -> GridPane.getColumnSpan(node) == 1 && GridPane.getRowSpan(node) == 1); // remove only bombs

		bombs.forEach(bomb -> {
			boardToShowBombsIn.getChildren().add(
					getBombLabel(bomb, (boardToShowBombsIn.getId().toLowerCase().startsWith("o")))
			);
		});
	}

	private void updateBombThrowability () {
		clickableOpponentBoardTilePane.getChildren().stream()
				.filter(node -> node instanceof Label)
				.map(node -> ((Label) node))
				.forEach(coord -> {
					int index = clickableOpponentBoardTilePane.getChildren().indexOf(coord);

					EventHandler<MouseEvent> onEntered = new EventHandler<>() {
						@Override
						public void handle (MouseEvent event) {
							try {
								if (BombController.getInstance().canThrowBomb(getXFrom1(index), getYFrom1(index))) {
									((Label) event.getSource()).setOpacity(0.3);
									System.out.printf("(x,y) = (%d,%d)%n", getXFrom1(index), getYFrom1(index));
								}
							} catch (BombController.InvalidCoordinateException | BombController.CoordinateAlreadyBombedException e) {
								coord.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
							}
						}
					},
							onExited = new EventHandler<>() {
								@Override
								public void handle (MouseEvent event) {
									try {
										if (BombController.getInstance().canThrowBomb(getXFrom1(index), getYFrom1(index)))
											((Label) event.getSource()).setOpacity(0);
									} catch (BombController.InvalidCoordinateException | BombController.CoordinateAlreadyBombedException e) {
										coord.removeEventHandler(MouseEvent.MOUSE_EXITED, this);
									}
								}
							},
							onClick = new EventHandler<>() {
								@Override
								public void handle (MouseEvent e) {
									try {
										BombController.getInstance().throwBomb(getXFrom1(index), getYFrom1(index));

										updateAllPage();
									} catch (BombController.InvalidCoordinateException | BombController.CoordinateAlreadyBombedException exception) {
										coord.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
									}
								}
							};
					coord.addEventHandler(MouseEvent.MOUSE_CLICKED, onClick);
					coord.addEventHandler(MouseEvent.MOUSE_ENTERED, onEntered);
					coord.addEventHandler(MouseEvent.MOUSE_EXITED, onExited);
				});
	}

	private void updateAllPage () {
		updateYourBoard();
		updateOpponentBoard();
	}

	public int getXFrom1 (int index) {
		int x = (index + 1) % 10;
		x = (x == 0 ? 10 : x);
		return x;
	}

	public int getYFrom1 (int index) {
		int y = (index + 1) / 10;
//		y = (y == 11 ? 10 : y);
		return y;
	}

	private Label getBombLabel (Bomb bomb, boolean isForBigBoard) {
		return new Label() {{
			String imgUrl = (bomb.wasSuccessful() ? "https://i.imgur.com/EH897Ol.png" : "https://i.imgur.com/MFUJ8DN.png");
			int imgSize = (isForBigBoard ? 60 : 30);

			setStyle("-fx-background-image: url('" + imgUrl + "');" +
					"  -fx-background-size: " + imgSize + ";" +
					"  -fx-background-position: center;" +
					"  -fx-background-repeat: no-repeat;");

			setMinSize(imgSize, imgSize);
			setMaxSize(imgSize, imgSize);

			GridPane.setColumnIndex(this, bomb.getX());
			GridPane.setRowIndex(this, bomb.getY());
		}};
	}

	public void closeGame (ActionEvent actionEvent) {
		stage.close();
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(1);
	}

	private void setShipLabelIDs () {
		int index = -1;
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d".formatted(5, 2));
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d".formatted(5, 1));
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d".formatted(4, 1));
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d".formatted(3, 1));
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d_%d".formatted(2, 1, 1));
		opponentBoardGridpane.getChildren().get(++index).setId("Oship%d_%d_%d".formatted(2, 1, 2));

		index = -1;
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d".formatted(5, 2));
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d".formatted(5, 1));
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d".formatted(4, 1));
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d".formatted(3, 1));
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d_%d".formatted(2, 1, 1));
		yourBoardGridpane.getChildren().get(++index).setId("Yship%d_%d_%d".formatted(2, 1, 2));
	}
}
