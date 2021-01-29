package Controller.Menus;

import Controller.MainController;
import Model.AccountRelated.Gamer;
import Model.GameRelated.GameLog;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendProfileController implements Initializable {
	private static Gamer frnd;
	public GridPane friendProfile;
	public HBox faveGames;
	public Label username, platoAge, fullName;
	public Label reversiDraws, reversiScore, reversiWins, reversiLosses;
	public Label battleseaScore, battleseaWins, battleseaLosses, battleseaDraws;
	public ImageView pfp;

	public static Gamer getFrnd () {
		return frnd;
	}

	public static void setFrnd (Gamer frnd) {
		FriendProfileController.frnd = frnd;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		pfp.setImage(new Image(frnd.getPfpUrl()));

		username.setText(frnd.getUsername());

		platoAge.setText(frnd.getDaysSinceRegistration() + "d");

		fullName.setText(frnd.getFirstName() + " " + frnd.getLastName());

		reversiScore.setText(String.valueOf(GameLog.getPoints(frnd, "Reversi")));
		reversiWins.setText(String.valueOf(GameLog.getWinCount(frnd, "Reversi")));
		reversiDraws.setText(String.valueOf(GameLog.getDrawCount(frnd, "Reversi")));
		reversiLosses.setText(String.valueOf(GameLog.getLossCount(frnd, "Reversi")));

		battleseaScore.setText(String.valueOf(GameLog.getPoints(frnd, "BattleSea")));
		battleseaWins.setText(String.valueOf(GameLog.getWinCount(frnd, "BattleSea")));
		battleseaDraws.setText(String.valueOf(GameLog.getDrawCount(frnd, "BattleSea")));
		battleseaLosses.setText(String.valueOf(GameLog.getLossCount(frnd, "BattleSea")));

		if (frnd.getFaveGames().contains("Reversi"))
			faveGames.getChildren().add(new ImageView(){{
				setImage(new Image(String.valueOf(MainController.setImageFromFile("src/com/Resources/Images/reversiIcon.png"))));
				setFitHeight(75);
				setPickOnBounds(true);
				setPreserveRatio(true);
			}});

		if (frnd.getFaveGames().contains("BattleSea"))
			faveGames.getChildren().add(new ImageView(){{
				setImage(new Image(String.valueOf(MainController.setImageFromFile("src/com/Resources/Images/battleseaIcon.png"))));
				setFitHeight(75);
				setPickOnBounds(true);
				setPreserveRatio(true);
			}});
	}
}
