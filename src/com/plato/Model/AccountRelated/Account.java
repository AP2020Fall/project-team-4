package Model.AccountRelated;

import Controller.IDGenerator;

import java.util.LinkedList;

public abstract class Account {
	private String firstName, lastName, username, password, userID, email, phoneNum;

	protected static LinkedList<Account> accounts = new LinkedList<>();

	public Account (String firstName, String lastName, String username, String password, String email, String phoneNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.userID = IDGenerator.generateNext();
		this.email = email;
		this.phoneNum = phoneNum;
	}

	// for serialization
	public Account () {}

	public static void addAccount (Class accType, String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		if (accType.getSimpleName().equalsIgnoreCase("admin"))
			accounts.addLast(new Admin(firstName, lastName, username, password, email, phoneNum));
		else
			accounts.addLast(new Gamer(firstName, lastName, username, password, email, phoneNum, money));
	}

	public static void removeAccount (String username) {
		accounts.remove(getAccount(username));
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isPasswordCorrect (String pw) {
		return this.password.equals(pw);
	}

	public void editField (String field, String newVal) {
		switch (field) {
			case "first name" -> setFirstName(newVal);
			case "last name" -> setLastName(newVal);
			case "username" -> setUsername(newVal);
			case "password" -> setPassword(newVal);
			case "email" -> setEmail(newVal);
			case "phone num" -> setPhoneNum(newVal);
		}
	}

	public static boolean isEmailOK (String email) {
		return email.toLowerCase().matches("[a-z0-9_.]+@[a-z]+\\.com");
	}

	public static boolean isPhoneNumOK (String phoneNum) {
		return phoneNum.matches("\\d{10,11}");
	}

	public static boolean accountExists (String username) {
		return accounts.stream()
				.anyMatch(account -> account.getUsername().equals(username));
	}

	public static Account getAccount (String username) {
		return accounts.stream()
				.filter(account -> account.getUsername().equals(username))
				.findAny().get();
	}

	public static LinkedList<Account> getAccounts () {
		return accounts;
	}

	public static void setAccounts (LinkedList<Account> accounts) {
		Account.accounts = accounts;
	}

	public String getUsername () {
		return username;
	}

	public String getFirstName () {
		return firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public String getEmail () {
		return email;
	}

	public String getPassword () {
		return password;
	}

	public String getPhoneNum () {
		return phoneNum;
	}

	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}

	public void setLastName (String lastName) {
		this.lastName = lastName;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public void setUserID (String userID) {
		this.userID = userID;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public void setPhoneNum (String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
