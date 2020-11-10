public class Gamer extends Account{

	private int score = 0;
	private int money;


	public Gamer (String firstName, String lastName, String username, int money) {
		super(firstName, lastName, username);
		this.money = money;
	}
}
