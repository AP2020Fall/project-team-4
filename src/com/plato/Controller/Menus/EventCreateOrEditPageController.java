package Controller.Menus;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EventCreateOrEditPageController {
	private static Stage stage;
	private static Event event;
	public ImageView eventImg;
	public ImageView uploadEventImg;
	public Label title;
	public TextField titleTextField;
	public ImageView gameImg;
	public SplitMenuButton gameEditMenu;
	public Label start;
	public DatePicker startDatePicker;
	public Label end;
	public DatePicker endDatePicker;
	public Label coins;
	public SplitMenuButton coinSplitMenu;
	public Label details;
	public TextArea detailsTextArea;
	public Button joinEventBtn;
	public Button removeEventBtn;
	public HBox topButtonsHbox;
	public Button closeStageBtn;
	public Button confirmEditsBtn;
	public Button revertEditsBtn;

	public static void setStage (Stage stage) {
		EventCreateOrEditPageController.stage = stage;
		EventCreateOrEditPageController.stage.setOnCloseRequest(e -> EventCreateOrEditPageController.stage = null);
	}

	public static void setStage (Stage stage, Event event) {
		EventCreateOrEditPageController.stage = stage;
		EventCreateOrEditPageController.stage.setOnCloseRequest(e -> {
			EventCreateOrEditPageController.stage = null;
			EventCreateOrEditPageController.event = null;
		});
		EventCreateOrEditPageController.event = event;
	}

	public void uploadImg (MouseEvent mouseEvent) {
	}

	public void editTitle (MouseEvent mouseEvent) {
	}

	public void editGame (MouseEvent mouseEvent) {
	}

	public void editStartDate (MouseEvent mouseEvent) {
	}

	public void editEndDate (MouseEvent mouseEvent) {
	}

	public void editCoins (MouseEvent mouseEvent) {
	}

	public void editDetails (MouseEvent mouseEvent) {
	}

	public void joinEvent (ActionEvent actionEvent) {
	}

	public void removeEvent (ActionEvent actionEvent) {
	}

	public void revertEdits (ActionEvent actionEvent) {
	}

	public void confirmEdits (ActionEvent actionEvent) {
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}
}
