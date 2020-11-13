package AccountRelated;

import java.util.LinkedList;

public abstract class Account {
	private String firstName, lastName, username;
	
	private static LinkedList<Account> accounts = new LinkedList<>();

	public Account (String firstName, String lastName, String username) {
		// TODO: 11/10/2020 AD
	}

	public void passDay () { // for adding to daysSinceRegistration
		// TODO: 11/13/2020 AD
	}

	public String getUsername () {
		return username;
	}
}
