package Controller.Menus;

import Controller.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class EventSettingsController implements Initializable {
	public static MenuButton eventTypeMenuSaved, gameMenuSaved, minReqMenuSaved;
	private static boolean isForCreateOrEdit;
	private static Stage stage;
	public MenuButton eventTypeMenu, gameMenu, minReqMenu;
	public GridPane gridPane;
	private StringProperty eventSettings;
	private MouseEvent mouseEvent;
	private ActionEvent actionEvent;



	public EventSettingsController() {
		this.mouseEvent = null;
		this.actionEvent = null;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public static void setStage (Stage stage) {
		EventSettingsController.stage = stage;
		EventSettingsController.stage.setOnCloseRequest(e -> EventSettingsController.stage = null);
	}

	public static void setIsForCreateOrEdit (boolean isForCreateOrEdit) {
		EventSettingsController.isForCreateOrEdit = isForCreateOrEdit;
	}

	public static MenuButton getEventTypeMenuSaved () {
		return eventTypeMenuSaved;
	}

	public static MenuButton getGameMenuSaved () {
		return gameMenuSaved;
	}

	public static MenuButton getMinReqMenuSaved () {
		return minReqMenuSaved;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		for (int i = 0; i < 2; i++) {
			int finalI = i;
			gameMenu.getItems().get(i).setOnAction(e -> gameMenu.setText(gameMenu.getItems().get(finalI).getText()));
		}

		eventTypeMenu.textProperty().addListener((observable, oldValue, newValue) ->
				eventSettings.setValue(getEventSettingsAsString())
		);
		gameMenu.textProperty().addListener((observable, oldValue, newValue) ->
				eventSettings.setValue(getEventSettingsAsString())
		);
		minReqMenu.textProperty().addListener((observable, oldValue, newValue) ->
				eventSettings.setValue(getEventSettingsAsString())
		);

		eventSettings = new SimpleStringProperty("");
		eventSettings.addListener((observable, oldValue, newValue) -> {
			if (newValue.contains("Minimum") || newValue.contains("name") || newValue.contains("Type")) {
				eventTypeMenuSaved.setText(eventTypeMenu.getText());
				gameMenuSaved.setText(gameMenu.getText());
				minReqMenuSaved.setText(minReqMenu.getText());
			}
		});
	}

	private String getEventSettingsAsString () {
		return "%s|%s|%s".formatted(eventTypeMenu.getText(), gameMenu.getText(), minReqMenu.getText());
	}

	public void isLoginTimes () {
		gameMenu.setVisible(false);
		minReqMenu.setVisible(true);

		minReqMenu.getItems().clear();

		for (int i = 10; i <= 100; i += 10) {
			String finalI = String.valueOf(i);
			minReqMenu.getItems().addAll(new MenuItem() {{
				setText(finalI);
				setOnAction(e -> minReqMenu.setText(getText()));
			}});
		}

		resetPageWrite(getActionEvent());
	}
	public void isLoginTimesWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.isLoginTimes");
	}
	public void resetPage () {
		minReqMenu.setText("Minimum Requirement");
		gameMenu.setText("Game name");
		eventTypeMenu.setText(((MenuItem) actionEvent.getSource()).getText());
	}
	public void resetPageWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.resetPage");
	}
	public void closeStage () {
		stage.close();
	}
	public void closeStageWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.closeStage");
	}
	public void isMVPInGame () {
		gameMenu.setVisible(true);
		minReqMenu.setVisible(false);

		resetPageWrite(getActionEvent());
	}
	public void isMVPInGameWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.isMVPInGame");
	}
	public void isPlayTimes () {
		minReqMenu.setVisible(true);
		gameMenu.setVisible(true);

		minReqMenu.getItems().clear();

		for (int i = 5; i <= 50; i += 5) {
			String finalI = String.valueOf(i);
			minReqMenu.getItems().addAll(new MenuItem() {{
				setText(finalI);
				setOnAction(e -> minReqMenu.setText(getText()));
			}});
		}

		resetPageWrite(getActionEvent());
	}
	public void isPlayTimeWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.isPlayTimes");
	}
	public void isWinTimes () {
		minReqMenu.setVisible(true);
		gameMenu.setVisible(true);

		minReqMenu.getItems().clear();

		for (int i = 5; i <= 30; i += 5) {
			String finalI = String.valueOf(i);
			minReqMenu.getItems().addAll(new MenuItem() {{
				setText(finalI);
				setOnAction(e -> minReqMenu.setText(getText()));
			}});
		}

		resetPageWrite(getActionEvent());
	}
	public void isWinTimesWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.isWinTimes");
	}
	public void isWinTimesNonStop () {
		minReqMenu.setVisible(true);
		gameMenu.setVisible(true);

		minReqMenu.getItems().clear();

		for (int i = 2; i <= 10; i++) {
			String finalI = String.valueOf(i);
			minReqMenu.getItems().addAll(new MenuItem() {{
				setText(finalI);
				setOnAction(e -> minReqMenu.setText(getText()));
			}});
		}

		resetPageWrite(getActionEvent());
	}
	public void isWinTimesNonStopWrite(ActionEvent actionEvent) {
		setActionEvent(actionEvent);
		MainController.write("EventSettings.isWinTimesNonStop");
	}
}
