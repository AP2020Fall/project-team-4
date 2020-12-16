package Model.GameRelated;

import Model.AccountRelated.Gamer;

public abstract class Player {
	private final Gamer gamer;
	private int score = 0;

	protected Player (Gamer gamer) {
		this.gamer = gamer;
	}

	public void addToScore(int score){
		this.score +=score;
	}

	public Gamer getGamer () {
		return gamer;
	}

	public String getUsername () {
		return getGamer().getUsername();
	}

	public int getScore () {
		return score;
	}
}