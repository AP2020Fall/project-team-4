package Controller.Menus;


import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.GameLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
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
	public GridPane mainGridPane;
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private static Socket socket;

	public AccountPageController() {
		this.actionEvent = null;
		this.mouseEvent = null;
	}




	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		AccountPageController.gamerOrAdmin = gamerOrAdmin;
	}

	@Override

	public void initialize (URL location, ResourceBundle resources) {
		try {
			dataOutputStream.writeUTF("getCurrentAccLoggedIn");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		}
	}

	public void logout () throws IOException {
		dataOutputStream.writeUTF("logOut");
		dataOutputStream.flush();
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
	public void logoutWrite(MouseEvent actionEvent) {

		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.logout");
	}

	public void uploadPfp () {
		MainController.openUploadPfpWindow(MainController.getInstance().getPrimaryStage(), pfp);
	}
	public void uploadPfpWrite(MouseEvent mouseEvent) {setMouseEvent(mouseEvent);
		MainController.write("AccountPage.uploadPfp");
	}
	public void editPassword () {
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
	public void editPasswordWrite(MouseEvent actionEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.editPassword");
	}
	public void displayPersonalInfo () {
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
	public void displayPersonalInfoWrite(MouseEvent actionEvent) {
		MainController.write("AccountPage.displayPersonalInfo");
	}
	public void changeDropDownMenuVisibility () {
		dropDownMenu.setVisible(!dropDownMenu.isVisible());
	}
	public void changeDropDownMenuVisibilityWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.changeDropDownMenuVisibility");
	}
	public void mouseIsOut () {
		if (getMouseEvent().getSource() instanceof Label)
			((Label) getMouseEvent().getSource()).setOpacity(1);

		else if (getMouseEvent().getSource() instanceof ImageView)
			((ImageView) getMouseEvent().getSource()).setOpacity(1);
	}
	public void mouseIsOutWrite() {
		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.mouseIsOut");
	}
	public void mouseIsOver () {
		if (getMouseEvent().getSource() instanceof Label)
			((Label) getMouseEvent().getSource()).setOpacity(0.8);

		else if (getMouseEvent().getSource() instanceof ImageView)
			((ImageView) getMouseEvent().getSource()).setOpacity(0.8);
	}
	public void mouseIsOverWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.mouseIsOver");
	}

	public void openReversiMainMenu () {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void openReversiMainMenuWrite(MouseEvent mouseEvent) {
		MainController.write("AccountPage.openReversiMainMenu");
	}
	public void openBattleSeaMainMenu () {
		try {
			GameMenuController.setGameName("BattleSea");
			Stage battleSeaMainMenu = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/GameMenu.fxml").toURI().toURL()),
					"BattleSea Main Menu",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			GameMenuController.setStage(battleSeaMainMenu);
			battleSeaMainMenu.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}public void openBattleSeaMainMenuWrite(MouseEvent mouseEvent) {
		setMouseEvent(mouseEvent);
		MainController.write("AccountPage.openBattleSeaMainMenu");
	}
}
