package plato.Model.GameRelated.BattleSea;

import plato.Model.GameRelated.Game;

class Bomb {
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
		if (bomb.wasSuccessFul(thrower))
			return new Bomb(game, x, y, true);
		return new Bomb(game, x, y, false);
	}

	public boolean wasSuccessFul (PlayerBattleSea thrower) {
		PlayerBattleSea thrownAt = (PlayerBattleSea) game.getOpponentOf(thrower);
		Bomb bomb = thrower.getBombsThrown().getLast();

		return Ship.getAllCoords(thrownAt.getShips()).stream()
				.anyMatch(coord -> coord[0] == bomb.getX() && coord[1] == bomb.getY());
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
}
