package plato.View.AccountRelated;

import plato.Model.AccountRelated.Message;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class MessageView {
	public static void displayAdminMessages (LinkedList<Message> messages) {
		messages.forEach(message -> System.out.printf("%t> %s - %s%n",
				message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd")),
				message.getText()
		));
	}
}
