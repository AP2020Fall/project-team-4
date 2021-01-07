package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenuController implements Initializable {
	public static Stage stage;
	public StackPane pwStackPane;
	public ImageView showPwOrNot;
	public PasswordField pwFieldpwHidden;
	public TextField pwFieldpwShown, usernameTxtFld;
	public Label usernameError, AdminGamerTitle, passwordError;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		AdminGamerTitle.setText(((Admin.adminHasBeenCreated() ? "gamer" : "admin") + " sign-up").toUpperCase());
		RegisterFormController.adjustWidthBasedOnTextLength(passwordError);
		RegisterFormController.adjustWidthBasedOnTextLength(usernameError);
		RegisterFormController.adjustWidthBasedOnTextLength(AdminGamerTitle);

		pwFieldpwShown = (TextField) pwStackPane.getChildren().get(0);
		pwFieldpwHidden = (PasswordField) pwStackPane.getChildren().get(1);
		showPwOrNot = (ImageView) pwStackPane.getChildren().get(2);

		Image showImg = new Image("https://cdn1.iconfinder.com/data/icons/essentials-pack/96/show_view_eye_visible_on-256.png"),
				hideImg = new Image("https://cdn1.iconfinder.com/data/icons/essentials-pack/96/hidden_invisible_hide_eye_private-256.png");

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
		stage.setOnCloseRequest(e -> stage = null);
	}

	public void signUp (ActionEvent actionEvent) {
		Stage stage = new Stage();
		try {
			stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/RegisterForm.fxml").toURI().toURL()),
					"Register Form",
					true,
					MainController.getInstance().getPrimaryStage()
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String password = (showPwOrNot.getImage().getUrl().contains("invisible") ? pwFieldpwShown : pwFieldpwHidden).getText();
		try {
			AccountController.getInstance().register(null, usernameTxtFld.getText(), password, "", "", "", "", 0);
		} catch (AccountController.AccountWithUsernameAlreadyExistsException e) {
			usernameError.setText(e.getMessage());
			return;
		} catch (MainController.InvalidFormatException e) {
			if (e.getMessage().toLowerCase().startsWith("username")) {
				usernameError.setText(e.getMessage());
				return;
			}
			if (e.getMessage().toLowerCase().startsWith("password")) {
				passwordError.setText(e.getMessage());
				return;
			}
		}

		RegisterFormController.setStage(stage);
		RegisterFormController.setUsername(usernameTxtFld.getText());
		RegisterFormController.setPassword(password);
		stage.show();
	}

	public static void setStage (Stage stage) {
		RegisterMenuController.stage = stage;
	}

	public static Stage getStage () {
		return stage;
	}
}
