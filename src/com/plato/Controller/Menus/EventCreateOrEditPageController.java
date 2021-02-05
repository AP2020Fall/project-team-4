package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.EventController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import com.google.gson.Gson;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
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
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private static Socket socket;


	public static void setStage (Stage stage) {
		EventCreateOrEditPageController.stage = stage;
		EventCreateOrEditPageController.stage.setOnCloseRequest(e -> EventCreateOrEditPageController.stage = null);
	}

	public static void setIsForCreateOrInfo (boolean isForCreateOrInfo) {
		EventCreateOrEditPageController.isForCreateOrInfo = isForCreateOrInfo;
	}

	public DatePicker getStartDatePicker() {
		return startDatePicker;
	}

	public DatePicker getEndDatePicker() {
		return endDatePicker;
	}

	public static Event getEvent () {
		return event;
	}

	public static void setEvent (Event event) {
		EventCreateOrEditPageController.event = event;
	}

	public void uploadImg (MouseEvent mouseEvent) {
		// TODO: 1/14/2021 AD
	}

	public void editTitle (MouseEvent mouseEvent) {
		titleTextField.setVisible(!titleTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(titleTextField.isVisible() ? "cancel" : "edit");
		titleTextField.setText(title.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editGame (MouseEvent mouseEvent) {
		gameEditMenu.setVisible(!gameEditMenu.isVisible());
		((Label) mouseEvent.getSource()).setText(gameEditMenu.isVisible() ? "cancel" : "edit");
		gameEditMenu.setText(event.getGameName());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editStartDate (MouseEvent mouseEvent) {
		startDatePicker.setVisible(!startDatePicker.isVisible());
		((Label) mouseEvent.getSource()).setText(startDatePicker.isVisible() ? "cancel" : "edit");
		startDatePicker.setValue(LocalDate.parse(
				start.getText(),
				DateTimeFormatter.ofPattern("d MMM" + (event.getStart().getYear() != event.getEnd().getYear() ? " yyyy" : "")))
		);
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editEndDate (MouseEvent mouseEvent) {
		endDatePicker.setVisible(!endDatePicker.isVisible());
		((Label) mouseEvent.getSource()).setText(endDatePicker.isVisible() ? "cancel" : "edit");
		endDatePicker.setValue(LocalDate.parse(
				end.getText(),
				DateTimeFormatter.ofPattern("d MMM" + (event.getStart().getYear() != event.getEnd().getYear() ? " yyyy" : "")))
		);
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editCoins (MouseEvent mouseEvent) {
		coinSplitMenu.setVisible(!coinSplitMenu.isVisible());
		((Label) mouseEvent.getSource()).setText(coinSplitMenu.isVisible() ? "cancel" : "edit");
		coinSplitMenu.setText(coins.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editDetails (MouseEvent mouseEvent) {
		detailsTextArea.setVisible(!detailsTextArea.isVisible());
		((Label) mouseEvent.getSource()).setText(detailsTextArea.isVisible() ? "cancel" : "edit");
		detailsTextArea.setText(details.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void removeEvent (ActionEvent actionEvent) throws IOException {
		dataOutputStream.writeUTF("removeEvent_" + event.getEventID());
		dataOutputStream.flush();
		//Event.removeEvent(event.getEventID());
		mainGridPane.getChildren().clear();
		mainGridPane.setVisible(false);
	}

	public void revertEdits (ActionEvent actionEvent) {
		titleTextField.setVisible(false);
		startDatePicker.setVisible(false);
		endDatePicker.setVisible(false);
		gameEditMenu.setVisible(false);
		detailsTextArea.setVisible(false);
		coinSplitMenu.setVisible(false);
	}

	public void confirmEdits (ActionEvent actionEvent) throws IOException {
		if (titleTextField.isVisible()) {
			try {
				dataOutputStream.writeUTF("editEvent_title_" + titleTextField.getText());
				dataOutputStream.flush();
				//EventController.getClient().editEvent(event, "title", titleTextField.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (gameEditMenu.isVisible()) {
			dataOutputStream.writeUTF("editEvent_game menu_" + gameEditMenu.getText());
			dataOutputStream.flush();
			//EventController.getClient().editEvent(event, "game menu", gameEditMenu.getText());
		}

		if (coinSplitMenu.isVisible()) {
			dataOutputStream.writeUTF("editEvent_prize_" + coinSplitMenu.getText());
			dataOutputStream.flush();
			//EventController.getClient().editEvent(event, "prize", coinSplitMenu.getText());
		}

		if (detailsTextArea.isVisible()) {
			dataOutputStream.writeUTF("editEvent_details_" + detailsTextArea.getText());
			dataOutputStream.flush();
			//		EventController.getClient().editEvent(event, "details", detailsTextArea.getText());
		}

		if (startDatePicker.isVisible()) {
			dataOutputStream.writeUTF("editEvent_start date_" + startDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")));
			dataOutputStream.flush();
			//EventController.getClient().editEvent(event, "start date", startDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")));
		}

		if (endDatePicker.isVisible()) {
			dataOutputStream.writeUTF("editEvent_end date_" + endDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")));
			dataOutputStream.flush();
			//		EventController.getClient().editEvent(event, "end date", endDatePicker.getValue().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")));
		}

		dataOutputStream.writeUTF("editEvent_pic-url_" + eventImg.getImage().getUrl());
		dataOutputStream.flush();
		//EventController.getClient().editEvent(event, "pic-url", eventImg.getImage().getUrl());
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		if (event != null && event.hasStarted()) {
			try {
				makeEventUnEditableAndUnremovable();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

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
			try {
				makeEventUnEditableAndUnremovable();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		else {
			updateDisplayInfo();

			updateEditableParts();

			boolean isForGamerOrAdmin = AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer;

			// gamer cant edit or delete
			if (isForGamerOrAdmin) {
				try {
					makeEventUnEditableAndUnremovable();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				mainGridPane.getChildren().remove(eventSettingsBtn);
			}
			else {
				topButtonsHbox.getChildren().remove(closeStageBtn);
				downBtnsHbox.getChildren().removeAll(joinOrDropoutEventBtn, createEventBtn, cancelBtn);
			}
		}
	}

	private void makeEventUnEditableAndUnremovable () throws IOException {
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

	private void updateJoinOrDropOutBtn () throws IOException {
		dataOutputStream.writeUTF("getCurrentAccLoggedIn_");
		dataOutputStream.flush();
		Account account = new Gson().fromJson(dataInputStream.readUTF() , Account.class);
		if (event.participantExists(account.getUsername())) {
			joinOrDropoutEventBtn.setText("Drop-out");
			joinOrDropoutEventBtn.setOnAction(e -> {
				try {
					dataOutputStream.writeUTF("stopParticipatingInEvent_" + event.getEventID());
					dataOutputStream.flush();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			//	EventController.getClient().stopParticipatingInEvent(event.getEventID());
				try {
					updateJoinOrDropOutBtn();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			});
		}
		else {
			joinOrDropoutEventBtn.setText("Join");
			joinOrDropoutEventBtn.setOnAction(e -> {
				try {
					dataOutputStream.writeUTF("participateInEvent_" + event.getEventID());
					dataOutputStream.flush();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			//	EventController.getClient().participateInEvent(event.getEventID());
				try {
					updateJoinOrDropOutBtn();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
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

	public void createEvent (ActionEvent actionEvent) {
		try {
		//	String title, String gameName, String picUrl, LocalDate start, LocalDate end, double eventPrize, String details
			dataOutputStream.writeUTF("createEvent_"
					+ titleTextField.getText() + "_"
					+ gameEditMenu.getText() + "_"
					+ eventImg.getImage().getUrl() + "_"
					+ coinSplitMenu.getText() + "_"
					+ detailsTextArea.getText()
			);
			dataOutputStream.flush();
//			EventController.getClient().createEvent(
//					titleTextField.getText(),
//					gameEditMenu.getText(),
//					eventImg.getImage().getUrl(),
//					startDatePicker.getValue(),
//					endDatePicker.getValue(),
//					Double.parseDouble(coinSplitMenu.getText()),
//					detailsTextArea.getText()
//			);
		} catch (IOException e) {
			allErrors.setText(allErrors.getText() + "\n" + e.getMessage());
		}
	}

	public void openEventSettings (ActionEvent actionEvent) {
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
}