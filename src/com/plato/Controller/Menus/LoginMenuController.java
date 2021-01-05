package Controller.Menus;

import Controller.MainController;
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

	public static void setStage (Stage stage) {
		LoginMenuController.stage = stage;
		stage.setOnCloseRequest(windowEvent -> LoginMenuController.stage = null);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		RegisterFormController.adjustWidthBasedOnTextLength(passwordError);
		RegisterFormController.adjustWidthBasedOnTextLength(usernameError);

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
		delAccLbl.setOnMouseEntered(mouseEvent -> delAccLbl.setOpacity(0.8));
		delAccLbl.setOnMouseExited(mouseEvent -> delAccLbl.setOpacity(1));
		sgnUpLbl.setOnMouseEntered(mouseEvent -> sgnUpLbl.setOpacity(0.8));
		sgnUpLbl.setOnMouseExited(mouseEvent -> sgnUpLbl.setOpacity(1));

		showPwOrNot.resize(25, 25);
		showPwOrNot.setSmooth(true);
		showPwOrNot.toFront();
	}

	public void login (ActionEvent actionEvent) {
		// TODO: 1/4/2021 AD
	}

	public void signUp (MouseEvent mouseEvent) {
		stage.close();
		stage = null;
		try {
			MainController.getInstance().createAndReturnNewStage(FXMLLoader.load(new File("src/com/plato/View/Menus/RegisterMenu.fxml").toURI().toURL()),
					"Register Menu",
					true,
					stage
			).show();
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
