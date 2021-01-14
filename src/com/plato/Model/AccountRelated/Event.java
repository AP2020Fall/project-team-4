package Model.AccountRelated;

import Controller.IDGenerator;
import Model.GameRelated.GameLog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public abstract class Event {
	private static LinkedList<Event> events = new LinkedList<>();
	private final String eventID;
	private String pictureUrl;
	private String title, details;
	private double eventScore;
	private LocalDate start, end;
	private LinkedList<EventParticipant> participants = new LinkedList<>();
	private boolean awardsGiven = false; // true if an event has ended and its awards have been given, false otherwise

	protected Event (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		this.pictureUrl = pictureUrl;
		this.title = title;
		this.details = details;
		this.eventScore = eventScore;
		this.start = start;
		this.end = end;
		this.eventID = IDGenerator.generateNext();
	}

	/**
	 * @param eventType if 1 -> MVPEvent
	 *                  if 2 -> LoginEvent
	 *                  if 3 -> NumOfPlayedEvent
	 *                  if 4 -> NumOfWinsEvent
	 *                  if 5 -> NConsecutiveWinsEvent
	 */
	public static void addEvent (int eventType, int numberOfRequired, String pictureUrl, String title, String gameName, String details, double eventScore, LocalDate start, LocalDate end) {
		switch (eventType) {
			case 1 -> new MVPEvent(pictureUrl, title, details, eventScore, start, end, gameName);
			case 2 -> new LoginEvent(pictureUrl, title, details, eventScore, start, end, numberOfRequired);
			case 3 -> new NumberOfPlayedEvent(pictureUrl, title, details, eventScore, start, end, numberOfRequired, gameName);
			case 4 -> new NumberOfWinsEvent(pictureUrl, title, details, eventScore, start, end, numberOfRequired, gameName);
			case 5 -> new WinGameNTimesConsecutiveLyEvent(pictureUrl, title, details, eventScore, start, end, numberOfRequired, gameName);

			default -> throw new IllegalStateException("Unexpected value: " + eventType);
		}
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
				.sorted(Comparator.comparing(Event::getStart)                    // from earliest starting
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
	public void editField (String field, String newVal) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		switch (field.toLowerCase()) {
			case "title" -> title = newVal;
			case "prize" -> eventScore = Double.parseDouble(newVal);
			case "start date" -> start = LocalDate.parse(newVal, dateTimeFormatter);
			case "end date" -> end = LocalDate.parse(newVal, dateTimeFormatter);
			case "details" -> details = newVal;
			case "pic-url" -> pictureUrl = newVal;

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
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
		awardsGiven = true;
	}

	// بازیکن را به لیست شرکت کنندگان ایونت اضافه میکند
	public abstract void addParticipant (Gamer gamer);

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
				return (Gamer) Account.getAccount(participants.get(i).getUsername());
		return null;
	}

	// لیست کل شرکنندگان رو میده
	public LinkedList<EventParticipant> getParticipants () {
		if (participants == null)
			participants = new LinkedList<>();
		return participants;
	}

	public String getEventID () {
		return eventID;
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

	public String getDetails () {
		return details;
	}

	public abstract String getGameName ();

	public abstract String getHowTo ();
}

class MVPEvent extends Event {
	private String gameName;

	public MVPEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end, String gameName) {
		super(pictureUrl, title, details, eventScore, start, end);
		this.gameName = gameName;
	}

	public MVPEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		super(pictureUrl, title, details, eventScore, start, end);
	}

	@Override
	public void editField (String field, String newVal) {
		super.editField(field, newVal);

		switch (field.toLowerCase()) {
			case "title", "pic-url", "details", "end date", "start date", "prize" -> {}
			case "game name" -> gameName = newVal;

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
	}

	@Override
	public void giveAwardsOfOverdueEvent () {
		super.giveAwardsOfOverdueEvent();

		Gamer mvpGamer = getMvp();

		if (GameLog.getPlayedCount(mvpGamer, gameName) == 0) return;

		mvpGamer.setMoney(mvpGamer.getMoney() + getEventScore());
	}

	@Override
	public void addParticipant (Gamer gamer) {
		getParticipants().add(new MVPEventParticipant(gamer.getUsername()));
	}

	public Gamer getMvp () {
		return GameLog.getAllGamersWhoPlayedGame(gameName).stream()
				.sorted((gamer1, gamer2) -> {
					int cmp;

					cmp = -GameLog.getPoints(gamer1, gameName).compareTo(GameLog.getPoints(gamer2, gameName));
					if (cmp != 0) return cmp;

					cmp = -GameLog.getWinCount(gamer1, gameName).compareTo(GameLog.getWinCount(gamer2, gameName));
					if (cmp != 0) return cmp;

					cmp = GameLog.getLossCount(gamer1, gameName).compareTo(GameLog.getLossCount(gamer2, gameName));
					if (cmp != 0) return cmp;

					cmp = GameLog.getPlayedCount(gamer1, gameName).compareTo(GameLog.getPlayedCount(gamer2, gameName));
					if (cmp != 0) return cmp;

					return -GameLog.getDrawCount(gamer1, gameName).compareTo(GameLog.getDrawCount(gamer2, gameName));
				})
				.collect(Collectors.toCollection(LinkedList::new))
				.get(0);
	}

	@Override
	public String getGameName () {
		return gameName;
	}

	@Override
	public String getHowTo () {
		return "get to the #1 spot on the scoreboard for " + gameName + " until " + getEnd().format(DateTimeFormatter.ofPattern("dth of MMMM")) + " to get the prize";
	}
}

class LoginEvent extends Event {
	private int numberOfRequired;

	public LoginEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end, int numberOfRequired) {
		super(pictureUrl, title, details, eventScore, start, end);
		this.numberOfRequired = numberOfRequired;
	}

	public LoginEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		super(pictureUrl, title, details, eventScore, start, end);
	}

	@Override
	public void giveAwardsOfOverdueEvent () {
		super.giveAwardsOfOverdueEvent();

		getWinners()
				.forEach(participant -> {
					Gamer gamer = (Gamer) Account.getAccount(participant.getUsername());
					gamer.setMoney(gamer.getMoney() + getEventScore());
				});
	}

	public LinkedList<LoginEventParticipant> getWinners () {
		return getParticipants().stream()
				.map(participant -> ((LoginEventParticipant) participant))
				.filter(participant -> participant.getNumberOfLogins() >= numberOfRequired)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void addParticipant (Gamer gamer) {
		getParticipants().add(new LoginEventParticipant(gamer.getUsername()));
	}

	@Override
	public String getGameName () {
		return null;
	}

	@Override
	public String getHowTo () {
		return "login " + numberOfRequired + " time" + (numberOfRequired == 1 ? "" : "s") + " until " + getEnd().format(DateTimeFormatter.ofPattern("dth of MMMM")) + " to get the prize";
	}
}

