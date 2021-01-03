package Controller.Menus;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminRegisterMenuController implements Initializable {
	public StackPane pwStackPane;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		Image showImg = new Image("https://cdn1.iconfinder.com/data/icons/essentials-pack/96/show_view_eye_visible_on-256.png"),
				hideImg = new Image("https://cdn1.iconfinder.com/data/icons/essentials-pack/96/hidden_invisible_hide_eye_private-256.png");
		ImageView showPwOrNot = (ImageView) pwStackPane.getChildren().get(2);
		TextField pwFieldpwShown = (TextField) pwStackPane.getChildren().get(0);
		PasswordField pwFieldpwHidden = (PasswordField) pwStackPane.getChildren().get(1);

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

		showPwOrNot.setOnMouseEntered(mouseEvent -> showPwOrNot.setOpacity(0.75));
		showPwOrNot.setOnMouseExited(mouseEvent -> showPwOrNot.setOpacity(1));

		showPwOrNot.setFitHeight(25);
		showPwOrNot.setFitWidth(25);
		showPwOrNot.setSmooth(true);
		showPwOrNot.toFront();
	}
}
