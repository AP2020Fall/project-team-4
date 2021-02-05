package Controller.Menus;

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

public class EditPWController implements Initializable {
	private static Stage stage;
	public StackPane oldPwStackPane, newPwStackPane;
	public Label oldPasswordError;
	public ImageView newShowPwOrNot, oldShowPwOrNot;
	public PasswordField newPwFieldpwHidden, oldPwFieldpwHidden;
	public TextField newPwFieldpwShown, oldPwFieldpwShown;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private static Socket socket;

	public static void setStage (Stage stage) {
		EditPWController.stage = stage;
		EditPWController.stage.setOnCloseRequest(event -> EditPWController.stage = null);
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		// for new password
		{
			newPwFieldpwShown = (TextField) newPwStackPane.getChildren().get(0);
			newPwFieldpwHidden = (PasswordField) newPwStackPane.getChildren().get(1);
			newShowPwOrNot = (ImageView) newPwStackPane.getChildren().get(2);

			Image showImg = MainController.getImageFromFile("src/com/Resources/Images/eyeVisible.png"),
					hideImg = MainController.getImageFromFile("src/com/Resources/Images/hiddenEye.png");

			newShowPwOrNot.setImage(showImg);
			newPwFieldpwHidden.toFront();

			newShowPwOrNot.setOnMouseClicked(mouseEvent -> {
				if (newShowPwOrNot.getImage().getUrl().equals(showImg.getUrl())) {
					newShowPwOrNot.setImage(hideImg);
					newPwFieldpwShown.setText(newPwFieldpwHidden.getText());
					newPwFieldpwShown.toFront();
				}
				else {
					newShowPwOrNot.setImage(showImg);
					newPwFieldpwHidden.setText(newPwFieldpwShown.getText());
					newPwFieldpwHidden.toFront();
				}
				newShowPwOrNot.toFront();
			});
			newShowPwOrNot.setOnMouseEntered(mouseEvent -> newShowPwOrNot.setOpacity(0.8));
			newShowPwOrNot.setOnMouseExited(mouseEvent -> newShowPwOrNot.setOpacity(1));

			newShowPwOrNot.resize(25, 25);
			newShowPwOrNot.setSmooth(true);
			newShowPwOrNot.toFront();
		}

		// for old password
		{
			oldPwFieldpwShown = (TextField) oldPwStackPane.getChildren().get(0);
			oldPwFieldpwHidden = (PasswordField) oldPwStackPane.getChildren().get(1);
			oldShowPwOrNot = (ImageView) oldPwStackPane.getChildren().get(2);

			Image showImg = MainController.getImageFromFile("src/com/Resources/Images/eyeVisible.png"),
					hideImg = MainController.getImageFromFile("src/com/Resources/Images/hiddenEye.png");

			oldShowPwOrNot.setImage(showImg);
			oldPwFieldpwHidden.toFront();

			oldShowPwOrNot.setOnMouseClicked(mouseEvent -> {
				if (oldShowPwOrNot.getImage().getUrl().equals(showImg.getUrl())) {
					oldShowPwOrNot.setImage(hideImg);
					oldPwFieldpwShown.setText(oldPwFieldpwHidden.getText());
					oldPwFieldpwShown.toFront();
				}
				else {
					oldShowPwOrNot.setImage(showImg);
					oldPwFieldpwHidden.setText(oldPwFieldpwShown.getText());
					oldPwFieldpwHidden.toFront();
				}
				oldShowPwOrNot.toFront();
			});

			oldShowPwOrNot.setOnMouseEntered(mouseEvent -> oldShowPwOrNot.setOpacity(0.8));
			oldShowPwOrNot.setOnMouseExited(mouseEvent -> oldShowPwOrNot.setOpacity(1));

			oldShowPwOrNot.resize(25, 25);
			oldShowPwOrNot.setSmooth(true);
			oldShowPwOrNot.toFront();
		}
	}

	public void confirmPasswordEdit (ActionEvent actionEvent) {
		String oldPassword = (oldShowPwOrNot.getImage().getUrl().contains("invisible") ? oldPwFieldpwShown : oldPwFieldpwHidden).getText(),
				newPassword = (newShowPwOrNot.getImage().getUrl().contains("invisible") ? newPwFieldpwShown : newPwFieldpwHidden).getText();

		try {
			dataOutputStream.writeUTF("changePWCommand_"+oldPassword+"_"+newPassword);
			dataOutputStream.flush();
		//	AccountController.getClient().changePWCommand(oldPassword, newPassword);
			closeStage(actionEvent);

		} catch (IOException e) {
			oldPasswordError.setText(e.getMessage());
		}
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

}
