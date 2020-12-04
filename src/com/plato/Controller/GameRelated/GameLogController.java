package plato.Controller.GameRelated;

import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.GameLog;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.GameRelated.GameLogView;
import plato.View.Menus.Menu;
import plato.View.Menus._11GameMenu;

import java.util.LinkedList;

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
		LinkedList<String> gameHistory = GameLog.getGameHistory(((_11GameMenu) Menu.getMenuIn()).getGameName());

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
}
