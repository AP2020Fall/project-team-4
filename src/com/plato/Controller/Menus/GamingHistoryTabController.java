package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Gamer;
import Model.GameRelated.Game;
import Model.GameRelated.GameLog;
import Model.GameRelated.Player;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

import javafx.event.ActionEvent;

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
    private ActionEvent actionEvent;
    private MouseEvent mouseEvent;

    public GamingHistoryTabController(ActionEvent actionEvent, MouseEvent mouseEvent) {
        this.actionEvent = actionEvent;
        this.mouseEvent = mouseEvent;
    }

    public GamingHistoryTabController() {
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

    public static void setStage(Stage stage){
        GamingHistoryTabController.stage = stage;
        GamingHistoryTabController.stage.setOnCloseRequest(e -> GamingHistoryTabController.stage = null);
    }

    public static void setAccount (Account account) {
        GamingHistoryTabController.account = account;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       updateListOfGamesWrite(new ActionEvent());
    }

    public void updateListOfGames(){
        gamingHistoryList.getItems().clear();
        Gamer currentAccLoggedIn = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
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
//                        if(game.getGameName().equals("Reversi")) setImage(MainController.getImageFromFile("src/com/Resources/Images/eyeVisible.png"));
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

    public void updateListOfGamesWrite(ActionEvent actionEvent){
        setActionEvent(actionEvent);
        MainController.write("GamingHistoryTab.updateListOfGames");
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
