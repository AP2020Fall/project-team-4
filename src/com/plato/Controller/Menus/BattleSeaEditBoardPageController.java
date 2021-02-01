package Controller.Menus;

import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.BattleSea.ShipController;
import Controller.GameRelated.GameController;
import Controller.MainController;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.BattleSea.Ship;
import View.GameRelated.BattleSea.BattleSeaView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleSeaEditBoardPageController implements Initializable {
	private static Stage stage;
	public Button generate1RandBoardButton;
	public ImageView pfp2, pfp1;
	public Label username2, username1;
	public Circle turnIndicator2, turnIndicator1;
	public GridPane board;
	public Label ship5_2, ship5_1, ship4_1, ship3_1, ship2_1_1, ship2_1_2;
	private PlayerBattleSea player1, player2;
	private LinkedList<Ship> currentBoard;
	private IntegerProperty editingTurn = new SimpleIntegerProperty(-1);
	private MouseEvent mouseEvent;
	private ActionEvent actionEvent;

	public BattleSeaEditBoardPageController(MouseEvent mouseEvent, ActionEvent actionEvent) {
		this.mouseEvent = mouseEvent;
		this.actionEvent = actionEvent;
	}

	public BattleSeaEditBoardPageController() {
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

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
				turnIndicator1.setVisible(true);
				turnIndicator2.setVisible(!turnIndicator1.isVisible());

				generate1RandBoardButton.fire();
			}
			else if (newValue.intValue() == 2) {
				turnIndicator1.setVisible(false);
				turnIndicator2.setVisible(!turnIndicator1.isVisible());

				((BattleSea) GameController.getInstance().getCurrentGameInSession()).getListOfBattleSeaPlayers().get(0)
						.finalizeBoard(currentBoard);

				generate1RandBoardButton.fire();
			}

			else if (oldValue.intValue() == 2) {
				((BattleSea) GameController.getInstance().getCurrentGameInSession()).getListOfBattleSeaPlayers().get(1)
						.finalizeBoard(currentBoard);

				try {
					Stage gameStage = MainController.getInstance().createAndReturnNewStage(
							FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaPlayPage.fxml").toURI().toURL()),
							"BattleSea Game",
							true,
							MainController.getInstance().getPrimaryStage()
					);
					BattleSeaPlayPageController.setStage(gameStage);
					gameStage.show();
					stage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		editingTurn.set(1);
	}

	public void closeStage () {
		stage.close();
	}

	public void closeStageWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("BattleSeaEditBoardPage.closeStage");

	}

	public void doneEditing () {
		switch (editingTurn.intValue()) {
			case 1 -> editingTurn.set(2);
			case 2 -> editingTurn.set(-1);
		}
	}
	public void doneEditingWrite (ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("BattleSeaEditBoardPage.doneEditing");
	}

		public void generate5RandBoards () {
		LinkedList<LinkedList<Ship>> randBoards = BattleSea.get5RandBoards();
		try {
			BattleSea5RandBoardsController.setCurrentRandBoards(randBoards);
			Stage generate5RandBoardsStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSea5RandBoards.fxml").toURI().toURL()),
					"5 Random Boards",
					true,
					stage
			);
			BattleSea5RandBoardsController.setStage(generate5RandBoardsStage);
			BattleSea5RandBoardsController.selectedBoardProperty().addListener(observable ->
					setBoard(randBoards.get(BattleSea5RandBoardsController.selectedBoardProperty().get() - 1), board)
			);
			generate5RandBoardsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate5RandBoardsWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("BattleSeaEditBoardPage.generate5RandBoards");

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

	public void generate1RandBoard () {
		LinkedList<Ship> randBoard = BattleSea.getRandBoard();

		setBoard(randBoard, board);
	}
	public void generate1RandBoardWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("BattleSeaEditBoardPage.generate1RandBoardWrite");

	}
	public void rotateShip () {
		Label shipImageView = ((Label) getMouseEvent().getSource());
		Ship ship = currentBoard.stream()
				.filter(ship1 -> ship1.getLeftMostX() == GridPane.getColumnIndex(shipImageView) + 1 && ship1.getTopMostY() == GridPane.getRowIndex(shipImageView) + 1)
				.findAny().get();

		try {
			ShipController.getInstance().rotateShip(currentBoard, ship);

			setBoard(currentBoard, board);

			BattleSeaView.getInstance().displayBoard(BattleSeaController.getInstance().getBoardAsStringBuilder(currentBoard));
		} catch (ShipController.CantChangeDirException e) {
			System.out.println(e.getMessage());
		}
	}
	public void rotateShipWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("BattleSeaEditBoardPage.rotateShip");
	}

	public void mouseIsOver () {
		if (getMouseEvent().getSource() instanceof Label)
			((Label) getMouseEvent().getSource()).setOpacity(0.8);
		else if (getMouseEvent().getSource() instanceof Button)
			((Button) getMouseEvent().getSource()).setOpacity(0.8);
	}

		public void mouseIsOverWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
			MainController.write("BattleSeaEditBoardPage.mouseEvent");
		}

			public void mouseIsOut () {
		if (getMouseEvent().getSource() instanceof Label)
			((Label) getMouseEvent().getSource()).setOpacity(1);
		else if (getMouseEvent().getSource() instanceof Button)
			((Button) getMouseEvent().getSource()).setOpacity(1);
	}
	public void mouseIsOutWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("BattleSeaEditBoardPage.mouseIsOut");
	}
	public void moveShipIfPossible () {
		Label shipToMove = (Label) getMouseEvent().getSource();
		Ship ship = currentBoard.stream()
				.filter(ship1 -> ship1.getLeftMostX() - 1 == GridPane.getColumnIndex(shipToMove) && ship1.getTopMostY() - 1 == GridPane.getRowIndex(shipToMove))
				.findAny().get();

		int newX = (int) ((getMouseEvent().getSceneX() - board.getBoundsInParent().getMinX()) / (board.getBoundsInParent().getWidth() / board.getColumnCount())),
				newY = (int) ((getMouseEvent().getSceneY() - board.getBoundsInParent().getMinY()) / (board.getBoundsInParent().getHeight() / board.getRowCount()));

		try {
			ShipController.getInstance().moveShip(currentBoard, ship, newX + 1, newY + 1);

			GridPane.setColumnIndex(shipToMove, newX);
			GridPane.setRowIndex(shipToMove, newY);

			BattleSeaView.getInstance().displayBoard(BattleSeaController.getInstance().getBoardAsStringBuilder(currentBoard));
		} catch (ShipController.InvalidCoordinateException e) {
//			System.out.printf("Cannot move ship to (x,y)=(%d,%d)%n", newX + 1, newY + 1);
//			BattleSeaView.getInstance().displayBoard(currentBoard);
		}
	}

	public void moveShipIfPossibleWrite (MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("BattleSeaEditBoardPage.moveShipIfPossible");

	}
}
