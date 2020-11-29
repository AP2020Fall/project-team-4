package plato.Model.GameRelated.BattleSea;

import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class PlayerBattleSea extends Player {

	private Ship[] ships = new Ship[6];
	private LinkedList<Bomb> bombsThrown = new LinkedList<>();

	public PlayerBattleSea(Game game, Gamer gamer) {
		super(game, gamer);
	}

	public void throwBomb (int x, int y) {
		bombsThrown.add(Bomb.throwBomb(getGame(), x, y, this));
	}

	public void finalizeBoard (Ship[] ships) { // todo dont forget to call before start of game
		this.ships = ships;

		for (Ship ship : this.ships)
			ship.setGame(getGame());
	}

	public boolean hasBeenBombedBefore (int x, int y) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown().stream()
				.anyMatch(bomb -> bomb.getX() == x && bomb.getY() == y);
	}

	public Bomb getBombObj (int x, int y) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown().stream()
				.filter(bomb -> bomb.getX() == x && bomb.getY() == y)
				.findAny().get();
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
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown();
	}

	public LinkedList<Bomb> getOpponentBombsThrown (boolean successBombs) {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getBombsThrown(successBombs);
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
		return new LinkedList<>(Arrays.asList(((PlayerBattleSea) getGame().getOpponentOf(this)).ships));
	}

	public LinkedList<Ship> getOpponentShips (boolean destroyed) {
		return (LinkedList<Ship>) ((PlayerBattleSea) getGame().getOpponentOf(this)).getShips().stream()
				.filter(ship -> ship.isDestroyed(this) == destroyed)
				.collect(Collectors.toList());
	}

	public String[][] getPlayerBoard () {
		// TODO: 11/17/2020 AD
		return new String[0][];
	}

	public String[][] getOpponentBoard () {
		return ((PlayerBattleSea) getGame().getOpponentOf(this)).getPlayerBoard();
	}
}
