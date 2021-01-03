package Controller.Menus;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminRegisterMenuController implements Initializable {
	public ImageView showPwOrNot;
	public PasswordField pwFieldpwHidden;
	public TextField pwFieldpwShown;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		Image showImg = new Image(String.valueOf(new File("src/com/Resources/ShowPW.png"))),
				hideImg = new Image(String.valueOf(new File("src/com/Resources/DontShowPW.png")));
		showPwOrNot.setImage(hideImg);
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
	}
}
