package plato.AccountRelated;

public class Admin extends Account {
	public Admin (String firstName, String lastName, String username, String password, String email, String phoneNum) {
		super(firstName, lastName, username, password, email, phoneNum);
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static Admin getAdmin () {
		return getAccounts().stream()
				.filter(account -> account instanceof Admin)
				.map(account -> ((Admin) account))
				.findAny().get();
	}

	public static boolean adminHasBeenCreated () {
		return getAccounts().stream()
				.anyMatch(account -> account instanceof Admin);
	}

	public static void sendMsg (String text) {
		new Message(text);
	}
}