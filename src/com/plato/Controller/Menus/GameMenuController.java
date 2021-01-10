package Controller.Menus;

import javafx.stage.Stage;

public class GameMenuController

{
    private static Stage stage;
    private static String gameName;

    public static void setStage (Stage stage) {
        GameMenuController.stage = stage;
        GameMenuController.stage.setOnCloseRequest(e -> {
            GameMenuController.stage = null;
            gameName = "";
        });
    }

    public static void setGameName (String gameName)
    {

        GameMenuController.gameName = gameName;

    }

}
