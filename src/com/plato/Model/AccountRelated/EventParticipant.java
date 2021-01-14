package Model.AccountRelated;

public abstract class EventParticipant {
	private final String username;

	public EventParticipant (String username) {
		this.username = username;
	}

	public String getUsername () {
		return username;
	}
}

class MVPEventParticipant extends EventParticipant {

	public MVPEventParticipant (String username) {
		super(username);
	}
}

class LoginEventParticipant extends EventParticipant {
	private int numberOfLogins = 0;

	public LoginEventParticipant (String username) {
		super(username);
	}

	public void login () {
		numberOfLogins++;
	}

	public int getNumberOfLogins () {
		return numberOfLogins;
	}
}

class NumberOfPlayedEventParticipant extends EventParticipant {
	private int numberOfPlayed = 0;

	public NumberOfPlayedEventParticipant (String username) {
		super(username);
	}

	public void gamePlayed() {
		numberOfPlayed++;
	}

	public int getNumberOfPlayed () {
		return numberOfPlayed;
	}
}

class NumberOfWinsEventParticipant extends EventParticipant {
	private int numberOfWins = 0;

	public NumberOfWinsEventParticipant (String username) {
		super(username);
	}

	public void gameWon() {
		numberOfWins++;
	}

	public int getNumberOfWins () {
		return numberOfWins;
	}
}

class WinGameNTimesConsecutiveLyEventParticipant extends EventParticipant {
	private int numberOfWins = 0;

	public WinGameNTimesConsecutiveLyEventParticipant (String username) {
		super(username);
	}

	public void gameLostOrTied () {
		numberOfWins=0;
	}

	public void gameWon() {
		numberOfWins++;
	}

	public int getNumberOfWins () {
		return numberOfWins;
	}
}