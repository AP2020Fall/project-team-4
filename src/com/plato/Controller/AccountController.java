package plato.Controller;

import plato.Model.AccountRelated.Account;

public class AccountController {
	private static Account currentAccLoggedIn = null;

	private static class RepeatedUsernameException extends Exception {
		public RepeatedUsernameException () {
			super("An account with this username already exists.");
		}
	}
}
