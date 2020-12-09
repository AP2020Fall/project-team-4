package Model.GameRelated;

public enum GameConclusion {
	IN_SESSION(0,0),
	DRAW(1,1),
	PLAYER1_WIN(2,-2),
	PLAYER2_WIN(2,-2);

	private final Integer winnerPoints;
	private final Integer loserPoints;

	GameConclusion(Integer winnerPoints, Integer loserPoints) {
		this.winnerPoints = winnerPoints;
		this.loserPoints = loserPoints;
	}

	public Integer getWinnerPoints() {
		return winnerPoints;
	}

	public Integer getLoserPoints() {
		return loserPoints;
	}
}