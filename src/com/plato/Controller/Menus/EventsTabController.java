package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.EventController;
import Controller.MainController;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EventsTabController implements Initializable {
	private static boolean gamerOrAdmin;
	public CheckBox showUpcoming, showInSession, showParticipatingIn;
	public Button createEventBtn;
	public ListView<GridPane> eventsList;
	public Pane eventInfo;
	public GridPane gridPane;

	public static void setGamerOrAdmin (boolean gamerOrAdmin) {
		EventsTabController.gamerOrAdmin = gamerOrAdmin;
	}

	public void filter (ActionEvent actionEvent) {
		LinkedList<Event> eventsToShow;
		Gamer gamer = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();

		String showWhich = (showInSession.isSelected() ? "y" : "n") + (showUpcoming.isSelected() ? "y" : "n") + (showParticipatingIn.isSelected() ? "y" : "n");

		eventsToShow = switch (showWhich) {
			case "nnn", "yyn" -> Event.getAllEvents();
			case "ynn" -> Event.getAllInSessionEvents();
			case "nyn" -> Event.getAllUpcomingEvents();
			case "nny", "yyy" -> Event.getAllEventsParticipatingIn(gamer);
			case "nyy" -> Event.getAllUpcomingEventsParticipatingIn(gamer);
			case "yny" -> Event.getInSessionEventsParticipatingIn(gamer);
			default -> throw new IllegalStateException("Unexpected value: " + showWhich);
		};

		updateList(Event.getSortedEvents(eventsToShow));
	}

	public void createEvent (ActionEvent actionEvent) {
		try {
			EventCreateOrEditPageController.setIsForCreateOrInfo(true);
			Stage stage = MainController.getInstance().createAndReturnNewStage(
					FXMLLoader.load(new File("src/com/plato/View/Menus/EventCreateOrEditPage.fxml").toURI().toURL()),
					"Create Event",
					true,
					MainController.getInstance().getPrimaryStage()
			);
			EventCreateOrEditPageController.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		if (gamerOrAdmin)
			gridPane.getChildren().remove(createEventBtn);

//		Event.addEvent("https://upload.wikimedia.org/wikipedia/commons/3/36/Large_bonfire.jpg", "Big Event", "BattleSea", "details", 25, LocalDate.of(2021, 1, 11), LocalDate.of(2021, 1, 12));
		updateList(Event.getAllEvents());
	}

	public void updateList (LinkedList<Event> eventsToShow) {
		eventsList.getItems().clear();
		eventsToShow.forEach(event ->
				eventsList.getItems().add(new GridPane() {{
					ImageView eventPic = new ImageView() {{
						setImage(new Image(event.getPictureUrl()));
						setFitHeight(150);
						setFitWidth(150);
						setSmooth(true);
						setPreserveRatio(true);
						setHalignment(this, HPos.CENTER);
						setValignment(this, VPos.CENTER);
						setRowIndex(this, 0);
						setColumnIndex(this, 0);
						setRowSpan(this, 2);
					}};

					Label eventName = new Label() {{
						setText(event.getTitle());
						setFont(Font.font("Arial", FontWeight.BOLD, 26));
						setTextAlignment(TextAlignment.CENTER);
						setHalignment(this, HPos.CENTER);
						setValignment(this, VPos.CENTER);
						setRowIndex(this, 0);
						setColumnIndex(this, 1);
					}};

					HBox coin = new HBox() {{
						getChildren().add(new Label() {{
							setText(String.valueOf(event.getEventScore()));
							setFont(Font.font("Arial", 24));
							setTextAlignment(TextAlignment.CENTER);
						}});
						getChildren().add(new ImageView() {{
							setImage(new Image("src/com/Resources/Images/coin.png"));
							setFitWidth(30);
							setFitHeight(30);
							setSmooth(true);
							setPreserveRatio(true);
						}});
						setSpacing(5);
						setAlignment(Pos.CENTER);
						setHalignment(this, HPos.CENTER);
						setValignment(this, VPos.CENTER);
						setRowIndex(this, 1);
						setColumnIndex(this, 1);
					}};

					getChildren().addAll(eventPic, eventName, coin);

					if (gamerOrAdmin)
						getChildren().addAll(
								new Label() {{
									if (event.hasStarted())
										setText("end in \n" + Math.toIntExact(ChronoUnit.DAYS.between(event.getEnd(), LocalDate.now())) + "d");
									else
										setText("start in \n" + Math.toIntExact(ChronoUnit.DAYS.between(LocalDate.now(), event.getStart())) + "d");

									setFont(Font.font("Arial", 20));
									setTextAlignment(TextAlignment.CENTER);
									setRowIndex(this, 0);
									setColumnIndex(this, 2);
									setRowSpan(this, 2);
								}},
								new Button() {{
									Gamer currentLoggedIn = (Gamer) AccountController.getInstance().getCurrentAccLoggedIn();
									// is already participating in event
									if (event.participantExists(currentLoggedIn.getUsername())) {
										setText("Drop-out");
										setOnAction(e -> {
											EventController.getInstance().stopParticipatingInEvent(event.getEventID());
											filter(new ActionEvent());
										});
									}
									else {
										setText("Join");
										setOnAction(e -> {
											EventController.getInstance().participateInEvent(event.getEventID());
											filter(new ActionEvent());
										});
									}

									setFont(Font.font("Arial", FontWeight.BOLD, 27));
									setTextAlignment(TextAlignment.CENTER);
									setOnMouseEntered(e -> setOpacity(0.8));
									setOnMouseExited(e -> setOpacity(1));
									setRowIndex(this, 0);
									setColumnIndex(this, 3);
									setRowSpan(this, 2);
								}}
						);

					else
						getChildren().addAll(
								new Button() {{
									setMinSize(60, 50);
									setMaxSize(60, 50);

									setStyle("-fx-background-image: url('https://i.imgur.com/KDWC4LH.png');" +
											"  -fx-background-size: 40 40;" +
											"  -fx-background-radius: 20;" +
											"  -fx-background-position: center;" +
											"  -fx-background-repeat: no-repeat;" +
											"  -fx-background-color: transparent;");

									setOnAction(e -> editEvent(event));
									setRowIndex(this, 0);
									setColumnIndex(this, 2);
									setRowSpan(this, 2);
								}},
								new Button() {{
									setMinSize(50, 50);
									setMaxSize(50, 50);

									setStyle("-fx-background-image: url('https://i.imgur.com/iZoXnCW.png?1');" +
											"  -fx-background-size: 50 50;" +
											"  -fx-background-radius: 25;" +
											"  -fx-background-position: center;" +
											"  -fx-background-repeat: no-repeat;" +
											"  -fx-background-color: transparent;");

									setOnAction(e -> removeEvent(event));
									setRowIndex(this, 0);
									setColumnIndex(this, 3);
									setRowSpan(this, 2);
								}}
						);

					if (event.hasStarted())
						getChildren().remove(getChildren().size() - 2, getChildren().size());

					setOnMouseEntered(e -> setOpacity(0.8));
					setOnMouseExited(e -> setOpacity(1));
					setOnMouseClicked(e -> displayEventInfo(event));

					setMinWidth(eventsList.getMinWidth());
					setMaxWidth(eventsList.getMaxWidth());

					setHgap(30);
					setVgap(10);
				}}));
	}

	private void removeEvent (Event event) {
		Event.removeEvent(event.getEventID());
		filter(new ActionEvent());
	}

	private void editEvent (Event event) {
		try {
			eventInfo.getChildren().clear();
			EventCreateOrEditPageController.setEvent(event);
			EventCreateOrEditPageController.setIsForCreateOrInfo(false);
			eventInfo.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/EventCreateOrEditPage.fxml").toURI().toURL()));
			GridPane.setValignment(eventInfo.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(eventInfo.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayEventInfo (Event event) {
		try {
			eventInfo.getChildren().clear();
			EventCreateOrEditPageController.setEvent(event);
			EventCreateOrEditPageController.setIsForCreateOrInfo(false);
			eventInfo.getChildren().add(FXMLLoader.load(new File("src/com/plato/View/Menus/EventCreateOrEditPage.fxml").toURI().toURL()));
			GridPane.setValignment(eventInfo.getChildren().get(0), VPos.CENTER);
			GridPane.setHalignment(eventInfo.getChildren().get(0), HPos.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseIsOut (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(0.8);
	}

	public void mouseIsOver (MouseEvent mouseEvent) {
		((Button) mouseEvent.getSource()).setOpacity(1);
	}
}
















