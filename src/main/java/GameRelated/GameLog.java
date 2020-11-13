package GameRelated;

import java.util.LinkedList;

public class GameLog {

	private String logID;
	private double point = 0;

	private LinkedList<Game> games = new LinkedList<>();

	public GameLog () {
		this.logID = IDG;
	}

	public void addToLog (Game game) {
		games.addLast(game);
	}

	public int getPlayedCount () {
		return games.size();
	}

	public int getPlayedCount (String gameName) {
		return (int) games.stream()
				.filter(game -> game.getGameName().equals(gameName))
				.count();
	}

	public int getWinsCount () {
		return games.stream()
				.filter(game ->) // FIXME: count games that the gamer w this log has won
				.count();
	}

	public int getWinsCount (String gameName) {
		return games.stream()
				.filter(game -> game.getGameName().equals(gameName) && ) // FIXME: count games that the gamer w this log has won
				.count();
	}

	public String getLogID () {
		return logID;
	}

	public double getPoint () {
		return point;
	}

	public double getPoints (String gameName) {
		// TODO: 11/13/2020 AD
		// finding the type of game based on name
		// search in the scoreboard of the game for the gamer username
		// calculte score based on the stats
		return 0;
	}

	public int getLevel (String gameName) {
		// TODO: 11/13/2020 AD
		return 0;
	}

	public void setPoint (double point) {
		this.point = point;
	}

	public LinkedList<Game> getGames () {
		return games;
	}
}
