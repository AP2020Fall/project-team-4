package plato.Controller.GameRelated;

import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.GameLog;
import plato.View.GameRelated.GameLogView;
import plato.View.Menus.GameRelatedMenus.GameMenu;

import java.util.LinkedList;

public class GameLogController {
	public static void displayPtsLoggedInPlayerEarnedFromGame () {
		int points = GameLog.getPoints(((Gamer) AccountController.getCurrentAccLoggedIn()), GameMenu.getGameName());
		GameLogView.displayPtsPlayerEarnedFromGame(points, GameMenu.getGameName());
	}

	public static void displayPlayedCountOfGameByLoggedInPlayer () {
		int count = GameLog.getPlayedCount(((Gamer) AccountController.getCurrentAccLoggedIn()), GameMenu.getGameName());
		GameLogView.displayCountForPlayerPlayingGame(count, GameMenu.getGameName());
	}

	public static void displayWinCountOfGameByLoggedInPlayer () {
		int count = GameLog.getWinCount(((Gamer) AccountController.getCurrentAccLoggedIn()), GameMenu.getGameName());
		GameLogView.displayCountForPlayerWinningGame(count, GameMenu.getGameName());
	}

	public static void displayLogOfGame () {
		int allPlayedCount = GameLog.getPlayedCount(GameMenu.getGameName());
		LinkedList<String> gameHistory = GameLog.getGameHistory(GameMenu.getGameName());

		GameLogView.displayLogOfGame(GameMenu.getGameName(), allPlayedCount, gameHistory);
	}
}
