package Controller.Menus;

import Controller.AccountRelated.EventController;
import Model.AccountRelated.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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

	public static void setEvent (Event event) {
		EventPageController.event = event;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		joinEventBtn.setOnAction(e -> joinEvent(event.getEventID()));
	}

	public void joinEvent (String eventID) {
		EventController.getInstance().participateInEvent(eventID);
	}
}
