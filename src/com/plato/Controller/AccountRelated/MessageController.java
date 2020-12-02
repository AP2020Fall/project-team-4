package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Message;
import plato.View.AccountRelated.MessageView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;

public class MessageController {
	public static void displayAdminMessages () {
		LinkedList<Message> messages = Message.getAllMessages();
		messages.sort(Comparator.comparing(Message::getDateTime));

		MessageView.displayAdminMessages(new LinkedList<>() {{
			for (Message message : messages)
				add("%s %s".formatted(
						message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd")),
						message.getText())
				);
		}});
	}
}
