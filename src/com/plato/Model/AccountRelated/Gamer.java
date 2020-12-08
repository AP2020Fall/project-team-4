package Model.AccountRelated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Gamer extends Account {

	private int awardsFromEvents = 0;
	private double money;
	private LocalDate accountStartDate;
	private LinkedList<Gamer> frnds = new LinkedList<>();
	private LinkedList<String> faveGames = new LinkedList<>();

	protected Gamer (String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		super(firstName, lastName, username, password, email, phoneNum);
		accountStartDate = LocalDate.now();
		this.money = money;
	}

	public void sendFrndReq (String usernameTo) {
		FriendRequest.addFriendReq(this, (Gamer) getAccount(usernameTo));
	}

	public void addFrnd (Gamer friend) {
		frnds.addLast(friend);
	}

	public void removeFrnd (Gamer friend) {
		frnds.remove(friend);
	}

	public LinkedList<Gamer> getFrnds () {
		return frnds;
	}

	public boolean frndExists (String un) {
		return frnds.stream()
				.anyMatch(gamer -> gamer.getUsername().equals(un));
	}

	public Gamer getFrnd (String un) {
		return frnds.stream()
				.filter(gamer -> gamer.getUsername().equals(un))
				.findAny().get();
	}

	public LinkedList<FriendRequest> getFriendRequestsGotten () {
		return FriendRequest.getFriendReq(this);
	}

	public LinkedList<AdminGameReco> getAdminGameRecosGotten () {
		return (LinkedList<AdminGameReco>) getAdminGameRecosGotten().stream()
				.filter(reco -> reco.getGamer().getUsername().equals(getUsername()))
				.sorted(Comparator.comparing(AdminGameReco::getGameName))
				.collect(Collectors.toList());
	}

	public void addToFaveGames (String gameName) {
		if (!faveGames.contains(gameName))
			faveGames.addLast(gameName);
	}

	public void participateInEvent (String eventID) {
		Event.getEvent(eventID).addParticipant(this);
	}

	public void stopParticipatingInEvent (String eventID) {
		Event.getEvent(eventID).removeParticipant(this);
	}

	public int getDaysSinceRegistration () {
		return Math.toIntExact(ChronoUnit.DAYS.between(LocalDateTime.now(), accountStartDate));
	}

	public static void setGamers (LinkedList<Gamer> gamers) {
		getAccounts().addAll(gamers);
	}

	public LinkedList<String> getFaveGames () {
		return faveGames;
	}

	public static ArrayList<Gamer> getGamers () {
		return (ArrayList<Gamer>) getAccounts().stream()
				.filter(account -> account instanceof Gamer)
				.map(account -> ((Gamer) account))
				.collect(Collectors.toList());
	}

	public double getMoney () {
		return money;
	}

	private void giveEventAward (int award) {
		awardsFromEvents += award;
	}

	public int getAwardsFromEvents () {
		return awardsFromEvents;
	}
}