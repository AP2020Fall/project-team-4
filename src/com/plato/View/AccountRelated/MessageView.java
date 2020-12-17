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
		messages.forEach(message -> {
			StringBuilder text = new StringBuilder();
			for (int i = 1; i < message.split(" ").length; i++)
				text.append(message.split(" ")[i]).append(" ");

			System.out.printf("\t> %s - %s%n",
					message.split(" ")[0],
					text.toString()
			);
		});
	}
}
