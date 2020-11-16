package plato.AccountRelated;

import plato.IDGenerator;

import java.util.LinkedList;

public abstract class Account {
	private String firstName, lastName, username, password, userID, email, phoneNum;

	private static LinkedList<Account> accounts = new LinkedList<>();

	public Account (String firstName, String lastName, String username, String password, String email, String phoneNum) {
		// TODO: 11/10/2020 AD
		this.userID = IDGenerator.generateNext();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNum = phoneNum;
	}
	
	public String getPersonalInfo () {
		// TODO: 11/16/2020 AD  
	}
	
	public boolean isPasswordCorrect (String pw) {
		// TODO: 11/16/2020 AD  
	}

	public void editField (String field, String newVal) {
		// TODO: 11/16/2020 AD
		// also check if the entered field is valid
	}

	public static void passDay () { // for adding to daysSinceRegistration
		// TODO: 11/13/2020 AD
	}

	public String getUsername () {
		return username;
	}
}
