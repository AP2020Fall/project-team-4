package plato.Controller.GameRelated;

import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Game;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.GameRelated.GameView;
import plato.View.Menus._11GameMenu;
import plato.View.Menus.Menu;

import java.util.LinkedList;

public class GameController {
	private static Game currentGameInSession = null; // todo set back to null at the end of any game

	private static GameController gameController;

	public static GameController getInstance () {
		if (gameController == null)
			gameController = new GameController();
		return gameController;
	}

	// based on which menu _11GameMenu.getGameName() shows go to _12_1GameplayBattleSeaMenu or ReversiMenu
	public void runGame () {
		// TODO: 11/30/2020 AD
		// 		todo set currentGameInSession to the new game created
	}

	// based on which game input points to change to menu corresponding to that game
	public void goToGameMenu () {
		// TODO: 11/30/2020 AD
	}

	public void addGameToFavesOfLoggedInGamer () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).addToFaveGames(_11GameMenu.getGameName());
			GameView.getInstance().displaySuccessfulFaveGameAdditionMessage(_11GameMenu.getGameName());
		}
	}

	public void displayGameHowToPlay () {
		GameView.getInstance().displayGameHowToPlay(_11GameMenu.getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getBattleseaDetails() : Reversi.getReversiDetails());
	}

	public void displayTurn () {
		GameView.getInstance().displayTurn(gameController.getCurrentGame().getTurnGamer().getUsername());
	}

	public void displayGameConclusion () {
		String conclusion;
		switch (gameController.getCurrentGame().getConclusion()) {
			case DRAW -> conclusion = "D";
			case PLAYER1_WIN -> conclusion = "1W";
			case PLAYER2_WIN -> conclusion = "2W";
			case IN_SESSION -> conclusion = "IS";
			default -> conclusion = "";
		}

		Gamer player1Gamer = gameController.getCurrentGame().getListOfPlayers().get(0).getGamer(),
				player2Gamer = gameController.getCurrentGame().getListOfPlayers().get(1).getGamer();

		GameView.getInstance().displayGameConclusion(
				conclusion,
				player1Gamer.getUsername(),
				player2Gamer.getUsername(),
				gameController.getCurrentGame().getPlayer(player1Gamer).getScore(),
				gameController.getCurrentGame().getPlayer(player2Gamer).getScore()
		);
	}

	public void displayInGameScores () {
		Gamer player1Gamer = gameController.getCurrentGame().getListOfPlayers().get(0).getGamer(),
				player2Gamer = gameController.getCurrentGame().getListOfPlayers().get(1).getGamer();

		GameView.getInstance().displayInGameScores(
				player1Gamer.getUsername(),
				player2Gamer.getUsername(),
				gameController.getCurrentGame().getPlayer(player1Gamer).getScore(),
				gameController.getCurrentGame().getPlayer(player2Gamer).getScore()
		);
	}

	public void showScoreboardOfGame () {
		LinkedList<String> scoreBoard = _11GameMenu.getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getScoreboard() : Reversi.getScoreboard();
		GameView.getInstance().displayScoreboardOfGame(_11GameMenu.getGameName(), scoreBoard);
	}

	public Game getCurrentGame () {
		return currentGameInSession;
	}
}
