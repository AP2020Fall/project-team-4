package Controller.GameRelated;

import Controller.AccountRelated.AccountController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.GameLog;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameLogView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GameLogController {
	private static GameLogController gameLogController;

	public static GameLogController getInstance () {
		if (gameLogController == null)
			gameLogController = new GameLogController();
		return gameLogController;
	}

	public void displayPtsLoggedInPlayerEarnedFromGame () {
		int points = GameLog.getPoints(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()), ((_11GameMenu) Menu.getMenuIn()).getGameName());
		GameLogView.getInstance().displayPtsPlayerEarnedFromGame(points, ((_11GameMenu) Menu.getMenuIn()).getGameName());
	}

	public void displayPlayedCountOfGameByLoggedInPlayer () {
		int count = GameLog.getPlayedCount(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()), ((_11GameMenu) Menu.getMenuIn()).getGameName());
		GameLogView.getInstance().displayCountForPlayerPlayingGame(count, ((_11GameMenu) Menu.getMenuIn()).getGameName());
	}

	public void displayWinCountOfGameByLoggedInPlayer () {
		int count = GameLog.getWinCount(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()), ((_11GameMenu) Menu.getMenuIn()).getGameName());
		GameLogView.getInstance().displayCountForPlayerWinningGame(count, ((_11GameMenu) Menu.getMenuIn()).getGameName());
	}

	public void displayLogOfGame () {
		int allPlayedCount = GameLog.getPlayedCount(((_11GameMenu) Menu.getMenuIn()).getGameName());
		LinkedList<String> gameHistory = getGameHistoryAsStrings(GameLog.getGameHistory(((_11GameMenu) Menu.getMenuIn()).getGameName()));

		GameLogView.getInstance().displayLogOfGame(((_11GameMenu) Menu.getMenuIn()).getGameName(), allPlayedCount, gameHistory);
	}

	public void displayAllPointsOfPlayer () {
		int points =
				GameLog.getPoints(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()), BattleSea.class.getSimpleName())            // all points from battlesea
						+
						GameLog.getPoints(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()), Reversi.class.getSimpleName());    // all points from reversi

		GameLogView.getInstance().displayAllPointsOfPlayer(points);
	}

	public void displayLastGamePlayed () {
		String gameName = GameLog.getLastGamePlayed((Gamer) AccountController.getInstance().getCurrentAccLoggedIn());
		GameLogView.getInstance().displayLastGamePlayed(gameName);
	}

	public void displayFullGamingHistoryOfGamer () {
		LinkedList<Game> history = GameLog.getGameHistory(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
		LinkedList<String> historyStrs = getGameHistoryAsStrings(history);

		GameLogView.getInstance().displayGamingHistoryOfGamer("", historyStrs);
	}

	public void displayGamingHistoryOfGamerInGame (String gamename) {
		LinkedList<Game> history = GameLog.getGameHistory(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn())).stream()
				.filter(game -> game.getGameName().equalsIgnoreCase(gamename))
				.collect(Collectors.toCollection(LinkedList::new));
		LinkedList<String> historyStrs = getGameHistoryAsStrings(history);

		GameLogView.getInstance().displayGamingHistoryOfGamer(gamename, historyStrs);
	}

	public LinkedList<String> getGameHistoryAsStrings (LinkedList<Game> gamesHistory) {
		LinkedList<String> gamesHistoryAsStrings = new LinkedList<>();

		gamesHistory.forEach(game ->
				gamesHistoryAsStrings.add("%s, %s %d-%d %s".formatted(
						game.getDateGameEnded().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd")),
						game.getListOfPlayers().get(0).getUsername(),
						game.getScores()[0],
						game.getScores()[1],
						game.getListOfPlayers().get(1).getUsername()
				))
		);

		return gamesHistoryAsStrings;
	}

	public void displayPlayerStatsInGame (String gamename) {
		Gamer gamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		GameLogView.getInstance().displayPlayerStatsInGame(
				gamename,
				GameLog.getLevel(gamer, gamename),
				GameLog.getPoints(gamer, gamename),
				GameLog.getWinCount(gamer, gamename),
				GameLog.getLossCount(gamer, gamename),
				GameLog.getPlayedCount(gamer, gamename)
		);
	}
}
