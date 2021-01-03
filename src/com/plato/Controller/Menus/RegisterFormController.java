package Controller.Menus;

import Model.AccountRelated.Admin;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {
	private boolean isForAdmin;


	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		isForAdmin = !Admin.adminHasBeenCreated();
		System.out.println("RegisterFormController.initialize");
	}
}
