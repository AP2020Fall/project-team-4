package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.Player;

public class PlayerReversi extends Player {
	private final String color;


	public PlayerReversi (Gamer gamer, String color) {
		super(gamer);
		this.color = color;
	}

	public String getColor () {
		return color;
	}


}
