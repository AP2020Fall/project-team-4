package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Admin;
import plato.Model.AccountRelated.Gamer;
import plato.View.AccountRelated.AccountView;
import plato.View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class AccountController {
	private static Account currentAccLoggedIn = null;

	public static void login () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();
				break;
			} catch (NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
				System.out.print("Password:[/cancel to cancel filling form] "); String password = Menu.getInputLine();

				if (password.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.getAccount(username).isPasswordCorrect(password))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		currentAccLoggedIn = Account.getAccount(username);

		// todo based on type of account loggedInAcc is go to admin or gamer main menu
	}

	public static void deleteAccount () {
		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (Account.getAccount(username) instanceof Admin)
					throw new AdminAccountCantBeDeletedException();

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();

				break;
			} catch (AdminAccountCantBeDeletedException | NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
				System.out.print("Password:[/cancel to cancel filling form] "); String password = Menu.getInputLine();

				if (password.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.getAccount(username).isPasswordCorrect(password))
					throw new PaswordIncorrectException();

				break;
			} catch (PaswordIncorrectException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Account.removeAccount(username);
		if (currentAccLoggedIn.getUsername().equals(username))
			logoutCommand();
	}

	public static void register () {
		// TODO: 11/30/2020 AD

		String username;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

				if (username.trim().toLowerCase().equals("/cancel")) return;

				if (Account.accountExists(username))
					throw new AccountWithUsernameAlreadyExistsException();
				break;
			} catch (AccountWithUsernameAlreadyExistsException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		// trying to ask for password and full name
		System.out.print("Password:[/cancel to cancel filling form] "); String password = Menu.getInputLine();
		if (password.trim().toLowerCase().equals("/cancel")) return;

		System.out.print("First Name:[/cancel to cancel filling form] "); String firstName = Menu.getInputLine();
		if (firstName.trim().toLowerCase().equals("/cancel")) return;

		System.out.print("Last Name:[/cancel to cancel filling form] "); String lastName = Menu.getInputLine();
		if (lastName.trim().toLowerCase().equals("/cancel")) return;

		String email;
		while (true)
			try {
				System.out.print("Email Address:[/cancel to cancel filling form] "); email = Menu.getInputLine();

				if (email.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.isEmailOK(email))
					throw new InvalidEmailFormatException();
				break;
			} catch (InvalidEmailFormatException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		String phoneNum;
		while (true)
			try {
				System.out.print("Phone Number:[/cancel to cancel filling form] "); phoneNum = Menu.getInputLine();

				if (phoneNum.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.isPhoneNumOK(phoneNum))
					throw new InvalidPhoneNumFormatException();
				break;
			} catch (InvalidPhoneNumFormatException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		// if admin account hasnt already been created make admin account
		// 		otherwise ask for initial money amount and make a gamer account
		if (!Admin.adminHasBeenCreated()) {
			Account.addAccount(Admin.class, firstName, lastName, username, password, email, phoneNum, 0);
			Menu.printErrorMessage("Admin account created successfully.");
		}
		else {
			// trying to get initial balance
			//		if input is not a number or is negative try asking for it again
			double initMoney;
			while (true) {
				try {
					System.out.print("Initial Balance:[/cancel to cancel filling form] "); initMoney = Double.parseDouble(Menu.getInputLine());

					if (phoneNum.trim().toLowerCase().equals("/cancel")) return;

					if (initMoney < 0)
						throw new NegativeMoneyException();

					break;
				} catch (NumberFormatException e) {
					Menu.printErrorMessage("Initial Balance must be a number.");
				} catch (NegativeMoneyException e) {
					Menu.printErrorMessage(e.getMessage());
				}
			}

			Account.addAccount(Gamer.class, firstName, lastName, username, password, email, phoneNum, initMoney);
			Menu.printErrorMessage("Gamer account created successfully.");
		}
	}

	public static void changePWCommand () {
		while (true)
			try {
				System.out.print("Old password:[/cancel to cancel filling form] "); String oldPW = Menu.getInputLine();

				if (!AccountController.getCurrentAccLoggedIn().isPasswordCorrect(oldPW))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		System.out.print("New password: "); String newPW = Menu.getInputLine();

		AccountController.getCurrentAccLoggedIn().editField("password", newPW);
	}

	public static void editAccFieldCommand () {
		LinkedList<String> availableFields = (LinkedList<String>) Arrays.asList(new String[]{
				"First Name",
				"Last Name",
				"Username",
				"Email",
				"Phone Number"});
		AccountView.displayEditableFields(availableFields);

		int field = Integer.parseInt(Menu.getInputLine());

		switch (field) {
			case 1 -> {
				System.out.print("New First name: "); getCurrentAccLoggedIn().editField("first name", Menu.getInputLine()); // FIXME: add cancel option
				System.out.println("First name changed successfully.");
			}
			case 2 -> {
				System.out.print("New Last name: "); getCurrentAccLoggedIn().editField("last name", Menu.getInputLine()); // FIXME: add cancel option
				System.out.println("Last name changed successfully.");
			}
			case 3 -> {
				String username;
				while (true)
					try {
						System.out.print("New Username:[/cancel to cancel filling form] "); username = Menu.getInputLine();

						if (username.trim().toLowerCase().equals("/cancel")) return;

						if (Account.accountExists(username))
							throw new AccountWithUsernameAlreadyExistsException();
						break;
					} catch (AccountWithUsernameAlreadyExistsException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				getCurrentAccLoggedIn().editField("username", Menu.getInputLine());
				System.out.println("Username changed successfully.");
			}
			case 4 -> {
				String newEmail;
				while (true)
					try {
						System.out.print("Email Address:[/cancel to cancel filling form] "); newEmail = Menu.getInputLine();

						if (newEmail.trim().toLowerCase().equals("/cancel")) return;

						if (!Account.isEmailOK(newEmail))
							throw new InvalidEmailFormatException();
						break;
					} catch (InvalidEmailFormatException e) {
						Menu.printErrorMessage(e.getMessage());
					}
				getCurrentAccLoggedIn().editField("email", newEmail);
				System.out.println("Email changed successfully.");
			}
			case 5 -> {
				String newPhoneNum;
				while (true)
					try {
						System.out.print("Phone Number:[/cancel to cancel filling form] "); newPhoneNum = Menu.getInputLine();

						if (newPhoneNum.trim().toLowerCase().equals("/cancel")) return;

						if (!Account.isPhoneNumOK(newPhoneNum))
							throw new InvalidPhoneNumFormatException();
						break;
					} catch (InvalidPhoneNumFormatException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				getCurrentAccLoggedIn().editField("phone num", newPhoneNum);
				System.out.println("Phone number changed successfully.");
			}
			default -> Menu.printErrorMessage("Invalid field.");
		}

	}

	public static void diplayPersonalInfo () {
		Account account = AccountController.getCurrentAccLoggedIn();
		AccountView.displayPersonalInfo(account.getUsername(), account.getFirstName(), account.getLastName(), account.getEmail(), account.getPhoneNum());
	}

	public static void logoutCommand () {
		logout();
		// todo change the menu to login menu
		// 		if was in game menu set current game name in game menu to null
	}

	public static Account getCurrentAccLoggedIn () {
		return currentAccLoggedIn;
	}

	public static void logout () {
		currentAccLoggedIn = null;
	}

	public static boolean isLoggedIn () {
		return currentAccLoggedIn != null;
	}

	private static class RepeatedUsernameException extends Exception {
		public RepeatedUsernameException () {
			super("An account with this username already exists.");
		}
	}

	static class NoAccountExistsWithUsernameException extends Exception {
		public NoAccountExistsWithUsernameException () {
			super("No account exists with this username.");
		}
	}

	private static class AccountWithUsernameAlreadyExistsException extends Exception {
		public AccountWithUsernameAlreadyExistsException () {
			super("An Account already exists with this username.");
		}
	}

	private static class PaswordIncorrectException extends Exception {
		public PaswordIncorrectException () {
			super("Password incorrect.");
		}
	}

	private static class AdminAccountCantBeDeletedException extends Exception {
		public AdminAccountCantBeDeletedException () {
			super("Admin account can't be deleted.");
		}
	}

	private static class InvalidEmailFormatException extends Exception {
		public InvalidEmailFormatException () {
			super("Email format is invalid.");
		}
	}

	private static class InvalidPhoneNumFormatException extends Exception {
		public InvalidPhoneNumFormatException () {
			super("Phone number format is invalid.");
		}
	}


	private static class NegativeMoneyException extends Throwable {
		public NegativeMoneyException () {
			super("Initial Balance must be a positive number.");
		}
	}
}
