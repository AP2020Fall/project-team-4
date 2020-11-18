package plato.GameRelated.BattleSea;

import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;
import plato.GameRelated.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

class PlayerBattleSea extends Player {

	private Ship[] ships = new Ship[6];
	private LinkedList<Bomb> bombsThrown = new LinkedList<>();

	public PlayerBattleSea(Gamer gamer) {
		super(gamer);
	}

	public void throwBomb (int x, int y) {
		bombsThrown.add(Bomb.throwBomb(x, y, this));
	}

	public boolean hasBeenBombedBefore (int x, int y) {
		return getOpponent().getBombsThrown().stream()
				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y);
	}

	public Bomb getBombObj (int x, int y) {
		return getOpponent().getBombsThrown().stream()
				.filter(bomb -> bomb.getX() == x && bomb.getY() == y)
				.findAny().get();
	}

	@Override
	public PlayerBattleSea getOpponent () {
		return (PlayerBattleSea) super.getOpponent();
	}

	public LinkedList<Bomb> getBombsThrown () {
		return bombsThrown;
	}

	public LinkedList<Bomb> getBombsThrown (boolean successBombs) {
		return (LinkedList<Bomb>) getBombsThrown().stream()
				.filter(bomb -> bomb.getWasSuccessful() == successBombs)
				.collect(Collectors.toList());
	}

	public LinkedList<Bomb> getOpponentBombsThrown () {
		return getOpponent().getBombsThrown();
	}

	public LinkedList<Bomb> getOpponentBombsThrown (boolean successBombs) {
		return getOpponent().getBombsThrown(successBombs);
	}

	public LinkedList<Ship> getShips () {
		return new LinkedList<>(Arrays.asList(ships));
	}

	public LinkedList<Ship> getShips (boolean destroyed) {
		return (LinkedList<Ship>) getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toList());
	}

	public LinkedList<Ship> getOpponentShips () {
		return new LinkedList<>(Arrays.asList(getOpponent().ships));
	}

	public LinkedList<Ship> getOpponentShips (boolean destroyed) {
		return (LinkedList<Ship>) getOpponent().getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toList());
	}

	public String[][] getPlayerBoard () {
		// TODO: 11/17/2020 AD
		return new String[0][];
	}

	public String[][] getOpponentBoard () {
		return getOpponent().getPlayerBoard();
	}
}