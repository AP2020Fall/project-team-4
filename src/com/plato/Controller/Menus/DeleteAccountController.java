package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Controller.Client;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements Initializable {
	private static Stage stage;
	public Label passwordError, usernameError;
	public TextField username;
	public StackPane pwStackPane;
	public ImageView showPwOrNot;
	public PasswordField pwFieldpwHidden;
	public TextField pwFieldpwShown;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;
	private static Socket socket;

	public static void setStage (Stage stage) {
		DeleteAccountController.stage = stage;
		stage.setOnCloseRequest(windowEvent -> DeleteAccountController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		RegisterFormController.adjustWidthBasedOnTextLength(passwordError);
		RegisterFormController.adjustWidthBasedOnTextLength(usernameError);

		pwFieldpwShown = (TextField) pwStackPane.getChildren().get(0);
		pwFieldpwHidden = (PasswordField) pwStackPane.getChildren().get(1);
		showPwOrNot = (ImageView) pwStackPane.getChildren().get(2);

		Image showImg = MainController.getImageFromFile("src/com/Resources/Images/eyeVisible.png"),
				hideImg = MainController.getImageFromFile("src/com/Resources/Images/hiddenEye.png");

		showPwOrNot.setImage(showImg);
		pwFieldpwHidden.toFront();

		showPwOrNot.setOnMouseClicked(mouseEvent -> {
			if (showPwOrNot.getImage().getUrl().equals(showImg.getUrl())) {
				showPwOrNot.setImage(hideImg);
				pwFieldpwShown.setText(pwFieldpwHidden.getText());
				pwFieldpwShown.toFront();
			}
			else {
				showPwOrNot.setImage(showImg);
				pwFieldpwHidden.setText(pwFieldpwShown.getText());
				pwFieldpwHidden.toFront();
			}
			showPwOrNot.toFront();
		});

		showPwOrNot.setOnMouseEntered(mouseEvent -> showPwOrNot.setOpacity(0.8));
		showPwOrNot.setOnMouseExited(mouseEvent -> showPwOrNot.setOpacity(1));

		showPwOrNot.resize(25, 25);
		showPwOrNot.setSmooth(true);
		showPwOrNot.toFront();
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public void removeAccount (ActionEvent actionEvent) throws IOException {
//		Stage stage = new Stage();
//		try {
//			stage = MainController.getClient().createAndReturnNewStage(
//					FXMLLoader.load(new File("src/com/plato/View/Menus/LoginMenu.fxml").toURI().toURL()),
//					"Login Menu",
//					true,
//					MainController.getClient().getPrimaryStage()
//			);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		String password = (showPwOrNot.getImage().getUrl().contains("invisible") ? pwFieldpwShown : pwFieldpwHidden).getText();

		try {
			AccountController.getInstance().deleteAccount(username.getText(), password);
		} catch (MainController.InvalidFormatException e) {
			if (e.getMessage().toLowerCase().startsWith("username")) {
				usernameError.setText(e.getMessage());
				return;
			}
			if (e.getMessage().toLowerCase().startsWith("password")) {
				passwordError.setText(e.getMessage());
				return;
			}
		} catch (AccountController.NoAccountExistsWithUsernameException | AccountController.AdminAccountCantBeDeletedException e) {
			usernameError.setText(e.getMessage());
			return;
		} catch (AccountController.PaswordIncorrectException e) {
			passwordError.setText(e.getMessage());
			return;
		}

		dataOutputStream.writeUTF("deleteAccount_" + username.getText());
		dataOutputStream.flush();
		//AccountController.getClient().deleteAccount(username.getText(), password);

		DeleteAccountController.stage.close();
//		stage.show();
	}

}
