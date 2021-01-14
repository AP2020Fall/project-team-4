package Controller.Menus;

import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import Model.AccountRelated.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
		new Message(gamer, "Hellooooo");
		new Message(gamer, "Howwww the fuck are you ");
		new Message(gamer, "eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy eyyyyyyyyyyyyy ");
		adminPfp.setImage(new Image(Admin.getAdmin().getPfpUrl()));
		msgList.getItems().clear();

		//					msgList.getItems().add(new GridPane() {{
		//						getChildren().add(new ImageView() {{
		//							setImage(new Image("https://i.imgur.com/4l0anrI.png?5"));
		//							setFitWidth(20);
		//							setPreserveRatio(true);
		//							setSmooth(true);
		//							setRowIndex(this, 0);
		//							setColumnIndex(this, 0);
		//						}});
		//
		//						getChildren().add(new Label() {{
		//							setText(message.getDateTime().format(DateTimeFormatter.ofPattern("H:mm a")));
		//							System.out.println("getText() = " + getText());
		//
		//							setFont(Font.font("Chalkboard", FontPosture.ITALIC, 10));
		//							setTextFill(Color.WHITE);
		//							setTextAlignment(TextAlignment.CENTER);
		//
		//							setMargin(this, new Insets(13, 10,0,3));
		//
		//							setValignment(this, VPos.TOP);
		//							setHalignment(this, HPos.RIGHT);
		//
		//							setRowIndex(this, 0);
		//							setColumnIndex(this, 1);
		//							setRowSpan(this, 2);
		//						}});
		//
		//						LinkedList<String> words = new LinkedList<>(Arrays.asList(message.getText().split(" "))),
		//								lines = getLines(words);
		//
		//						getChildren().add(new Label() {{
		//							AtomicReference<String> textLined = new AtomicReference<>("");
		//							lines.forEach(line -> textLined.set(textLined.get() + line + "\n"));
		//
		//							setText(textLined.get());
		//
		//							setFont(Font.font("Chalkboard", FontPosture.ITALIC, 15));
		//							setTextFill(Color.WHITE);
		//							setTextAlignment(TextAlignment.LEFT);
		//							setMaxWidth(300);
		//							setPrefHeight(Region.USE_COMPUTED_SIZE);
		//							setPrefWidth(Region.USE_COMPUTED_SIZE);
		//							setStyle("-fx-background-color: #2d59ff;" +
		//									"  -fx-background-radius: 15;");
		//							setPadding(new Insets(10, 10 + 10, 10, 10 + 10));
		//							setMargin(this, new Insets(10, 10,0,0));
		//							setRowIndex(this, 0);
		//							setColumnIndex(this, 0);
		//							setRowSpan(this, 2);
		//							setColumnSpan(this, 2);
		//						}});
		//						setPadding(new Insets(0, 30, 30, 5));
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

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}
}
