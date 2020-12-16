package Model.GameRelated.BattleSea;

import Controller.GameRelated.GameController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Player;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class PlayerBattleSea extends Player {

	private LinkedList<Ship> ships;
	private final LinkedList<Bomb> bombsThrown = new LinkedList<>();

	public PlayerBattleSea (//Game game,fixme
							Gamer gamer) {
		super(//game, fixme
				gamer);
	}

	public void throwBomb (int x, int y) {
		bombsThrown.addLast(Bomb.throwBomb(x, y, this));
	}

	public void finalizeBoard (LinkedList<Ship> ships) {
		this.ships = ships;

//		for (Ship ship : this.ships)
//			ship.setGame(getGame());fixme
	}

	public boolean hasBeenBombedBefore (int x, int y) {
		return this.getBombsThrown().stream()
				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y);
//		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown().stream()
//				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y); fixme
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
		return ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(this)).getBombsThrown();
//		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown(); fixme
	}

	public LinkedList<Bomb> getOpponentBombsThrown (boolean successBombs) {
		return ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(this)).getBombsThrown(successBombs);
//		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown(successBombs); fixme
	}

	public LinkedList<Ship> getShips (boolean destroyed) {
		return getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LinkedList<Ship> getOpponentShips () {
		return new LinkedList<>(((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(this)).ships);
//		return new LinkedList<>(((PlayerBattleSea) getGame().getOpponentOf(this)).ships);fixme
	}

	public LinkedList<Ship> getOpponentShips (boolean destroyed) {
		return ((PlayerBattleSea) GameController.getInstance().getCurrentGameInSession().getOpponentOf(this)).getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toCollection(LinkedList::new));
//		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getShips().stream()
//				.filter(ship -> ship.isDestroyed(this) == destroyed)
//				.collect(Collectors.toCollection(LinkedList::new));fixme
	}

	public LinkedList<Ship> getShips () {
		return ships;
	}
}
