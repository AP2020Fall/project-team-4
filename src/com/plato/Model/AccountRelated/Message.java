package plato.Model.AccountRelated;


import java.time.LocalDateTime;
import java.util.LinkedList;

public class Message {
	private String text;
	private LocalDateTime dateTime;

	private static LinkedList<Message> allMessages = new LinkedList<>();

	public Message (String text) {
		this.text = text;
		this.dateTime = LocalDateTime.now();

		allMessages.addLast(this);
	}

	public static LinkedList<Message> getAllMessages () {
		return allMessages;
	}

	public LocalDateTime getDateTime () {
		return dateTime;
	}

	public String getText () {
		return text;
	}
}