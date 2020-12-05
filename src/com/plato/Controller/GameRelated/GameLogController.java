package Controller.GameRelated;

import Controller.AccountRelated.AccountController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.GameLog;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameLogView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

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
