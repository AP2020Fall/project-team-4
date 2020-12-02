package plato.View.AccountRelated;

import plato.Model.AccountRelated.AdminGameReco;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminGameRecoView {
	public static void displayAdminsRecosToPlayer (LinkedList<AdminGameReco> gameRecosGotten) {
		System.out.print("Suggestions you've gotten from admin: ");

		AtomicInteger i = new AtomicInteger(1);
		String list = "%d. %s".formatted(i.getAndIncrement(), gameRecosGotten.getFirst().getGameName());
		if (gameRecosGotten.size() > 1)
			list += " - %d. %s".formatted(i.getAndIncrement(), gameRecosGotten.get(1).getGameName());

		System.out.println(list);
	}

	public static void displayAllAdminRecos (LinkedList<AdminGameReco> allRecos) {
		System.out.println(" | RecoID\t| Gamer\t| Game\t|");
		allRecos.forEach(reco -> {
			System.out.printf(" | %s\t| %s\t| %s\t|%n",
					reco.getRecoID(),
					reco.getGamerUsername(),
					reco.getGameName()
			);
		});
	}
}
