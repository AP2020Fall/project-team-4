package plato.AccountRelated;

public class Gamer extends Account {

	private int score = 0;
	private int money;


	public Gamer (String firstName, String lastName, String username, int money) {
		super(firstName, lastName, username);
		this.money = money;
	}

	// FIXME: if needed add a method that gets the gamer account that a given gameLog belongs to
}
