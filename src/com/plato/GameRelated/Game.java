package plato.GameRelated;

import plato.AccountRelated.Gamer;
import plato.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game {

	//attributes
	private final String gameID;

	private HashMap<Gamer, Integer> players_pts = new HashMap<>();
	private int turn = 0;
	private GameConclusion conclusion = GameConclusion.IN_SESSION;
	private LocalDateTime dateGameEnded;

	//constructor

	public Game (ArrayList<Gamer> players) {
		this.gameID = IDGenerator.generateNext();
		players.forEach(player -> this.players_pts.put(player, 0));
	}

	//other methods

	public abstract void setScores ();

	public abstract void executeGame ();

	public abstract void concludeGame ();

	public abstract boolean gameEnded ();

	public abstract String[][] getBoard ();

	public boolean doesIDExist (int gameID) {return false;}

	//getters and setters

	public abstract String getGameName ();

	public String getGameID () {
		return gameID;
	}

	public HashMap<Gamer, Integer> getPlayers_pts () {
		return players_pts;
	}

	public boolean gameNameIsValid (String gameName) {
		// TODO: 11/13/2020 AD
		return false;
	}

	public GameConclusion getConclusion () {
		return conclusion;
	}

	public Gamer getWinner () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public boolean playerWUsernameWon (String username) {
		if (conclusion == GameConclusion.IN_SESSION || conclusion == GameConclusion.DRAW) return false;

		return getWinner().getUsername().equals(username);
	}
}

enum GameConclusion {
	IN_SESSION,
	DRAW,
	PLAYER1_WIN,
	PLAYER2_WIN
}