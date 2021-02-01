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
import javafx.scene.input.MouseEvent;
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
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;

	public RegisterMenuController() {
		this.actionEvent = null;
		this.mouseEvent = null;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public static Stage getStage () {
		return stage;
	}

	public static void setStage (Stage stage) {
		RegisterMenuController.stage = stage;
		RegisterMenuController.stage.setOnCloseRequest(e -> RegisterMenuController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		AdminGamerTitle.setText(((Admin.adminHasBeenCreated() ? "gamer" : "admin") + " sign-up").toUpperCase());
		RegisterFormController.adjustWidthBasedOnTextLength(passwordError);
		RegisterFormController.adjustWidthBasedOnTextLength(usernameError);
		RegisterFormController.adjustWidthBasedOnTextLength(AdminGamerTitle);

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

	public void signUp () {

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
		try {
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/RegisterForm.fxml").toURI().toURL()),
					"Register Form",
					true,
					MainController.getInstance().getPrimaryStage()
			);

			RegisterFormController.setStage(stage);
			RegisterFormController.setUsername(usernameTxtFld.getText());
			RegisterFormController.setPassword(password);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void signUpWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("RegisterMenu.signUp");
	}
}
