package Controller.Menus;

import Model.AccountRelated.AdminGameReco;
import Model.AccountRelated.Gamer;
import Controller.Client;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileForAdminController implements Initializable {
	private static Gamer gamer;
	public CheckBox doRecoReversi, doRecoBattleSea;
	public Label username, email, messageError;
	public ImageView pfp;
	public TextArea msg;
	public GridPane msgGrdPn;
	public Button backBtn, sendBtn;
	private static UserProfileForAdminController userProfileForAdminController;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;

	public static UserProfileForAdminController getInstance(){
		if(userProfileForAdminController == null)
			userProfileForAdminController = new UserProfileForAdminController();
		return userProfileForAdminController;
	}

	public static void setGamer (Gamer gamer) {
		UserProfileForAdminController.gamer = gamer;
	}

	public static Gamer getGamer() {
		return gamer;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		pfp.setImage(new Image(gamer.getPfpUrl()));

		username.setText(gamer.getUsername());

		email.setText(gamer.getEmail());

		doRecoReversi.setSelected(AdminGameReco.recommendationExists(gamer, "Reversi"));
		doRecoBattleSea.setSelected(AdminGameReco.recommendationExists(gamer, "BattleSea"));

		doRecoReversi.setOnAction(e -> {
			if (doRecoReversi.isSelected())
				AdminGameReco.addReco("Reversi", gamer);
			else
				AdminGameReco.removeReco("Reversi", gamer);
		});

		doRecoBattleSea.setOnAction(e -> {
			if (doRecoBattleSea.isSelected())
				AdminGameReco.addReco("BattleSea", gamer);
			else
				AdminGameReco.removeReco("BattleSea", gamer);
		});
	}

	public void sendMsg (ActionEvent actionEvent) {
		msgGrdPn.setVisible(true);
	}

	public void sendMessageDone (ActionEvent actionEvent) {
		try {
			dataOutputStream.writeUTF("sendMsg_" + msg.getText());
			dataOutputStream.flush();
			//MessageController.getClient().sendMsg(gamer, msg.getText());
			cancelSendingMsg(actionEvent);
		} catch (IOException e) {
			messageError.setText(e.getMessage());
		}
	}

	public void cancelSendingMsg (ActionEvent actionEvent) {
		msgGrdPn.setVisible(false);
		msg.setText("");
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(1);
	}
}
