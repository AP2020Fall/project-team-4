package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.AdminGameReco;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.AccountRelated.AdminGameRecoView;
import plato.View.Menus.Menu;
import plato.View.Menus._11GameMenu;

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
		// TODO DOODODODODOODODOODODO
	}

	public void displayAdminsRecosToPlayer () {
		LinkedList<AdminGameReco> recosForPlayer = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getAdminGameRecosGotten();

		AdminGameRecoView.getInstance().displayAdminsRecosToPlayer(new LinkedList<>() {{
			for (AdminGameReco adminGameReco : recosForPlayer)
				add(adminGameReco.getGameName());
		}});

		while (true)
			try {
				Menu.print("Choose game:[/cancel to cancel filling form] "); String command = Menu.getInputLine();

				if (command.trim().equalsIgnoreCase("/cancel")) return;

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
		// todo enter submenu to be able to remove reco
	}

	public void addReco () {
		//TODO TODOODODOOD
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

		if (BattleSea.class.getSimpleName().equals(recos.get(gameNum - 1))) {
			// TODO: enter game menu for battlesea
		}
		else if (Reversi.class.getSimpleName().equals(recos.get(gameNum - 1))) {
			// TODO: enter game menu for reversi
		}
	}

	private static class AdminGameRecoAlreadyExists extends Exception {
		public AdminGameRecoAlreadyExists () {
			super("You have already recommended this game to this gamer.");
		}
	}
}
