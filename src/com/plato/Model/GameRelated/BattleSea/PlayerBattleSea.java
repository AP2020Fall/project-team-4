package Model.GameRelated.BattleSea;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.Player;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class PlayerBattleSea extends Player {

	private LinkedList<Ship> ships;
	private final LinkedList<Bomb> bombsThrown = new LinkedList<>();

	public PlayerBattleSea (Game game, Gamer gamer) {
		super(game, gamer);
	}

	public void throwBomb (int x, int y) {
		bombsThrown.addLast(Bomb.throwBomb(getGame(), x, y, this));
	}

	public void finalizeBoard (LinkedList<Ship> ships) {
		this.ships = ships;

		for (Ship ship : this.ships)
			ship.setGame(getGame());
	}

	public boolean hasBeenBombedBefore (int x, int y) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown().stream()
				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y);
	}

	public Bomb getBombObj (int x, int y) {
		return this.getBombsThrown().stream()
				.filter(bomb -> bomb.getX() == x && bomb.getY() == y)
				.findAny().get();
	}

	public LinkedList<Bomb> getBombsThrown () {
		return bombsThrown;
	}

	public LinkedList<Bomb> getBombsThrown (boolean successBombs) {
		return getBombsThrown().stream()
				.filter(bomb -> bomb.getWasSuccessful() == successBombs)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Bomb> getOpponentBombsThrown () {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown();
	}

	public LinkedList<Bomb> getOpponentBombsThrown (boolean successBombs) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown(successBombs);
	}

	public LinkedList<Ship> getShips (boolean destroyed) {
		return getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Ship> getOpponentShips () {
		return new LinkedList<>(((PlayerBattleSea) getGame().getOpponentOf(this)).ships);
	}

	public LinkedList<Ship> getOpponentShips (boolean destroyed) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Ship> getShips () {
		return ships;
	}
}
