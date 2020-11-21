package plato.AccountRelated;

import plato.IDGenerator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Event {
	private final String eventID, gameName;
	private double eventScore;
	private final LocalDate start, end;
	private LinkedList<Gamer> participants = new LinkedList<>();

	private static LinkedList<Event> events = new LinkedList<>();

	public Event (String gameName, double eventScore, LocalDate start, LocalDate end) {
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
		events.addLast(this);
	}

	public void editField () {
		// TODO: 11/16/2020 AD
	}

	public static void removeEvent (String eventID) {
		// TODO: 11/16/2020 AD
	}

	public static Event getEvent (String eventID) {
		// TODO: 11/16/2020 AD
		return null;
	}

	public static boolean eventExists (String eventID) {
		// TODO: 11/16/2020 AD
		return false;
	}

	private boolean hasStarted () {
		// TODO: 11/16/2020 AD
		return false;
	}

	private boolean isDue () {
		// TODO: 11/16/2020 AD
		return false;
	}

	public boolean isInSession () {
		return hasStarted() && !isDue();
	}

	public void dealWOverdueEvents () {
		// TODO: 11/16/2020 AD
	}

	public void giveAwardsOfOverdueEvents () {
		// TODO: 11/16/2020 AD
	}

	public static LinkedList<Event> getEvents () {
		return events;
	}

	public static LinkedList<Event> getInSessionEvents () {
		return (LinkedList<Event>) getEvents().stream()
				.filter(Event::isInSession)
				.collect(Collectors.toList());
	}

	public void addParticipant (Gamer gamer) {
		// TODO: 11/20/2020 AD
	}
	
	public void removeParticipant (Gamer gamer) {
		// TODO: 11/20/2020 AD  
	}

	public boolean participantExists (String username) {
		// TODO: 11/20/2020 AD
		return false;
	}

	public Gamer getParticipant (String username) {
		// TODO: 11/20/2020 AD
		return null;
	}

	public LinkedList<Gamer> getParticipants () {
		return participants;
	}
}
