public class Gamer extends Account {
    private String firstname, lastname, username, accountId, password, email, phoneNumber, daysSinceRegistration, friends;
    private Gameslog gameslog;
	private int score = 0;
	private int money;
	private int DaysSinceRegistration = 0;


	public Gamer(String firstName, String lastName, String username, String accountID, String password, String email, String phoneNumber, Gameslog gameslog, int daysSinceRegistration, int money, int score, String friends
				 )
	{
		super(firstName, lastName, username,
				accountID, password, email, phoneNumber
				);

		this.money = money;
	}
}
