package View.AccountRelated;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminGameRecoView {
	private static AdminGameRecoView adminGameRecoView;

	public static AdminGameRecoView getInstance () {
		if (adminGameRecoView == null)
			adminGameRecoView = new AdminGameRecoView();
		return adminGameRecoView;
	}

	public void displayAdminsRecosToPlayer (LinkedList<String> gameRecosGotten) { // every string is in form -> "gameName"
		if (gameRecosGotten.size() == 0) {
			System.out.println("You haven't received any suggestions from admin yet.");
			return;
		}
		System.out.print("Suggestions you've gotten from admin: ");

		AtomicInteger i = new AtomicInteger(1);
		String list = "%d. %s".formatted(i.getAndIncrement(), gameRecosGotten.getFirst());
		if (gameRecosGotten.size() > 1)
			list += " - %d. %s".formatted(i.getAndIncrement(), gameRecosGotten.getLast());

		System.out.println(list);
	}

	public void displayAllAdminRecos (LinkedList<String> allRecos) { // every string is in form -> "recoID gamerUsername gameName"
		System.out.println(" | RecoID |  Gamer\t| Game\t|");
		allRecos.forEach(reco -> {
			System.out.printf(" | %s | %s\t| %s\t\t|%n",
					reco.split(" ")[0],
					reco.split(" ")[1],
					reco.split(" ")[2]
			);
		});
	}
}
