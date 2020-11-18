package plato.GameRelated;

import plato.AccountRelated.Gamer;
import plato.GameRelated.BattleSea.BattleSea;
import plato.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
public abstract class Game {

	private final String gameID;

	private final HashMap<Gamer, Integer> players_pts = new HashMap<>();
	private  int turn = 0;
	private  GameConclusion conclusion = GameConclusion.IN_SESSION;
	private LocalDateTime dateGameEnded;

	private static ArrayList<Game> gamesInSession = new ArrayList<>();

	public Game (ArrayList<Gamer> players) {
		this.gameID = IDGenerator.generateNext();
		players.forEach(player -> this.players_pts.put(player, 0));
		gamesInSession.add(this);
	}

	public void nextTurn(){
		// TODO: 11/17/2020 AD  
	}

	public void showNextTurn(){
		// TODO: 11/17/2020 AD  
	}

	public abstract void setScores ();

	public abstract void concludeGame ();

	public abstract boolean gameEnded ();

	public abstract String[][] getBoard ();

	public String getGameName () {
		return getClass().getSimpleName();
	}

	public boolean doesIDExist (int gameID) {return false;}

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
	
	public void addToScore(Gamer gamer ,Integer score){
		//fixme players_pts.get(gamer) = players_pts.get(gamer) + score;
	}
}

enum GameConclusion {
	IN_SESSION,
	DRAW,
	PLAYER1_WIN,
	PLAYER2_WIN
}