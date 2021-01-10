package Controller.Menus;


import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AccountPageController implements Initializable
{

	private static boolean gamerOrAdmin;
	public ImageView pfp;


	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		AccountPageController.gamerOrAdmin = gamerOrAdmin;
	}


	@Override
	public void initialize (URL location, ResourceBundle resources)
	{

		Account currentLoggedIn = AccountController.getInstance().getCurrentAccLoggedIn();

		pfp.setImage(new Image(currentLoggedIn.getPfpUrl()));
	}


	public void logout (ActionEvent actionEvent)
	{
		AccountController.getInstance().logout();



		try {

			MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/LoginMenu.fxml").toURI().toURL()),
					"Login Menu",
					false,
					null
			)
					.show();


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
