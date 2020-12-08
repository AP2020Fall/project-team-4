package Controller.AccountRelated;

import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import View.AccountRelated.AccountView;
import View.Menus.Menu;

import java.util.Arrays;
import java.util.LinkedList;

public class AccountController {
	private Account currentAccLoggedIn = null;

	private static AccountController accountController;

	public static AccountController getInstance () {
		if (accountController == null)
			accountController = new AccountController();
		return accountController;
	}

	public void login () {
		String username;
		while (true)
			try {
				Menu.print("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();
				break;
			} catch (NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
				System.out.print("Password:[/c to cancel] "); String password = Menu.getInputLine();

				if (password.trim().equalsIgnoreCase("/c")) return;

				if (!Account.getAccount(username).isPasswordCorrect(password))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		currentAccLoggedIn = Account.getAccount(username);

		Menu.addMenusForAdminOrGamer(currentAccLoggedIn instanceof Gamer ? "G" : "A");

		Menu.getMenu("3" + (currentAccLoggedIn instanceof Gamer ? "G" : "A")).enter();
	}

	public void deleteAccount () {
		String username;
		while (true)
			try {
				Menu.print("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();

				if (Account.getAccount(username) instanceof Admin)
					throw new AdminAccountCantBeDeletedException();

				break;
			} catch (AdminAccountCantBeDeletedException | NoAccountExistsWithUsernameException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
				Menu.print("Password:[/c to cancel] "); String password = Menu.getInputLine();

				if (password.trim().equalsIgnoreCase("/c")) return;

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

	public void register () {
		String username;
		while (true)
			try {
				Menu.print("Username:[/c to cancel] "); username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (Account.accountExists(username))
					throw new AccountWithUsernameAlreadyExistsException();
				break;
			} catch (AccountWithUsernameAlreadyExistsException e) {
				Menu.printErrorMessage(e.getMessage());
			}


		// trying to ask for password and full name
		Menu.print("Password:[/c to cancel] "); String password = Menu.getInputLine();
		if (password.trim().equalsIgnoreCase("/c")) return;

		Menu.print("First Name:[/c to cancel] "); String firstName = Menu.getInputLine();
		if (firstName.trim().equalsIgnoreCase("/c")) return;

		Menu.print("Last Name:[/c to cancel] "); String lastName = Menu.getInputLine();
		if (lastName.trim().equalsIgnoreCase("/c")) return;

		String email;
		while (true)
			try {
				Menu.print("Email Address:[/c to cancel] "); email = Menu.getInputLine();

				if (email.trim().equalsIgnoreCase("/c")) return;

				if (!Account.isEmailOK(email))
					throw new InvalidEmailFormatException();
				break;
			} catch (InvalidEmailFormatException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		String phoneNum;
		while (true)
			try {
				Menu.print("Phone Number:[/c to cancel] "); phoneNum = Menu.getInputLine();

				if (phoneNum.trim().equalsIgnoreCase("/c")) return;

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
					Menu.print("Initial Balance:[/c to cancel] "); initMoney = Double.parseDouble(Menu.getInputLine());

					if (phoneNum.trim().equalsIgnoreCase("/c")) return;

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

		Menu.addMenu("2");
		Menu.getMenu("2").enter();
	}

	public void changePWCommand () {
		while (true)
			try {
				Menu.print("Old password:[/c to cancel] "); String oldPW = Menu.getInputLine();

				if (!AccountController.getInstance().getCurrentAccLoggedIn().isPasswordCorrect(oldPW))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Menu.print("New password: "); String newPW = Menu.getInputLine();

		AccountController.getInstance().getCurrentAccLoggedIn().editField("password", newPW);
	}

	public void editAccFieldCommand () {
		LinkedList<String> availableFields = (LinkedList<String>) Arrays.asList(new String[]{
				"First Name",
				"Last Name",
				"Username",
				"Email",
				"Phone Number"});
		AccountView.getInstance().displayEditableFields(availableFields);

		int field = Integer.parseInt(Menu.getInputLine());

		switch (field) {
			case 1 -> {
				Menu.print("New First name: ");
				getCurrentAccLoggedIn().editField("first name", Menu.getInputLine());

				Menu.println("First name changed successfully.");
			}


			case 2 -> {
				Menu.print("New Last name:[/c to cancel] "); getCurrentAccLoggedIn().editField("last name", Menu.getInputLine());
				Menu.println("Last name changed successfully.");
			}
			case 3 -> {
				String username;
				while (true)
					try {
						Menu.print("New Username:[/c to cancel] "); username = Menu.getInputLine();

						if (username.trim().equalsIgnoreCase("/c")) return;

						if (Account.accountExists(username))
							throw new AccountWithUsernameAlreadyExistsException();
						break;
					} catch (AccountWithUsernameAlreadyExistsException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				getCurrentAccLoggedIn().editField("username", Menu.getInputLine());
				Menu.println("Username changed successfully.");
			}
			case 4 -> {
				String newEmail;
				while (true)
					try {
						Menu.print("New email address:[/c to cancel] "); newEmail = Menu.getInputLine();

						if (newEmail.trim().equalsIgnoreCase("/c")) return;

						if (!Account.isEmailOK(newEmail))
							throw new InvalidEmailFormatException();
						break;
					} catch (InvalidEmailFormatException e) {
						Menu.printErrorMessage(e.getMessage());
					}
				getCurrentAccLoggedIn().editField("email", newEmail);
				Menu.println("Email changed successfully.");
			}
			case 5 -> {
				String newPhoneNum;
				while (true)
					try {
						Menu.print("Phone Number:[/c to cancel] "); newPhoneNum = Menu.getInputLine();

						if (newPhoneNum.trim().equalsIgnoreCase("/c")) return;

						if (!Account.isPhoneNumOK(newPhoneNum))
							throw new InvalidPhoneNumFormatException();
						break;
					} catch (InvalidPhoneNumFormatException e) {
						Menu.printErrorMessage(e.getMessage());
					}

				getCurrentAccLoggedIn().editField("phone num", newPhoneNum);
				Menu.println("Phone number changed successfully.");
			}
			default -> Menu.printErrorMessage("Invalid field.");
		}

	}

	public void displayPersonalInfo () {
		Account account = AccountController.getInstance().getCurrentAccLoggedIn();

		if (account instanceof Admin)
			AccountView.getInstance().displayPersonalInfo(
					account.getUsername(),
					account.getFirstName(),
					account.getLastName(),
					account.getEmail(),
					account.getPhoneNum()
			);
		else
			AccountView.getInstance().displayPersonalInfo(
					account.getUsername(),
					account.getFirstName(),
					account.getLastName(),
					account.getEmail(),
					account.getPhoneNum(),
					((Gamer) account).getMoney()
			);
	}

	public void logoutCommand () {
		logout();
	}

	public Account getCurrentAccLoggedIn () {
		return currentAccLoggedIn;
	}

	public void logout () {
		currentAccLoggedIn = null;
	}

	public boolean isLoggedIn () {
		return currentAccLoggedIn != null;
	}

	public static class NoAccountExistsWithUsernameException extends Exception {
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
