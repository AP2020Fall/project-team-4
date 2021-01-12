package Controller.Menus;

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
	}

	public void editFirstName (MouseEvent mouseEvent) {
		firstNameTextField.setVisible(true);
		firstNameTextField.setText(firstName.getText());
	}

	public void editLastName (MouseEvent mouseEvent) {
		lastNameTextField.setVisible(true);
		lastNameTextField.setText(lastName.getText());
	}

	public void editUsername (MouseEvent mouseEvent) {
		usernameTextField.setVisible(true);
		usernameTextField.setText(username.getText());
	}

	public void editEmail (MouseEvent mouseEvent) {
		emailTextField.setVisible(true);
		emailTextField.setText(email.getText());
	}

	public void editPhoneNum (MouseEvent mouseEvent) {
		phoneNumberTextField.setVisible(true);
		phoneNumberTextField.setText(phoneNumber.getText());
	}

	public void confirmAllEdits (ActionEvent actionEvent) {
		// do all the below for first name, last name, etc.
//
//		String newVal = "", field = "";
//
//		switch (((Label) actionEvent.getSource()).getOnMouseClicked()) {
//			// TODO: initialize newVal and field here
//		}
//
//		try {
//			AccountController.getInstance().editAccField(field, newVal);
//		} catch (MainController.InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (AccountController.AccountWithUsernameAlreadyExistsException e) {
//			e.printStackTrace();
//		}

		updateFieldViews();
	}

	public void uploadPfp (MouseEvent mouseEvent) {
		// TODO: 1/12/2021 AD
	}
}
