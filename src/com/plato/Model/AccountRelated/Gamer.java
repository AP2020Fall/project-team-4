package Model.AccountRelated;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Gamer extends Account {
	private int awardsFromEvents = 0;
	private double money;
	private LocalDate accountStartDate;
	private LinkedList<String> frnds = new LinkedList<>();
	private LinkedList<String> faveGames = new LinkedList<>();

	protected Gamer (String pfp, String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		super(pfp, firstName, lastName, username, password, email, phoneNum);
		accountStartDate = LocalDate.now();
		this.money = money;
	}

	// for serialization
	public Gamer () {}

	public static LinkedList<Gamer> getGamers (LinkedList<Gamer> gamerList, String usernameSearch) {
		if (usernameSearch.length() == 0) return gamerList;

		return gamerList.stream()
				.filter(account -> account.getUsername().contains(usernameSearch))
				.sorted((g1, g2) -> {
					int cmp;

					// number of times search is repeated in usernames
					cmp = -Integer.compare((g1.getUsername().length() - g1.getUsername().replace(usernameSearch, "").length()) / usernameSearch.length(),
							(g2.getUsername().length() - g2.getUsername().replace(usernameSearch, "").length()) / usernameSearch.length());

					if (cmp != 0)
						return cmp;

					// index at which the match starts
					cmp = Integer.compare(g1.getUsername().indexOf(usernameSearch), g2.getUsername().indexOf(usernameSearch));
					if (cmp != 0)
						return cmp;

					// if all else fails sort by username
					return g1.getUsername().compareToIgnoreCase(g2.getUsername());
				})
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<Gamer> getAvailableGamersForFrndReq (Gamer gamerFrom) {
		return Gamer.getGamers().stream()
				.filter(gamerTo ->
						!gamerTo.getUsername().equals(gamerFrom.getUsername()) &&
								!gamerFrom.frndExists(gamerTo.getUsername()) &&
								!FriendRequest.frndReqExists(gamerFrom.getUsername(), gamerTo.getUsername())
				)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<Gamer> getGamers () {
		return getAccounts().stream()
				.filter(account -> account instanceof Gamer)
				.map(account -> ((Gamer) account))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static void setGamers (LinkedList<Gamer> gamers) {
		getAccounts().addAll(gamers);
	}

	public boolean frndExists (String un) {
		return frnds.stream()
				.anyMatch(gamerUN -> gamerUN.equals(un));
	}

	public void sendFrndReq (String usernameTo) {
		FriendRequest.addFriendReq(this.getUsername(), usernameTo);
	}

	public void addFrnd (String friendUN) {
		frnds.addLast(friendUN);
	}

	public void removeFrnd (String friendUN) {
		frnds.remove(friendUN);
	}

	public Gamer getFrnd (String un) {
		return frnds.stream()
				.filter(gamerUN -> gamerUN.equals(un))
				.map(gamerun -> ((Gamer) Account.getAccount(gamerun)))
				.findAny().get();
	}

	public LinkedList<FriendRequest> getFriendRequestsGotten () {
		return FriendRequest.getFriendReq(this.getUsername());
	}

	public void addToFaveGames (String gameName) {
		if (!faveGames.contains(gameName))
			faveGames.addLast(gameName);
	}

	public LinkedList<String> getFrnds () {
		return frnds;
	}

	public void setFrnds (LinkedList<String> frnds) {
		this.frnds = frnds;
	}

	public void participateInEvent (String eventID) {
		Event.getEvent(eventID).addParticipant(this);
	}

	public void stopParticipatingInEvent (String eventID) {
		Event.getEvent(eventID).removeParticipant(this);
	}

	public int getDaysSinceRegistration () {
		return Math.toIntExact(ChronoUnit.DAYS.between(accountStartDate, LocalDate.now()));
	}

	public LinkedList<String> getFaveGames () {
		return faveGames;
	}

	public void setFaveGames (LinkedList<String> faveGames) {
		this.faveGames = faveGames;
	}

	public LinkedList<AdminGameReco> getAdminGameRecosGotten () {
		return AdminGameReco.getRecommendations().stream()
				.filter(reco -> reco.getGamer().getUsername().equals(getUsername()))
				.sorted(Comparator.comparing(AdminGameReco::getGameName))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public double getMoney () {
		return money;
	}

	public void setMoney (double money) {
		this.money = money;
	}

	private void giveEventAward (int award) {
		awardsFromEvents += award;
	}

	public int getAwardsFromEvents () {
		return awardsFromEvents;
	}

	public void setAwardsFromEvents (int awardsFromEvents) {
		this.awardsFromEvents = awardsFromEvents;
	}

	public void setAccountStartDate (LocalDate accountStartDate) {
		this.accountStartDate = accountStartDate;
	}

	public void removeFaveGame (String gameName) {
		if (faveGames.contains(gameName))
		faveGames.remove(gameName);
	}
}