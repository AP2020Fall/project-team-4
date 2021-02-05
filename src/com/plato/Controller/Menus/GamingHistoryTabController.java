package Controller.Menus;

import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.GameLog;
import Controller.Client;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamingHistoryTabController implements Initializable {

    private static String gameName;
    private static Stage stage;
    private static Account account;

    public GridPane gameInfo;
    public ListView<GridPane> gamingHistoryList;
    public GridPane gameStats;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void setStage(Stage stage){
        GamingHistoryTabController.stage = stage;
        GamingHistoryTabController.stage.setOnCloseRequest(e -> GamingHistoryTabController.stage = null);
    }

    public static void setAccount (Account account) {
        GamingHistoryTabController.account = account;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataInputStream = Client.getClient().getDataInputStream();
        dataOutputStream = Client.getClient().getDataOutputStream();
        try {
            updateListOfGames(new ActionEvent());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void updateListOfGames(ActionEvent actionEvent) throws IOException {
        gamingHistoryList.getItems().clear();

        dataOutputStream.writeUTF("getCurrentAccLoggedIn_");
        dataOutputStream.flush();
        Gamer currentAccLoggedIn = new Gson().fromJson(dataInputStream.readUTF() , Gamer.class);
        // TODO: 2/3/2021
        for(Game game : GameLog.getGameHistory(currentAccLoggedIn)){
            Circle circle = new Circle(40);

            gamingHistoryList.getItems().add(new GridPane(){
                {
                    getRowConstraints().add(new RowConstraints() {{
                        setMinHeight(circle.getRadius() * 2);
                        setMaxHeight(circle.getRadius() * 2);
                    }});

                    getColumnConstraints().add(new ColumnConstraints() {{
                        setMinWidth(circle.getRadius() * 2);
                        setMaxWidth(circle.getRadius() * 2);
                    }});

//                    ImageView icon = new ImageView() {{
//                        if(game.getGameName().equals("Reversi")) setImage(i.imgur.com/lKOxPw8.png);
//                        else if(game.getGameName().equals("BattleSea")) setImage();
//                        setSmooth(true);
//                        setPreserveRatio(true);
//                        setFitHeight(circle.getRadius() * 2);
//                        setClip(circle);
//                        setPadding(new Insets(5, 5, 5, 5));
//                    }};

                    Label gameName = new Label() {{
                        setText(game.getGameName());
                        setFont(Font.font("Arial", FontWeight.BOLD, 20));
                        setTextAlignment(TextAlignment.LEFT);
                        setPadding(new Insets(0, 10, 0, 10));
                    }};

                    Label gameConclusion = new Label() {{
                        setText(game.getConclusion().name());
                        setFont(Font.font("Arial", FontWeight.BOLD, 15));
                        setTextAlignment(TextAlignment.LEFT);
                        setPadding(new Insets(0, 10, 0, 10));
                    }};

                    add(gameConclusion, 2, 0);
                    add(gameName, 1, 0);
                    setOnMouseClicked(e -> displayGameInfo(game));
                    setOnMouseEntered(e -> setOpacity(0.8));
                    setOnMouseExited(e -> setOpacity(1));
                }});
        }
    }


    public void displayGameInfo(Game game){
        try{
            gameInfo.getChildren().clear();
            if(game.gameEnded()){
                gameInfo.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/GameConclusionWindow.fxml").toURI().toURL()));
                GridPane.setValignment(gameInfo.getChildren().get(0), VPos.CENTER);
                GridPane.setHalignment(gameInfo.getChildren().get(0), HPos.CENTER);
            }
            else{
                if(game.getGameName().equals("Reversi")){
                    gameInfo.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/ReversiGame.fxml").toURI().toURL()));
                    GridPane.setValignment(gameInfo.getChildren().get(0), VPos.CENTER);
                    GridPane.setHalignment(gameInfo.getChildren().get(0), HPos.CENTER);
                } else if(game.getGameName().equals("BattleSea")){
                    gameInfo.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/BattleSeaPlayPage.fxml").toURI().toURL()));
                    GridPane.setValignment(gameInfo.getChildren().get(0), VPos.CENTER);
                    GridPane.setHalignment(gameInfo.getChildren().get(0), HPos.CENTER);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
