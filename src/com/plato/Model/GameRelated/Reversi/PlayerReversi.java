package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.Player;

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
