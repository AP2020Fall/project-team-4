package Controller.AccountRelated;

import Model.AccountRelated.Message;
import View.AccountRelated.MessageView;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class MessageController {
	private static MessageController messageController;

	public static MessageController getInstance () {
		if (messageController == null)
			messageController = new MessageController();
		return messageController;
	}

	public void displayAdminMessages () {
		LinkedList<Message> messages = Message.getAllMessages();

		MessageView.getInstance().displayAdminMessages(new LinkedList<>() {{
			for (Message message : messages)
				add("%s %s".formatted(
						message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd")),
						message.getText())
				);
		}});
	}

	public void sendMsg () {
		String msg;
		while (true)
			try {
				Menu.printAskingForInput("Your message[/c to cancel] -> ");
				msg = Menu.getInputLine();

				if (msg.trim().equalsIgnoreCase("/c")) return;

				else if (msg.trim().equals(""))
					throw new EmptyMessageException();

				new Message(msg);
				break;
			} catch (EmptyMessageException e) {
				Menu.printErrorMessage(e.getMessage());
			}
	}

	private class EmptyMessageException extends Exception {
		public EmptyMessageException () {
			super("You can't send an empty message.");
		}
	}
}
