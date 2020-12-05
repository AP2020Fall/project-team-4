package Controller.AccountRelated;

import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Reversi.Reversi;
import View.AccountRelated.EventView;
import View.Menus.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

public class EventController {
	private static EventController eventController;

	public static EventController getInstance () {
		if (eventController == null)
			eventController = new EventController();
		return eventController;
	}

	public void createEvent () {
		EventView.getInstance().displayAvailableGames();

		String gameNum;
		while (true) {
			System.out.print("Choose Game:[/cancel to cancel filling form] "); gameNum = String.valueOf(Integer.parseInt(Menu.getInputLine()));

			if (gameNum.trim().toLowerCase().equals("/cancel")) return;

			if (gameNum.equals(1) || gameNum.equals(2))
				break;
			Menu.printErrorMessage("Invalid input.");
		}

		String gameName = (Integer.parseInt(gameNum) == 1) ? BattleSea.class.getSimpleName() : Reversi.class.getSimpleName();

		LocalDate start;
		while (true) {
			try {
				System.out.print("Start Date[d-MMM-yyyy][/cancel to cancel filling form]: "); start = LocalDate.parse(Menu.getInputLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));

				if (gameNum.trim().toLowerCase().equals("/cancel")) return;

				if (start.isBefore(LocalDate.now()))
					throw new StartDateTimeHasAlreadyPassedException();
				break;
			} catch (StartDateTimeHasAlreadyPassedException e) {
				Menu.printErrorMessage(e.getMessage());
			} catch (DateTimeParseException e) {
				Menu.printErrorMessage("Invalid start date format.");
			}
		}

		LocalDate end;
		while (true) {
			try {
				System.out.print("End Date[d-MMM-yyyy][/cancel to cancel filling form]: "); end = LocalDate.parse(Menu.getInputLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));

				if (gameNum.trim().toLowerCase().equals("/cancel")) return;

				if (end.isBefore(LocalDate.now()))
					throw new StartDateTimeHasAlreadyPassedException();
				if (end.isBefore(start))
					throw new StartDateTimeIsAfterEndException();
				break;
			} catch (StartDateTimeHasAlreadyPassedException | StartDateTimeIsAfterEndException e) {
				Menu.printErrorMessage(e.getMessage());
			} catch (DateTimeParseException e) {
				Menu.printErrorMessage("Invalid end date format.");
			}
		}

		double eventPrize;
		while (true) {
			try {
				System.out.print("Event Prize[/cancel to cancel filling form]: "); eventPrize = Double.parseDouble(Menu.getInputLine());

				if (gameNum.trim().toLowerCase().equals("/cancel")) return;

				break;
			} catch (NumberFormatException e) {
				Menu.printErrorMessage("Invalid format.");
			}
		}

		Event.addEvent(gameName, eventPrize, start, end);
	}

	public void displayInSessionEvents () {
		EventView.getInstance().displayEvents(new LinkedList<>() {{
			for (Event inSessionEvent : Event.getInSessionEvents()) {
				add("%s %s %s %s %.01f".formatted(
						inSessionEvent.getEventID(),
						inSessionEvent.getGameName(),
						inSessionEvent.getStart().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEnd().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEventScore()));
			}
		}});
	}

	public void displayInSessionEventsParticipatingIn () {
		EventView.getInstance().displayEvents(new LinkedList<>() {{
			for (Event inSessionEvent : Event.getInSessionEventsParticipatingIn(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()))) {
				add("%s %s %s %s %.01f".formatted(
						inSessionEvent.getEventID(),
						inSessionEvent.getGameName(),
						inSessionEvent.getStart().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEnd().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEventScore()));
			}
		}});
	}

	public void displayEventInfo () {
		String eventid;
		while (true)
			try {
				System.out.print("Event ID:[/cancel to cancel filling form] "); eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/cancel")) return;

				if (Event.eventInSessionExists(eventid))
					throw new EventDoesntExistException();

				break;
			} catch (EventDoesntExistException eventDoesntExist) {
				eventDoesntExist.printStackTrace();
			}
		Event event = Event.getEvent(eventid);

		EventView.getInstance().displayEventInfo(
				event.getGameName(),
				event.getStart().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
				event.getEnd().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
				event.getEventScore());
	}

	public void participateInEvent () {
		String eventid;
		while (true)
			try {
				System.out.print("Event ID:[/cancel to cancel filling form] "); eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/cancel")) return;

				if (Event.eventInSessionExists(eventid))
					throw new EventDoesntExistException();

				break;
			} catch (EventDoesntExistException eventDoesntExist) {
				eventDoesntExist.printStackTrace();
			}
		Event event = Event.getEvent(eventid);

		event.addParticipant(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
	}

	public void stopParticipatingInEvent () {
		Event event;
		while (true)
			try {
				System.out.print("Event ID:[/cancel to cancel filling form] "); String eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/cancel")) return;

				if (Event.eventInSessionExists(eventid))
					throw new EventDoesntExistException();

				event = Event.getEvent(eventid);
				if (!event.participantExists(AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new NotParticipatingInEventException();

				break;
			} catch (EventDoesntExistException | NotParticipatingInEventException e) {
				e.printStackTrace();
			}

		event.removeParticipant(((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()));
	}

	public void editEvent () {
		// TODO TODODODODODODOODODOD
	}

	public void removeEvent () {
		while (true)
			try {
				System.out.print("Event ID:[/cancel to cancel filling form] "); String eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/cancel")) return;

				if (Event.eventInSessionExists(eventid))
					throw new EventDoesntExistException();

				Event.removeEvent(eventid);
				break;
			} catch (EventDoesntExistException eventDoesntExist) {
				eventDoesntExist.printStackTrace();
			}
	}

	private static class StartDateTimeHasAlreadyPassedException extends Exception {
		public StartDateTimeHasAlreadyPassedException () {
			super("Start date has already passed.");
		}
	}

	private static class StartDateTimeIsAfterEndException extends Exception {
		public StartDateTimeIsAfterEndException () {
			super("End date must be after or on the same day as the start date.");
		}
	}

	private static class EventDoesntExistException extends Exception {
		public EventDoesntExistException () {
			super("No event with this eventID exists.");
		}
	}

	private static class NotParticipatingInEventException extends Exception {
		public NotParticipatingInEventException () {
			super("You are not participating in event");
		}
	}
}
