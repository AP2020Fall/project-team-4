package Model.GameRelated;

import Controller.IDGenerator;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public abstract class Game {

	private final String gameID;
	protected String details;

	private final ArrayList<Player> listOfPlayers = new ArrayList<>();
	private int turn = 0;
	private GameConclusion conclusion = GameConclusion.IN_SESSION;
	private LocalDateTime dateGameEnded;

	private static LinkedList<Game> allGames = new LinkedList<>();

	protected Game (ArrayList<Gamer> players) {
		this.gameID = IDGenerator.generateNext();

		Collections.shuffle(players);
		if (this instanceof BattleSea) {
			listOfPlayers.add(new PlayerBattleSea(this, players.get(0)));
			listOfPlayers.add(new PlayerBattleSea(this, players.get(1)));
		}
		else if (this instanceof Reversi) {
			listOfPlayers.add(new PlayerReversi(this, players.get(0), "b"));
			listOfPlayers.add(new PlayerReversi(this, players.get(1), "w"));
		}

		allGames.add(this);
	}

	public static void startGame (Game game) {
		allGames.addLast(game);
	}

	private boolean gameHasEnded () {
		return dateGameEnded != null;
	}

	/**
	 * if turn = 0 it is player one turn
	 * if turn = 1 it is player two turn
	 */
	public void nextTurn () {
		if (turn == 0) {turn = 1;}
		else if (turn == 1) {turn = 0;}
	}

	public Gamer getTurnGamer () {
		return listOfPlayers.get(turn).getGamer();
	}

	public Player getTurnPlayer () {
		return listOfPlayers.get(turn);
	}

	public int getTurnNum () {
		return turn;
	}

	public Player getOpponentOf (Player player) {
		// TODO: 11/28/2020 AD
		return null;
	}

	public Player getPlayer (Gamer gamer) {
		return listOfPlayers.stream().filter(player -> player.getGamer().equals(gamer)).findAny().get();
	}

	public void concludeGame () {
		// set end time
		// set Conclusion
	}

	public abstract boolean gameEnded ();

	public Gamer getWinner () {
		// TODO: 11/13/2020 AD
		return null;
	}

	public boolean playerWUsernameWon (String username) {
		if (conclusion == GameConclusion.IN_SESSION || conclusion == GameConclusion.DRAW) return false;

		return getWinner().getUsername().equals(username);
	}

	public String getGameName () {
		return getClass().getSimpleName();
	}

	public GameConclusion getConclusion () {
		return conclusion;
	}

	public void setConclusion (GameConclusion conclusion) {
		this.conclusion = conclusion;
	}

	public int[] getScores () {
		return new int[]{listOfPlayers.get(0).getScore(), listOfPlayers.get(1).getScore()};
	}

	public ArrayList<Player> getListOfPlayers () {
		return listOfPlayers;
	}

	public static LinkedList<Game> getAllGames () {
		return allGames;
	}

	public static void setAllGames (LinkedList<Game> allGames) {
		Game.allGames.addAll(allGames);
	}

	public String getDetails () {
		return details;
	}

	public static LinkedList<Game> getAllFinishedGames () {
		return (LinkedList<Game>) allGames.stream().filter(Game::gameHasEnded).collect(Collectors.toList());
	}

	public LocalDateTime getDateGameEnded () {
		return dateGameEnded;
	}

	public void setDetails (String details) {
		if (this instanceof Reversi)
			Reversi.setDetailsForReversi(details);
		else
			BattleSea.setDetailsForBattlesea(details);
	}
}
