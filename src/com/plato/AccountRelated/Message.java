package plato.AccountRelated;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Message {
	private String text;
	private final Gamer gamer;
	private LocalDateTime dateTime;

	private static LinkedList<Message> allMessages = new LinkedList<>();

	public Message (String text, Gamer gamer) {
		this.text = text;
		this.gamer = gamer;
		this.dateTime = LocalDateTime.now();

		allMessages.addLast(this);
	}

	public static LinkedList<Message> getMessages (Gamer gamer) {
		return (LinkedList<Message>) allMessages.stream()
				.filter(message -> message.gamer.getUsername().equals(gamer.getUsername()))
				.collect(Collectors.toList());
	}

	public static LinkedList<Message> getAllMessages () {
		return allMessages;
	}

	public LocalDateTime getDateTime () {
		return dateTime;
	}
}
