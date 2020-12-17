package Model.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Player;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class PlayerBattleSea extends Player {

	private LinkedList<Ship> ships;
	private LinkedList<Bomb> bombsThrown = new LinkedList<>();

	public PlayerBattleSea (Gamer gamer) {
		super(gamer);
	}

	public void throwBomb (int x, int y) {
		bombsThrown.addLast(Bomb.throwBomb(x, y, this));
	}

	public void finalizeBoard (LinkedList<Ship> ships) {
		this.ships = ships;
	}

	public boolean hasBeenBombedBefore (int x, int y) {
		return this.getBombsThrown().stream()
				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y);
	}

	public Bomb getBombObj (int x, int y) {
		return this.getBombsThrown().stream()
				.filter(bomb -> bomb.getX() == x && bomb.getY() == y)
				.findAny().get();
	}

	public LinkedList<Bomb> getBombsThrown () {
		if (bombsThrown == null)
			bombsThrown = new LinkedList<>();

		return bombsThrown;
	}

	public LinkedList<Bomb> getBombsThrown (boolean successBombs) {
		return getBombsThrown().stream()
				.filter(bomb -> bomb.wasSuccessful() == successBombs)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Bomb> getOpponentBombsThrown () {
		return ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getOpponentOf(this))
				.getBombsThrown();
	}

	public LinkedList<Bomb> getOpponentBombsThrown (boolean successBombs) {
		return ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getOpponentOf(this))
				.getBombsThrown(successBombs);
	}

	public LinkedList<Ship> getShips (boolean destroyed) {
		return getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Ship> getOpponentShips () {
		return new LinkedList<>(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession()
				.getOpponentOf(this)).ships);
	}

	public LinkedList<Ship> getOpponentShips (boolean destroyed) {
		PlayerBattleSea opponent = (PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(this);

		return opponent
				.getShips().stream()
				.filter(ship -> ship.isDestroyed(opponent) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Ship> getShips () {
		return ships;
	}
}
