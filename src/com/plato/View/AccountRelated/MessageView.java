package View.AccountRelated;

import java.util.LinkedList;

public class MessageView {
	private static MessageView messageView;

	public static MessageView getInstance () {
		if (messageView == null)
			messageView = new MessageView();
		return messageView;
	}

	public void displayAdminMessages (LinkedList<String> messages) { // every string is in form -> "date text"
		messages.forEach(message -> System.out.printf("\t> %s - %s%n",
				message.split(" ")[0],
				message.split(" ")[1]
		));
	}
}
