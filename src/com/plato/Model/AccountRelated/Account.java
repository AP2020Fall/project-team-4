package Model.AccountRelated;

import Controller.IDGenerator;
import javafx.scene.image.Image;

import java.util.LinkedList;

public abstract class Account {
	protected static LinkedList<Account> accounts = new LinkedList<>();
	private String firstName, lastName, username, password, userID, email, phoneNum;
	private String pfpUrl;

	public Account (Image pfp, String firstName, String lastName, String username, String password, String email, String phoneNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.userID = IDGenerator.generateNext();
		this.email = email;
		this.phoneNum = phoneNum;
		this.pfpUrl = pfp.getUrl();
	}

	// for serialization
	public Account () {}

	// با توجه به ورودی اکانت-تایپ میاد یه اکانت گیمر یا ادمین با این ویژگی ها رو میسازه
	@SuppressWarnings("rawtypes")
	public static void addAccount (Class accType, Image pfp, String firstName, String lastName, String username, String password, String email, String phoneNum, double money) {
		if (accType.getSimpleName().equalsIgnoreCase("admin"))
			accounts.addLast(new Admin(pfp, firstName, lastName, username, password, email, phoneNum));
		else
			accounts.addLast(new Gamer(pfp, firstName, lastName, username, password, email, phoneNum, money));

	}

	// اکانت با این نام کاربری رو حذف میکنه
	public static void removeAccount (String username) {
		accounts.remove(getAccount(username));
	}

	// چک میکنه که فرمت ایمیل درسته یا نه
	public static boolean isEmailOK (String email) {
		return email.toLowerCase().matches("[a-z0-9_.]+@[a-z]+\\.com");
	}

	// چک میکنه که فرمت شماره تلفن درسته یا نه
	public static boolean isPhoneNumOK (String phoneNum) {
		return phoneNum.matches("\\d{10,11}");
	}

	// چک میکنه که آیا اکانت با این نام کاربری وجود داره یا نه
	public static boolean accountExists (String username) {
		return accounts.stream()
				.anyMatch(account -> account.getUsername().equals(username));
	}

	// دنبال اکانت با این نام کاربری میگرده
	public static Account getAccount (String username) {
		return accounts.stream()
				.filter(account -> account.getUsername().equals(username))
				.findAny().get();
	}

	public static LinkedList<Account> getAccounts () {
		return accounts;
	}

	// برای deserialize کردن
	public static void setAccounts (LinkedList<Account> accounts) {
		Account.accounts = accounts;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isPasswordCorrect (String pw) {
		return this.password.equals(pw);
	}

	// ویژگی گفته شده رو به مقدار جدید گفته شده تغییر میده
	public void editField (String field, String newVal) {
		switch (field) {
			case "first name" -> setFirstName(newVal);
			case "last name" -> setLastName(newVal);
			case "username" -> setUsername(newVal);
			case "password" -> setPassword(newVal);
			case "email" -> setEmail(newVal);
			case "phone number" -> setPhoneNum(newVal);
			case "pfp url" -> setPfpUrl(newVal);
		}
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getFirstName () {
		return firstName;
	}

	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public void setLastName (String lastName) {
		this.lastName = lastName;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public String getPhoneNum () {
		return phoneNum;
	}

	public void setPhoneNum (String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPfpUrl () {
		return pfpUrl;
	}

	public void setPfpUrl (String pfpUrl) {
		this.pfpUrl = pfpUrl;
	}

	public void setUserID (String userID) {
		this.userID = userID;
	}
}
