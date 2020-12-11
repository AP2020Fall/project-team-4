package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.AdminGameReco;
import Model.AccountRelated.Gamer;
import View.AccountRelated.AdminGameRecoView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminGameRecoController {
	private static AdminGameRecoController adminGameRecoController;

	public static AdminGameRecoController getInstance () {
		if (adminGameRecoController == null)
			adminGameRecoController = new AdminGameRecoController();
		return adminGameRecoController;
	}

	public void giveRecommendationToGamer () {
		String gamerUN;
		while (true)
			try {
				Menu.printAskingForInput("Gamer username:[/c to cancel] "); gamerUN = Menu.getInputLine();

				if (gamerUN.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(gamerUN))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (Account.getAccount(gamerUN) instanceof Admin)
					throw new CantRecommendToYourselfException();
				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | CantRecommendToYourselfException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		String gamechoice;
		LinkedList<String> recoChoices = new LinkedList<>(Arrays.asList("BattleSea", "Reversi"));

		while (true)
			try {
				AtomicInteger count = new AtomicInteger(1);
				recoChoices.forEach(game -> Menu.println("%d. %s".formatted(count.getAndIncrement(), game)));

				Menu.printAskingForInput("Your game choice:[/c to cancel] "); gamechoice = Menu.getInputLine();

				if (gamechoice.trim().equalsIgnoreCase("/c")) return;

				if (!gamechoice.matches("[1-2]"))
					throw new MainController.InvalidInputException();

				if (AdminGameReco.recommendationExists((Gamer) Account.getAccount(gamerUN), recoChoices.get(Integer.parseInt(gamechoice))))
					throw new AdminGameRecoAlreadyExists();

				break;
			} catch (MainController.InvalidInputException | AdminGameRecoAlreadyExists e) {
				Menu.printErrorMessage(e.getMessage());
			}


		AdminGameReco.addReco(recoChoices.get(Integer.parseInt(gamechoice)), ((Gamer) Account.getAccount(gamerUN)));
	}

	public void displayAdminsRecosToPlayer () {
		LinkedList<AdminGameReco> recosForPlayer = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getAdminGameRecosGotten();

		AdminGameRecoView.getInstance().displayAdminsRecosToPlayer(new LinkedList<>() {{
			for (AdminGameReco adminGameReco : recosForPlayer)
				add(adminGameReco.getGameName());
		}});
		if (recosForPlayer.size() == 0) return;

//		while (true)
//			try {
//				Menu.printAskingForInput("Choose game:[/c to cancel] "); String command = Menu.getInputLine();
//
//				if (command.trim().equalsIgnoreCase("/c")) return;
//
//				int choice = Integer.parseInt(command);
//
//				if (choice < 1 || choice > recosForPlayer.size())
//					throw new InputMismatchException();
//
//				((_11GameMenu) Menu.getMenuIn().getChildMenus().get(MainController.getCommand()))
//						.setGameName(recosForPlayer.get(choice - 1).getGameName());
//				break;
//			} catch (InputMismatchException e) {
//				Menu.printErrorMessage("Invalid input");
//			}

		MainController.enterAppropriateMenu();
	}

	public void displayAllAdminRecos () {
		LinkedList<AdminGameReco> allRecos = AdminGameReco.getRecommendations();
		allRecos.sort(Comparator
				.comparing(AdminGameReco::getGamerUsername) // from a-z
				.thenComparing(AdminGameReco::getGameName)  // first battlesea recos then reversi
				.thenComparing(AdminGameReco::getRecoID));

		AdminGameRecoView.getInstance().displayAllAdminRecos(new LinkedList<>() {{
			for (AdminGameReco reco : allRecos)
				add("%s %s %s".formatted(reco.getRecoID(), reco.getGamerUsername(), reco.getGameName()));
		}});
	}

	public void removeReco () {
		String recoID;
		while (true)
			try {
				Menu.printAskingForInput("Suggestion ID:[/c to cancel] "); recoID = Menu.getInputLine();

				if (recoID.trim().equalsIgnoreCase("/c")) return;

				if (!AdminGameReco.recommendationExists(recoID))
					throw new AdminGameRecoDoesntExistException();

				break;
			} catch (AdminGameRecoDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		AdminGameReco.removeReco(recoID);
	}

	public void chooseRecoGame () {
		LinkedList<AdminGameReco> recos = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getAdminGameRecosGotten();

		int choice;
		while (true)
			try {
				Menu.printAskingForInput("Choose game:[/c to cancel] "); String command = Menu.getInputLine();

				if (command.trim().equalsIgnoreCase("/c")) return;

				choice = Integer.parseInt(command);

				if (choice < 1 || choice > recos.size())
					throw new InputMismatchException();

				((_11GameMenu) Menu.getMenuIn().getChildMenus().get(MainController.getCommand()))
						.setGameName(recos.get(choice - 1).getGameName());
				break;
			} catch (InputMismatchException e) {
				Menu.printErrorMessage("Invalid input");
			}

		switch (recos.get(choice - 1).getGameName().toLowerCase()) {
			case "battlesea" -> Menu.getMenu("11B").enter();

			case "reversi" -> Menu.getMenu("11R").enter();
		}
	}

	private static class AdminGameRecoAlreadyExists extends Exception {
		public AdminGameRecoAlreadyExists () {
			super("You have already recommended this game to this gamer.");
		}
	}

	private static class CantRecommendToYourselfException extends Exception {
		public CantRecommendToYourselfException () {
			super("You cannot enter your own username.");
		}
	}

	private static class AdminGameRecoDoesntExistException extends Exception {
		public AdminGameRecoDoesntExistException () {
			super("Suggestion does not exist.");
		}
	}
}
