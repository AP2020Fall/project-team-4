package plato.Model.GameRelated;

import plato.Model.AccountRelated.Gamer;

public abstract class Player {
	private final Game game;
	private final Gamer gamer;
	private int score = 0;

	protected Player (Game game, Gamer gamer) {
		this.game = game;
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

	public Game getGame () {
		return game;
	}
}