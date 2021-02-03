package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventController {
	private static EventController eventController;
	public Object participateInEvent;

    public static EventController getInstance () {
		if (eventController == null)
			eventController = new EventController();
		return eventController;
	}

	public void createEvent (String title, String gameName, String picUrl, LocalDate start, LocalDate end, double eventPrize, String details) throws MainController.InvalidFormatException, GameNameCantBeEmptyException, StartDateTimeHasAlreadyPassedException, StartDateTimeIsAfterEndException, EndDateTimeHasAlreadyPassedException {

		if (!title.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("First name");

		if (gameName.isEmpty() || gameName.isBlank())
			throw new GameNameCantBeEmptyException();

		if (start.isBefore(LocalDate.now()))
			throw new StartDateTimeHasAlreadyPassedException();

		if (end.isBefore(start))
			throw new StartDateTimeIsAfterEndException();

		if (end.isBefore(LocalDate.now()))
			throw new EndDateTimeHasAlreadyPassedException();

//		Event.addEvent(picUrl, title, gameName, details, eventPrize, start, end); // fixme
	}

	public void participateInEvent (String eventid) {
		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).participateInEvent(eventid);
	}

	public void stopParticipatingInEvent (String eventid) {
		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).stopParticipatingInEvent(eventid);
	}

	public void editEvent (Event event, String field, String newVal) throws MainController.InvalidFormatException, StartDateTimeHasAlreadyPassedException, StartDateTimeIsAfterEndException, EndDateTimeHasAlreadyPassedException, CantEditInSessionEventException {
		if (event.isInSession())
			throw new CantEditInSessionEventException();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

		switch (field.toLowerCase()) {
			case "title" -> {
				if (!newVal.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("New title");

				event.editField(field, newVal);
			}

			case "game name" -> {
				if (newVal.isEmpty() || newVal.isBlank())
					throw new MainController.InvalidFormatException("New Game name");
			}

			case "prize", "details", "pic-url" -> {}


			case "start date" -> {
				LocalDate start = LocalDate.parse(newVal, dateTimeFormatter);
				if (start.isBefore(LocalDate.now()))
					throw new StartDateTimeHasAlreadyPassedException();

				if (event.getEnd().isBefore(start))
					throw new StartDateTimeIsAfterEndException();
			}

			case "end date" -> {
				LocalDate end = LocalDate.parse(newVal, dateTimeFormatter);
				if (end.isBefore(LocalDate.now()))
					throw new EndDateTimeHasAlreadyPassedException();

				if (event.getEnd().isBefore(end))
					throw new StartDateTimeIsAfterEndException();
			}

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
		event.editField(field, newVal);
	}

	public void stopParticipatingInEvent() {
	}

	public static class StartDateTimeHasAlreadyPassedException extends Exception {
		public StartDateTimeHasAlreadyPassedException () {
			super("Start date has already passed.");
		}
	}

	public static class EndDateTimeHasAlreadyPassedException extends Exception {
		public EndDateTimeHasAlreadyPassedException () {
			super("End date has already passed.");
		}
	}

	public static class StartDateTimeIsAfterEndException extends Exception {
		public StartDateTimeIsAfterEndException () {
			super("End date must be after or on the same day as the start date.");
		}
	}

	private static class EventDoesntExistException extends Exception {
		public EventDoesntExistException () {
			super("No event with this eventID exists.");
		}
	}

	public static class CantEditInSessionEventException extends Exception {
		public CantEditInSessionEventException () {
			super("Can't edit an event that is in session.");
		}
	}

	private static class NotParticipatingInEventException extends Exception {
		public NotParticipatingInEventException () {
			super("You are not participating in event");
		}
	}

	private static class AlreadyParticipatingInEventException extends Exception {
		public AlreadyParticipatingInEventException () {
			super("You are already participating in this event");
		}
	}

	public static class GameNameCantBeEmptyException extends Exception {
		public GameNameCantBeEmptyException () {
			super("Game Name can't be empty");
		}
	}
}
