package Model.AccountRelated;

import Controller.IDGenerator;

import java.util.LinkedList;

public abstract class Account {
	private String firstName, lastName, username, password, userID, email, phoneNum;

	protected static LinkedList<Account> accounts = new LinkedList<>();

	protected Account (String firstName, String lastName, String username, String password, String email, String phoneNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.userID = IDGenerator.generateNext();
		this.email = email;
		this.phoneNum = phoneNum;
	}

	public static void addAccount (Class accType, String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		if (accType.getSimpleName().equalsIgnoreCase("admin"))
			accounts.addLast(new Admin(firstName, lastName, username, password, email, phoneNum));
		else
			accounts.addLast(new Gamer(firstName, lastName, username, password, email, phoneNum, money));
	}

	public static void removeAccount (String username) {
		accounts.remove(getAccount (username));

	}

	public boolean isPasswordCorrect (String pw) {
		return this.password.equals(pw);
	}

	public void editField (String field, String newVal) {
		String input = "field";
		scanner  =new scanner(system.in);
	}

	public static boolean isEmailOK (String email) {
		return email.matches("[a-z0-9_.]+@[a-z]+\\.com");
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
}
