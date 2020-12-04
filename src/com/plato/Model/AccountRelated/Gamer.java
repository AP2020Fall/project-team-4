package plato.Model.AccountRelated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Gamer extends Account {

	private int score = 0;
	private double money;
	private LocalDate accountStartDate;
	// todo add gamelog if needed
	private LinkedList<Gamer> frnds = new LinkedList<>();
	private LinkedList<String> faveGames = new LinkedList<>();

	protected Gamer (String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		super(firstName, lastName, username, password, email, phoneNum);
		accountStartDate = LocalDate.now();
		this.money = money;
	}

	public int getScore () {
		return score;
	}

	public LinkedList<String> getFaveGames () {
		return faveGames;
	}

	public static LinkedList<Gamer> getGamers () {
		return ((LinkedList<Gamer>) getAccounts().stream()
				.filter(account -> account instanceof Gamer)
				.map(account -> ((Gamer) account))
				.collect(Collectors.toList()));
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
		// TODO: 11/20/2020 AD
	}

	public void stopParticipatingInEvent (String eventID) {
		// TODO: 11/20/2020 AD
	}

	public int getDaysSinceRegistration () {
		return Math.toIntExact(ChronoUnit.DAYS.between(LocalDateTime.now(), accountStartDate));
	}
}