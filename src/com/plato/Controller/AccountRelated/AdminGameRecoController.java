package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.AdminGameReco;
import plato.Model.AccountRelated.Gamer;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.AccountRelated.AdminGameRecoView;
import plato.View.Menus.Menu;

import java.util.Comparator;
import java.util.LinkedList;

public class AdminGameRecoController {

	public static void giveRecommendationToGamer () {
		// TODO DOODODODODOODODOODODO
	}

	public static void displayAdminsRecosToPlayer () {
		AdminGameRecoView.displayAdminsRecosToPlayer(new LinkedList<>(){{
			for (AdminGameReco adminGameReco : ((Gamer) AccountController.getCurrentAccLoggedIn()).getAdminGameRecosGotten())
				add(adminGameReco.getGameName());
		}});
	}

	public static void displayAllAdminRecos () {
		LinkedList<AdminGameReco> allRecos = AdminGameReco.getRecommendations();
		allRecos.sort(Comparator
				.comparing(AdminGameReco::getGamerUsername) // from a-z
				.thenComparing(AdminGameReco::getGameName)  // first battlesea recos then reversi
				.thenComparing(AdminGameReco::getRecoID));

		AdminGameRecoView.displayAllAdminRecos(new LinkedList<>(){{
			for (AdminGameReco reco : allRecos)
				add("%s %s %s".formatted(reco.getRecoID(), reco.getGamerUsername(), reco.getGameName()));
		}});
		// todo enter submenu to be able to remove reco
	}

	public static void removeReco () {
		// TODO TOTOOTOTOTOOTOTO
	}

	public static void chooseRecoGame () {
		LinkedList<AdminGameReco> recos = ((Gamer) AccountController.getCurrentAccLoggedIn()).getAdminGameRecosGotten();

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
