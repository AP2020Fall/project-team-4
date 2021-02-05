package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import View.AccountRelated.AccountView;
import javafx.scene.image.Image;

public class AccountController {
	private static AccountController accountController;
	private Account currentAccLoggedIn = null;
	private boolean saveLoginInfo = false; // ask at login. also set to false when logout

	public static AccountController getInstance () {
		if (accountController == null)
			accountController = new AccountController();
		return accountController;
	}

	public void login (String username, String password, boolean rememberMe) throws MainController.InvalidFormatException, NoAccountExistsWithUsernameException, PaswordIncorrectException, MainController.SuccessfulOperationException {

		if (!username.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Username");

		if (!Account.accountExists(username))
			throw new NoAccountExistsWithUsernameException();

		if (!Account.getAccount(username).isPasswordCorrect(password))
			throw new PaswordIncorrectException();

		throw new MainController.SuccessfulOperationException();
//		saveLoginInfo = rememberMe;
//		currentAccLoggedIn = Account.getAccount(username);
	}

	public void deleteAccount (String username, String password) throws MainController.InvalidFormatException, NoAccountExistsWithUsernameException, AdminAccountCantBeDeletedException, PaswordIncorrectException {
		if (!username.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Username");

		if (!Account.accountExists(username))
			throw new NoAccountExistsWithUsernameException();

		if (Account.getAccount(username) instanceof Admin)
			throw new AdminAccountCantBeDeletedException();

		if (!Account.getAccount(username).isPasswordCorrect(password))
			throw new PaswordIncorrectException();

		Account.removeAccount(username);
	}

	public void register (Image pfp, String username, String password, String firstName, String lastName, String email, String phoneNum, double initMoney) throws MainController.InvalidFormatException, AccountWithUsernameAlreadyExistsException, MainController.SuccessfulOperationException {

		if (!username.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Username");
		if (!password.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Password");

		if (Account.accountExists(username))
			throw new AccountWithUsernameAlreadyExistsException();

		if (!firstName.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("First Name");
		if (!lastName.matches("[!-~]+"))
			throw new MainController.InvalidFormatException("Last Name");

		if (!Account.isEmailOK(email))
			throw new InvalidEmailFormatException();

		if (!Account.isPhoneNumOK(phoneNum))
			throw new InvalidPhoneNumFormatException();

		throw new MainController.SuccessfulOperationException();
	}

	public void changePWCommand (String oldPW, String newPW) throws PaswordIncorrectException {

		if (!AccountController.getInstance().getCurrentAccLoggedIn().isPasswordCorrect(oldPW))
			throw new PaswordIncorrectException();

		AccountController.getInstance().getCurrentAccLoggedIn().editField("password", newPW);
	}

	public void editAccField (String field, String newVal) throws MainController.InvalidFormatException, AccountWithUsernameAlreadyExistsException {
		switch (field.toLowerCase()) {
			case "first name" -> {
				if (!newVal.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("New first name");
			}
			case "last name" -> {
				if (!newVal.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("New last name");
			}
			case "username" -> {
				if (!newVal.matches("[!-~]+"))
					throw new MainController.InvalidFormatException("New username");

				if (Account.accountExists(newVal))
					throw new AccountWithUsernameAlreadyExistsException();
			}
			case "email" -> {
				if (!Account.isEmailOK(newVal))
					throw new InvalidEmailFormatException();
			}
			case "phone number" -> {
				if (!Account.isPhoneNumOK(newVal))
					throw new InvalidPhoneNumFormatException();
			}
			case "pfp url" -> {}
			default -> throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
		}
//		getCurrentAccLoggedIn().editField(field.toLowerCase(), newVal);
		Account.getAccount(currentAccLoggedIn.getUsername()).editField(field.toLowerCase(), newVal);
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

    public void editAccFieldCommand() {
    }

    public static class NoAccountExistsWithUsernameException extends Exception {
		public NoAccountExistsWithUsernameException () {
			super("No account exists with this username.");
		}
	}

	public static class SuccessfulLogin extends MainController.SuccessfulOperationException {
		public SuccessfulLogin () {
			super();
		}
	}

	public static class AccountWithUsernameAlreadyExistsException extends Exception {
		public AccountWithUsernameAlreadyExistsException () {
			super("An Account already exists with this username.");
		}
	}

	public static class PaswordIncorrectException extends Exception {
		public PaswordIncorrectException () {
			super("Password incorrect.");
		}
	}



	public static class AdminAccountCantBeDeletedException extends Exception {
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

	public static class NegativeMoneyException extends Throwable {
		public NegativeMoneyException () {
			super("Initial Balance must be a positive number.");
		}
	}
}
