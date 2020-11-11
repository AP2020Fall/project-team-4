import java.util.ArrayList;

public class Gamer extends Account {
	private String firstname, lastname, username, accountId, password, email, phoneNumber;
	private Gameslog gameslog; //fixme:add new()
	private int score = 0;
	private int money;
	private int DaysSinceRegistration = 0;
	private ArrayList<Account> friends = new ArrayList<>();

	public Gamer( String firstname, String lastname, String username, String accountID, String password, String email, String phoneNumber,int money)

	{
		super( firstname, lastname, username, accountID, password, email, phoneNumber);
		this.money = money;
	}

}

