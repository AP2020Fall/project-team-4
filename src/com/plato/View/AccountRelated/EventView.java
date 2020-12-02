package plato.View.AccountRelated;

import java.util.LinkedList;

public class EventView {
	public static void displayAvailableGames () {
		System.out.printf("%t1. %s - 2. %s%n", "BattleSea", "Reversi");
	}

	public static void displayInSessionEvents (LinkedList<String> inSessionEvents) { // every string is in form -> "eventID gameName start end eventScore"
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
}
