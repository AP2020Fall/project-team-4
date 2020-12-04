package plato.View.AccountRelated;

import java.util.LinkedList;

public class EventView {
	private static EventView eventView;

	public static EventView getInstance () {
		if (eventView == null)
			eventView = new EventView();
		return eventView;
	}

	public void displayAvailableGames () {
		System.out.printf("%t1. %s - 2. %s%n", "BattleSea", "Reversi");
	}

	public void displayInSessionEvents (LinkedList<String> inSessionEvents) { // every string is in form -> "eventID gameName start end eventScore"
		System.out.println(" | EventID\t| Game\t| Start\t| End\t| Prize\t|");
		inSessionEvents.forEach(event -> {
			System.out.printf(" | %s\t| %s\t| %s\t| %s\t| %s\t|%n",
					event.split(" ")[0],
					event.split(" ")[1],
					event.split(" ")[2],
					event.split(" ")[3],
					event.split(" ")[4]
			);
		});
	}

	public void displayEventInfo (String gameName, String start, String end, double prizeScore) {
		System.out.printf("Game = %s\tStart = %s\tEnd = %s\tPrize = %.01f\t%n",
				gameName,
				start,
				end,
				prizeScore
		);
	}
}
