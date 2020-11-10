public class Gamer extends Account {

	private int score = 0;
	private int money;


	public Gamer(String firstName, String lastName, String username, String accountID, String password, String email, String phoneNumber, String daysSinceRegistration, int money, int score, String gamesLog, String friends, String friendsRequestlist
				 )
	{
		super(firstName, lastName, username,
				accountID, password, email, phoneNumber
				);

		this.money = money;
	}
}
