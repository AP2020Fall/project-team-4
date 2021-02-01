package Controller.Menus;

import Controller.AccountRelated.MessageController;
import Controller.MainController;
import Model.AccountRelated.AdminGameReco;
import Model.AccountRelated.Gamer;
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
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;

	public UserProfileForAdminController() {
		this.actionEvent = null;
		this.mouseEvent = null;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public void setMouseEvent(MouseEvent mouseEvent) {
		this.mouseEvent = mouseEvent;
	}

	public static void setGamer (Gamer gamer) {
		UserProfileForAdminController.gamer = gamer;
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
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

	public void sendMsg () {
		msgGrdPn.setVisible(true);
	}

	public void sendMsgWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("UserProfileForAdmin.sendMsg");
	}

	public void sendMessageDone () {
		try {
			MessageController.getInstance().sendMsg(gamer, msg.getText());
			cancelSendingWrite(getActionEvent());
		} catch (MessageController.EmptyMessageException e) {
			messageError.setText(e.getMessage());
		}
	}

	public void sendMessageDoneWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("UserProfileForAdmin.sendMessageDone");
	}

	public void cancelSendingMsg () {
		msgGrdPn.setVisible(false);
		msg.setText("");
	}

	public void cancelSendingWrite(ActionEvent actionEvent){
		setActionEvent(actionEvent);
		MainController.write("UserProfileForAdmin.cancelSending");
	}

	public void mouseIsOver () {
		((Button) getMouseEvent().getSource()).setOpacity(0.8);
	}

	public void mouseIsOverWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("UserProfileForAdmin.mouseIsOver");
	}

	public void mouseIsOut () {
		((Button) getMouseEvent().getSource()).setOpacity(1);
	}

	public void mouseIsOutWrite(MouseEvent mouseEvent){
		setMouseEvent(mouseEvent);
		MainController.write("UserProfileForAdmin.mouseIsOut");
	}
}
