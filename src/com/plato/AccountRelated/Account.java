package plato.AccountRelated;

import plato.IDGenerator;
import plato._Interactor;

import java.util.LinkedList;
import java.util.regex.Matcher;

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
		return null;
	}
	
	public boolean isPasswordCorrect (String pw) {
		// TODO: 11/16/2020 AD  
		return false;
	}

	public void editField (String field, String newVal) {
		// TODO: 11/16/2020 AD
		// also check if the entered field is valid
	}

	public static void passDay () { // for adding to daysSinceRegistration
		// TODO: 11/13/2020 AD
	}

	public boolean isEmailOK (String email) {
		return email.toLowerCase().matches("[a-z0-9_.]+@[gmail|[yahoo|email]]\\.com");
	}

	public boolean isPhoneNumOK (String phoneNum) {
		return phoneNum.matches("\\d{10,11}");
	}

	public static Account getAccount (String username) {
		return accounts.stream()
				.filter(account -> account.getUsername().equals(username))
				.findAny().get();
	}

	public static boolean accountExists (String username) {
		return accounts.stream()
				.anyMatch(account -> account.getUsername().equals(username));
	}

	public String getUsername () {
		return username;
	}

	public static LinkedList<Account> getAccounts () {
		return accounts;
	}
}


















