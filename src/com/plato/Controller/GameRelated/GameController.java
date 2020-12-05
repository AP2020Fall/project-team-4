package Controller.GameRelated;

import Controller.AccountRelated.AccountController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

import java.util.ArrayList;
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
		Gamer player2;
		while (true)
			try {
				System.out.print("Second Player's username:[/cancel to cancel filling form] "); String username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/cancel")) return;

				if (!Account.accountExists(username))
					throw new AccountController.NoAccountExistsWithUsernameException();
				player2 = (Gamer) Account.getAccount(username);

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		Gamer finalPlayer2 = player2;
		ArrayList<Gamer> players = new ArrayList<>() {{
			add(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
			add(finalPlayer2);
		}};

		Game game;
		switch (((_11GameMenu) Menu.getMenuIn()).getGameName().toLowerCase()) {
			case "battlesea" ->
					game = new BattleSea(players);
			case "reversi" ->
					game = new Reversi(players);
			default -> throw new IllegalStateException("Unexpected value: " + ((_11GameMenu) Menu.getMenuIn()).getGameName().toLowerCase());
		}

		Game.startGame(game);
		currentGameInSession = game;

		Menu.getMenuIn().getChildMenus().get(8).enter();
	}

	public void addGameToFavesOfLoggedInGamer () {
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().toLowerCase().equals("y")) {
			((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).addToFaveGames(((_11GameMenu) Menu.getMenuIn()).getGameName());
			GameView.getInstance().displaySuccessfulFaveGameAdditionMessage(((_11GameMenu) Menu.getMenuIn()).getGameName());
		}
	}

	public void displayGameHowToPlay () {
		GameView.getInstance().displayGameHowToPlay(((_11GameMenu) Menu.getMenuIn()).getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getBattleseaDetails() : Reversi.getReversiDetails());
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

	public void displayScoreboardOfGame () {
		LinkedList<String> scoreBoard = ((_11GameMenu) Menu.getMenuIn()).getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getScoreboard() : Reversi.getScoreboard();
		GameView.getInstance().displayScoreboardOfGame(((_11GameMenu) Menu.getMenuIn()).getGameName(), scoreBoard);
	}

	public Game getCurrentGame () {
		return currentGameInSession;
	}
}
