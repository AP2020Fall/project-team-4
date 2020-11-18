package plato.GameRelated;

import plato.AccountRelated.Gamer;
import plato.GameRelated.BattleSea.BattleSea;
import plato.GameRelated.BattleSea.PlayerBattleSea;
import plato.GameRelated.Reversi.PlayerReversi;
import plato.GameRelated.Reversi.Reversi;
import plato.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
public abstract class Game {

	private final String gameID;

	private final ArrayList<Player> listOfPlayers = new ArrayList<>();
	private  int turn = 0;
	private  GameConclusion conclusion = GameConclusion.IN_SESSION;
	private LocalDateTime dateGameEnded;

	private static ArrayList<Game> gamesInSession = new ArrayList<>();

	public Game (ArrayList<Gamer> players) {
		this.gameID = IDGenerator.generateNext();
		if (this instanceof BattleSea)
			players.forEach(player -> listOfPlayers.add(new PlayerBattleSea(player)));
		else if (this instanceof Reversi)
			players.forEach(player -> listOfPlayers.add(new PlayerReversi(player)));// FIXME: 11/18/2020
		gamesInSession.add(this);
	}

	public void nextTurn(){
		// TODO: 11/17/2020 AD  
	}

	public Gamer getTurn(){
		// TODO: 11/17/2020 AD  
	return null;}

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



	public Gamer getWinner () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public boolean playerWUsernameWon (String username) {
		if (conclusion == GameConclusion.IN_SESSION || conclusion == GameConclusion.DRAW) return false;

		return getWinner().getUsername().equals(username);
	}
	
	public void addToScore(Player player ,Integer score){
		//fixme players_pts.get(gamer) = players_pts.get(gamer) + score;
	}

	public boolean checkCoordinates(int number){
		if(number>0 && number<9){ return true;}
	return false;}

	public GameConclusion getConclusion () {
		return conclusion;
	}

	public void setConclusion(GameConclusion con){
		conclusion = con;
	}
}

enum GameConclusion {
	IN_SESSION,
	DRAW,
	PLAYER1_WIN,
	PLAYER2_WIN
}