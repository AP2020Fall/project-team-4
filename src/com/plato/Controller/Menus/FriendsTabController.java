package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.GamerController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class FriendsTabController implements Initializable {
	public GridPane frndProfile;
	public ListView<GridPane> frndsList;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		try {
			updateFrndsList(new ActionEvent());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateFrndsList (ActionEvent actionEvent) throws IOException {
		frndsList.getItems().clear();
//		dataOutputStream.writeUTF("getCurrentAccLoggedIn");
//		dataOutputStream.flush();
		Gamer currentAccLoggedIn = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		for (String frndUN : currentAccLoggedIn.getFrnds()) {
			Gamer frndAcc = (Gamer) Account.getAccount(frndUN);

			Circle circle = new Circle(40);

			frndsList.getItems().add(new GridPane() { {
				getRowConstraints().add(new RowConstraints() {{
					setMinHeight(circle.getRadius() * 2);
					setMaxHeight(circle.getRadius() * 2);
				}});

				getColumnConstraints().add(new ColumnConstraints() {{
					setMinWidth(circle.getRadius() * 2);
					setMaxWidth(circle.getRadius() * 2);
				}});

				ImageView pfp = new ImageView() {{
					setImage(new Image(frndAcc.getPfpUrl()));
					setSmooth(true);
					setPreserveRatio(true);
					setFitHeight(circle.getRadius() * 2);
					setClip(circle);
					setPadding(new Insets(5, 5, 5, 5));
				}};
				Label username = new Label() {{
					setText(frndAcc.getUsername());
					setFont(Font.font("Arial", FontWeight.BOLD, 20));
					setTextAlignment(TextAlignment.LEFT);
					setPadding(new Insets(0, 10, 0, 10));
				}};
				Button unfrndBtn = new Button() {{
					setMinSize(50, 50);
					setMaxSize(50, 50);
					setStyle("-fx-background-image: url('https://i.imgur.com/iZoXnCW.png?1');" +
							" -fx-background-size: 50;" +
							" -fx-background-radius: 25;" +
							" -fx-background-color: transparent;");
					setFont(Font.font("Arial", 14));
					setTextAlignment(TextAlignment.CENTER);
					setPadding(new Insets(5, 5, 5, 5));
					setOnAction(e -> {
						try {
							unfriend(username.getText());
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					});
					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
				}};

				circle.setCenterX(pfp.getFitHeight() / 2);
				circle.setCenterY(pfp.getFitHeight() / 2);

				add(pfp, 0, 0);
				add(username, 1, 0);
				add(unfrndBtn, 2, 0);

				setOnMouseClicked(e -> displayFriendProfile(((Label) getChildren().get(1)).getText()));
				setOnMouseEntered(e -> setOpacity(0.8));
				setOnMouseExited(e -> setOpacity(1));
			}});
		}
	}

	private void unfriend (String frndUN) throws IOException {
		dataOutputStream.writeUTF("removeFriend_"+frndUN);
		dataOutputStream.flush();
		//GamerController.getInstance().removeFriend(frndUN);

		if (FriendProfileController.getFrnd() != null && FriendProfileController.getFrnd().getUsername().equals(frndUN))
			frndProfile.getChildren().clear();

		frndsList.getItems()
				.removeIf(gridPane -> ((Label) gridPane.getChildren().get(1)).getText().equals(frndUN));
	}

	private void displayFriendProfile (String frndUn) {
		try {
			frndProfile.getChildren().clear();
			FriendProfileController.setFrnd((Gamer) Account.getAccount(frndUn));
			frndProfile.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/FriendProfile.fxml").toURI().toURL()));
			GridPane.setValignment(frndProfile.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(frndProfile.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayFrndRequests (ActionEvent actionEvent) {
		Stage stage;
		try {
			stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/FriendRequestManagementPage.fxml").toURI().toURL()),
					"Friend Requests",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			FriendRequestManagementPageController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}