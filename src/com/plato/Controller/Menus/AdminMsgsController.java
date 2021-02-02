package Controller.Menus;

import Controller.MainController;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import Model.AccountRelated.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AdminMsgsController implements Initializable {
	private static Stage stage;
	private static Gamer gamer;
	public ImageView adminPfp;
	public ListView<GridPane> msgList;
	private ActionEvent actionEvent;
	private MouseEvent mouseEvent;



	public AdminMsgsController() {
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

	public static void setStage (Stage stage) {
		AdminMsgsController.stage = stage;
		AdminMsgsController.stage.setOnCloseRequest(e -> {
			AdminMsgsController.stage = null;
			gamer = null;
		});
	}

	public static void setGamer (Gamer gamer) {
		AdminMsgsController.gamer = gamer;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		adminPfp.setImage(new Image(Admin.getAdmin().getPfpUrl()));
		msgList.getItems().clear();

		LinkedList<Message> messagesToGamer = Message.getMessagesToGamer(gamer);
		for (int i = 0, messagesToGamerSize = messagesToGamer.size(); i < messagesToGamerSize; i++)
			try {
				Message message = messagesToGamer.get(i);
				MessageTemplateController.setMessage(message, (i == 0 || !messagesToGamer.get(i - 1).getDateTime().toLocalDate().equals(message.getDateTime().toLocalDate())));
				msgList.getItems().add(
						FXMLLoader.load(new File("src/com/plato/View/Menus/MessageTemplate.fxml").toURI().toURL())
				);
				msgList.getItems().get(msgList.getItems().size() - 1).setLayoutX(25);
			} catch (IOException e) { e.printStackTrace(); }
	}

	public void closeStage () {
		stage.close();
	}

	public void closeStageWrite(ActionEvent actionEvent)
	{
		setActionEvent(actionEvent);
		MainController.write("AdminMsgs.closeStage");
	}

}
