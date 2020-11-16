package plato.AccountRelated;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Message {
	private String text;
	private final Gamer gamer;
	private LocalDateTime time;

	private LinkedList<Message> messages = new LinkedList<>();

	public Message (String text, Gamer gamer) {
		this.text = text;
		this.gamer = gamer;
		this.time = LocalDateTime.now();

		messages.addLast(this);
	}

	private LinkedList<Message> getMessages (Gamer gamer) {
		return (LinkedList<Message>) messages.stream()
				.filter(message -> message.gamer.getUsername().equals(gamer.getUsername()))
				.collect(Collectors.toList());


	}
}
