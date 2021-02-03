package Controller.Menus;

import Controller.MainController;
import Model.GameRelated.BattleSea.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleSea5RandBoardsController implements Initializable {
	private static Stage stage;
	private static LinkedList<LinkedList<Ship>> currentRandBoards;
	private static IntegerProperty selectedBoard = new SimpleIntegerProperty(-1);
	public GridPane board1, board2, board3, board4, board5;



	public static void setStage (Stage stage) {
		BattleSea5RandBoardsController.stage = stage;
		BattleSea5RandBoardsController.stage.setOnCloseRequest(e -> {
			BattleSea5RandBoardsController.stage = null;
			currentRandBoards = null;
		});
	}

	public static void setCurrentRandBoards (LinkedList<LinkedList<Ship>> currentRandBoards) {
		BattleSea5RandBoardsController.currentRandBoards = currentRandBoards;
	}

	public static IntegerProperty selectedBoardProperty () {
		return selectedBoard;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		new LinkedList<>(Arrays.asList(board1, board2, board3, board4, board5))
				.forEach(board -> {
					int index = -1;
					board.getChildren().get(++index).setId("ship%d_%d%s".formatted(5, 2, board.getId().charAt(5)));
					board.getChildren().get(++index).setId("ship%d_%d%s".formatted(5, 1, board.getId().charAt(5)));
					board.getChildren().get(++index).setId("ship%d_%d%s".formatted(4, 1, board.getId().charAt(5)));
					board.getChildren().get(++index).setId("ship%d_%d%s".formatted(3, 1, board.getId().charAt(5)));
					board.getChildren().get(++index).setId("ship%d_%d_%d%s".formatted(2, 1, 1, board.getId().charAt(5)));
					board.getChildren().get(++index).setId("ship%d_%d_%d%s".formatted(2, 1, 2, board.getId().charAt(5)));
				});

		setBoard(currentRandBoards.get(0), board1);
		setBoard(currentRandBoards.get(1), board2);
		setBoard(currentRandBoards.get(2), board3);
		setBoard(currentRandBoards.get(3), board4);
		setBoard(currentRandBoards.get(4), board5);
	}

	private void setBoard (LinkedList<Ship> board, GridPane boardToShowShipsIn) {
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

	public void selectBoard (MouseEvent mouseEvent) {
		selectedBoard.set(Integer.parseInt(String.valueOf(((GridPane) mouseEvent.getSource()).getId().charAt(5))));

		closeGen5RandBoard(new ActionEvent());
	}

	public void closeGen5RandBoard (ActionEvent actionEvent) {
		stage.close();
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((GridPane) mouseEvent.getSource()).setOpacity(1);
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((GridPane) mouseEvent.getSource()).setOpacity(0.8);
	}
}
