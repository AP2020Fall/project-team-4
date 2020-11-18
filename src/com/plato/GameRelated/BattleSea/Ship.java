package plato.GameRelated.BattleSea;

import java.util.LinkedList;

class Ship {
	private int leftMostX, topMostY;
	private boolean isVertical;

	private final int S_SIZE, L_SIZE;

	Ship (int sSize, int lSize, boolean isVertical) {
		this.S_SIZE = sSize;
		this.L_SIZE = lSize;
		this.isVertical = isVertical;
	}

	public boolean isDestroyed (PlayerBattleSea shipOwner) {
		return getAllCoords().size()
				==
				shipOwner.getOpponent().getBombsThrown().stream()
						.filter(bomb -> bomb.wasSuccessFul(shipOwner.getOpponent()))
						.count();
	}

	public LinkedList<int[]> getAllCoords () {
		// TODO: 11/17/2020 AD
		return null;
	}

	private void changeDir () {
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

	private void move (int x, int y) {
		if (canMove(x, y)) {
			this.leftMostX = x;
			this.topMostY = y;
		}
	}

	private boolean isShipPosValid (Ship[] board, int newX, int newY, boolean newIsVertical) {
		// TODO: 11/17/2020 AD
		return false;
	}

	public	PlayerBattleSea getPlayer () {
		return BattleSea.getPlayers().stream()
				.filter(player -> player.getShips().contains(this))
				.findAny().get();
	}

	public String getShipType () {
		// TODO: 11/17/2020 AD
		// get via size
		return null;
	}

	public int getLeftMostX () {
		return leftMostX;
	}

	public int getTopMostY () {
		return topMostY;
	}

	private int getL_SIZE () {
		return L_SIZE;
	}

	private int getS_SIZE () {
		return S_SIZE;
	}

	public boolean isVertical () {
		return isVertical;
	}
}