package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Message;
import plato.View.AccountRelated.MessageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MessageController {
	public static void displayAdminMessages () {
		LinkedList<Message> messages = Message.getAllMessages();
		messages.sort(Comparator.comparing(Message::getDateTime));

		MessageView.displayAdminMessages(messages);
	}
}
