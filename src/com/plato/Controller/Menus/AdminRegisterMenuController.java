package Controller.Menus;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminRegisterMenuController implements Initializable {
	public ImageView showPwOrNot;
	public PasswordField pwFieldpwHidden;
	public TextField pwFieldpwShown;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		Image showImg = new Image("https://www.flaticon.com/svg/static/icons/svg/565/565654.svg"),
				hideImg = new Image("https://www.flaticon.com/svg/static/icons/svg/565/565655.svg");
		showPwOrNot.setImage(showImg);
		pwFieldpwShown.setVisible(false);
		pwFieldpwHidden.setVisible(true);
		
		showPwOrNot.setOnMouseClicked(mouseEvent -> {
			if (showPwOrNot.getImage().getUrl().equals(showImg.getUrl())) {
				showPwOrNot.setImage(hideImg);
				pwFieldpwShown.setVisible(false);
				pwFieldpwHidden.setVisible(true);
				pwFieldpwHidden.setText(pwFieldpwShown.getText());
			}
			else {
				showPwOrNot.setImage(showImg);
				pwFieldpwHidden.setVisible(false);
				pwFieldpwShown.setVisible(true);
				pwFieldpwShown.setText(pwFieldpwHidden.getText());
			}
		});
		showPwOrNot.setFitHeight(25);
		showPwOrNot.setFitWidth(25);
		showPwOrNot.setVisible(true);
	}
}
