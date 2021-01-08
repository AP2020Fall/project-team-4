package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.GamerController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FriendsTabController implements Initializable {
	public GridPane frndProfile;
	public ListView<GridPane> frndsList;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		Gamer currentAccLoggedIn = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		for (String frndUN : currentAccLoggedIn.getFrnds()) {

			Gamer frndAcc = (Gamer) Account.getAccount(frndUN);

			frndsList.getItems().add(new GridPane() {{

				Circle circle = new Circle(50);

				frndsList.getItems().add(new GridPane() {{
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
						setText("unfriend");
						setFont(Font.font("Arial", 14));
						setTextAlignment(TextAlignment.CENTER);
						setPadding(new Insets(5, 5, 5, 5));
						setOnAction(e -> unfriend(frndUN));
						setOnMouseEntered(e -> setOpacity(0.8));
						setOnMouseExited(e -> setOpacity(1));
					}};

					circle.setCenterX(pfp.getFitHeight() / 2);
					circle.setCenterY(pfp.getFitHeight() / 2);

					add(pfp, 0, 0);
					add(username, 1, 0);
					add(unfrndBtn, 2, 0);

					setOnMouseClicked(e -> displayGamerProfile(((Label) getChildren().get(1)).getText()));
					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
				}});
			}});
		}
	}

	private void unfriend (String frndUN) {
		GamerController.getInstance().removeFriend(frndUN);
		if (FriendProfileController.getFrnd().getUsername().equals(frndUN))
			frndProfile.getChildren().clear();

		frndsList.getItems()
				.removeIf(gridPane -> ((Label) gridPane.getChildren().get(1)).getText().equals(frndUN));
	}

	private void displayGamerProfile (String frndUn) {
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
		Stage stage = null;
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