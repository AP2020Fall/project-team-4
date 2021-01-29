package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.FriendRequestController;
import Model.AccountRelated.Account;
import Model.AccountRelated.FriendRequest;
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
import javafx.scene.input.MouseEvent;
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
			Circle circle = new Circle(40);

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
					setPadding(new Insets(5, 5, 5, 5));
				}};
				Label username = new Label() {{
					setText(gamer.getUsername());
					setFont(Font.font("Arial", FontWeight.BOLD, 20));
					setTextAlignment(TextAlignment.LEFT);
					setPadding(new Insets(0, 10, 0, 10));
					setMinWidth(175);
				}};
				Button sendFrndReqBtn = new Button() {{
					setStyle("-fx-background-image: url('https://i.imgur.com/0bfM4ED.png?1');" +
							" -fx-background-size: 50 50;" +
							" -fx-background-radius: 25;");
					setMaxSize(50, 50);
					setMinSize(50, 50);
					setPadding(new Insets(2.5, 2.5, 2.5, 2.5));
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
		FriendRequest.getFriendReq(AccountController.getInstance().getCurrentAccLoggedIn().getUsername()).forEach(friendRequest ->
				System.out.println("friendRequest.getFromUsername() = " + friendRequest.getFromUsername() + "\t,\tfriendRequest.getToUsername() = " + friendRequest.getToUsername()));


		for (FriendRequest friendRequest : FriendRequest.getFriendReq(AccountController.getInstance().getCurrentAccLoggedIn().getUsername())) {
			Gamer gamerFrom = (Gamer) Account.getAccount(friendRequest.getFromUsername());

			Circle circle = new Circle(50);

			frndReqsGottenList.getItems().add(new GridPane() {{
				getRowConstraints().add(new RowConstraints() {{
					setMinHeight(circle.getRadius() * 2);
					setMaxHeight(circle.getRadius() * 2);
				}});

				getColumnConstraints().add(new ColumnConstraints() {{
					setMinWidth(circle.getRadius() * 2);
					setMaxWidth(circle.getRadius() * 2);
				}});

				ImageView pfp = new ImageView() {{
					setImage(new Image(gamerFrom.getPfpUrl()));
					setSmooth(true);
					setPreserveRatio(true);
					setFitHeight(circle.getRadius() * 2);
					setClip(circle);
					setPadding(new Insets(5, 5, 5, 5));
				}};
				Label username = new Label() {{
					setText(gamerFrom.getUsername());
					setFont(Font.font("Arial", FontWeight.BOLD, 20));
					setTextAlignment(TextAlignment.LEFT);
					setPadding(new Insets(0, 10, 0, 10));
					setMinWidth(200);
				}};
				Button acceptBtn = new Button() {{
					setStyle("-fx-background-image: url('https://i.imgur.com/BqfqMoS.png');" +
							"-fx-background-size: 80 80;" +
							"-fx-background-radius: 40;");
					setMaxSize(80, 80);
					setMinSize(80, 80);
					setPadding(new Insets(5, 5, 5, 5));
					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
					setOnMouseClicked(e -> acceptFriendReq(username.getText()));
				}};
				Button declineBtn = new Button() {{
					setStyle("-fx-background-image: url('https://i.imgur.com/kMFxd9Q.png');" +
							"-fx-background-size: 80 80;" +
							"-fx-background-radius: 40;");
					setMaxSize(80, 80);
					setMinSize(80, 80);
					setPadding(new Insets(5, 5, 5, 5));
					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
					setOnMouseClicked(e -> declineFriendReq(username.getText()));
				}};

				circle.setCenterX(pfp.getFitHeight() / 2);
				circle.setCenterY(pfp.getFitHeight() / 2);

				add(pfp, 0, 0);
				add(username, 1, 0);
				add(declineBtn, 2, 0);
				add(acceptBtn, 3, 0);
			}});
		}
		clearSearch.setOnMouseClicked(e -> search.setText(""));

		search.textProperty().addListener((observableValue, s, t1) -> updateAvailableGamersList());
	}

	public void closeFriendReqSendingWindow (ActionEvent actionEvent) {
		sendFrndReqWindow.setVisible(false);
		search.setText("");
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(0.8);
		else if (mouseEvent.getSource() instanceof Label)
			((Label) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		if (mouseEvent.getSource() instanceof Button)
			((Button) mouseEvent.getSource()).setOpacity(1);
		else if (mouseEvent.getSource() instanceof Label)
			((Label) mouseEvent.getSource()).setOpacity(1);
	}
}
