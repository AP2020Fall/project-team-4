package plato.Controller.GameRelated;

import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.GameRelated.GameView;
import plato.View.Menus.GameRelatedMenus.GameMenu;
import plato.View.Menus.Menu;

import java.util.LinkedList;

public class GameController {
	private static Game currentGameInSession = null; // todo set back to null at the end of any game

	// based on which menu GameMenu.getGameName() shows go to BattleSeaMenu or ReversiMenu
	public static void runGame () {
		// TODO: 11/30/2020 AD
		// 		todo set currentGameInSession to the new game created
	}

	// based on which game input points to change to menu corresponding to that game
	public static void goToGameMenu () {
		// TODO: 11/30/2020 AD
	}

	public static void addGameToFavesOfLoggedInGamer () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			((Gamer) AccountController.getCurrentAccLoggedIn()).addToFaveGames(GameMenu.getGameName());
			GameView.displaySuccessfulFaveGameAdditionMessage(GameMenu.getGameName());
		}
	}

	public static void displayGameHowToPlay () {
		GameView.displayGameHowToPlay(GameMenu.getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getDetails() : Reversi.getDetails());
	}

	public static void showScoreboardOfGame () {
		LinkedList<String> scoreBoard = GameMenu.getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getScoreboard(): Reversi.getScoreboard();
		GameView.displayScoreboardOfGame(GameMenu.getGameName(), scoreBoard);
	}

	public static Game getCurrentGame () {
		return currentGameInSession;
	}
}
