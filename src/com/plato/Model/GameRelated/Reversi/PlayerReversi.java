package Model.GameRelated.Reversi;

import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.Player;

public class PlayerReversi extends Player {
	private final String color;
	private int reversiPoints = 0;

	public PlayerReversi (Game game, Gamer gamer, String color) {
		super(game, gamer);
		this.color = color;
	}

	public String getColor () {
		return color;
	}

	/*public int getReversiPoints() {
		return reversiPoints;
	}

	public void addReversiPoints(int add){reversiPoints+=add;}*/


}
