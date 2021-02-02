package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class DisplayPersonalAccInfoController implements Initializable {
	private static Stage stage;
	private static Account account;
	private static boolean gamerOrAdmin;
	public ImageView pfp, uploadPfp;
	public Label firstName, lastName, username,
			email, phoneNumber, coins;
	public TextField firstNameTextField, lastNameTextField, usernameTextField,
			emailTextField, phoneNumberTextField;
	public Label allError;
	public HBox coinHbox;
	public GridPane mainGridPane;
	public LinkedList<Label> editButtons = new LinkedList<>();
	private MouseEvent mouseEvent;
	private ActionEvent actionEvent;



	public DisplayPersonalAccInfoController() {
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
		DisplayPersonalAccInfoController.stage = stage;
		DisplayPersonalAccInfoController.stage.setOnCloseRequest(e -> {
			DisplayPersonalAccInfoController.stage = null;
			account = null;
		});
	}

	public static void setAccount (Account account) {
		DisplayPersonalAccInfoController.account = account;
		DisplayPersonalAccInfoController.gamerOrAdmin = account instanceof Gamer;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		pfp.setImage(new Image(account.getPfpUrl()));
		updateFieldViews();

		if (!gamerOrAdmin) {
			mainGridPane.getChildren().removeAll(coinHbox, uploadPfp);
			mainGridPane.getRowConstraints().remove(4);
		}
		else {
			coins.setText(String.valueOf(((Gamer) account).getMoney()));
		}
	}

	public void closeStage () {
		stage.close();
	}
	public void closeStageWrite(ActionEvent actionEvent)
	{
		setActionEvent(actionEvent);
		MainController.write("DisplayPersonalInfo.closeStage");
	}
	public void updateFieldViews () {
		firstName.setText(account.getFirstName());
		lastName.setText(account.getLastName());
		username.setText(account.getUsername());
		email.setText(account.getEmail());
		phoneNumber.setText(account.getPhoneNum());

		LinkedList<TextField> allTextFields = new LinkedList<>(Arrays.asList(firstNameTextField, lastNameTextField, usernameTextField, phoneNumberTextField, emailTextField));
		allTextFields.forEach(textField -> textField.setVisible(false));
		editButtons.forEach(button -> button.setText("edit"));
		editButtons.clear();
	}

	public void editFirstName () {
		firstNameTextField.setVisible(!firstNameTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(firstNameTextField.isVisible() ? "cancel" : "edit");
		firstNameTextField.setText(firstName.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editFirstNameWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.editFirstName");
	}
	public void editLastName () {
		lastNameTextField.setVisible(!lastNameTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(lastNameTextField.isVisible() ? "cancel" : "edit");
		lastNameTextField.setText(lastName.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editLastNameWrite(MouseEvent mouseEvent)
	{
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.editLastName");
	}
	public void editUsername () {
		usernameTextField.setVisible(!usernameTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(usernameTextField.isVisible() ? "cancel" : "edit");
		usernameTextField.setText(username.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editUsernameWrite(MouseEvent mouseEvent)
	{
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.editUsername");
	}
	public void editEmail () {
		emailTextField.setVisible(!emailTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(emailTextField.isVisible() ? "cancel" : "edit");
		emailTextField.setText(email.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editEmailWrite(MouseEvent mouseEvent)
	{
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.editEmail");
	}
	public void editPhoneNum () {
		phoneNumberTextField.setVisible(!phoneNumberTextField.isVisible());
		((Label) getMouseEvent().getSource()).setText(phoneNumberTextField.isVisible() ? "cancel" : "edit");
		phoneNumberTextField.setText(phoneNumber.getText());
		editButtons.add(((Label) getMouseEvent().getSource()));
	}
	public void editPhoneNumWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.editPhoneNum");
	}
	public void confirmAllEdits () {
		if (firstNameTextField.isVisible()) {
			try {
				AccountController.getInstance().editAccField("first name", firstNameTextField.getText());
				allError.setText("");
			} catch (MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException e) {
				allError.setText(allError.getText() + "\n" + e.getMessage());
			}
		}

		if (lastNameTextField.isVisible()) {
			try {
				AccountController.getInstance().editAccField("last name", lastNameTextField.getText());
				allError.setText("");
			} catch (MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException e) {
				allError.setText(allError.getText() + "\n" + e.getMessage());
			}
		}

		if (usernameTextField.isVisible()) {
			try {
				AccountController.getInstance().editAccField("username", usernameTextField.getText());
				allError.setText("");
			} catch (MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException e) {
				allError.setText(allError.getText() + "\n" + e.getMessage());
			}
		}

		if (emailTextField.isVisible()) {
			try {
				AccountController.getInstance().editAccField("email", emailTextField.getText());
				allError.setText("");
			} catch (MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException e) {
				allError.setText(allError.getText() + "\n" + e.getMessage());
			}
		}

		if (phoneNumberTextField.isVisible()) {
			try {
				AccountController.getInstance().editAccField("phone number", phoneNumberTextField.getText());
				allError.setText("");
			} catch (MainController.InvalidFormatException | AccountController.AccountWithUsernameAlreadyExistsException e) {
				allError.setText(allError.getText() + "\n" + e.getMessage());
			}
		}

		updateFieldViews();
	}
	public void confirmAllEditsWrite(ActionEvent actionEvent)
	{
		setActionEvent(actionEvent);
		MainController.write("DisplayPersonalInfo.confirmAllEdits");
	}
	public void uploadPfp ()
	{
		MainController.openUploadPfpWindow(stage, pfp);
	}
	public void uploadPfpWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("DisplayPersonalInfo.uploadPfp");
	}
}
