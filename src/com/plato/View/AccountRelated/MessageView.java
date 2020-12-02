package plato.View.AccountRelated;

import java.util.LinkedList;

public class MessageView {
	public static void displayAdminMessages (LinkedList<String> messages) { // every string is in form -> "date text"
		messages.forEach(message -> System.out.printf("\t> %s - %s%n",
				message.split(" ")[0],
				message.split(" ")[1]
		));
	}
}
