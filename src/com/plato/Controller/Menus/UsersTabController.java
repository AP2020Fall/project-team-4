package Controller.Menus;

import Model.AccountRelated.Gamer;
import Controller.Client;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersTabController implements Initializable {
	public TextField search;
	public Label clearSearch;
	public ListView<GridPane> accountsList;
	public Pane profile;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private Object Account;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		dataInputStream = Client.getClient().getDataInputStream();
		dataOutputStream = Client.getClient().getDataOutputStream();
		updateAccountsList();

		clearSearch.setOnMouseEntered(e -> clearSearch.setOpacity(0.8));
		clearSearch.setOnMouseExited(e -> clearSearch.setOpacity(1));
		clearSearch.setOnMouseClicked(e -> search.setText(""));

		search.textProperty().addListener((observableValue, s, t1) -> updateAccountsList());
	}

	private void updateAccountsList () {
		accountsList.getItems().clear();

		// TODO: 2/3/2021
		for (Gamer gamer : Gamer.getGamers(Gamer.getGamers(), search.getText())) {
			Circle circle = new Circle(50);

			accountsList.getItems().add(new GridPane() {{
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
				}};
				Label username = new Label() {{
					setText(gamer.getUsername());
					setFont(Font.font("Arial", FontWeight.BOLD, 20));
					setTextAlignment(TextAlignment.LEFT);
					setPadding(new Insets(0, 10, 0, 10));
				}};

				circle.setCenterX(pfp.getFitHeight() / 2);
				circle.setCenterY(pfp.getFitHeight() / 2);

				add(pfp, 0, 0);
				add(username, 1, 0);

				setOnMouseClicked(e -> displayGamerProfile(((Label) getChildren().get(1)).getText()));
				setOnMouseEntered(e -> setOpacity(0.8));
				setOnMouseExited(e -> setOpacity(1));
			}});
		}
	}

	private void displayGamerProfile (String username) {
		try {
			profile.getChildren().clear();
			dataOutputStream.writeUTF("getAccount_" + username);
			dataOutputStream.flush();
			Gamer gamer = new Gson().fromJson(dataInputStream.readUTF() , Gamer.class);
			UserProfileForAdminController.setGamer(gamer);
			profile.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/UserProfileForAdmin.fxml").toURI().toURL()));
			GridPane.setValignment(profile.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(profile.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
