package Model.GameRelated.BattleSea;

import Controller.GameRelated.GameController;

public class Bomb {
	private final int x, y;
	private Boolean wasSuccessful;

	Bomb (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Bomb throwBomb (int x, int y, PlayerBattleSea thrower) {
		Bomb bomb = new Bomb(x, y);
		bomb.setWasSuccessful(bomb.wasSuccessFul(thrower));
		return bomb;
	}

	private boolean wasSuccessFul (PlayerBattleSea thrower) {
		PlayerBattleSea thrownAt = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(thrower);

		return Ship.getAllCoords(thrownAt.getShips()).stream()
				.anyMatch(coord -> coord[0] == this.getX() && coord[1] == this.getY());
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public Boolean getWasSuccessful () {
		return wasSuccessful;
	}

	public void setWasSuccessful (Boolean wasSuccessful) {
		this.wasSuccessful = wasSuccessful;
	}
}
