package plato.Model.GameRelated.Reversi;

import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Player;

public class PlayerReversi extends Player {
	private final String color;

	public PlayerReversi (Game game, Gamer gamer, String color) {
		super(game, gamer);
		this.color = color;
	}

	public String getColor () {
		return color;
	}
}
