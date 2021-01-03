package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import View.AccountRelated.AccountView;

import java.util.Arrays;
import java.util.LinkedList;

public class AccountController {
	private static AccountController accountController;
	private Account currentAccLoggedIn = null;
	private boolean saveLoginInfo = false; // ask at login. also set to false when logout

	public static AccountController getInstance () {
		if (accountController == null)
			accountController = new AccountController();
		return accountController;
	}

	public void login () {
		String username = "";
		while (true)
			try {
//				Menu.printAskingForInput("Username:[/c to cancel] ");
//				username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!username.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("Username");

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();
				break;
			} catch (NoAccountExistsWithUsernameException | MainController.InvalidFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
//				Menu.printAskingForInput("Password:[/c to cancel] ");
				String password = "";
//				password = Menu.getInputLine();

				if (password.trim().equalsIgnoreCase("/c")) return;

				if (!Account.getAccount(username).isPasswordCorrect(password))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
//				Menu.printErrorMessage(e.getMessage());
			}


		currentAccLoggedIn = Account.getAccount(username);

//		Menu.printAskingForInput("Remember me?[y/n] ");
//		saveLoginInfo = Menu.getInputLine().trim().equalsIgnoreCase("y");

//		Menu.addMenusForAdminOrGamer(currentAccLoggedIn instanceof Gamer ? "G" : "A");

//		Menu.getMenu("3" + (currentAccLoggedIn instanceof Gamer ? "G" : "A")).enter();
	}

