package Controller.AccountRelated;

import Model.AccountRelated.AdminGameReco;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Reversi.Reversi;
import View.AccountRelated.AdminGameRecoView;
import View.Menus.Menu;
import View.Menus._11GameMenu;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class AdminGameRecoController {
	private static AdminGameRecoController adminGameRecoController;

	public static AdminGameRecoController getInstance () {
		if (adminGameRecoController == null)
			adminGameRecoController = new AdminGameRecoController();
		return adminGameRecoController;
	}

	public void giveRecommendationToGamer () {
		// TODO: TOTOTOTOOTOTOTOTOTOOTOTOT
	}

	public void displayAdminsRecosToPlayer () {
		LinkedList<AdminGameReco> recosForPlayer = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getAdminGameRecosGotten();

		AdminGameRecoView.getInstance().displayAdminsRecosToPlayer(new LinkedList<>() {{
			for (AdminGameReco adminGameReco : recosForPlayer)
				add(adminGameReco.getGameName());
		}});

		while (true)
			try {
				Menu.print("Choose game:[/c to cancel] "); String command = Menu.getInputLine();

				if (command.trim().equalsIgnoreCase("/c")) return;

				int choice = Integer.parseInt(command);

				if (choice < 1 || choice > recosForPlayer.size())
					throw new InputMismatchException();


				((_11GameMenu) Menu.getMenuIn().getChildMenus().get(5))
						.setGameName(recosForPlayer.get(choice - 1).getGameName());
				break;
			} catch (InputMismatchException e) {
				Menu.printErrorMessage("Invalid input");
			}

		Menu.getMenuIn().getChildMenus().get(5).enter();
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
		// TODO TOTOOTOTOTOOTOTO
	}

	public void chooseRecoGame () {
		LinkedList<AdminGameReco> recos = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getAdminGameRecosGotten();

		int gameNum = Integer.parseInt(Menu.getInputLine());

		switch (recos.size()) {
			case 1 -> { if (gameNum != 1) return; }
			case 2 -> { if (gameNum != 1 && gameNum != 2) return; }
			default -> {return;}
		}

		switch (recos.get(gameNum - 1).getGameName().toLowerCase()) {

			case "battlesea" -> Menu.getMenu("11B").enter();

			case "reversi" -> Menu.getMenu("11R").enter();
		}
	}

	private static class AdminGameRecoAlreadyExists extends Exception {
		public AdminGameRecoAlreadyExists () {
			super("You have already recommended this game to this gamer.");
		}
	}
}
