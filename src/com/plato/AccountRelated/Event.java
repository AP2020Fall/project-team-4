package plato.AccountRelated;

import plato.IDGenerator;

import java.time.LocalDate;
import java.util.LinkedList;

public class Event {
	private final String gameName, eventID;
	private double eventScore;
	private final LocalDate start, end;

	private static LinkedList<Event> events = new LinkedList<>();

	public Event (String gameName, double eventScore, LocalDate start, LocalDate end) {
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
	}


}
