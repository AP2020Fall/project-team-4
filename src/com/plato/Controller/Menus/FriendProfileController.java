package Controller.Menus;

import Model.AccountRelated.Gamer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendProfileController implements Initializable {
	private static Gamer gamer;

	public static Gamer getGamer () {
		return gamer;
	}

	public static void setGamer (Gamer gamer) {
		FriendProfileController.gamer = gamer;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

	}
}
