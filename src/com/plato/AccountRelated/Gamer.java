package plato.AccountRelated;

import plato.GameRelated.GameLog;

import java.util.LinkedList;

public class Gamer extends Account {

	private int score = 0;
	private double money;
	private int daysSinceRegistration = 0;
	private final GameLog gameLog = new GameLog();
	private LinkedList<Account> frnds = new LinkedList<>();
	private LinkedList<String> faveGames = new LinkedList<>();

	public Gamer (String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		super(firstName, lastName, username, password, email, phoneNum);
		this.money = money;
	}

	public String getGamingHistory () {
		// TODO: 11/16/2020 AD
	}

	public String getGameStats (String gameName) {
		// TODO: 11/16/2020 AD
	}

	public int getScore () {
		return score;
	}

	public LinkedList<String> getFaveGames () {
		return faveGames;
	}



	// FIXME: if needed add a method that gets the gamer account that a given gameLog belongs to
}
