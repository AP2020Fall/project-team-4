package Controller.GameRelated;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Game;
import Model.GameRelated.Reversi.Reversi;
import View.GameRelated.GameView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

import java.util.ArrayList;
import java.util.LinkedList;

import static Model.GameRelated.Game.getScoreboard;

public class GameController {
	private static GameController gameController;

	private Game currentGameInSession = null;

	public static GameController getInstance () {
		if (gameController == null)
			gameController = new GameController();
		return gameController;
	}

	// based on which menu _11GameMenu.getGameName() shows go to _12_1GameplayBattleSeaMenu or ReversiMenu
	public void runGame () {
		Gamer player2;
		while (true)
			try {
				Menu.printAskingForInput("Second Player's username:[/c to cancel] ");
				String username2 = Menu.getInputLine();

				if (username2.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(username2))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (username2.equals(Admin.getAdmin().getUsername()))
					throw new CantPlayWithAdminException();

				if (AccountController.getInstance().getCurrentAccLoggedIn().getUsername().equals(username2))
					throw new CantPlayWithYourselfException();

				player2 = (Gamer) Account.getAccount(username2);

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | CantPlayWithAdminException | CantPlayWithYourselfException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		Gamer finalPlayer2 = player2;
		ArrayList<Gamer> players = new ArrayList<>() {{
			add(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
			add(finalPlayer2);
		}};

		Game game;
		switch (((_11GameMenu) Menu.getMenuIn()).getGameName().toLowerCase()) {
			case "battlesea" -> {
				game = new BattleSea(players);
			}
			case "reversi" -> {
				game = new Reversi(players);
				((Reversi) game).emptyBoard();
			}
			default -> throw new IllegalStateException("Unexpected value: " + ((_11GameMenu) Menu.getMenuIn()).getGameName().toLowerCase());
		}

		Game.startGame(game);
		getInstance().setCurrentGameInSession(game);

		MainController.enterAppropriateMenu();
	}

	public void addGameToFavesOfLoggedInGamer () {
		String gameName = ((_11GameMenu) Menu.getMenuIn()).getGameName();
		if (((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFaveGames().contains(gameName)) {
			Menu.printErrorMessage("This game is already in your favorites list");
			return;
		}
		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().equalsIgnoreCase("y")) {
			((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).addToFaveGames(gameName);
			GameView.getInstance().displaySuccessfulFaveGameAdditionMessage(((_11GameMenu) Menu.getMenuIn()).getGameName());
		}
	}

	public void displayGameHowToPlay () {
		GameView.getInstance().displayGameHowToPlay(
				((_11GameMenu) Menu.getMenuIn()).getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getBattleseaDetails() : Reversi.getReversiDetails()
		);
	}

	public void displayTurn () {
		GameView.getInstance().displayTurn(gameController.getCurrentGameInSession().getTurnGamer().getUsername());
	}

	public void displayGameConclusion () {
		String conclusion;
		switch (gameController.getCurrentGameInSession().getConclusion()) {
			case DRAW -> conclusion = "D";
			case PLAYER1_WIN -> conclusion = "1W";
			case PLAYER2_WIN -> conclusion = "2W";
			case IN_SESSION -> conclusion = "IS";
			default -> conclusion = "";
		}

		Gamer player1Gamer = gameController.getCurrentGameInSession().getListOfPlayers().get(0).getGamer(),
				player2Gamer = gameController.getCurrentGameInSession().getListOfPlayers().get(1).getGamer();

		GameView.getInstance().displayGameConclusion(
				conclusion,
				player1Gamer.getUsername(),
				player2Gamer.getUsername(),
				gameController.getCurrentGameInSession().getPlayer(player1Gamer).getScore(),
				gameController.getCurrentGameInSession().getPlayer(player2Gamer).getScore()
		);
	}

	public void displayScoreboardOfGame () {
		LinkedList<String> scoreBoard = getScoreboard(((_11GameMenu) Menu.getMenuIn()).getGameName());
		GameView.getInstance().displayScoreboardOfGame(((_11GameMenu) Menu.getMenuIn()).getGameName(), scoreBoard);
	}

	public Game getCurrentGameInSession () {
		return currentGameInSession;
	}

	public void setCurrentGameInSession (Game currentGameInSession) {
		getInstance().currentGameInSession = currentGameInSession;
	}

	public void editDetails (String gameName) {
		String details;
		while (true)
			try {
				Menu.printAskingForInput(gameName + "'s Details[/c to cancel] -> "); details = Menu.getInputLine();

				if (details.trim().equalsIgnoreCase("/c")) return;

				else if (details.trim().equals(""))
					throw new EmptyDetailsException();

				break;
			} catch (EmptyDetailsException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		if (gameName.trim().equalsIgnoreCase("battlesea"))
			BattleSea.setDetailsForBattlesea(details);
		else
			Reversi.setDetailsForReversi(details);
		Menu.printSuccessfulOperation("Details of " + gameName + " changed successfully.");
	}

	private static class CantPlayWithYourselfException extends Exception {
		public CantPlayWithYourselfException () {
			super("You should select another gamer's username than yourself to play with");
		}
	}

	private static class CantPlayWithAdminException extends Exception {
		public CantPlayWithAdminException () {
			super("You can't play with Admin");
		}
	}

	private static class EmptyDetailsException extends Exception {
		public EmptyDetailsException () {
			super("You can't set the game details to empty.");
		}
	}
}
