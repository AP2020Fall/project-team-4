package View;
import Controller.AccountRelated.AccountController;
import Controller.MainController;
import Controller.Menus.LoginMenuController;
import Controller.Menus.MainMenuController;
import Controller.Menus.RegisterMenuController;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static javafx.application.Application.launch;


public class Client extends Application {

    private static MainController mainController;
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;



    public static void main(String[] args){
        launch(args);
    }

//    public static void main (String[] args) throws IOException {
//        System.out.println("Client : client started");
//        try {
//            Scanner scanner = new Scanner(System.in);
//            InetAddress ip = InetAddress.getByName("localhost");
//            Socket socket = new Socket(ip, 5056);
//            System.out.println("Client : Connecting to Server...");
//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            launch(args);
////            while (true) {
////                String message = scanner.nextLine();
////                dataOutputStream.writeUTF(message);
////                if (message.equals("Exit")) {
////                    System.out.println("Closing this connection : " + socket);
////                    socket.close();
////                    System.out.println("Connection closed");
////                    break;
////                }
////            }
////            scanner.close();
//  //          dataOutputStream.close();
//        } catch (Exception e) {
//            System.out.println("Client : connecting to server failed due to exception");
//            e.printStackTrace();
//        }
//    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        getInstance().primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> saveEverything());
        MainController.DayPassController.getInstance().start();

        try {
            getInstance().deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // is signed in
        String path;

        if (!Admin.adminHasBeenCreated()) {
            RegisterMenuController.setStage(primaryStage);
            path = "src/com/plato/View/Menus/RegisterMenu.fxml";
        }

        else if (AccountController.getInstance().getCurrentAccLoggedIn() == null) {
            LoginMenuController.setStage(primaryStage);
            path = "src/com/plato/View/Menus/LoginMenu.fxml";
        }
        else {
            MainMenuController.setGamerOrAdmin(AccountController.getInstance().getCurrentAccLoggedIn() instanceof Gamer);
            LoginMenuController.setStage(primaryStage);
            path = "src/com/plato/View/Menus/MainMenu.fxml";
        }

        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(new File(path).toURI().toURL())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.show();
    }

    public static MainController getInstance () {
        if (mainController == null)
            mainController = new MainController();
        return mainController;
    }

    private void initGsonAndItsBuilder () {
        gsonBuilder.setDateFormat("yyyy-MMM-dd HH:mm:ss");
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.serializeNulls();
        gson = FxGson.addFxSupport(gsonBuilder).create();
    }

    public void saveEverything () {
        try {
            initGsonAndItsBuilder();
            getInstance().serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
