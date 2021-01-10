package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GamesMenuController implements Initializable

{
	private static Stage stage;
	private static boolean isForFaveGames;
	public Button battlseaButton;
	public Button reversiButton;
	public AnchorPane anchorPane;


	public static void setIsForFaveGames (boolean forFaveGames)
	{ // todo when fxml is called this method should be called
		isForFaveGames = forFaveGames;
	}





	public static void setStage (Stage stage)
	{
		GamesMenuController.stage = stage;
		GamesMenuController.stage.setOnCloseRequest(e -> GamesMenuController.stage = null);
	}


	public void battleSeaMainMenu (ActionEvent actionEvent)


	{
		try

		{
			GameMenuController.setGameName("BattleSea");
			Stage battleSeaMainMenu = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameMenu.fxml").toURI().toURL()),
					"BattleSea Main Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			GameMenuController.setStage(battleSeaMainMenu);
			battleSeaMainMenu.show();
			stage.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}


	public void closeStage (ActionEvent actionEvent) {
		stage.close();

	}

	public void reversiMainMenu(ActionEvent actionEvent)

	{

		try {

			GameMenuController.setGameName("Reversi");
			Stage reversiMainMenu = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameMenu.fxml").toURI().toURL()),
					"Reversi Main Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);

			GameMenuController.setStage(reversiMainMenu);

			reversiMainMenu.show();

			stage.close();

		}
		catch (IOException e)

		{
			e.printStackTrace();

		}

	}

	@Override

	public void initialize(URL location, ResourceBundle resources)
	{

		Gamer currentLoggedIn = ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn());

		if (isForFaveGames)
		{


			if (!currentLoggedIn.getFaveGames().contains("BattleSea"))
				anchorPane.getChildren().remove(battlseaButton);


			if (!currentLoggedIn.getFaveGames().contains("Reversi"))
				anchorPane.getChildren().remove(reversiButton);

		}
	}
}