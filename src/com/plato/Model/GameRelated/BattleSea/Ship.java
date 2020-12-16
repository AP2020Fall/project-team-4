package Model.GameRelated.BattleSea;

import Controller.GameRelated.BattleSea.BattleSeaController;
import Controller.GameRelated.GameController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Ship {
	private int leftMostX, topMostY;
	private boolean isVertical;

	private final int L_SIZE, S_SIZE;

	public Ship (int leftMostX, int topMostY, boolean isVertical, int l_SIZE, int s_SIZE) {
		this.leftMostX = leftMostX;
		this.topMostY = topMostY;
		this.isVertical = isVertical;
		L_SIZE = l_SIZE;
		S_SIZE = s_SIZE;
	}

	public boolean isDestroyed (PlayerBattleSea shipOwner) {
		return getAllCoords(getPlayer().getShips()).size()
				==
				((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(shipOwner)).getBombsThrown().stream()
						.filter(Bomb::wasSuccessful)
						.count();
	}

	public static LinkedList<int[]> getAllCoords (LinkedList<Ship> ships) {
		LinkedList<int[]> coords = new LinkedList<>();

		for (Ship ship : ships) {
			int x = ship.getLeftMostX(),
					y = ship.getTopMostY();

			for (int s = 0; s < ship.getS_SIZE(); s++) {
				for (int l = 0; l < ship.getL_SIZE(); l++) {
					coords.add(new int[]{x, y});
					if (ship.isVertical())
						y++;
					else
						x++;
				}
				if (ship.isVertical()) {
					x++;
					y = ship.getTopMostY();
				}
				else {
					y++;
					x = ship.getLeftMostX();
				}
			}
		}

		return coords;
	}

	public void changeDir () {
		if (canChangeDir())
			isVertical = !isVertical;
	}

	public boolean canChangeDir () {
		LinkedList<Ship> board = BattleSeaController.getInstance().getCurrentlyEditingTrialBoard();
		return isShipPosValid(board, leftMostX, topMostY, !isVertical());
	}

	public boolean canMove (int x, int y) {
		LinkedList<Ship> board = BattleSeaController.getInstance().getCurrentlyEditingTrialBoard();
		return isShipPosValid(board, x, y, isVertical());
	}

	public void move (int x, int y) {
		if (canMove(x, y)) {
			this.leftMostX = x;
			this.topMostY = y;
		}
	}

	public boolean isShipPosValid (LinkedList<Ship> board, int newX, int newY, boolean newIsVertical) {

		if (!BattleSea.checkCoordinates(newX) || !BattleSea.checkCoordinates(newY)) return false;

		if (!BattleSea.checkCoordinates((newIsVertical ? newY : newX) + this.getL_SIZE() - 1)) return false;

		if (!BattleSea.checkCoordinates((newIsVertical ? newX : newY) + this.getS_SIZE() - 1)) return false;

		LinkedList<Ship> shipsExclThis = board.stream()
				.filter(ship -> !ship.equals(this))
				.collect(Collectors.toCollection(LinkedList::new));

		LinkedList<int[]> thisCoords =
				getAllCoords(new LinkedList<>(Collections.singletonList(this)));

		AtomicBoolean valid = new AtomicBoolean(true);
		getAllCoords(shipsExclThis).forEach(coord -> {
			for (int[] thisCoord : thisCoords)
				if (thisCoord[0] == coord[0] && thisCoord[1] == coord[1])
					valid.set(false);
		});
		return valid.get();
	}

	public PlayerBattleSea getPlayer () {
		return GameController.getInstance().getCurrentGameInSession().getListOfPlayers().stream()
				.map(player -> ((PlayerBattleSea) player))
				.filter(player -> player.getShips().contains(this))
				.findAny().get();
	}

	public int getLeftMostX () {
		return leftMostX;
	}

	public int getTopMostY () {
		return topMostY;
	}

	public int getL_SIZE () {
		return L_SIZE;
	}

	public int getS_SIZE () {
		return S_SIZE;
	}

	public boolean isVertical () {
		return isVertical;
	}

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ship ship = (Ship) o;
		return leftMostX == ship.leftMostX && topMostY == ship.topMostY && isVertical == ship.isVertical && L_SIZE == ship.L_SIZE && S_SIZE == ship.S_SIZE;
	}

	@Override
	public int hashCode () {
		return Objects.hash(leftMostX, topMostY, isVertical, L_SIZE, S_SIZE);
	}
}
