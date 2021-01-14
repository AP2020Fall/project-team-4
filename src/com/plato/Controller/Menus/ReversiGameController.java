package Controller.Menus;

import Controller.GameRelated.GameController;
import Controller.GameRelated.Reversi.ReversiController;
import Model.GameRelated.Reversi.PlayerReversi;
import javafx.fxml.Initializable;
import Model.GameRelated.Reversi.Reversi;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


import java.net.URL;
import java.util.ResourceBundle;

public class ReversiGameController implements Initializable {

    private PlayerReversi player1 , player2;
    public ImageView pfp2, pfp1;
    public Label username2, username1;





    public static void setStage() {
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        player1 = (PlayerReversi) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(0);
        player2 = (PlayerReversi) GameController.getInstance().getCurrentGameInSession().getListOfPlayers().get(1);

        //setting profile pictures
        {
            pfp1.setImage(new Image(player1.getGamer().getPfpUrl()));
            pfp1.setSmooth(true);
            pfp1.setClip(new Circle(pfp1.getBoundsInLocal().getCenterX(), pfp1.getBoundsInLocal().getCenterY(), 50));

            pfp2.setImage(new Image(player2.getGamer().getPfpUrl()));
            pfp2.setSmooth(true);
            pfp2.setClip(new Circle(pfp2.getBoundsInLocal().getCenterX(), pfp2.getBoundsInLocal().getCenterY(), 50));
        }

        // setting the usernames
        {
            username1.setText(player1.getUsername());
            username2.setText(player2.getUsername());
        }
    }
}
