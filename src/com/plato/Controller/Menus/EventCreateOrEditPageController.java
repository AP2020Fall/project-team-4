package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.EventController;
import Controller.MainController;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EventCreateOrEditPageController implements Initializable {
	private static Stage stage;
	private static Event event;
	private static boolean isForCreateOrInfo;
	public ImageView eventImg, uploadEventImg;
	public Label title, start, end, coins, details, howToWinPrize;
	public TextField titleTextField;
	public ImageView gameImg;
	public SplitMenuButton gameEditMenu, coinSplitMenu;
	public DatePicker startDatePicker, endDatePicker;
	public TextArea detailsTextArea;
	public Button joinOrDropoutEventBtn, removeEventBtn, closeStageBtn, confirmEditsBtn, revertEditsBtn, createEventBtn, cancelBtn, eventSettingsBtn;
	public HBox topButtonsHbox, downBtnsHbox, gameHbox;
	public GridPane mainGridPane;
	public Label allErrors;
	public LinkedList<Label> editButtons = new LinkedList<>();
	private MouseEvent mouseEvent;
	private  ActionEvent actionEvent;


	public EventCreateOrEditPageController() {
		this.mouseEvent = null;
		this.actionEvent =null;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public static void setStage (Stage stage) {
		EventCreateOrEditPageController.stage = stage;
		EventCreateOrEditPageController.stage.setOnCloseRequest(e -> EventCreateOrEditPageController.stage = null);
	}

	public static void setIsForCreateOrInfo (boolean isForCreateOrInfo) {
		EventCreateOrEditPageController.isForCreateOrInfo = isForCreateOrInfo;
	}

	public static Event getEvent () {
		return event;
	}

	public static void setEvent (Event event) {
		EventCreateOrEditPageController.event = event;
	}

	public void uploadImg () {
		// TODO: 1/14/2021 AD
	}
	public void uploadImgWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.uploadImg");
	}
	public void editTitle () {
		titleTextField.setVisible(!titleTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(titleTextField.isVisible() ? "cancel" : "edit");
		titleTextField.setText(title.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editTitleWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editTitle");
	}
	public void editGame () {
		gameEditMenu.setVisible(!gameEditMenu.isVisible());
		((Label) getMouseEvent().getSource()).setText(gameEditMenu.isVisible() ? "cancel" : "edit");
		gameEditMenu.setText(event.getGameName());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editGameWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editGame");
	}
	public void editStartDate () {
		startDatePicker.setVisible(!startDatePicker.isVisible());
		((Label) getMouseEvent().getSource()).setText(startDatePicker.isVisible() ? "cancel" : "edit");
		startDatePicker.setValue(LocalDate.parse(
				start.getText(),
				DateTimeFormatter.ofPattern("d MMM" + (event.getStart().getYear() != event.getEnd().getYear() ? " yyyy" : "")))
		);
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editStartDateWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editStartDate");
	}
	public void editEndDate () {
		endDatePicker.setVisible(!endDatePicker.isVisible());
		((Label) getMouseEvent().getSource()).setText(endDatePicker.isVisible() ? "cancel" : "edit");
		endDatePicker.setValue(LocalDate.parse(
				end.getText(),
				DateTimeFormatter.ofPattern("d MMM" + (event.getStart().getYear() != event.getEnd().getYear() ? " yyyy" : "")))
		);
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editEndDateWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editEndDateWrite");
	}
	public void editCoins () {
		coinSplitMenu.setVisible(!coinSplitMenu.isVisible());
		((Label) getMouseEvent().getSource()).setText(coinSplitMenu.isVisible() ? "cancel" : "edit");
		coinSplitMenu.setText(coins.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editCoinsWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editCoins");
	}
	public void editDetails () {
		detailsTextArea.setVisible(!detailsTextArea.isVisible());
		((Label) getMouseEvent().getSource()).setText(detailsTextArea.isVisible() ? "cancel" : "edit");
		detailsTextArea.setText(details.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editDetailsWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.editDetails");
	}
	public void removeEvent () {
		Event.removeEvent(event.getEventID());
		mainGridPane.getChildren().clear();
		mainGridPane.setVisible(false);
	}
	public void removeEventWrite(ActionEvent actionEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("EventCreateOrEditPage.removeEvent");
	}
	public void revertEdits () {
		titleTextField.setVisible(false);
		startDatePicker.setVisible(false);
		endDatePicker.setVisible(false);
		gameEditMenu.setVisible(false);
		detailsTextArea.setVisible(false);
		coinSplitMenu.setVisible(false);
	}
	public void revertEditsWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventCreateOrEditPage.removeEvent");
	}
	public void confirmEdits () {
		try {
			if (titleTextField.isVisible()) {
				try {
					EventController.getInstance().editEvent(event, "title", titleTextField.getText());
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}

			}
			if (gameEditMenu.isVisible()) {
				try {
					EventController.getInstance().editEvent(event, "game menu", gameEditMenu.getText());
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}
			}

			if (coinSplitMenu.isVisible()) {
				try {
					EventController.getInstance().editEvent(event, "prize", coinSplitMenu.getText());
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}
			}

			if (detailsTextArea.isVisible()) {
				try {
					EventController.getInstance().editEvent(event, "details", detailsTextArea.getText());
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}
			}

			if (startDatePicker.isVisible()) {
				try {
					EventController.getInstance().editEvent(
							event, "start date", startDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy"))
					);
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}
			}

			if (endDatePicker.isVisible()) {
				try {
					EventController.getInstance().editEvent(
							event, "end date", endDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy"))
					);
				} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
					allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
				}
			}

			try {
				EventController.getInstance().editEvent(event, "pic-url", eventImg.getImage().getUrl());
			} catch (EventController.StartDateTimeIsAfterEndException | MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeHasAlreadyPassedException e) {
				allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
			}
		} catch (EventController.CantEditInSessionEventException e) {
			e.printStackTrace();
		}
	}
	public void confirmEditWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventCreateOrEditPage.confirmEdit");
	}
	public void closeStage () {
		stage.close();
	}
	public void closeStageWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventCreateOrEditPage.closeStage");
	}
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		if (event != null && event.hasStarted())
			makeEventUnEditableAndUnremovable();

		if (isForCreateOrInfo || (!isForCreateOrInfo && AccountController.getInstance().getCurrentAccLoggedIn() instanceof Admin)) {
			mainGridPane.getChildren().remove(gameHbox);
			mainGridPane.getRowConstraints().remove(3);


		}

		allErrors.setText("");
		startDatePicker.setValue(LocalDate.now());
		endDatePicker.setValue(LocalDate.now());
		startDatePicker.setConverter(new StringConverter<>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");

			@Override
			public String toString (LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString (String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		endDatePicker.setConverter(startDatePicker.getConverter());

		if (isForCreateOrInfo) {
			topButtonsHbox.getChildren().removeAll(removeEventBtn, howToWinPrize, eventSettingsBtn);
			mainGridPane.getRowConstraints().remove(2);
			displayAllEditableParts();
			makeEventUnEditableAndUnremovable();
		}

		else {
			updateDisplayInfo();

			updateEditableParts();

			boolean isForGamerOrAdmin = AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer;

			// gamer cant edit or delete
			if (isForGamerOrAdmin) {
				makeEventUnEditableAndUnremovable();
				mainGridPane.getChildren().remove(eventSettingsBtn);
			}
			else {
				topButtonsHbox.getChildren().remove(closeStageBtn);
				downBtnsHbox.getChildren().removeAll(joinOrDropoutEventBtn, createEventBtn, cancelBtn);
			}
		}
	}

	private void makeEventUnEditableAndUnremovable () {
		downBtnsHbox.getChildren().remove(1, 6);
		topButtonsHbox.getChildren().removeAll(closeStageBtn, removeEventBtn);
		mainGridPane.getChildren().remove(uploadEventImg);

		if (!isForCreateOrInfo)
			updateJoinOrDropOutBtn();

		// remove all edit buttons
		mainGridPane.getChildren().stream()
				.filter(node -> node instanceof HBox)
				.map(node -> ((HBox) node))
				.forEach(hBox ->
						hBox.getChildren().stream()
								.filter(node -> node instanceof VBox)
								.map(node -> ((VBox) node))
								.forEach(vBox -> vBox.getChildren().remove(vBox.getChildren().size() - 1)));
	}

	private void updateJoinOrDropOutBtn () {
		if (event.participantExists(AccountController.getInstance().getCurrentAccLoggedIn().getUsername())) {
			joinOrDropoutEventBtn.setText("Drop-out");
			joinOrDropoutEventBtn.setOnAction(e -> {
				EventController.getInstance().stopParticipatingInEvent(event.getEventID());
				updateJoinOrDropOutBtn();
			});
		}
		else {
			joinOrDropoutEventBtn.setText("Join");
			joinOrDropoutEventBtn.setOnAction(e -> {
				EventController.getInstance().participateInEvent(event.getEventID());
				updateJoinOrDropOutBtn();
			});
		}
	}

	private void updateEditableParts () {
		eventImg.setImage(new Image(event.getPictureUrl()));
		titleTextField.setText(event.getTitle());
		gameEditMenu.setText(event.getGameName());
		startDatePicker.setValue(event.getStart());
		endDatePicker.setValue(event.getEnd());
		coinSplitMenu.setText(String.valueOf(event.getEventScore()));
		detailsTextArea.setText(event.getDetails());
	}

	public void updateFieldViews () {
		updateDisplayInfo();

		new LinkedList<>(
				Arrays.asList(titleTextField, gameEditMenu, startDatePicker, endDatePicker, coinSplitMenu, detailsTextArea)
		)
				.forEach(node -> node.setVisible(false));

		editButtons.forEach(button -> button.setText("edit"));
		editButtons.clear();
	}

	public void updateDisplayInfo () {
		eventImg.setImage(new Image(event.getPictureUrl()));
		title.setText(event.getTitle());
		gameImg.setImage(new Image(Game.getGamePictureUrl(event.getGameName())));
		String format = "d MMM" + (event.getStart().getYear() != event.getEnd().getYear() ? "yyyy" : "");
		start.setText(event.getStart().format(DateTimeFormatter.ofPattern(format)));
		end.setText(event.getEnd().format(DateTimeFormatter.ofPattern(format)));
		coins.setText(String.valueOf(event.getEventScore()));
		details.setText(event.getDetails());
		howToWinPrize.setText(event.getHowTo());
	}

	private void displayAllEditableParts () {
		titleTextField.setVisible(true);
		gameEditMenu.setVisible(true);
		startDatePicker.setVisible(true);
		endDatePicker.setVisible(true);
		coinSplitMenu.setVisible(true);
		detailsTextArea.setVisible(true);
	}

	public void createEvent () {
		try {
			EventController.getInstance().createEvent(
					titleTextField.getText(),
					gameEditMenu.getText(),
					eventImg.getImage().getUrl(),
					startDatePicker.getValue(),
					endDatePicker.getValue(),
					Double.parseDouble(coinSplitMenu.getText()),
					detailsTextArea.getText()
			);
		} catch (MainController.InvalidFormatException | EventController.EndDateTimeHasAlreadyPassedException | EventController.StartDateTimeIsAfterEndException | EventController.StartDateTimeHasAlreadyPassedException | EventController.GameNameCantBeEmptyException e) {
			allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
		}
	}
	public void createEventWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventCreateOrEditPage.createEvent");
	}
	public void openEventSettings () {
		try {
			EventSettingsController.setIsForCreateOrEdit(title.getText().equals("-") || title.getText().isEmpty() || title.getText().isBlank());
			Stage settingsStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/EventSettings.fxml").toURI().toURL()),
					"Event Condition to win",
					true,
					stage
			);
			EventSettingsController.setStage(stage);
			settingsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void openEventSettingsWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventCreateOrEditPage.openEventSetting");
	}
}