package Controller.Menus;


import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.GameLog;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AccountPageController implements Initializable {

	private static boolean gamerOrAdmin;
	public ImageView pfp;
	public Label username;
	public GridPane dropDownMenu;
	public Label friends, wins, platoAge, coins;
	public ProgressBar progressToNextLevelBattleSea, progressToNextLevelReversi;
	public Label lvlBattleSea, playedBattlesea, pointsBattlesea, winsBattlesea, drawsBattlesea, lossesBattlesea;
	public Label lvlReversi, playedReversi, pointsReversi, winsReversi, drawsReversi, lossesReversi;
	public ImageView uploadPfp;
	public GridPane mainGridPane;
	public ImageView logoutBtn;

	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		AccountPageController.gamerOrAdmin = gamerOrAdmin;
	}

	@Override

	public void initialize (URL location, ResourceBundle resources) {

		Account currentLoggedIn = AccountController.getInstance().getCurrentAccLoggedIn();

		pfp.setImage(new Image(currentLoggedIn.getPfpUrl()));
		username.setText(currentLoggedIn.getUsername());

		// if admin remove everything unnecessary
		if (!gamerOrAdmin) {
			mainGridPane.getChildren().removeIf(node ->
					(GridPane.getRowIndex(node) != null && (GridPane.getRowIndex(node) >= 2 && GridPane.getRowIndex(node) <= 5))
							||
							(node.getId() != null && node.getId().equals("uploadPfp"))
			);

			for (int i = 0; i < 4; i++)
				mainGridPane.getRowConstraints().remove(mainGridPane.getRowCount() - 1);

			mainGridPane.setMaxHeight(55 + 150 + 20 + 30);
			mainGridPane.setMinHeight(mainGridPane.getMaxHeight());
		}
		else {
			Gamer gamer = ((Gamer) currentLoggedIn);

			friends.setText(String.valueOf(gamer.getFrnds().size()));
			wins.setText(String.valueOf(GameLog.getWinCount(gamer, "BattleSea") + GameLog.getWinCount(gamer, "Reversi")));
			platoAge.setText(gamer.getDaysSinceRegistration() + "d");

			progressToNextLevelBattleSea.setProgress(GameLog.getProgressToNextLevel(gamer, "BattleSea"));
			progressToNextLevelReversi.setProgress(GameLog.getProgressToNextLevel(gamer, "Reversi"));

			coins.setText(String.valueOf(gamer.getMoney()));

			lvlBattleSea.setText("Lvl" + GameLog.getLevel(gamer, "BattleSea"));
			lvlReversi.setText("Lvl" + GameLog.getLevel(gamer, "Reversi"));

			playedBattlesea.setText(String.valueOf(GameLog.getPlayedCount(gamer, "BattleSea")));
			pointsBattlesea.setText(String.valueOf(GameLog.getPoints(gamer, "BattleSea")));
			winsBattlesea.setText(String.valueOf(GameLog.getWinCount(gamer, "BattleSea")));
			drawsBattlesea.setText(String.valueOf(GameLog.getDrawCount(gamer, "BattleSea")));
			lossesBattlesea.setText(String.valueOf(GameLog.getLossCount(gamer, "BattleSea")));

			playedReversi.setText(String.valueOf(GameLog.getPlayedCount(gamer, "Reversi")));
			pointsReversi.setText(String.valueOf(GameLog.getPoints(gamer, "Reversi")));
			winsReversi.setText(String.valueOf(GameLog.getWinCount(gamer, "Reversi")));
			drawsReversi.setText(String.valueOf(GameLog.getDrawCount(gamer, "Reversi")));
			lossesReversi.setText(String.valueOf(GameLog.getLossCount(gamer, "Reversi")));

			uploadPfp.setOnMouseEntered(e -> uploadPfp.setOpacity(0.8));
			uploadPfp.setOnMouseExited(e -> uploadPfp.setOpacity(1));
		}
		logoutBtn.setOnMouseEntered(e -> logoutBtn.setOpacity(0.8));
		logoutBtn.setOnMouseExited(e -> logoutBtn.setOpacity(1));
		for (int i = 0; i < 2; i++) {
			Label label = (Label) dropDownMenu.getChildren().get(i);
			label.setOnMouseEntered(e -> label.setOpacity(0.8));
			label.setOnMouseExited(e -> label.setOpacity(1));
		}
	}

	public void logout (MouseEvent actionEvent) {
		AccountController.getInstance().logout();
		try {
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/LoginMenu.fxml").toURI().toURL()),
					"Login Menu",
					false,
					null
			);
			LoginMenuController.setStage(stage);
			stage.setMinWidth(364);
			stage.setMinHeight(271);
			stage.setMaxWidth(stage.getMinWidth());
			stage.setMaxHeight(stage.getMinHeight());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void uploadPfp (MouseEvent mouseEvent) {
		// TODO: 1/11/2021 AD  
	}

	public void editPassword (MouseEvent actionEvent) {
		try {
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/EditPW.fxml").toURI().toURL()),
					"",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			EditPWController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayPersonalInfo (MouseEvent actionEvent) {
		try {
			DisplayPersonalAccInfoController.setAccount(AccountController.getInstance().getCurrentAccLoggedIn());
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/DisplayPersonalAccInfo.fxml").toURI().toURL()),
					"Personal Info",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			DisplayPersonalAccInfoController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDropDownMenu (MouseEvent mouseEvent) {
		dropDownMenu.setVisible(true);
	}
}