class NumberOfPlayedEvent extends Event {
	private int numberOfRequired;
	private String gameName;

	public NumberOfPlayedEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end, int numberOfRequired, String gameName) {
		super(pictureUrl, title, details, eventScore, start, end);
		this.numberOfRequired = numberOfRequired;
		this.gameName = gameName;
	}

	public NumberOfPlayedEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		super(pictureUrl, title, details, eventScore, start, end);
	}

	@Override
	public void editField (String field, String newVal) {
		super.editField(field, newVal);

		switch (field.toLowerCase()) {
			case "title", "pic-url", "details", "end date", "start date", "prize" -> {}
			case "game name" -> gameName = newVal;

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
	}

	@Override
	public void giveAwardsOfOverdueEvent () {
		super.giveAwardsOfOverdueEvent();

		getWinners()
				.forEach(participant -> {
					Gamer gamer = (Gamer) Account.getAccount(participant.getUsername());
					gamer.setMoney(gamer.getMoney() + getEventScore());
				});
	}

	public LinkedList<NumberOfPlayedEventParticipant> getWinners () {
		return getParticipants().stream()
				.map(participant -> ((NumberOfPlayedEventParticipant) participant))
				.filter(participant -> participant.getNumberOfPlayed() >= numberOfRequired)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void addParticipant (Gamer gamer) {
		getParticipants().add(new NumberOfPlayedEventParticipant(gamer.getUsername()));
	}

	@Override
	public String getGameName () {
		return gameName;
	}

	@Override
	public String getHowTo () {
		return "play " + gameName + " " + numberOfRequired + " time" + (numberOfRequired == 1 ? "" : "s") + " until " + getEnd().format(DateTimeFormatter.ofPattern("dth of MMMM")) + " to get the prize";
	}
}

class NumberOfWinsEvent extends Event {
	private int numberOfRequired;
	private String gameName;

	public NumberOfWinsEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end, int numberOfRequired, String gameName) {
		super(pictureUrl, title, details, eventScore, start, end);
		this.numberOfRequired = numberOfRequired;
		this.gameName = gameName;
	}

	public NumberOfWinsEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		super(pictureUrl, title, details, eventScore, start, end);
	}

	@Override
	public void editField (String field, String newVal) {
		super.editField(field, newVal);

		switch (field.toLowerCase()) {
			case "title", "pic-url", "details", "end date", "start date", "prize" -> {}
			case "game name" -> gameName = newVal;

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
	}

	@Override
	public void giveAwardsOfOverdueEvent () {
		super.giveAwardsOfOverdueEvent();

		getWinners()
				.forEach(participant -> {
					Gamer gamer = (Gamer) Account.getAccount(participant.getUsername());
					gamer.setMoney(gamer.getMoney() + getEventScore());
				});
	}

	public LinkedList<NumberOfWinsEventParticipant> getWinners () {
		return getParticipants().stream()
				.map(participant -> ((NumberOfWinsEventParticipant) participant))
				.filter(participant -> participant.getNumberOfWins() >= numberOfRequired)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void addParticipant (Gamer gamer) {
		getParticipants().add(new NumberOfWinsEventParticipant(gamer.getUsername()));
	}

	@Override
	public String getGameName () {
		return gameName;
	}

	@Override
	public String getHowTo () {
		return "win " + gameName + " " + numberOfRequired + " time" + (numberOfRequired == 1 ? "" : "s") + " until " + getEnd().format(DateTimeFormatter.ofPattern("dth of MMMM")) + " to get the prize";
	}
}

/**
 * player wins in event if they win a game n times one after another. basically no loss or draw or forfeit for n times
 */
class WinGameNTimesConsecutiveLyEvent extends Event {
	private int numberOfRequired;
	private String gameName;

	public WinGameNTimesConsecutiveLyEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end, int numberOfRequired, String gameName) {
		super(pictureUrl, title, details, eventScore, start, end);
		this.numberOfRequired = numberOfRequired;
		this.gameName = gameName;
	}

	public WinGameNTimesConsecutiveLyEvent (String pictureUrl, String title, String details, double eventScore, LocalDate start, LocalDate end) {
		super(pictureUrl, title, details, eventScore, start, end);
	}

	@Override
	public void editField (String field, String newVal) {
		super.editField(field, newVal);

		switch (field.toLowerCase()) {
			case "title", "pic-url", "details", "end date", "start date", "prize" -> {}
			case "game name" -> gameName = newVal;

			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
	}

	@Override
	public void giveAwardsOfOverdueEvent () {
		super.giveAwardsOfOverdueEvent();

		getWinners()
				.forEach(participant -> {
					Gamer gamer = (Gamer) Account.getAccount(participant.getUsername());
					gamer.setMoney(gamer.getMoney() + getEventScore());
				});
	}

	public LinkedList<WinGameNTimesConsecutiveLyEventParticipant> getWinners () {
		return getParticipants().stream()
				.map(participant -> ((WinGameNTimesConsecutiveLyEventParticipant) participant))
				.filter(participant -> participant.getNumberOfWins() >= numberOfRequired)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void addParticipant (Gamer gamer) {
		getParticipants().add(new WinGameNTimesConsecutiveLyEventParticipant(gamer.getUsername()));
	}

	@Override
	public String getGameName () {
		return gameName;
	}

	@Override
	public String getHowTo () {
		return "win " + gameName + " " + numberOfRequired + " time" + (numberOfRequired == 1 ? "" : "s") + " consecutively until " + getEnd().format(DateTimeFormatter.ofPattern("dth of MMMM")) + " to get the prize";
	}
}