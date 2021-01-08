package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.FriendRequestController;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

import java.net.URL;
import java.util.ResourceBundle;

public class FriendRequestManagementPageController implements Initializable {
	private static Stage stage;
	public ListView<GridPane> frndReqsGottenList, availableForFrndReqList;
	public GridPane sendFrndReqWindow;
	public TextField search;
	public Label clearSearch;
	public Button btn3, btn2, btn1;

	public static void setStage (Stage stage) {
		FriendRequestManagementPageController.stage = stage;
		FriendRequestManagementPageController.stage.setOnCloseRequest(e -> FriendRequestManagementPageController.stage = null);
	}

	public void sendFriendReq (ActionEvent actionEvent) {
		sendFrndReqWindow.setVisible(true);
		updateAvailableGamersList();
	}

	private void updateAvailableGamersList () {
		Gamer currentLoggedIn = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
		availableForFrndReqList.getItems().clear();

		for (Gamer gamer : Gamer.getGamers(Gamer.getAvailableGamersForFrndReq(currentLoggedIn), search.getText())) {
			Circle circle = new Circle(30);

			availableForFrndReqList.getItems().add(new GridPane() {{
				getRowConstraints().add(new RowConstraints() {{
					setMinHeight(circle.getRadius() * 2);
					setMaxHeight(circle.getRadius() * 2);
				}});

				getColumnConstraints().add(new ColumnConstraints() {{
					setMinWidth(circle.getRadius() * 2);
					setMaxWidth(circle.getRadius() * 2);
				}});

				ImageView pfp = new ImageView() {{
					setImage(new Image(gamer.getPfpUrl()));
					setSmooth(true);
					setPreserveRatio(true);
					setFitHeight(circle.getRadius() * 2);
					setClip(circle);
					setPadding(new Insets(5,5,5,5));
				}};
				Label username = new Label() {{
					setText(gamer.getUsername());
					setFont(Font.font("Arial", FontWeight.BOLD, 20));
					setTextAlignment(TextAlignment.LEFT);
					setPadding(new Insets(0, 10, 0, 10));
					setMinWidth(200);
				}};
//				Button declineBtn = new Button() {{
//					setStyle("-fx-background-color: url('https://i.imgur.com/kMFxd9Q.png');");
//					setStyle("-fx-background-size: 50 50;");
//					setStyle("-fx-background-radius: 25;");
//					setMaxSize(50, 50);
//					setMinSize(50, 50);
//					setOnMouseEntered(e -> setOpacity(0.8));
//					setOnMouseExited(e -> setOpacity(1));
//					setOnMouseClicked(e -> acceptFriendReq(username.getText()));
//				}};
//				Button acceptBtn = new Button() {{
//					setStyle("-fx-background-color: url('https://i.imgur.com/kMFxd9Q.png');");
//					setStyle("-fx-background-size: 50 50;");
//					setStyle("-fx-background-radius: 25;");
//					setMaxSize(50, 50);
//					setMinSize(50, 50);
//					setOnMouseEntered(e -> setOpacity(0.8));
//					setOnMouseExited(e -> setOpacity(1));
//					setOnMouseClicked(e -> declineFriendReq(username.getText()));
//				}};
				Button sendFrndReqBtn = new Button() {{
					setStyle("-fx-background-image: url('https://i.imgur.com/kMFxd9Q.png');" +
							" -fx-background-size: 50 50;" +
							" -fx-background-radius: 25;");
					setMaxSize(50, 50);
					setMinSize(50, 50);
					setPadding(new Insets(2.5,2.5,2.5,2.5));
					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
					setOnMouseClicked(e -> sendFriendReqDone(username.getText()));
				}};

				circle.setCenterX(pfp.getFitHeight() / 2);
				circle.setCenterY(pfp.getFitHeight() / 2);

				add(pfp, 0, 0);
				add(username, 1, 0);
				add(sendFrndReqBtn, 2, 0);
			}});
		}
	}

	private void sendFriendReqDone (String usernameTo) {
		FriendRequestController.getInstance().sendFrndRequest(usernameTo);
		availableForFrndReqList.getItems().removeIf(item -> ((Label) item.getChildren().get(1)).getText().equals(usernameTo));
	}

	private void declineFriendReq (String usernameFrom) {
		FriendRequestController.getInstance().declineFriendReq(usernameFrom);
		frndReqsGottenList.getItems().removeIf(item -> ((Label) item.getChildren().get(1)).getText().equals(usernameFrom));
	}

	private void acceptFriendReq (String usernameFrom) {
		FriendRequestController.getInstance().acceptFriendReq(usernameFrom);
		frndReqsGottenList.getItems().removeIf(item -> ((Label) item.getChildren().get(1)).getText().equals(usernameFrom));
	}

	public void closeStage (ActionEvent actionEvent) {
		stage.close();
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		updateFriendReqsList();

		btn1.setOnMouseEntered(e -> btn1.setOpacity(0.8));
		btn1.setOnMouseExited(e -> btn1.setOpacity(1));
		btn2.setOnMouseEntered(e -> btn2.setOpacity(0.8));
		btn2.setOnMouseExited(e -> btn2.setOpacity(1));
		btn3.setOnMouseEntered(e -> btn3.setOpacity(0.8));
		btn3.setOnMouseExited(e -> btn3.setOpacity(1));
		clearSearch.setOnMouseEntered(e -> clearSearch.setOpacity(0.8));
		clearSearch.setOnMouseExited(e -> clearSearch.setOpacity(1));
		clearSearch.setOnMouseClicked(e -> search.setText(""));

		search.textProperty().addListener((observableValue, s, t1) -> updateAvailableGamersList());
	}

	private void updateFriendReqsList () {
		// TODO: 1/8/2021 AD
	}

	public void closeFriendReqSendingWindow (ActionEvent actionEvent) {
		sendFrndReqWindow.setVisible(false);
		search.setText("");
	}
}
