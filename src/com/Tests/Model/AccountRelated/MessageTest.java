package Model.AccountRelated;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MessageTest {
	@BeforeEach
	@AfterEach
	public void resetEverything () {
		Message.setAllMessages(new LinkedList<>());
	}

	@Test
	public void sendMsgTest () throws InterruptedException {
		assumeTrue(Message.getAllMessages().size() == 0);
		Message message1 = new Message("Hi1");
		Thread.sleep(300);
		Message message2 = new Message("Hi2");
		Thread.sleep(300);
		Message message3 = new Message("Hi3");
		Thread.sleep(300);
		Message message4 = new Message("Hi4");
		Thread.sleep(300);

		assertEquals(4, Message.getAllMessages().size());

		assertEquals("Hi1", Message.getAllMessages().get(0).getText());
		assertEquals("Hi2", Message.getAllMessages().get(1).getText());
		assertEquals("Hi3", Message.getAllMessages().get(2).getText());
		assertEquals("Hi4", Message.getAllMessages().get(3).getText());
	}
}
