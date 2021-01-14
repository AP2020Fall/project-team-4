package Controller.Menus;

import Model.AccountRelated.Message;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MessageTemplateController implements Initializable {
	private static Message message;
	private static boolean showDate;
	public GridPane messageGridPane;
	public Label msg, time, date;
	public HBox dateHBox;

	public static void setMessage (Message message, boolean showDate) {
		MessageTemplateController.message = message;
		MessageTemplateController.showDate = showDate;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		LinkedList<String> lines = getLines(new LinkedList<>(Arrays.asList(message.getText().split(" "))));

		AtomicReference<String> textLined = new AtomicReference<>("");
		lines.forEach(line -> textLined.set(textLined.get() + line + "\n"));

		msg.setWrapText(true);
		msg.setText(textLined.get());

		time.setText(message.getDateTime().format(DateTimeFormatter.ofPattern("H:mm a")));
		time.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		if (!showDate) {
			messageGridPane.getChildren().remove(dateHBox);
			messageGridPane.getRowConstraints().remove(0);
		}
		else {
			date.setText(message.getDateTime().format(DateTimeFormatter.ofPattern("yy-MMM-d")));
			date.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		}
	}

	private LinkedList<String> getLines (LinkedList<String> words) {
		LinkedList<String> lines = new LinkedList<>();
		Iterator<String> wordIT = words.iterator();

		int maxCharInLine = 50;

		while (wordIT.hasNext()) {
			String nextWord = wordIT.next();

			if (lines.size() == 0)
				lines.add(nextWord);
			else {
				String newLine = lines.getLast() + " " + nextWord;

				if (newLine.length() > maxCharInLine)
					lines.add(nextWord);
				else
					lines.set(lines.size() - 1, newLine);
			}
		}

		return lines;
	}
}
