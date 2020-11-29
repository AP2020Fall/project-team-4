package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;

public class AccountController {
	private static Account currentAccLoggedIn = null;

	public static void tryToLogin () {
		// TODO: 11/30/2020 AD
	}

	public static void tryToDeleteAccount () {
		// TODO: 11/30/2020 AD
	}

	public static void tryToRegister () {
		// TODO: 11/30/2020 AD
	}

	public static Account getCurrentAccLoggedIn () {
		return currentAccLoggedIn;
	}

	public static boolean isLoggedIn () {
		return currentAccLoggedIn != null;
	}

	private static class RepeatedUsernameException extends Exception {
		public RepeatedUsernameException () {
			super("An account with this username already exists.");
		}
	}
}
