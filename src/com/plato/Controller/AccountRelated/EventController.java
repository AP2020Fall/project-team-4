package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Event;
import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.AccountRelated.EventView;
import plato.View.Menus.Menu;

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

	public void displayInSessionEventsAndGotoSubmenu () {
		EventView.getInstance().displayInSessionEvents(new LinkedList<>() {{
			for (Event inSessionEvent : Event.getInSessionEvents()) {
				add("%s %s %s %s %.01f".formatted(
						inSessionEvent.getEventID(),
						inSessionEvent.getGameName(),
						inSessionEvent.getStart().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEnd().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")),
						inSessionEvent.getEventScore()));
			}
		}});
		// todo go to submenu to edit and remove event
	}

	public void editEvent () {
		// TODO TODODODODODODOODODOD
	}

	public void removeEvent () {
		// TODO TODODODODODODOODODOD
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

	private static class EventDoesntExist extends Exception {
		public EventDoesntExist () {
			super("No event with this eventID exists.");
		}
	}
}
