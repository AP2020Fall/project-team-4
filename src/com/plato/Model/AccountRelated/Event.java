package Model.AccountRelated;

import Controller.IDGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Event {
	private static LinkedList<Event> events = new LinkedList<>();
	private final String eventID;
	private String pictureUrl;
	private String title;
	private String gameName;
	private double eventScore;
	private LocalDate start, end;
	private LinkedList<Gamer> participants = new LinkedList<>();
	private boolean awardsGiven = false; // true if an event has ended and its awards have been given, false otherwise

	private Event (String pictureUrl, String title, String gameName, double eventScore, LocalDate start, LocalDate end) {
		this.pictureUrl = pictureUrl;
		this.title = title;
		this.gameName = gameName;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
	}

	// اطلاعات ایونت رو از کنترلر میگه و ایونت رو میسازه
	public static void addEvent (String pictureUrl, String title, String gameName, double eventScore, LocalDate start, LocalDate end) {
		events.addLast(new Event(pictureUrl, title, gameName, eventScore, start, end));
	}

	// بین ایونتها میگرده دنبال یه ایونتی که آیدیش این باشه
	public static void removeEvent (String eventID) {
		events.remove(getEvent(eventID));
	}

	// یین همه ایونتا میگرده و اونایی که این کاربر جزو شرکت کننده هاشون بوده رو برمیگردونه
	public static LinkedList<Event> getInSessionEventsParticipatingIn (Gamer gamer) {
		return getAllInSessionEvents().stream()
				.filter(event -> event.participantExists(gamer.getUsername()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	// وقتی هر روز برای ایونتایی که تایمشون تموم شده میگرده، این متد جایزه اونایی که تموم شدن رو میده
	@SuppressWarnings("ForLoopReplaceableByForEach")
	public static void dealWOverdueEvents () {
		for (int i = 0; i < events.size(); i++) {

			Event event = events.get(i);
			if (event.isDue() && !event.awardsGiven)
				event.giveAwardsOfOverdueEvent();
		}
	}

	public static LinkedList<Event> getAllEvents () {
		return events;
	}

	// برای deserialize کردن
	public static void setEvents (LinkedList<Event> events) {
		Event.events = events;
	}

	// ایونتایی که شروع شدند .لی تموم نشدند رو مرتب میکنه و برمیگردونه
	// ترتیب مرتب کردن تو کامنتای داخل متده
	public static LinkedList<Event> getAllInSessionEvents () {
		return getAllEvents().stream()
				.filter(Event::isInSession)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	// چک میکنه اگه ایونت شروع نشده ای با این آیدی وجود داره یا نه
	@SuppressWarnings("unused")
	public static boolean upcomingEventExists (String eventID) {
		return events.stream()
				.filter(event -> !event.hasStarted())
				.anyMatch(event -> event.getEventID().equals(eventID));
	}

	public static LinkedList<Event> getAllUpcomingEvents () {
		return events.stream()
				.filter(event -> !event.hasStarted())
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<Event> getSortedEvents (LinkedList<Event> list) {
		return list.stream()
				.sorted(Comparator.comparing(Event::getGameName)                // first battleSea then reversi events
						.thenComparing(Event::getStart)                         // from earliest starting
						.thenComparing(Event::getEnd)                           // from earliest ending
						.thenComparingDouble(Event::getEventScore).reversed()   // from highest prizes
						.thenComparing(Event::getEventID))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	// بین ایونتا دنبال این آیدی میگرده و اون ایونت رو پس میده
	@SuppressWarnings("OptionalGetWithoutIsPresent")

	public static Event getEvent (String eventID) {
		return events.stream()
				.filter(event -> event.getEventID().equals(eventID))
				.findAny().get();
	}

	// بین ایونتا میگرده که آیا این آیدی وجود داره یا نه
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean eventExists (String eventID) {
		return events.stream()
				.anyMatch(event -> event.getEventID().equals(eventID));
	}

	// چک میکنه که آیا ایونت درحال اجرایی با این آیدی وجود داره یا نه
	public static boolean eventInSessionExists (String eventID) {
		for (int i = 0; i < getAllInSessionEvents().size(); i++)
			if (getAllInSessionEvents().get(i).getEventID().equals(eventID))
				return true;
		return false;
	}

	public static LinkedList<Event> getAllEventsParticipatingIn (Gamer gamer) {
		return events.stream()
				.filter(event -> event.participantExists(gamer.getUsername()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<Event> getAllUpcomingEventsParticipatingIn (Gamer gamer) {
		return getAllUpcomingEvents().stream()
				.filter(event -> event.participantExists(gamer.getUsername()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	// ویژگی گفته شده رو تغییر میده
	@SuppressWarnings("EnhancedSwitchMigration")
	public void editField (String field, String newVal) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		switch (field.toLowerCase()) {
			case "title":
				title = newVal;
				break;
			case "game name":
				gameName = newVal;
				break;
			case "event score":
				eventScore = Double.parseDouble(newVal);
				break;
			case "start":
				start = LocalDate.parse(newVal, dateTimeFormatter);
				break;
			case "end":
				end = LocalDate.parse(newVal, dateTimeFormatter);
				break;
		}
	}

	// چک میکنه که آیا زمان شروع ایونت قبل یا خود امروز هست یا نه
	public boolean hasStarted () {
		return !LocalDate.now().isBefore(start);
	}

	// چک میکنه که ایا زمان پایان ایونت قبل امروز هست یا نه
	private boolean isDue () {
		return LocalDate.now().isAfter(end);
	}

	// چک میکنه کا آیا ایونت شروع شده و تموم نشده هست یا نه
	public boolean isInSession () {
		return hasStarted() && !isDue();
	}

	// وقتی هر روز برای ایونتایی که تایمشون تموم شده میگرده، این متد جایزه اونایی که تموم شدن رو میده
	public void giveAwardsOfOverdueEvent () {
		// TODO: 12/8/2020 AD
		awardsGiven = true;
	}

	// بازیکن را به لیست شرکت کنندگان ایونت اضافه میکند
	public void addParticipant (Gamer gamer) {
		participants.add(gamer);
	}

	// بازیکن را از لیست شرکت کنندگان ایونت حذف میکند
	public void removeParticipant (Gamer gamer) {
		participants.removeIf(participant -> participant.getUsername().equals(gamer.getUsername()));
	}

	// چک میکنه که آیا بازیکن تو ایونت شرکت میکنه یا نه
	@SuppressWarnings("ForLoopReplaceableByForEach")
	public boolean participantExists (String username) {
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).getUsername().equals(username))
				return true;
		}
		return false;
	}

	// دنبال شرکت کننده با این نام کاربری میگرده و برمیگردونه
	@SuppressWarnings({"ForLoopReplaceableByForEach", "unused"})
	public Gamer getParticipant (String username) {
		for (int i = 0; i < participants.size(); i++)
			if (participants.get(i).getUsername().equals(username))
				return participants.get(i);
		return null;
	}

	// لیست کل شرکنندگان رو میده
	public LinkedList<Gamer> getParticipants () {
		if (participants == null)
			participants = new LinkedList<>();
		return participants;
	}

	public String getEventID () {
		return eventID;
	}

	public String getGameName () {
		return gameName;
	}

	public double getEventScore () {
		return eventScore;
	}

	public LocalDate getStart () {
		return start;
	}

	public LocalDate getEnd () {
		return end;
	}

	public String getTitle () {
		return title;
	}

	public String getPictureUrl () {
		return pictureUrl;
	}
}
