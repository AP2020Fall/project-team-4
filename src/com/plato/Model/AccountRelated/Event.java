package Model.AccountRelated;

import Controller.IDGenerator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Event {
	private final String eventID;
	private String gameName;
	private double eventScore;
	private LocalDate start, end;
	private LinkedList<Gamer> participants = new LinkedList<>();
	private boolean awardsGiven = false; // true if an event has ended and its awards have been given, false otherwise

	private static LinkedList<Event> events = new LinkedList<>();

	private Event (String gameName, double eventScore, LocalDate start, LocalDate end) {
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
	}

	public static void addEvent (String gameName, double eventScore, LocalDate start, LocalDate end) {
		events.addLast(new Event(gameName, eventScore, start, end));
	}

	public static void removeEvent (String eventID) {
		events.remove(getEvent(eventID));
	}

	public static LinkedList<Event> getInSessionEventsParticipatingIn (Gamer gamer) {
		LinkedList<Event> inSessionEventsParticipatingIn = new LinkedList<>();

		for (int i = 0; i < getInSessionEvents().size(); i++) {
			Event inSessionEvent = getInSessionEvents().get(i);
			if (inSessionEvent.getParticipants().contains(gamer) && !inSessionEventsParticipatingIn.contains(inSessionEvent))
				inSessionEventsParticipatingIn.addLast(inSessionEvent);
		}

		return inSessionEventsParticipatingIn;
	}

	public void editField (String field, String newval) {
		switch (field) {
			case "game name":
				gameName = newval; break;
			case "event score":
				eventScore = Double.parseDouble(newval); break;
			case "start":
				start = LocalDate.parse(newval); break;
			case "end":
				end = LocalDate.parse(newval); break;
		}
	}

	private boolean hasStarted () {
		return !LocalDate.now().isBefore(start);
	}

	private boolean isDue () {
		return LocalDate.now().isAfter(end);
	}

	public boolean isInSession () {
		return hasStarted() && !isDue();
	}

	public static void dealWOverdueEvents () {
		for (int i = 0; i < events.size(); i++) {

			Event event = events.get(i);
			if (event.isDue() && !event.awardsGiven)
				event.giveAwardsOfOverdueEvent();
		}
	}

	public void giveAwardsOfOverdueEvent () {
		// TODO: 12/8/2020 AD
		awardsGiven = true;
	}

	public static LinkedList<Event> getEvents () {
		return events;
	}

	public static LinkedList<Event> getInSessionEvents () {
		return (LinkedList<Event>) getEvents().stream()
				.filter(Event::isInSession)
				.sorted(Comparator.comparing(Event::getGameName)                // first battlesea then reversi events
						.thenComparing(Event::getStart)                            // from earliest starting

						.thenComparing(Event::getEnd)                            // from earliest ending
						.thenComparingDouble(Event::getEventScore).reversed()    // from hishest prizes
						.thenComparing(Event::getEventID))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public void addParticipant (Gamer gamer) {
		participants.add(gamer);
	}

	public void removeParticipant (Gamer gamer) {
		participants.remove(gamer);
	}

	public boolean participantExists (String username) {
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).getUsername().equals(username))
				return true;
		}
		return false;
	}

	public Gamer getParticipant (String username) {
		for (int i = 0; i < participants.size(); i++)
			if (participants.get(i).getUsername().equals(username))
				return participants.get(i);
		return null;
	}

	public static Event getEvent (String eventID) {
		return events.stream()
				.filter(event -> event.getEventID().equals(eventID))
				.findAny().get();
	}

	public static boolean eventInSessionExists (String eventID) {
		for (int i = 0; i < getInSessionEvents().size(); i++)
			if (getInSessionEvents().get(i).getEventID().equals(eventID))
				return true;
		return false;
	}

	public LinkedList<Gamer> getParticipants () {
		return participants;
	}

	public static void setEvents (LinkedList<Event> events) {
		Event.events = events;
	}

	private void setGameName (String gameName) {
		this.gameName = gameName;
	}

	private void setEventScore (double eventScore) {
		this.eventScore = eventScore;
	}

	private void setStart (LocalDate start) {
		this.start = start;
	}

	private void setEnd (LocalDate end) {
		this.end = end;
	}

	public String getEventID () {
		return eventID;
	}

	public String getGameName () {
		return gameName;
	}

	public double getEventScore () {
		return eventScore;
	}

	public LocalDate getStart () {
		return start;
	}

	public LocalDate getEnd () {
		return end;
	}
}
