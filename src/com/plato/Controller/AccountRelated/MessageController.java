package Controller.AccountRelated;

import Model.AccountRelated.Gamer;
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

	public void sendMsg (Gamer gamer, String msg) throws EmptyMessageException {
		if (msg.trim().equals(""))
			throw new EmptyMessageException();

		new Message(gamer, msg);
	}

	public static class EmptyMessageException extends Exception {
		public EmptyMessageException () {
			super("You can't send an empty message.");
		}
	}
}
