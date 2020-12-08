package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Reversi.Reversi;
import View.AccountRelated.EventView;
import View.Menus.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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
			Menu.printAskingForInput("Choose Game:[/c to cancel] "); gameNum = String.valueOf(Integer.parseInt(Menu.getInputLine()));

			if (gameNum.trim().equalsIgnoreCase("/c")) return;

			if (gameNum.matches("[1-2]"))
				break;
			Menu.printErrorMessage("Invalid input.");
		}

		String gameName = (Integer.parseInt(gameNum) == 1) ? BattleSea.class.getSimpleName() : Reversi.class.getSimpleName();

		LocalDate start;
		while (true) {
			try {
				Menu.printAskingForInput("Start Date[d-MMM-yyyy][/c to cancel]: ");
				start = LocalDate.parse(Menu.getInputLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));

				if (gameNum.trim().equalsIgnoreCase("/c")) return;

				if (start.isBefore(LocalDate.now()))
					throw new StartDateTimeHasAlreadyPassedException();
				break;
			} catch (DateTimeParseException e) {
				Menu.printErrorMessage("Invalid start date format.");
			} catch (StartDateTimeHasAlreadyPassedException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		LocalDate end;
		while (true) {
			try {
				Menu.printAskingForInput("End Date[d-MMM-yyyy][/c to cancel]: ");
				end = LocalDate.parse(Menu.getInputLine(), DateTimeFormatter.ofPattern("d-MMM-yyyy"));

				if (gameNum.trim().equalsIgnoreCase("/c")) return;

				if (end.isBefore(LocalDate.now()))
					throw new EndDateTimeHasAlreadyPassedException();

				if (end.isBefore(start))
					throw new StartDateTimeIsAfterEndException();
				break;
			} catch (DateTimeParseException e) {
				Menu.printErrorMessage("Invalid end date format.");
			} catch (EndDateTimeHasAlreadyPassedException | StartDateTimeIsAfterEndException e) {
				Menu.printErrorMessage(e.getMessage());
			}
		}

		double eventPrize;
		while (true) {
			try {
				Menu.printAskingForInput("Event Prize[/c to cancel]: "); eventPrize = Double.parseDouble(Menu.getInputLine());

				if (gameNum.trim().equalsIgnoreCase("/c")) return;

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
				Menu.printAskingForInput("Event ID:[/c to cancel] "); eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/c")) return;

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
				Menu.printAskingForInput("Event ID:[/c to cancel] "); eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/c")) return;

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
				Menu.printAskingForInput("Event ID:[/c to cancel] "); String eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/c")) return;

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
		Event event;
		while (true)
			try {
				Menu.printAskingForInput("Event ID:[/c to cancel] "); String eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/c")) return;

				if (!Event.eventExists(eventid))
					throw new EventDoesntExistException();

				if (!Event.getEvent(eventid).hasStarted())
					throw new CantEditInSessionEventException();

				event = Event.getEvent(eventid);
				break;
			} catch (EventDoesntExistException | CantEditInSessionEventException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		LinkedList<String> availableFields = (LinkedList<String>) Arrays.asList(new String[]{
				"Game name",
				"Event prize",
				"Start date",
				"End date"
		});
		EventView.getInstance().displayEditableFields(availableFields);

		int field = 0;
		try {
			String fieldstr = (Menu.getInputLine());

			if (String.valueOf(fieldstr).matches("[1-4]"))
				throw new NumberFormatException();

			field = Integer.parseInt(fieldstr);
		} catch (NumberFormatException e) {
			Menu.printErrorMessage(new MainController.InvalidInputException().getMessage());
		}

		switch (field) {
			case 1 -> {
				String gameNum;
				while (true)
					try {
						Menu.printAskingForInput("Choose New Game:[/c to cancel] "); gameNum = Menu.getInputLine();

						if (gameNum.trim().equalsIgnoreCase("/c")) return;

						if (!gameNum.matches("[1-2]"))
							throw new MainController.InvalidInputException();
						break;
					} catch (MainController.InvalidInputException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				String gameName = (Integer.parseInt(gameNum) == 1) ? BattleSea.class.getSimpleName() : Reversi.class.getSimpleName();

				Menu.displayAreYouSureMessage();
				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
					event.editField("game name", gameName);
					Menu.printSuccessfulOperation("Game name changed successfully.");
				}
			}

			case 2 -> {
				String prize;
				while (true)
					try {
						Menu.printAskingForInput("New Event Prize Amount: [/c to cancel] "); prize = Menu.getInputLine();

						if (prize.trim().equalsIgnoreCase("/c")) return;
						Double.parseDouble(prize);
						break;
					} catch (NumberFormatException e) {
						Menu.printErrorMessage("Invalid format.");
					}

				Menu.displayAreYouSureMessage();
				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
					event.editField("event score", prize);
					Menu.printSuccessfulOperation("Event prize changed successfully.");
				}
			}

			case 3 -> {
				String startDate;
				while (true)
					try {
						Menu.printAskingForInput("New Event Start Date: [/c to cancel] "); startDate = Menu.getInputLine();

						if (startDate.trim().equalsIgnoreCase("/c")) return;

						LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);

						if (start.isBefore(LocalDate.now()))
							throw new StartDateTimeHasAlreadyPassedException();

						if (event.getEnd().isBefore(start))
							throw new StartDateTimeIsAfterEndException();

						break;
					} catch (DateTimeParseException e) {
						Menu.printErrorMessage("Invalid start date format.");
					} catch (StartDateTimeHasAlreadyPassedException | StartDateTimeIsAfterEndException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				Menu.displayAreYouSureMessage();
				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
					event.editField("start", startDate);
					Menu.printSuccessfulOperation("Start date changed successfully.");
				}
			}

			case 4 -> {
				String endDate;
				while (true)
					try {
						Menu.printAskingForInput("New Event End Date: [/c to cancel] "); endDate = Menu.getInputLine();

						if (endDate.trim().equalsIgnoreCase("/c")) return;

						LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);

						if (end.isBefore(LocalDate.now()))
							throw new EndDateTimeHasAlreadyPassedException();

						if (event.getEnd().isBefore(end))
							throw new StartDateTimeIsAfterEndException();

						break;
					} catch (DateTimeParseException e) {
						Menu.printErrorMessage("Invalid start date format.");
					} catch (EndDateTimeHasAlreadyPassedException | StartDateTimeIsAfterEndException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				Menu.displayAreYouSureMessage();
				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
					event.editField("end", endDate);
					Menu.printSuccessfulOperation("End date changed successfully.");
				}
			}
		}
	}

	public void removeEvent () {
		while (true)
			try {
				Menu.printAskingForInput("Event ID:[/c to cancel] "); String eventid = Menu.getInputLine();

				if (eventid.trim().equalsIgnoreCase("/c")) return;

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

	private static class EndDateTimeHasAlreadyPassedException extends Exception {
		public EndDateTimeHasAlreadyPassedException () {
			super("End date has already passed.");
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

	private static class CantEditInSessionEventException extends Exception {
		public CantEditInSessionEventException () {
			super("Can't edit an event that is in session.");
		}
	}

	private static class NotParticipatingInEventException extends Exception {
		public NotParticipatingInEventException () {
			super("You are not participating in event");
		}
	}
}
