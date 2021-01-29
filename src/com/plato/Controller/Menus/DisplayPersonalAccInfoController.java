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

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
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

	public void editFirstName (MouseEvent mouseEvent) {
		firstNameTextField.setVisible(!firstNameTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(firstNameTextField.isVisible() ? "cancel" : "edit");
		firstNameTextField.setText(firstName.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editLastName (MouseEvent mouseEvent) {
		lastNameTextField.setVisible(!lastNameTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(lastNameTextField.isVisible() ? "cancel" : "edit");
		lastNameTextField.setText(lastName.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editUsername (MouseEvent mouseEvent) {
		usernameTextField.setVisible(!usernameTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(usernameTextField.isVisible() ? "cancel" : "edit");
		usernameTextField.setText(username.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editEmail (MouseEvent mouseEvent) {
		emailTextField.setVisible(!emailTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(emailTextField.isVisible() ? "cancel" : "edit");
		emailTextField.setText(email.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void editPhoneNum (MouseEvent mouseEvent) {
		phoneNumberTextField.setVisible(!phoneNumberTextField.isVisible());
		((Label) mouseEvent.getSource()).setText(phoneNumberTextField.isVisible() ? "cancel" : "edit");
		phoneNumberTextField.setText(phoneNumber.getText());
		editButtons.add(((Label) mouseEvent.getSource()));
	}

	public void confirmAllEdits (ActionEvent actionEvent) {
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

	public void uploadPfp (MouseEvent mouseEvent) {
		MainController.openUploadPfpWindow(stage, pfp);
	}
}
