package Model.GameRelated;

import Model.AccountRelated.Gamer;

public abstract class Player {
	private final Gamer gamer;

	protected Player (Gamer gamer) {
		this.gamer = gamer;
	}

	public Gamer getGamer () {
		return gamer;
	}

	public String getUsername () {
		return getGamer().getUsername();
	}
}