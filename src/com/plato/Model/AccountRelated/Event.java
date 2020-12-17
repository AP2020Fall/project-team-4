package Model.AccountRelated;

import Controller.IDGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Event {
	private static LinkedList<Event> events = new LinkedList<>();
	private final String eventID;
	private String title;
	private String gameName;
	private double eventScore;
	private LocalDate start, end;
	private LinkedList<Gamer> participants = new LinkedList<>();
	private boolean awardsGiven = false; // true if an event has ended and its awards have been given, false otherwise

	private Event (String title, String gameName, double eventScore, LocalDate start, LocalDate end) {
		this.title = title;
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
	}

	public static void addEvent (String title, String gameName, double eventScore, LocalDate start, LocalDate end) {
		events.addLast(new Event(title, gameName, eventScore, start, end));
	}

	public static void removeEvent (String eventID) {
		events.remove(getEvent(eventID));
	}

	public static LinkedList<Event> getInSessionEventsParticipatingIn (Gamer gamer) {
		LinkedList<Event> inSessionEventsParticipatingIn = new LinkedList<>();

		for (Event inSessionEvent : getInSessionEvents()) {
			for (Gamer participant : inSessionEvent.getParticipants()) {
				if (participant.getUsername().equals(gamer.getUsername()) && !inSessionEventsParticipatingIn.contains(inSessionEvent))
					inSessionEventsParticipatingIn.add(inSessionEvent);
			}
		}

		return inSessionEventsParticipatingIn;
	}

	@SuppressWarnings("ForLoopReplaceableByForEach")
	public static void dealWOverdueEvents () {
		for (int i = 0; i < events.size(); i++) {

			Event event = events.get(i);
			if (event.isDue() && !event.awardsGiven)
				event.giveAwardsOfOverdueEvent();
		}
	}

	public static LinkedList<Event> getEvents () {
		return events;
	}

	public static void setEvents (LinkedList<Event> events) {
		Event.events = events;
	}

	public static LinkedList<Event> getInSessionEvents () {
		return getEvents().stream()
				.filter(Event::isInSession)
				.sorted(Comparator.comparing(Event::getGameName)                // first battleSea then reversi events
						.thenComparing(Event::getStart)                            // from earliest starting

						.thenComparing(Event::getEnd)                            // from earliest ending
						.thenComparingDouble(Event::getEventScore).reversed()    // from highest prizes
						.thenComparing(Event::getEventID))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@SuppressWarnings("unused")
	public static boolean notStartedEventExists (String eventID) {
		return events.stream()
				.filter(event -> !event.hasStarted())
				.anyMatch(event -> event.getEventID().equals(eventID));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static Event getEvent (String eventID) {
		return events.stream()
				.filter(event -> event.getEventID().equals(eventID))
				.findAny().get();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean eventExists (String eventID) {
		return events.stream()
				.anyMatch(event -> event.getEventID().equals(eventID));
	}

	public static boolean eventInSessionExists (String eventID) {
		for (int i = 0; i < getInSessionEvents().size(); i++)
			if (getInSessionEvents().get(i).getEventID().equals(eventID))
				return true;
		return false;
	}

	@SuppressWarnings("EnhancedSwitchMigration")
	public void editField (String field, String newVal) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		switch (field.toLowerCase()) {
			case "title":
				title = newVal;
				break;
			case "game name":
				gameName = newVal;
				break;
			case "event score":
				eventScore = Double.parseDouble(newVal);
				break;
			case "start":
				start = LocalDate.parse(newVal, dateTimeFormatter);
				break;
			case "end":
				end = LocalDate.parse(newVal, dateTimeFormatter);
				break;
		}
	}

	public boolean hasStarted () {
		return !LocalDate.now().isBefore(start);
	}

	private boolean isDue () {
		return LocalDate.now().isAfter(end);
	}

	public boolean isInSession () {
		return hasStarted() && !isDue();
	}

	public void giveAwardsOfOverdueEvent () {
		// TODO: 12/8/2020 AD
		awardsGiven = true;
	}

	public void addParticipant (Gamer gamer) {
		participants.add(gamer);
	}

	public void removeParticipant (Gamer gamer) {
		participants.removeIf(participant -> participant.getUsername().equals(gamer.getUsername()));
	}

	@SuppressWarnings("ForLoopReplaceableByForEach")
	public boolean participantExists (String username) {
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).getUsername().equals(username))
				return true;
		}
		return false;
	}

	@SuppressWarnings({"ForLoopReplaceableByForEach", "unused"})
	public Gamer getParticipant (String username) {
		for (int i = 0; i < participants.size(); i++)
			if (participants.get(i).getUsername().equals(username))
				return participants.get(i);
		return null;
	}

	public LinkedList<Gamer> getParticipants () {
		if (participants == null)
			participants = new LinkedList<>();
		return participants;
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

	public String getTitle () {
		return title;
	}
}
