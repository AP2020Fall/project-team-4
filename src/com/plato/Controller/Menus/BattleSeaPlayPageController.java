package Controller.Menus;

import Controller.GameRelated.BattleSea.BombController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.Bomb;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleSeaPlayPageController implements Initializable {
	private static Stage stage;
	private static BattleSea currentGame;
	private static int maxTime;
	private static boolean bombThrown = false;
	public ImageView pfp1, pfp2;
	public Circle turnIndicator1, turnIndicator2;
	public Label username1, username2;
	public ProgressBar timer1, timer2;
	public Label timerNum1, timerNum2;
	public GridPane opponentBoardGridpane, yourBoardGridpane;
	public TilePane clickableOpponentBoardTilePane;
	private PlayerBattleSea player1, player2;
	private IntegerProperty secondsRemaining = new SimpleIntegerProperty(maxTime);
	private Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<>() {
		@Override
		public void handle (ActionEvent e) {
			secondsRemaining.set(secondsRemaining.get() - 1);
			if (!currentGame.gameEnded())
				BattleSeaPlayPageController.this.updateAllPage();
			else {
				currentGame.concludeGame();
				MainController.getInstance().saveEverything();
				displayGameConclusion();
				timer.stop();
				System.out.println("Game Ended ");
			}
		}
	}));

	public static void setStage (Stage stage) {
		BattleSeaPlayPageController.stage = stage;
		BattleSeaPlayPageController.stage.setOnCloseRequest(e -> {
			BattleSeaPlayPageController.stage = null;
			currentGame = null;
			GameController.getInstance().setCurrentGameInSession(null);
		});
	}

	public static void setMaxTime (int maxTime) {
		BattleSeaPlayPageController.maxTime = maxTime;
	}

	public static void bombThrown () {
		bombThrown = true;
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

		secondsRemaining.addListener(observable -> {
			if (bombThrown)
				timer.playFromStart();
			if (!currentGame.gameHasEnded())
				updateAllPage();
		});

		timer.setCycleCount(maxTime);
		timer.setAutoReverse(false);

		timer.statusProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Animation.Status.STOPPED) {
				if (!currentGame.gameEnded()) {
					secondsRemaining.set(maxTime);

					if (bombThrown) {
						if (!((PlayerBattleSea) currentGame.getTurnPlayer())
								.getBombsThrown().getLast().wasSuccessful())
							currentGame.nextTurn();
					}
					else
						currentGame.nextTurn();
					bombThrown = false;
					timer.playFromStart();
				}
			}
		});

		timer.play();

		updateAllPage();
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

	public double getTimePercentageRemaining () {
		return ((double) maxTime - secondsRemaining.doubleValue()) / ((double) maxTime);
	}

	private void updateTimers () {
		timer1.setVisible(turnIndicator1.isVisible());
		timer2.setVisible(turnIndicator2.isVisible());
		timerNum1.setVisible(turnIndicator1.isVisible());
		timerNum2.setVisible(turnIndicator2.isVisible());

		double percentage = Math.max(Math.min(
				getTimePercentageRemaining()
				, 1.0), 0.0);

		timer1.setProgress(percentage);
		timer2.setProgress(percentage);
		timerNum1.setText(secondsRemaining.intValue() + "s");
		timerNum2.setText(timerNum1.getText());
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
		PlayerBattleSea currentPlayer = (PlayerBattleSea) currentGame.getTurnPlayer(),
				opponentPlayer = ((PlayerBattleSea) currentGame.getOpponentOf(currentPlayer));
		LinkedList<Ship> opponentBoard = currentPlayer.getOpponentShips();

		setBoard(opponentBoard, opponentBoardGridpane);

		// show only completely destroyed ships
		for (Ship ship : opponentBoard) {
			getShipView(opponentBoardGridpane, ship).setVisible(ship.isDestroyed(opponentPlayer));
		}

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

					EventHandler<MouseEvent>
							onEntered = new EventHandler<>() {
						@Override
						public void handle (MouseEvent event) {
							try {
								if (BombController.getInstance().canThrowBomb(getXFrom1(index), getYFrom1(index)))
									((Label) event.getSource()).setOpacity(0.3);
							} catch (BombController.CoordinateAlreadyBombedException e) {
								coord.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
							}
						}
					},
							onExited = event -> ((Label) event.getSource()).setOpacity(0),
							onClick = new EventHandler<>() {
								@Override
								public void handle (MouseEvent e) {
									throwBombIfPossible(index, coord, this);
								}
							};

					coord.addEventHandler(MouseEvent.MOUSE_CLICKED, onClick);
					coord.addEventHandler(MouseEvent.MOUSE_ENTERED, onEntered);
					coord.addEventHandler(MouseEvent.MOUSE_EXITED, onExited);
				});
	}

	private void throwBombIfPossible (int index, Label coord, EventHandler<MouseEvent> onClickHandler) {
		PlayerBattleSea currentPlayer = (PlayerBattleSea) currentGame.getTurnPlayer(),
				opponentPlayer = ((PlayerBattleSea) currentGame.getOpponentOf(currentPlayer));

		try {
			if (!bombThrown) {
				BombController.getInstance().throwBomb(getXFrom1(index), getYFrom1(index));
				System.out.println("bomb thrown at (x,y)=(%d,%d)".formatted(getXFrom1(index), getYFrom1(index)));

				// animate explosions if ship is destroyed
				if (opponentPlayer.shipExistsInXY(getXFrom1(index), getYFrom1(index))) {
					Ship shipToBeBombed = opponentPlayer.getShipAboutToBeBombed(getXFrom1(index), getYFrom1(index));

					if (shipToBeBombed.isDestroyed(opponentPlayer)) {
						Image explosionSprite = MainController.getImageFromFile("src/com/Resources/Images/explosion.png");

						animateSuccessFulBomb(explosionSprite, shipToBeBombed);
					}
				}
			}
		} catch (BombController.CoordinateAlreadyBombedException exception) {
			coord.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);
		}
	}

	private void animateSuccessFulBomb (Image image, Ship shipToBeBombed) {

		class SpriteAnimation extends Transition {

			private final ImageView imageView;
			private int lastIndex;

			public SpriteAnimation (ImageView imageView) {
				this.imageView = imageView;
				setCycleDuration(Duration.seconds(1.5));
				setInterpolator(Interpolator.LINEAR);
			}

			protected void interpolate (double k) {
				final int count = 12,
						columns = 12,
						width = 60, height = 60,
						index = Math.min((int) Math.floor(k * count), count - 1);

				if (index != lastIndex) {
					final int x = (index % columns) * width,
							y = (index / columns) * height;
					imageView.setViewport(new Rectangle2D(x, y, width, height));
					lastIndex = index;
				}
			}
		}

		LinkedList<SpriteAnimation> spriteAnimations = new LinkedList<>();
		for (int[] coord : Ship.getAllCoords(new LinkedList<>(Collections.singletonList(shipToBeBombed)))) {
			int x = coord[0] - 1, y = coord[1] - 1, index = getIndexFromXY(x, y);

			System.out.printf("trying to animate bomb at (x,y)=(%d,%d)%n", x + 1, y + 1);
			//Image view that will display our sprite
			ImageView imageView = new ImageView() {{
				setImage(image);
				setFitWidth(60);
				setFitHeight(60);
				setSmooth(true);
				clickableOpponentBoardTilePane.getChildren().add(index, this);
			}};

			Animation animation = new SpriteAnimation(imageView);
			animation.setCycleCount(1);
			animation.play();
			animation.setOnFinished(e -> {
				if (spriteAnimations.stream().noneMatch(spriteAnimation -> spriteAnimation.getStatus() == Animation.Status.RUNNING))
					clickableOpponentBoardTilePane.getChildren().removeIf(node -> node instanceof ImageView);
			});
		}
	}

	private int getIndexFromXY (int x, int y) {
		return 10 * y + x;
	}

	private void updateAllPage () {
		updateTurnIndicators();
		updateYourBoard();
		updateOpponentBoard();
		updateTimers();
	}

	public int getXFrom1 (int index) {
		int x = (index + 1) % 10;
		x = (x == 0 ? 10 : x);
		return x;
	}

	public int getYFrom1 (int index) {
		int y = (index + 1) / 10 + 1;
		if (getXFrom1(index) == 10) y--;
		return y;
	}

	private Label getBombLabel (Bomb bomb, boolean isForBigBoard) {
		return new Label() {{
			URL imgUrl = MainController.getImageUrlFromFile(bomb.wasSuccessful() ? "src/com/Resources/Images/successfulBomb.png" : "src/com/Resources/Images/redX.png");
			int imgSize = (isForBigBoard ? 60 : 30);

			setStyle("-fx-background-image: url('" + imgUrl + "');" +
					"  -fx-background-size: " + imgSize + ";" +
					"  -fx-background-position: center;" +
					"  -fx-background-repeat: no-repeat;");

			setMinSize(imgSize, imgSize);
			setMaxSize(imgSize, imgSize);

			GridPane.setColumnIndex(this, bomb.getX() - 1);
			GridPane.setRowIndex(this, bomb.getY() - 1);
			GridPane.setRowSpan(this, 1);
			GridPane.setColumnSpan(this, 1);
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
