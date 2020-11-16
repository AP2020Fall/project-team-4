package plato.AccountRelated;

import plato.IDGenerator;

public class Event {
	private final String gameName, eventID;
	private double eventScore;

	public Event (String gameName, double eventScore) {
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.eventID = IDGenerator.generateNext();
	}
}
