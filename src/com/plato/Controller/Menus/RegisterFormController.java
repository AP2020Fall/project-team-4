package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {
	private static Stage stage;
	public ImageView pfp, coinImg, uploadPfp;
	public SplitMenuButton coinMenu;
	public TextField firstName, lastName, email, phoneNum;
	public Label firstNameError, lastNameError, emailError, phoneNumError;
	public HBox coinHBox;
	public GridPane mainGridPane;
	private boolean isForAdmin;
	private static String username, password;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		isForAdmin = !Admin.adminHasBeenCreated();
		adjustWidthBasedOnTextLength(emailError);
		adjustWidthBasedOnTextLength(phoneNumError);
		adjustWidthBasedOnTextLength(lastNameError);
		adjustWidthBasedOnTextLength(firstNameError);

		for (int i = 0; i <= 60; i++) {
			coinMenu.getItems().add(new MenuItem(String.valueOf(i)));
			coinMenu.getItems().get(i).setOnAction(actionEvent -> coinMenu.setText(((MenuItem) actionEvent.getSource()).getText()));
		}
		coinImg.setOnMouseEntered(mouseEvent -> coinImg.setOpacity(0.8));
		coinImg.setOnMouseExited(mouseEvent -> coinImg.setOpacity(1));
		uploadPfp.setOnMouseEntered(mouseEvent -> uploadPfp.setOpacity(0.8));
		uploadPfp.setOnMouseExited(mouseEvent -> uploadPfp.setOpacity(1));

		if (isForAdmin) {
			mainGridPane.getChildren().remove(pfp);
			mainGridPane.getChildren().remove(uploadPfp);
			mainGridPane.getChildren().remove(coinHBox);
		}
	}

	public void uploadPfp (MouseEvent mouseEvent) {
		System.out.println("RegisterFormController.uploadPfp");
		MainController.openUploadPfpWindow(stage, pfp);
	}

	public void signUp (ActionEvent actionEvent) {

		double money;
		try {
			money = Double.parseDouble(coinMenu.getText());
		} catch (NumberFormatException e) { money = 0; }

		try {
			AccountController.getInstance().register(pfp.getImage(), username, password, firstName.getText(), lastName.getText(), email.getText(), phoneNum.getText(), money);
		} catch (AccountController.AccountWithUsernameAlreadyExistsException e) {
			return;
		} catch (MainController.InvalidFormatException e) {
			if (e.getMessage().toLowerCase().startsWith("first name")) {
				firstNameError.setText(e.getMessage());
			}
			if (e.getMessage().toLowerCase().startsWith("last name")) {
				lastNameError.setText(e.getMessage());
			}
			if (e.getMessage().toLowerCase().startsWith("email")) {
				emailError.setText(e.getMessage());
			}
			if (e.getMessage().toLowerCase().startsWith("phone number")) {
				phoneNumError.setText(e.getMessage());
			}
			return;
		}
		stage.close();
		RegisterMenuController.getStage().close();

		try {
			Stage loginStage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/LoginMenu.fxml").toURI().toURL()),
					"Login Menu",
					false,
					null
			);
			LoginMenuController.setStage(loginStage);
			loginStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setPassword (String password) {
		RegisterFormController.password = password;
	}

	public static void setUsername (String username) {
		RegisterFormController.username = username;
	}

	public static void setStage (Stage stage) {
		RegisterFormController.stage = stage;
		stage.setOnCloseRequest(windowEvent -> {
			username = null;
			password = null;
			RegisterFormController.stage = null;
		});
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	public static void adjustWidthBasedOnTextLength(Label label) {
		label.textProperty().addListener((observable, oldValue, newValue) -> {
			label.setPrefWidth(label.getText().length() * 7); // why 7? Totally trial number.
		});
	}
}
