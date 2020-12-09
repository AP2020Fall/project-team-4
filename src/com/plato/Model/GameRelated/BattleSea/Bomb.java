package Model.GameRelated.BattleSea;

import Model.GameRelated.Game;

public class Bomb {
	private final Game game;
	private final int x, y;
	private Boolean wasSuccessful;

	Bomb (Game game, int x, int y, Boolean wasSuccessful) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.wasSuccessful = wasSuccessful;
	}

	Bomb (Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
	}

	public static Bomb throwBomb (Game game, int x, int y, PlayerBattleSea thrower) {
		Bomb bomb = new Bomb(game, x, y);
		bomb.setWasSuccessful(bomb.wasSuccessFul(thrower));
		return bomb;
	}

	private boolean wasSuccessFul (PlayerBattleSea thrower) {
		PlayerBattleSea thrownAt = (PlayerBattleSea) game.getOpponentOf(thrower);

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
