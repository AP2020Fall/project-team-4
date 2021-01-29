package Model.GameRelated;

import Controller.IDGenerator;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Reversi.Reversi;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class Game {

	private static final LinkedList<Game> allGames = new LinkedList<>();
	private final String gameID;
	protected String details = "";
	private int turn = 0;
	private IntegerProperty turnNumProperty = new SimpleIntegerProperty(0);
	private GameConclusion conclusion = GameConclusion.IN_SESSION;
	private LocalDateTime dateGameEnded, dateGameStarted;

	protected Game () {
		this.gameID = IDGenerator.generateNext();
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

				scoreBoard.addLast("Rank: %d,  Username: %s,  Points: %d,  Wins: %d,  Losses: %d,  Draws: %d,  Played Count: %d".formatted(
						rank.get(), username, pts, wins, losses, draws, playCount
				));
				prevRankPts.set(pts);
				prevRankWins.set(wins);
				prevRankLosses.set(losses);
				prevRankDraws.set(draws);
				prevRankPlayCount.set(playCount);
			});
		}
		return scoreBoard;
	}

	public static String getGamePictureUrl (String gameName) {
		return (gameName.toLowerCase().startsWith("b")) ? "https://i.imgur.com/IQNxj6N.png" : "https://i.imgur.com/lKOxPw8.png";
	}

	public static void startGame (Game game) {
		allGames.addLast(game);
		game.dateGameStarted = LocalDateTime.now();
	}

	public static LinkedList<Game> getAllGames () {
		return allGames;
	}

	public static LinkedList<Game> getAllFinishedGames () {
		return allGames.stream().filter(Game::gameHasEnded).collect(Collectors.toCollection(LinkedList::new));
	}

	public boolean gameHasEnded () {
		return dateGameEnded != null;
	}

	/**
	 * if turn = 0 it is player one turn
	 * if turn = 1 it is player two turn
	 */
	public void nextTurn () {
		if (turn == 0) turn = 1;
		else if (turn == 1) turn = 0;
		turnNumProperty.set(turn);
	}

	public IntegerProperty getTurnNumProperty () {
		return turnNumProperty;
	}

	public Gamer getTurnGamer () {
		return getListOfPlayers().get(turn).getGamer();
	}

	public LinkedList<Player> getListOfPlayers () {
		if (this instanceof Reversi)
			return new LinkedList<>(((Reversi) this).getListOfReversiPlayers());
		else
			return new LinkedList<>(((BattleSea) this).getListOfBattleSeaPlayers());
	}

	public Player getTurnPlayer () {
		return getListOfPlayers().get(turn);
	}

	public int getTurnNum () {
		return turn;
	}

	public Player getOpponentOf (Player player) {
		if (player.equals(getListOfPlayers().get(0))) return getListOfPlayers().get(1);
		else if (player.equals(getListOfPlayers().get(1))) return getListOfPlayers().get(0);
		else return null;
	}

	public Player getPlayer (Gamer gamer) {
		return getListOfPlayers().stream().filter(player -> player.getGamer().getUsername().equals(gamer.getUsername())).findAny().get();
	}

	public void concludeGame () {
		// set Conclusion
		if (getWinner() == null)
			conclusion = (GameConclusion.DRAW);

		else if (getWinner().equals(getListOfPlayers().get(0).getGamer()))
			conclusion = (GameConclusion.PLAYER1_WIN);

		else if (getWinner().equals(getListOfPlayers().get(1).getGamer()))
			conclusion = (GameConclusion.PLAYER2_WIN);
		// set end time
		dateGameEnded = LocalDateTime.now();
	}

	public abstract Gamer getWinner ();

	public abstract boolean gameEnded ();

	public String getGameName () {
		return getClass().getSimpleName();
	}

	public GameConclusion getConclusion () {
		return conclusion;
	}

	public void setConclusion (GameConclusion conclusion) {
		this.conclusion = conclusion;
	}

	public abstract int getInGameScore (int playerNum);

	public String getDetails () {
		return details;
	}

	public LocalDateTime getDateGameEnded () {
		return dateGameEnded;
	}

	public void setDetailsForIndividualGame (String details) {
		this.details = details;
	}
}
