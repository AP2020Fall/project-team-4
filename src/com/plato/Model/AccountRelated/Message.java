package Model.AccountRelated;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Message {
	private static LinkedList<Message> allMessages = new LinkedList<>();
	private final String text;
	private final LocalDateTime dateTime;

	public Message (String text) {
		this.text = text;
		this.dateTime = LocalDateTime.now();

		allMessages.addLast(this);
	}

	public static LinkedList<Message> getAllMessages () {
		return allMessages.stream()
				.sorted(Comparator.comparing(Message::getDateTime))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static void setAllMessages (LinkedList<Message> allMessages) {
		Message.allMessages = allMessages;
	}

	public LocalDateTime getDateTime () {
		return dateTime;
	}

	public String getText () {
		return text;
	}
}