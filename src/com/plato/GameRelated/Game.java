package plato.GameRelated;

import plato.AccountRelated.Gamer;
import plato.GameRelated.BattleSea.BattleSea;
import plato.GameRelated.BattleSea.PlayerBattleSea;
import plato.GameRelated.Reversi.PlayerReversi;
import plato.GameRelated.Reversi.Reversi;
import plato.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
		// FIXME: 11/18/2020
		if (this instanceof BattleSea)
			players.forEach(player -> listOfPlayers.add(new PlayerBattleSea(player)));
		else if (this instanceof Reversi){
			Collections.shuffle(players);
			listOfPlayers.add(new PlayerReversi(players.get(0),"b"));
			listOfPlayers.add(new PlayerReversi(players.get(1),"w"));
		}
		gamesInSession.add(this);
	}

	/**
	 * if turn = 0 it is player one turn
	 * if turn = 1 it is player two turn
	 */
	public void nextTurn(){
		if(turn==0){turn=1;}
		else if(turn==1){turn=0;}
	}

	public Gamer getTurn(){
		return listOfPlayers.get(turn).getGamer;
	}

	public abstract void setScores ();

	public abstract void concludeGame ();

	public abstract boolean gameEnded ();

	public abstract String[][] getBoard ();

	public String getGameName () {
		return getClass().getSimpleName();
	}

	public static boolean gameNameIsValid (String gameName) {
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

	public boolean checkCoordinates(int number){
		if(number>0 && number<9){ return true;}
	return false;}

	public GameConclusion getConclusion () {
		return conclusion;
	}

	public void setConclusion (GameConclusion conclusion) {
		this.conclusion = conclusion;
	}

	public int[] getScores () {
		return new int[]{listOfPlayers.get(0).getScore(), listOfPlayers.get(1).getScore()};
	}
}

enum GameConclusion {
	IN_SESSION,
	DRAW,
	PLAYER1_WIN,
	PLAYER2_WIN
}