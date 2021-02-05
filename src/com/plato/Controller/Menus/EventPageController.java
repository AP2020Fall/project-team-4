package Controller.Menus;

import Controller.AccountRelated.EventController;
import Model.AccountRelated.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class EventPageController implements Initializable {
	private static Event event;
	public ImageView eventImg;
	public ImageView eventGameImg;
	public Label eventPrize;
	public ImageView coinImg;
	public Label eventTitle;
	public Button joinEventBtn;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private static Socket socket;


	public static void setEvent (Event event) {
		EventPageController.event = event;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		joinEventBtn.setOnAction(e -> {
			try {
				joinEvent(event.getEventID());
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		});
	}

	public void joinEvent (String eventID) throws IOException {
		dataOutputStream.writeUTF("participateInEvent_" + eventID);
		dataOutputStream.flush();
		//EventController.getClient().participateInEvent(eventID);
	}
}
