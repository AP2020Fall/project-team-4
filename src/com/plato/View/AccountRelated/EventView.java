package View.AccountRelated;

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

		inSessionEvents.forEach(event -> {
			int length = event.split(" ").length;

//			Menu.print("EventId: " + event.split(" ")[length - 5] + " -> ");

			StringBuilder title = new StringBuilder();
			for (int i = 0; i < length - 5; i++)
				title.append(event.split(" ")[i] + " ");

			System.out.printf("Title = %s  Game = %s  Start = %s  End = %s  Prize = %s %n",
					title.toString(),
					event.split(" ")[length - 4],
					event.split(" ")[length - 3],
					event.split(" ")[length - 2],
					event.split(" ")[length - 1]
			);
		});
	}

	public void displayEventInfo (String title, String gameName, String start, String end, double prizeScore) {
		System.out.printf("Title = %s%nGame = %s%nStart = %s  End = %s%nPrize = %.01f%n",
				title,
				gameName,
				start,
				end,
				prizeScore
		);
	}

	public void displayEditableFields (LinkedList<String> editableFields) {
//		Menu.printAskingForInput("Choose field to edit:\n");
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
