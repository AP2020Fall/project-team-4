package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
	private static Stage stage;
	public StackPane pwStackPane;
	public ImageView showPwOrNot;
	public PasswordField pwFieldpwHidden;
	public TextField pwFieldpwShown;
	public Label delAccLbl, sgnUpLbl, usernameError, passwordError;
	public CheckBox rememberMe;
	public TextField username;

	public static void setStage (Stage stage) {
		LoginMenuController.stage = stage;
		LoginMenuController.stage.setOnCloseRequest(e -> LoginMenuController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
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
		delAccLbl.setOnMouseEntered(mouseEvent -> delAccLbl.setOpacity(0.8));
		delAccLbl.setOnMouseExited(mouseEvent -> delAccLbl.setOpacity(1));
		sgnUpLbl.setOnMouseEntered(mouseEvent -> sgnUpLbl.setOpacity(0.8));
		sgnUpLbl.setOnMouseExited(mouseEvent -> sgnUpLbl.setOpacity(1));

		showPwOrNot.resize(25, 25);
		showPwOrNot.setSmooth(true);
		showPwOrNot.toFront();
	}

	public void login (ActionEvent actionEvent) {
		try {
			String password = pwStackPane.getChildren().get(1) instanceof PasswordField ? ((PasswordField) pwStackPane.getChildren().get(1)).getText() : ((TextField) pwStackPane.getChildren().get(1)).getText();

			AccountController.getInstance().login(username.getText(), password, rememberMe.isSelected());

		} catch (MainController.InvalidFormatException | AccountController.NoAccountExistsWithUsernameException e) {
			usernameError.setText(e.getMessage());
			return;
		} catch (AccountController.PaswordIncorrectException e) {
			passwordError.setText(e.getMessage());
			return;
		}

		Account account = AccountController.getInstance().getCurrentAccLoggedIn();

		stage.close();

		try {
			MainMenuController.setGamerOrAdmin(account instanceof Gamer);
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/MainMenu.fxml").toURI().toURL()),
					"Main Menu", false, null);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void signUp (MouseEvent mouseEvent) {
		stage.close();
		try {
			Stage regMenuStage = MainController.getInstance().createAndReturnNewStage(FXMLLoader.load(new File("src/com/plato/View/Menus/RegisterMenu.fxml").toURI().toURL()),
					"Register Menu",
					true,
					stage
			);
			RegisterMenuController.setStage(regMenuStage);
			regMenuStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteAccount (MouseEvent mouseEvent) {
		try {
			Stage deleteAccStage = MainController.getInstance().createAndReturnNewStage(FXMLLoader.load(new File("src/com/plato/View/Menus/DeleteAccount.fxml").toURI().toURL()),
					"Delete Account",
					true,
					stage
			);
			deleteAccStage.show();
			DeleteAccountController.setStage(deleteAccStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
