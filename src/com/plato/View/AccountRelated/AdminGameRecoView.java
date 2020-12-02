package plato.View.AccountRelated;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminGameRecoView {
	public static void displayAdminsRecosToPlayer (LinkedList<String> gameRecosGotten) { // every string is in form -> "gameName"
		System.out.print("Suggestions you've gotten from admin: ");

		AtomicInteger i = new AtomicInteger(1);
		String list = "%d. %s".formatted(i.getAndIncrement(), gameRecosGotten.getFirst());
		if (gameRecosGotten.size() > 1)
			list += " - %d. %s".formatted(i.getAndIncrement(), gameRecosGotten.getLast());

		System.out.println(list);
	}

	public static void displayAllAdminRecos (LinkedList<String> allRecos) { // every string is in form -> "recoID gamerUsername gameName"
		System.out.println(" | RecoID\t| Gamer\t| Game\t|");
		allRecos.forEach(reco -> {
			System.out.printf(" | %s\t| %s\t| %s\t|%n",
					reco.split(" ")[0],
					reco.split(" ")[1],
					reco.split(" ")[2]
			);
		});
	}
}
