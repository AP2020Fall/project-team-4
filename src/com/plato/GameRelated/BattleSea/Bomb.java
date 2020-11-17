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
		this.y = y; // fixme
	}

	public static Bomb throwBomb (int x, int y, Player thrower) {
		Bomb bomb = new Bomb(x, y);
		if (bomb.wasSuccessFul(thrower))
			return new Bomb(x, y, true);
		return new Bomb(x, y, false);
	}

	public boolean wasSuccessFul (Player thrower) {
		Player thrownAt = thrower.getOpponent();

		/* fixme get all ships of opponent player and check if any of their coordinates correlates to the coord of bomb
		thrownAt.getShips().stream()
				.map(Ship::getAllCoords) // linked list of all coords
				.anyMatch();
		 thrownAt.getShips().stream()
				.map(Ship::getAllCoords)
				.forEach(shipCoords -> {
					return shipCoords.stream()
							.anyMatch(shipCoord -> shipCoord[0] == x && shipCoord[1] == y);
				});
		 */
		return false;
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

