package Controller.Menus;

import Model.AccountRelated.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EventCreateOrEditPageController {
	private static Stage stage;
	private static Event event;
	private static boolean isForCreateOrInfo;
	public ImageView eventImg;
	public ImageView uploadEventImg;
	public Label title;
	public TextField titleTextField;
	public ImageView gameImg;
	public SplitMenuButton gameEditMenu;
	public Label start,end;
	public DatePicker startDatePicker,endDatePicker;
	public Label coins;
	public SplitMenuButton coinSplitMenu;
	public Label details;
	public TextArea detailsTextArea;
	public Button joinEventBtn;
	public Button removeEventBtn;
	public HBox topButtonsHbox;
	public Button closeStageBtn, confirmEditsBtn,revertEditsBtn;

	public static void setStage (Stage stage) {
		EventCreateOrEditPageController.stage = stage;
		EventCreateOrEditPageController.stage.setOnCloseRequest(e -> EventCreateOrEditPageController.stage = null);
	}

	public static void setIsForCreateOrInfo (boolean isForCreateOrInfo) {
		EventCreateOrEditPageController.isForCreateOrInfo = isForCreateOrInfo;
	}

	public static void setEvent (Event event) {
		EventCreateOrEditPageController.event = event;
	}

	public void uploadImg (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editTitle (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editGame (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editStartDate (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editEndDate (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editCoins (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editDetails (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void joinEvent (ActionEvent actionEvent) {
		// TODO: 1/14/2021 AD
	}

	public void removeEvent (ActionEvent actionEvent) {
		// TODO: 1/14/2021 AD
	}

	public void revertEdits (ActionEvent actionEvent) {
		// TODO: 1/14/2021 AD
	}

	public void confirmEdits (ActionEvent actionEvent) {
		// TODO: 1/14/2021 AD
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}
}