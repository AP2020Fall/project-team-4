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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GameController {
	private static GameController gameController;

	private Game currentGameInSession = null;

	// based on which menu _11GameMenu.getGameName() shows go to GameplayBattleSeaMenu or GameplayReversiMenu
	public void runGame (String username2, String gameName) throws MainController.InvalidFormatException, AccountController.NoAccountExistsWithUsernameException, CantPlayWithAdminException, CantPlayWithYourselfException {
		if (!username2.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Second player's username");

		if (!Account.accountExists(username2))
			throw new AccountController.NoAccountExistsWithUsernameException();

		if (username2.equals(Admin.getAdmin().getUsername()))
			throw new CantPlayWithAdminException();

		if (AccountController.getInstance().getCurrentAccLoggedIn().getUsername().equals(username2))
			throw new CantPlayWithYourselfException();

		Gamer player2 = (Gamer) Account.getAccount(username2);

		Gamer finalPlayer2 = player2;
		ArrayList<Gamer> players = new ArrayList<>() {{
			add(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
			add(finalPlayer2);
		}};

		Game game = null;
		switch (gameName.toLowerCase()) {
			case "battlesea" -> game = new BattleSea(players);
			case "reversi" -> {
				game = new Reversi(players);
				((Reversi) game).emptyBoard();
			}
		}

		Game.startGame(game);
		setCurrentGameInSession(game);
	}

	public void addFaveGame (String gameName) {
		Gamer gamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		gamer.addToFaveGames(gameName);
	}

	public void displayGameHowToPlay () {
//		GameView.getClient().displayGameHowToPlay(
//				((_11GameMenu) Menu.getMenuIn()).getGameName().equals(BattleSea.class.getSimpleName()) ? BattleSea.getBattleseaDetails() : Reversi.getReversiDetails()
//		);
	}

	public void displayTurn () {
		GameView.getInstance().displayTurn(
				"Reversi",
				GameController.getInstance().getCurrentGameInSession().gameEnded(),
				gameController.getCurrentGameInSession().getTurnGamer().getUsername()
		);
	}

	public Game getCurrentGameInSession () {
		return currentGameInSession;
	}

	public void setCurrentGameInSession (Game currentGameInSession) {
		getInstance().currentGameInSession = currentGameInSession;
	}

	public static GameController getInstance () {
		if (gameController == null)
			gameController = new GameController();
		return gameController;
	}

	public void displayGameConclusion (Game game) {
		String conclusion;
		switch (game.getConclusion()) {
			case DRAW -> conclusion = "D";
			case PLAYER1_WIN -> conclusion = "1W";
			case PLAYER2_WIN -> conclusion = "2W";
			case IN_SESSION -> conclusion = "IS";
			default -> conclusion = "";
		}

		Gamer player1Gamer = game.getListOfPlayers().get(0).getGamer(),
				player2Gamer = game.getListOfPlayers().get(1).getGamer();

		GameView.getInstance().displayGameConclusion(
				conclusion,
				player1Gamer.getUsername(),
				player2Gamer.getUsername(),
				game.getInGameScore(1),
				game.getInGameScore(2)
		);
	}

	public void displayScoreboardOfGame () {
//		LinkedList<String> scoreBoard = getScoreboard(((_11GameMenu) Menu.getMenuIn()).getGameName());
//		GameView.getClient().displayScoreboardOfGame(((_11GameMenu) Menu.getMenuIn()).getGameName(), scoreBoard);
	}

	public void editDetails (String gameName) {
		String details = "";
		while (true)
			try {
//				Menu.printAskingForInput(gameName + "'s Details[/c to cancel] -> ");
//				details = Menu.getInputLine();

				if (details.trim().equalsIgnoreCase("/c")) return;

				else if (details.trim().equals(""))
					throw new EmptyDetailsException();

				break;
			} catch (EmptyDetailsException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		if (gameName.trim().equalsIgnoreCase("battlesea"))
			BattleSea.setDetailsForBattlesea(details);
		else
			Reversi.setDetailsForReversi(details);
//		Menu.printSuccessfulOperation("Details of " + gameName + " changed successfully.");
	}

	public void displayPrevGamesAndChooseToContinue () {
		LinkedList<Game> unfinishedGames = new LinkedList<>();

//		switch (((_11GameMenu) Menu.getMenuIn()).getGameName().toLowerCase()) {
//			case "reversi" -> unfinishedGames = new LinkedList<>(Reversi.getAllReversiGames());
//			case "battlesea" -> unfinishedGames = new LinkedList<>(BattleSea.getAllBattleSeaGames());
//		}

		unfinishedGames = unfinishedGames.stream()
				.filter(game ->
						!game.gameHasEnded() &&
								game.getListOfPlayers().stream()
										.anyMatch(player -> player.getUsername()
												.equals(AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
				)
				.collect(Collectors.toCollection(LinkedList::new));

		LinkedList<Game> finalUnfinishedGames = unfinishedGames;
		GameView.getInstance().displayPrevGamesAndChooseToContinue(new LinkedList<>() {{

			for (Game unfinishedGame : finalUnfinishedGames) {

				String yourUsername = AccountController.getInstance().getCurrentAccLoggedIn().getUsername(),
						opponentUsername = unfinishedGame.getOpponentOf(unfinishedGame.getPlayer((Gamer) Account.getAccount(yourUsername))).getUsername();

				add("%s %s %d %d %s".formatted(
						opponentUsername,
						unfinishedGame.getListOfPlayers().get(0).getUsername(), // player1
						unfinishedGame.getInGameScore(1),                // score1
						unfinishedGame.getInGameScore(2),                // score2
						unfinishedGame.getListOfPlayers().get(1).getUsername()    //player2
				));
			}
		}});

		if (unfinishedGames.size() == 0) return;

		int gameChoice;
		while (true)
			try {
//				Menu.printAskingForInput("Which game to continue:[/c to cancel] ");
				String choice = "";
//				choice = Menu.getInputLine();

				if (choice.trim().equalsIgnoreCase("/c")) return;

				if (!choice.matches("[0-9]+"))
					throw new MainController.InvalidInputException();

				gameChoice = Integer.parseInt(choice);

				if (gameChoice < 1 || gameChoice > unfinishedGames.size())
					throw new MainController.InvalidInputException();

				break;
			} catch (MainController.InvalidInputException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		currentGameInSession = unfinishedGames.get(gameChoice - 1);
	}

	public void removeFaveGame (String gameName) {
		Gamer gamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		gamer.removeFaveGame(gameName);
	}

	public static class CantPlayWithYourselfException extends Exception {
		public CantPlayWithYourselfException () {
			super("You should select another gamer's username than yourself to play with");
		}
	}

	public static class CantPlayWithAdminException extends Exception {
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
