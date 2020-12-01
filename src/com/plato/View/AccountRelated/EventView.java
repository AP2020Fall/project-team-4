package plato.View.AccountRelated;

import plato.Model.AccountRelated.Event;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Reversi.Reversi;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class EventView {
	public static void displayAvailableGames () {
		System.out.printf("%t1. %s - 2. %s%n", BattleSea.class.getSimpleName(), Reversi.class.getSimpleName());
	}

	public static void displayInSessionEvents (LinkedList<Event> inSessionEvents) {
		System.out.println(" | EventID\t| Game\t| Start\t| End\t| Prize\t|");
		inSessionEvents.forEach(event -> {
			System.out.printf(" | %s\t| %s\t| %s\t| %s\t| %.01f\t|%n",
					event.getEventID(),
					event.getGameName(),
					event.getStart().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
					event.getEnd().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
					event.getEventScore()
			);
		});
	}
}
