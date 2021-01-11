package Controller.Menus;

import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.ShipController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleSeaEditBoardPageController implements Initializable {
	private static Stage stage;
	public Button generate1RandBoardButton;
	public ImageView pfp2, pfp1;
	public ProgressIndicator timer2, timer1;
	public Label username2, username1;
	public Circle turnIndicator2, turnIndicator1;
	public GridPane board;
	public GridPane board5, board4, board3, board2, board1; // random boards
	public GridPane genRandBoardWindow;
	public ImageView ship5_2, ship5_1, ship4_1, ship3_1, ship2_1_1, ship2_1_2;
	private PlayerBattleSea player1, player2;
	private LinkedList<Ship> currentBoard;
	private LinkedList<LinkedList<Ship>> currentRandBoards;
	private IntegerProperty editingTurn = new SimpleIntegerProperty(-1);

	public static void setStage (Stage stage) {
		stage.setMinWidth(1000);
		stage.setMinHeight(stage.getMinWidth());
		stage.setMaxWidth(stage.getMinWidth());
		stage.setMaxHeight(stage.getMinWidth());
		BattleSeaEditBoardPageController.stage = stage;
		BattleSeaEditBoardPageController.stage.setOnCloseRequest(e -> BattleSeaEditBoardPageController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		player1 = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(0);
		player2 = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(1);
		// setting the pfp's
		{
			pfp1.setImage(new Image(player1.getGamer().getPfpUrl()));
			pfp1.setSmooth(true);
			pfp1.setClip(new Circle(pfp1.getBoundsInLocal().getCenterX(), pfp1.getBoundsInLocal().getCenterY(), 50));

			pfp2.setImage(new Image(player2.getGamer().getPfpUrl()));
			pfp2.setSmooth(true);
			pfp2.setClip(new Circle(pfp2.getBoundsInLocal().getCenterX(), pfp2.getBoundsInLocal().getCenterY(), 50));
		}
		// setting the usernames
		{
			username1.setText(player1.getUsername());
			username2.setText(player2.getUsername());
		}

		editingTurn.addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() == 1) {
				timer1.setVisible(true);
				timer2.setVisible(false);

				turnIndicator1.setVisible(true);
				turnIndicator2.setVisible(false);

				generate1RandBoardButton.fire();
			}
			else if (newValue.intValue() == 2) {
				timer1.setVisible(false);
				timer2.setVisible(true);

				turnIndicator1.setVisible(false);
				turnIndicator2.setVisible(true);

				((BattleSea) GameController.getInstance().getCurrentGameInSession()).getListOfBattleSeaPlayers().get(0)
						.finalizeBoard(currentBoard);

				generate1RandBoardButton.fire();
			}

			else if (oldValue.intValue() == 2) {

				((BattleSea) GameController.getInstance().getCurrentGameInSession()).getListOfBattleSeaPlayers().get(1)
						.finalizeBoard(currentBoard);

				try {
					MainController.getInstance().createAndReturnNewStage(
							FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaPlayPage.fxml").toURI().toURL()),
							"BattleSea Game",
							true,
							MainController.getInstance().getPrimaryStage()
					).show();
					stage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		editingTurn.set(1);

		Timeline timer = new Timeline(new KeyFrame(Duration.minutes(1), e -> {

		}));
		timer.setOnFinished(e -> {
			if (editingTurn.intValue() == 1) {
				editingTurn.set(2);
				timer.playFromStart();
			}
			else {
				editingTurn.set(-1);
				timer.stop();
			}
		});
		timer.setCycleCount(5);
		timer.setAutoReverse(false);

		timer.play();

		board1.setOnMouseEntered(e -> board1.setOpacity(0.8));
		board1.setOnMouseExited(e -> board1.setOpacity(1));
		board2.setOnMouseEntered(e -> board2.setOpacity(0.8));
		board2.setOnMouseExited(e -> board2.setOpacity(1));
		board3.setOnMouseEntered(e -> board3.setOpacity(0.8));
		board3.setOnMouseExited(e -> board3.setOpacity(1));
		board4.setOnMouseEntered(e -> board4.setOpacity(0.8));
		board4.setOnMouseExited(e -> board4.setOpacity(1));
		board5.setOnMouseEntered(e -> board5.setOpacity(0.8));
		board5.setOnMouseExited(e -> board5.setOpacity(1));
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public void doneEditing (ActionEvent actionEvent) {
		switch (editingTurn.intValue()) {
			case 1 -> editingTurn.set(2);
			case 2 -> editingTurn.set(-1);
		}
	}

	public void generate5RandBoards (ActionEvent actionEvent) {
		genRandBoardWindow.setVisible(true);
		currentRandBoards = BattleSea.get5RandBoards();

		setBoard(currentRandBoards.get(0), board1);
		setBoard(currentRandBoards.get(1), board2);
		setBoard(currentRandBoards.get(2), board3);
		setBoard(currentRandBoards.get(3), board4);
		setBoard(currentRandBoards.get(4), board5);
	}

	public void generate1RandBoard (ActionEvent actionEvent) {
		LinkedList<Ship> randBoard = BattleSea.getRandBoard();
		BattleSeaController.getInstance().displayRandomlyGeneratedBoard(randBoard);

		setBoard(randBoard, board);
	}

	public void setBoard (LinkedList<Ship> board, GridPane boardToShowShipsIn) {
		currentBoard = board;

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
			ImageView shipToMove = boardToShowShipsIn.getChildren().stream()
					.filter(node -> node.getId().contains(finalSize))
					.map(node -> ((ImageView) node))
					.findAny().get();

			shipToMove.setRotate(ship.isVertical() ? 0 : 90);

			GridPane.setColumnIndex(shipToMove, ship.getLeftMostX() - 1);
			GridPane.setRowIndex(shipToMove, ship.getTopMostY() - 1);

			GridPane.setColumnSpan(shipToMove, ship.isVertical() ? ship.getS_SIZE() : ship.getL_SIZE());
			GridPane.setRowSpan(shipToMove, ship.isVertical() ? ship.getL_SIZE() : ship.getS_SIZE());

			double margin = switch (boardToShowShipsIn.getId()) {
				case "board1", "board2", "board3", "board4", "board5" -> .1;
				case "board" -> 1;
				default -> throw new IllegalStateException("Unexpected value: " + boardToShowShipsIn.getId());
			};
			GridPane.setMargin(shipToMove, new Insets(margin, margin, margin, margin));
		});
	}

	public void chooseRandBoard (MouseEvent mouseEvent) {
		setBoard(
				currentRandBoards.get(Integer.parseInt(String.valueOf((((GridPane) mouseEvent.getSource()).getId().charAt(5)))) - 1),
				board
		);
		closeGen5RandBoard(new ActionEvent());
	}

	public void closeGen5RandBoard (ActionEvent actionEvent) {
		genRandBoardWindow.setVisible(false);
	}

	public void rotateShip (MouseEvent mouseEvent) {
		ImageView shipImageView = ((ImageView) mouseEvent.getSource());
		Ship ship = currentBoard.stream()
				.filter(ship1 -> ship1.getLeftMostX() == GridPane.getColumnIndex(shipImageView) + 1 && ship1.getTopMostY() == GridPane.getRowIndex(shipImageView) + 1)
				.findAny().get();

		try {
			ShipController.getInstance().rotateShip(currentBoard, ship);
			setBoard(currentBoard, board);
		} catch (ShipController.CantChangeDirException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateShipPos (MouseEvent mouseEvent) {
		ImageView shipImgView = (ImageView) mouseEvent.getSource();
		Ship ship = currentBoard.stream()
				.filter(ship1 -> ship1.getLeftMostX() - 1 == GridPane.getColumnIndex(shipImgView) && ship1.getTopMostY() - 1 == GridPane.getRowIndex(shipImgView))
				.findAny().get();

		double destXOfImageView = mouseEvent.getSceneX() - shipImgView.getLayoutBounds().getWidth() / 2;
		double destYOfImageView = mouseEvent.getSceneY() - shipImgView.getLayoutBounds().getHeight() / 2;
		int destXOfShip = ((int) ((destXOfImageView - board.getLayoutBounds().getMinX()) / 70)),
				destYOfShip = ((int) ((destYOfImageView - board.getLayoutBounds().getMinY()) / 70));

		System.out.println("destXOfImageView = " + destXOfImageView + "\t\tdestYOfImageView = " + destYOfImageView);
		System.out.println("destXOfShip = " + destXOfShip + "\t\t\tdestYOfShip = " + destYOfShip);

		int oldShipX = ship.getLeftMostX(), oldShipY = ship.getTopMostY();
//		int boardGridPaneX = grid.getLayoutBounds().getMinX() +

		ShipController.getInstance().moveShip(currentBoard, ship, destXOfShip, destYOfShip);

		setBoard(currentBoard, board);
	}
}
