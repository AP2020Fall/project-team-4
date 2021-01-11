package Controller.Menus;

import Controller.AccountRelated.AccountController;
import Controller.AccountRelated.EventController;
import Model.AccountRelated.Event;
import Model.AccountRelated.Gamer;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EventsTabController implements Initializable {
	private static boolean gamerOrAdmin;
	public CheckBox showUpcoming, showInSession, showParticipatingIn;
	public Button createEventBtn;
	public ListView<GridPane> eventsList;
	public GridPane eventInfo;
	public ImageView eventImg, eventGameImg;
	public Label eventPrize, eventTitle, eventDetails;
	public Button joinEventBtn;

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
		// TODO: 1/11/2021 AD
	}

	public void joinEvent (ActionEvent actionEvent) {
		// TODO: 1/11/2021 AD
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		updateList(Event.getAllEvents());
	}

	public void updateList (LinkedList<Event> eventsToShow) {
		eventsToShow.forEach(event -> {
			eventsList.getItems().add(new GridPane() {{
				ImageView eventPic = new ImageView() {{
					setImage(new Image(event.getPictureUrl()));
					resize(50, 50);
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
					setFont(Font.font("Arial", FontWeight.BOLD, 16));
					setTextAlignment(TextAlignment.CENTER);
					setHalignment(this, HPos.CENTER);
					setValignment(this, VPos.CENTER);
					setRowIndex(this, 1);
					setColumnIndex(this, 0);
				}};

				HBox coin = new HBox() {{
					getChildren().add(new Label() {{
						setText(String.valueOf(event.getEventScore()));
						setFont(Font.font("Arial", 14));
						setTextAlignment(TextAlignment.CENTER);
					}});
					getChildren().add(new ImageView() {{
						setImage(new Image("https://i.imgur.com/Iq0MAc7.png"));
						resize(25, 25);
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
									setText("end in \n" + LocalDate.now().until(event.getEnd()) + "d");
								else
									setText("start in \n" + event.getStart().until(LocalDate.now()) + "d");

								setFont(Font.font("Arial", 10));
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
									setOnAction(e -> EventController.getInstance().participateInEvent(event.getEventID()));
								}
								else {
									setText("Join");
									setOnAction(e -> EventController.getInstance().stopParticipatingInEvent(event.getEventID()));
								}

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
								setMinSize(40, 40);
								setMaxSize(40, 40);

								setStyle("-fx-background-image: url('https://i.imgur.com/KDWC4LH.png');" +
										"  -fx-background-size: 40 40;" +
										"  -fx-background-radius: 20;");

								setOnAction(e -> editEvent(event));
								setRowIndex(this, 0);
								setColumnIndex(this, 2);
								setRowSpan(this, 2);
							}},
							new Button() {{
								setMinSize(40, 40);
								setMaxSize(40, 40);

								setStyle("-fx-background-image: url('https://i.imgur.com/iZoXnCW.png?1');" +
										"  -fx-background-size: 40 40;" +
										"  -fx-background-radius: 20;");

								setOnAction(e -> removeEvent(event));
								setRowIndex(this, 0);
								setColumnIndex(this, 3);
								setRowSpan(this, 2);
							}}
					);

				setOnMouseEntered(e -> setOpacity(0.8));
				setOnMouseExited(e -> setOpacity(1));
				setOnMouseClicked(e -> displayEventInfo(event.getEventID()));
			}});
		});
	}

	private void removeEvent (Event event) {
		// TODO: 1/11/2021 AD
	}

	private void editEvent (Event event) {
		// TODO: 1/11/2021 AD  
	}

	private void displayEventInfo (String eventID) {
		//todo
		eventInfo.setVisible(true);
	}
}
















