package Model.AccountRelated;


import Controller.Client;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Controller.MyGson.getGson;

public class Message {
	//private static LinkedList<Message> allMessages = new LinkedList<>();
	private final String text;
	private final LocalDateTime dateTime;
	private final Gamer gamer;

	public Message (Gamer gamer, String text) {
		this.text = text;
		this.gamer = gamer;
		this.dateTime = LocalDateTime.now();

		getAllMessages().addLast(this);
	}

	public static LinkedList<Message> getMessagesToGamer (Gamer gamer) {
		return getAllMessages().stream()
				.filter(message -> message.getGamer().getUsername().equals(gamer.getUsername()))
				.sorted(Comparator.comparing(Message::getDateTime))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<Message> getAllMessages () {
		LinkedList<Message> allMessages = new LinkedList<>();
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();

		try {
			dataOutputStream.writeUTF("getMsgs");
			dataOutputStream.flush();

			allMessages.addAll(getGson().fromJson(dataInputStream.readUTF(), new TypeToken<LinkedList<Message>>() {}.getType()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return allMessages.stream()
				.sorted(Comparator.comparing(Message::getDateTime))
				.collect(Collectors.toCollection(LinkedList::new));
	}

//	public static void setAllMessages (LinkedList<Message> allMessages) {
//		if (allMessages == null)
//			allMessages = new LinkedList<>();
//		Message.allMessages = allMessages;
//	}

	public LocalDateTime getDateTime () {
		return dateTime;
	}

	public String getText () {
		return text;
	}

	public Gamer getGamer () {
		return gamer;
	}
}