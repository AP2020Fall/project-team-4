package plato.AccountRelated;

import plato.GameRelated.GameLog;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Gamer extends Account {

	private int score = 0;
	private double money;
	private int daysSinceRegistration = 0;
	private final GameLog gameLog = new GameLog();
	private LinkedList<Gamer> frnds = new LinkedList<>();
	private LinkedList<String> faveGames = new LinkedList<>();

	public Gamer (String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		super(firstName, lastName, username, password, email, phoneNum);
		this.money = money;
	}

	public String getAccountStats () {
		// TODO: 11/16/2020 AD
		return null;
	}

	public String getGamingHistory () {
		// TODO: 11/16/2020 AD
		return null;
	}

	public String getGameStats (String gameName) {
		// TODO: 11/16/2020 AD
		return null;
	}

	public int getScore () {
		return score;
	}

	public LinkedList<String> getFaveGames () {
		return faveGames;
	}

	public LinkedList<Message> getMessages () {
		return Message.getMessages(this);
	}

	public static LinkedList<Gamer> getGamers () {
		return ((LinkedList<Gamer>) getAccounts().stream()
				.filter(account -> account instanceof Gamer)
				.map(account -> ((Gamer) account))
				.collect(Collectors.toList()));
	}

	public String getLastPlayedGame () {
		return gameLog.getGames().getLast().getGameName();
	}

	public void sendFrndReq (String usernameTo) {
		new FriendRequest(this, (Gamer) getAccount(usernameTo));
	}

	public void addFrnd(Gamer account) {
		frnds.addLast(account);
	}

	public void removeFrnd(Gamer account) {
		frnds.remove(account);
	}

	public LinkedList<Gamer> getFrnds () {
		return frnds;
	}

	public boolean frndExists (String un) {
		return frnds.stream()
				.anyMatch(gamer -> gamer.getUsername().equals(un));
	}

	public Gamer getFrnd(String un) {
		return frnds.stream()
				.filter(gamer -> gamer.getUsername().equals(un))
				.findAny().get();
	}

	public LinkedList<FriendRequest> getFriendRequestsGotten () {
		return FriendRequest.getFriendRequest(this);
	}

	public LinkedList<AdminGameReco> getAdminGameRecosGotten () {
		return (LinkedList<AdminGameReco>) getAdminGameRecosGotten().stream()
				.filter(reco -> reco.getGamer().getUsername().equals(getUsername()))
				.collect(Collectors.toList());
	}

	public void addToFaveGames (String gameName) {
		if (!faveGames.contains(gameName))
			faveGames.addLast(gameName);
	}

	// FIXME: if needed add a method that gets the gamer account that a given gameLog belongs to
}