	public void deleteAccount () {
		String username = "";
		while (true)
			try {
//				Menu.printAskingForInput("Username:[/c to cancel] ");
//				username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!username.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("Username");

				if (!Account.accountExists(username))
					throw new NoAccountExistsWithUsernameException();

				if (Account.getAccount(username) instanceof Admin)
					throw new AdminAccountCantBeDeletedException();

				break;
			} catch (AdminAccountCantBeDeletedException | NoAccountExistsWithUsernameException | MainController.InvalidFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		while (true)
			try {
//				Menu.printAskingForInput("Password:[/c to cancel] ");
				String password = "";
//				password = Menu.getInputLine();

				if (password.trim().equalsIgnoreCase("/c")) return;

				if (!Account.getAccount(username).isPasswordCorrect(password))
					throw new PaswordIncorrectException();

				break;
			} catch (PaswordIncorrectException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

//		Menu.displayAreYouSureMessage();
//		if (Menu.getInputLine().equalsIgnoreCase("y")) {
//			Account.removeAccount(username);
//			Menu.printSuccessfulOperation("Removed account successfully.");
//		}
	}

	public void register () {
		String username = "";
		while (true)
			try {
//				Menu.printAskingForInput("Username:[/c to cancel] ");
//				username = Menu.getInputLine();

				if (username.trim().equalsIgnoreCase("/c")) return;

				if (!username.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("Username");

				if (Account.accountExists(username))
					throw new AccountWithUsernameAlreadyExistsException();
				break;
			} catch (AccountWithUsernameAlreadyExistsException | MainController.InvalidFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}


		// trying to ask for password and full name
//		Menu.printAskingForInput("Password:[/c to cancel] ");
		String password = "";
//		password = Menu.getInputLine();
//		if (password.trim().equalsIgnoreCase("/c")) return;

		String firstName = "";
		while (true) {
			try {
//				Menu.printAskingForInput("First Name:[/c to cancel] ");
//				firstName = Menu.getInputLine();

				if (firstName.trim().equalsIgnoreCase("/c")) return;

				if (!firstName.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("First name");

				break;
			} catch (MainController.InvalidFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}
		}

		String lastName = "";
		while (true)
			try {
//				Menu.printAskingForInput("Last Name:[/c to cancel] ");
//				lastName = Menu.getInputLine();

				if (lastName.trim().equalsIgnoreCase("/c")) return;

				if (!lastName.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("Last name");

				break;
			} catch (MainController.InvalidFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		String email = "";
		while (true)
			try {
//				Menu.printAskingForInput("Email Address:[/c to cancel] ");
//				email = Menu.getInputLine();

				if (email.trim().equalsIgnoreCase("/c")) return;

				if (!Account.isEmailOK(email))
					throw new InvalidEmailFormatException();
				break;
			} catch (InvalidEmailFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		String phoneNum = "";
		while (true)
			try {
//				Menu.printAskingForInput("Phone Number:[/c to cancel] ");
//				phoneNum = Menu.getInputLine();

				if (phoneNum.trim().equalsIgnoreCase("/c")) return;

				if (!Account.isPhoneNumOK(phoneNum))
					throw new InvalidPhoneNumFormatException();
				break;
			} catch (InvalidPhoneNumFormatException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

		// if admin account hasnt already been created make admin account
		// 		otherwise ask for initial money amount and make a gamer account
		if (!Admin.adminHasBeenCreated()) {
			Account.addAccount(Admin.class, firstName, lastName, username, password, email, phoneNum, 0);
//			Menu.printSuccessfulOperation("Admin account created successfully.");
		}
		else {
			// trying to get initial balance
			//		if input is not a number or is negative try asking for it again
			double initMoney = 0;
			while (true) {
				try {
//					Menu.printAskingForInput("Initial Balance:[/c to cancel] ");
//					initMoney = Double.parseDouble(Menu.getInputLine());

					if (phoneNum.trim().equalsIgnoreCase("/c")) return;

					if (initMoney < 0)
						throw new NegativeMoneyException();

					break;
				} catch (NumberFormatException e) {
//					Menu.printErrorMessage("Initial Balance must be a number.");
				} catch (NegativeMoneyException e) {
//					Menu.printErrorMessage(e.getMessage());
				}
			}

			Account.addAccount(Gamer.class, firstName, lastName, username, password, email, phoneNum, initMoney);
//			Menu.printSuccessfulOperation("Gamer account created successfully.");
		}

//		Menu.addMenu("2");
//		Menu.getMenu("2").enter();
	}

	public void changePWCommand () {
		while (true)
			try {
//				Menu.printAskingForInput("Old password:[/c to cancel] ");
				String oldPW = "";
//				oldPW = Menu.getInputLine();

				if (oldPW.trim().equalsIgnoreCase("/c")) return;

				if (!AccountController.getInstance().getCurrentAccLoggedIn().isPasswordCorrect(oldPW))
					throw new PaswordIncorrectException();
				break;
			} catch (PaswordIncorrectException e) {
//				Menu.printErrorMessage(e.getMessage());
			}

//		Menu.printAskingForInput("New password: ");
		String newPW = "";
//		newPW = Menu.getInputLine();

//		Menu.displayAreYouSureMessage();
//		if (Menu.getInputLine().trim().equalsIgnoreCase("y"))
			AccountController.getInstance().getCurrentAccLoggedIn().editField("password", newPW);
	}

	public void editAccFieldCommand () {
		LinkedList<String> availableFields = new LinkedList<>(Arrays.asList(
				"First Name",
				"Last Name",
				"Username",
				"Email",
				"Phone Number"));
		AccountView.getInstance().displayEditableFields(availableFields);

		int field;
		try {
//			Menu.printAskingForInput("Choose field to edit:\n");
			String fieldstr = "";
//			fieldstr = Menu.getInputLine();

			if (!String.valueOf(fieldstr).matches("[1-5]"))
				throw new NumberFormatException();

			field = Integer.parseInt(fieldstr);
		} catch (NumberFormatException e) {
//			Menu.printErrorMessage(new MainController.InvalidInputException().getMessage());
			return;
		}

		switch (field) {
			case 1 -> {
				String new1name = "";
				while (true)
					try {
//						Menu.printAskingForInput("New First name:[/c to cancel] ");
//						new1name = Menu.getInputLine();

						if (new1name.trim().equalsIgnoreCase("/c")) return;

						if (!new1name.matches("[!-~]+"))
							throw new MainController.InvalidFormatException("New first name");

						break;
					} catch (MainController.InvalidFormatException e) {
//						Menu.printErrorMessage(e.getMessage());
					}
//				Menu.displayAreYouSureMessage();
//				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
//					getCurrentAccLoggedIn().editField("first name", new1name);
//					Menu.printSuccessfulOperation("First name changed successfully.");
//				}
			}
			case 2 -> {
				String new2name = "";
				while (true)
					try {
//						Menu.printAskingForInput("New Last name:[/c to cancel] ");
//						new2name = Menu.getInputLine();

						if (new2name.trim().equalsIgnoreCase("/c")) return;

						if (!new2name.matches("[!-~]+"))
							throw new MainController.InvalidFormatException("New last name");

						break;
					} catch (MainController.InvalidFormatException e) {
//						Menu.printErrorMessage(e.getMessage());
					}
//				Menu.displayAreYouSureMessage();
//				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
//					getCurrentAccLoggedIn().editField("last name", new2name);
//					Menu.printSuccessfulOperation("Last name changed successfully.");
//				}
			}
			case 3 -> {
				String username = "";
				while (true)
					try {
//						Menu.printAskingForInput("New Username:[/c to cancel] ");
//						username = Menu.getInputLine();

						if (username.trim().equalsIgnoreCase("/c")) return;

						if (!username.matches("[!-~]+"))
							throw new MainController.InvalidFormatException("New username");

						if (Account.accountExists(username))
							throw new AccountWithUsernameAlreadyExistsException();

						break;
					} catch (AccountWithUsernameAlreadyExistsException | MainController.InvalidFormatException e) {
//						Menu.printErrorMessage(e.getMessage());
					}

//				Menu.displayAreYouSureMessage();
//				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
//					getCurrentAccLoggedIn().editField("username", username);
//					Menu.printSuccessfulOperation("Username changed successfully.");
//				}
			}
			case 4 -> {
				String newEmail = "";
				while (true)
					try {
//						Menu.printAskingForInput("New email address:[/c to cancel] ");
//						newEmail = Menu.getInputLine();

						if (newEmail.trim().equalsIgnoreCase("/c")) return;

						if (!Account.isEmailOK(newEmail))
							throw new InvalidEmailFormatException();
						break;
					} catch (InvalidEmailFormatException e) {
//						Menu.printErrorMessage(e.getMessage());
					}

//				Menu.displayAreYouSureMessage();
//				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
//					getCurrentAccLoggedIn().editField("email", newEmail);
//					Menu.printSuccessfulOperation("Email changed successfully.");
//				}
			}
			case 5 -> {
				String newPhoneNum = "";
				while (true)
					try {
//						Menu.printAskingForInput("Phone Number:[/c to cancel] ");
//						newPhoneNum = Menu.getInputLine();

						if (newPhoneNum.trim().equalsIgnoreCase("/c")) return;

						if (!Account.isPhoneNumOK(newPhoneNum))
							throw new InvalidPhoneNumFormatException();
						break;
					} catch (InvalidPhoneNumFormatException e) {
//						Menu.printErrorMessage(e.getMessage());
					}

//				Menu.displayAreYouSureMessage();
//				if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
//					getCurrentAccLoggedIn().editField("phone num", newPhoneNum);
//					Menu.printSuccessfulOperation("Phone number changed successfully.");
//				}
			}
			default -> {
//				Menu.printErrorMessage("Invalid field.");
			}
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

	public void logout () {
		currentAccLoggedIn = null;
		saveLoginInfo = false;
	}

	public Account getCurrentAccLoggedIn () {
		return currentAccLoggedIn;
	}

	public void setCurrentAccLoggedIn (Account currentAccLoggedIn) {
		this.currentAccLoggedIn = currentAccLoggedIn;
	}

	public void setSaveLoginInfo (boolean saveLoginInfo) {
		this.saveLoginInfo = saveLoginInfo;
	}

	public boolean saveLoginInfo () {
		return saveLoginInfo;
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

	private static class InvalidEmailFormatException extends MainController.InvalidFormatException {
		public InvalidEmailFormatException () {
			super("Email");
		}
	}

	private static class InvalidPhoneNumFormatException extends MainController.InvalidFormatException {
		public InvalidPhoneNumFormatException () {
			super("Phone number");
		}
	}

	private static class NegativeMoneyException extends Throwable {
		public NegativeMoneyException () {
			super("Initial Balance must be a positive number.");
		}
	}
}
