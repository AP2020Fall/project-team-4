package View.AccountRelated;

import View.Menus.Menu;

import java.util.LinkedList;

public class EventView {
	private static EventView eventView;

	public static EventView getInstance () {
		if (eventView == null)
			eventView = new EventView();
		return eventView;
	}

	public void displayAvailableGames () {
		System.out.printf("\t1. %s - 2. %s%n", "BattleSea", "Reversi");
	}

	public void displayEvents (LinkedList<String> inSessionEvents) { // every string is in form -> "eventID gameName start end eventScore"
		System.out.println(" |\tEventID\t|\t  Game  \t|\t  Start  \t|\t\tEnd\t\t|\tPrize\t|");
		inSessionEvents.forEach(event ->
				System.out.printf(" |\t%s \t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|%n",
				event.split(" ")[0],
				event.split(" ")[1],
				event.split(" ")[2],
				event.split(" ")[3],
				event.split(" ")[4]
		));
	}

	public void displayEventInfo (String gameName, String start, String end, double prizeScore) {
		System.out.printf("Game = %s\tStart = %s\tEnd = %s\tPrize = %.01f\t%n",
				gameName,
				start,
				end,
				prizeScore
		);
	}

	public void displayEditableFields (LinkedList<String> editableFields) {
		Menu.printAskingForInput("Choose field to edit:\n");
		for (String field : editableFields) {
			System.out.printf("%d. %s%n", editableFields.indexOf(field) + 1, field);
		}
	}
}
