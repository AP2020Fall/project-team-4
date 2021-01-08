package Controller.Menus;

import Controller.AccountRelated.MessageController;
import Model.AccountRelated.AdminGameReco;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileForAdminController implements Initializable {
	private static Gamer gamer;
	public CheckBox doRecoReversi, doRecoBattleSea;
	public Label username, email, messageError;
	public Rectangle pfp;
	public TextArea msg;
	public GridPane msgGrdPn;
	public Button backBtn, sendBtn;

	public static void setGamer (Gamer gamer) {
		UserProfileForAdminController.gamer = gamer;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		Image pfpImage = new Image(gamer.getPfpUrl());
		pfp.setFill(new ImagePattern(pfpImage));
		pfp.setHeight(130);
		pfp.setWidth(130);

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

		backBtn.setOnMouseEntered(e -> backBtn.setOpacity(0.8));
		backBtn.setOnMouseExited(e -> backBtn.setOpacity(1));
		sendBtn.setOnMouseEntered(e -> sendBtn.setOpacity(0.8));
		sendBtn.setOnMouseExited(e -> sendBtn.setOpacity(1));
	}

	public void sendMsg (ActionEvent actionEvent) {
		msgGrdPn.setVisible(true);
	}

	public void sendMessageDone (ActionEvent actionEvent) {
		try {
			MessageController.getInstance().sendMsg(gamer, msg.getText());
			cancelSendingMsg(actionEvent);
		} catch (MessageController.EmptyMessageException e) {
			messageError.setText(e.getMessage());
		}
	}

	public void cancelSendingMsg (ActionEvent actionEvent) {
		msgGrdPn.setVisible(false);
		msg.setText("");
	}
}
