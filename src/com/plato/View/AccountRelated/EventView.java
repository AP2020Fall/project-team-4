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

	public void displayEvents (LinkedList<String> inSessionEvents) { // every string is in form -> "title eventID gameName start end eventScore"

		Menu.println(" |\tTitle\t\t|\tEventID\t|\t  Game  \t|\t  Start  \t|\t\tEnd\t\t|\tPrize\t|");
		inSessionEvents.forEach(event -> {
			int cols = event.split(" ").length;
			StringBuilder title = new StringBuilder();
			for (int i = 0; i < cols - 5; i++)
				title.append(event.split(" ")[i] + " ");

			System.out.printf(" | %s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|%n",
					title.toString(),
					event.split(" ")[cols-5],
					event.split(" ")[cols - 4],
					event.split(" ")[cols - 3],
					event.split(" ")[cols - 2],
					event.split(" ")[cols - 1]
			);
		});
	}

	public void displayEventInfo (String title, String gameName, String start, String end, double prizeScore) {
		System.out.printf("Title = %s%nGame = %s%nStart = %s\tEnd = %s%nPrize = %.01f%n",
				title,
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

	public void displayNotParticipatingInAnyEvents () {
		System.out.println("You are currently not participating in any events");
	}

	public void displayNoCurrentlyInSessionEvents () {
		System.out.println("There is no events currently in session.");
	}
}
