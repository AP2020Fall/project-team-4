package plato.GameRelated.BattleSea;

class Bomb {
	private final int x, y;
	private Boolean wasSuccessful;

	Bomb (int x, int y, Boolean wasSuccessful) {
		this.x = x;
		this.y = y;
		this.wasSuccessful = wasSuccessful;
	}

	Bomb (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Bomb throwBomb (int x, int y, PlayerBattleSea thrower) {
		Bomb bomb = new Bomb(x, y);
		if (bomb.wasSuccessFul(thrower))
			return new Bomb(x, y, true);
		return new Bomb(x, y, false);
	}

	public boolean wasSuccessFul (PlayerBattleSea thrower) {
		PlayerBattleSea thrownAt = thrower.getOpponent();
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

