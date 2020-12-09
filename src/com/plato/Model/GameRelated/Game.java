package Model.GameRelated;

import Controller.GameRelated.GameController;
import Controller.IDGenerator;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.BattleSea.PlayerBattleSea;
import Model.GameRelated.Reversi.PlayerReversi;
import Model.GameRelated.Reversi.Reversi;
import View.Menus.Menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
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

	public static LinkedList<String> getScoreboard (String gameName) {
		LinkedList<String> scoreBoard = new LinkedList<>();
		LinkedList<Gamer> allPlayersOfGameName = GameLog.getAllGamersWhoPlayedGame(gameName);
		allPlayersOfGameName.sort((gamer1, gamer2) -> {
			int cmp;

			cmp = -GameLog.getPoints(gamer1, gameName).compareTo(GameLog.getPoints(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = -GameLog.getWinCount(gamer1, gameName).compareTo(GameLog.getWinCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = GameLog.getLossCount(gamer1, gameName).compareTo(GameLog.getLossCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = GameLog.getPlayedCount(gamer1, gameName).compareTo(GameLog.getPlayedCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			cmp = -GameLog.getDrawCount(gamer1, gameName).compareTo(GameLog.getDrawCount(gamer2, gameName));
			if (cmp != 0) return cmp;

			return gamer1.getUsername().compareToIgnoreCase(gamer2.getUsername());
		});

		if (allPlayersOfGameName.size() == 0)
			scoreBoard.addLast("No one has played %s until now.".formatted(gameName));
		else {
			AtomicInteger rank = new AtomicInteger(1),
					prevRankPts = new AtomicInteger(),
					prevRankWins = new AtomicInteger(),
					prevRankLosses = new AtomicInteger(),
					prevRankDraws = new AtomicInteger(),
					prevRankPlayCount = new AtomicInteger();

			allPlayersOfGameName.forEach(gamer -> {
				String username = gamer.getUsername();
				int pts = (GameLog.getPoints(gamer, gameName)),
						wins = (GameLog.getWinCount(gamer, gameName)),
						losses = (GameLog.getLossCount(gamer, gameName)),
						draws = (GameLog.getDrawCount(gamer, gameName)),
						playCount = (GameLog.getPlayedCount(gamer, gameName));

				// if everything is the same for two players dont go to rank
				if (rank.get() != 1 &&
						(prevRankPts.get() != pts ||
						prevRankWins.get() != wins || prevRankLosses.get() != losses || prevRankDraws.get() != draws ||
						prevRankPlayCount.get() != playCount))
					rank.incrementAndGet();

				scoreBoard.addLast("Rank: %d,\tUsername: %s,\tPoints: %d,\tWins: %d,\tLosses: %d,\tDraws: %d,\tPlayed Count: %d".formatted(
						rank.get(), username, pts, wins, losses, draws, playCount
				));
				prevRankPts.set(pts); prevRankWins.set(wins); prevRankLosses.set(losses); prevRankDraws.set(draws); prevRankPlayCount.set(playCount);
			});
		}
		return scoreBoard;
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
		if (player.equals(listOfPlayers.get(0))) return listOfPlayers.get(1);
		else if (player.equals(listOfPlayers.get(1))) return listOfPlayers.get(0);
		else return null;
	}

	public Player getPlayer (Gamer gamer) {
		return listOfPlayers.stream().filter(player -> player.getGamer().equals(gamer)).findAny().get();
	}

	public void concludeGame () {
		// set Conclusion
		if (gameHasEnded()) {
			if (getWinner().equals(null))
				setConclusion(GameConclusion.DRAW);

			else if (getWinner().equals(listOfPlayers.get(0).getGamer()))
				setConclusion(GameConclusion.PLAYER1_WIN);

			else if (getWinner().equals(listOfPlayers.get(1).getGamer()))
				setConclusion(GameConclusion.PLAYER2_WIN);
		}
		else setConclusion(GameConclusion.IN_SESSION);
		// set end time
		dateGameEnded = LocalDateTime.now();
		// going back to game menu
		Menu.getMenu("11" + GameController.getInstance().getCurrentGameInSession().getGameName().indexOf(0));
		// getting rid of current game in session
		GameController.getInstance().setCurrentGameInSession(null);
	}

	public abstract boolean gameEnded ();

	public Gamer getWinner () { // fixme use getNumberOfBlack() and getNumberOfWhite() instead of scores
		if (listOfPlayers.get(0).getScore() > listOfPlayers.get(1).getScore()) return listOfPlayers.get(0).getGamer();
		else if (listOfPlayers.get(1).getScore() > listOfPlayers.get(0).getScore()) return listOfPlayers.get(1).getGamer();
		else return null;
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
		return allGames.stream().filter(Game::gameHasEnded).collect(Collectors.toCollection(LinkedList::new));
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
