package Model.GameRelated.BattleSea;

import Model.GameRelated.Game;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Ship {
	private Game game;
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
				((PlayerBattleSea) game.getOpponentOf(shipOwner)).getBombsThrown().stream()
						.filter(bomb -> bomb.wasSuccessFul((PlayerBattleSea) game.getOpponentOf(shipOwner)))
						.count();
	}

	public static LinkedList<int[]> getAllCoords (LinkedList<Ship> ships) {
		LinkedList<int[]> coords = new LinkedList<>();

		for (Ship ship : ships) {
			int dx = (ship.isVertical() ? 0 : 1),
					dy = (ship.isVertical() ? 1 : 0),
					x0 = ship.getLeftMostX(),
					y0 = ship.getTopMostY(),
					x = x0, y = y0;

			for (int i = 0; i < ship.getL_SIZE(); i++) {
				coords.add(new int[]{x, y});
				x += dx; y += dy;
			}
		}

		return coords;
	}

	public void changeDir () {
		if (canChangeDir())
			isVertical = !isVertical;
	}

	public boolean canChangeDir () {
		Ship[] board = (Ship[]) getPlayer().getShips().toArray();
		return isShipPosValid(board, leftMostX, topMostY, !isVertical());
	}

	public boolean canMove (int x, int y) {
		Ship[] board = (Ship[]) getPlayer().getShips().toArray();
		return isShipPosValid(board, x, y, isVertical());
	}

	public void move (int x, int y) {
		if (canMove(x, y)) {
			this.leftMostX = x;
			this.topMostY = y;
		}
	}

	public boolean isShipPosValid (Ship[] board, int newX, int newY, boolean newIsVertical) {

		if (newX < 1 || newY < 1 || newX > 10 || newY > 10) return false;

		if ((newIsVertical ? newY:newX) + this.getL_SIZE() - 1 > 10) return false;

		if ((newIsVertical ? newX:newY) + this.getS_SIZE() - 1 > 10) return false;

		if (Arrays.stream(board).filter(ship -> ship == null).count() == board.length) return true;

		LinkedList<Ship> shipsExclThis = (LinkedList<Ship>) Arrays.stream(board)
				.filter(ship -> !ship.equals(this))
				. collect(Collectors.toList());

		LinkedList<int[]> thisCoords =
				getAllCoords(new LinkedList<>(Collections.singletonList(this)));

		return getAllCoords(shipsExclThis).stream()
				.noneMatch(coord -> thisCoords.contains(coord));
	}

	public PlayerBattleSea getPlayer () {
		return game.getListOfPlayers().stream()
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

	public void setGame (Game game) {
		this.game = game;
	}
}
