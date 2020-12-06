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
		events.remove(getEvent(eventID))
	}

	public static LinkedList<Event> getInSessionEventsParticipatingIn (Gamer gamer) {
		// TODO: 12/5/2020 AD
		return null;
	}

	public void editField (String field, String newval) {
		switch (field){
			case "game name":gameName=newval; break;
			case "eventscore":eventScore=Double.parseDouble(newval);break;
			case "start":start=newval;break; // FIXME: 12/6/2020
			case "end":end=newval;break; // FIXME: 12/6/2020
		}

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

	public static void dealWOverdueEvents () {
		for (int i=0;i<events.size();i++)
			if (events.get(i).isDue()){
				events.get(i).giveAwardsOfOwerdueEvents()

			}

	}

	public static void giveAwardsOfOverdueEvents () {
	}
	// TODO: 12/6/2020

	public static LinkedList<Event> getEvents () {
		return events;
	}

	public static LinkedList<Event> getInSessionEvents () {
		return (LinkedList<Event>) getEvents().stream()
				.filter(Event::isInSession)
				.sorted(Comparator.comparing(Event::getGameName) 				// first battlesea then reversi events
						.thenComparing(Event::getStart)			 				// from earliest starting
						.thenComparing(Event::getEnd)							// from earliest ending
						.thenComparingDouble(Event::getEventScore).reversed()	// from hishest prizes
						.thenComparing(Event::getEventID))
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

	public static Event getEvent (String eventID) {
		// TODO: 11/16/2020 AD
		return null;
	}

	public static boolean eventInSessionExists (String eventID) {
		// TODO: 11/16/2020 AD
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
