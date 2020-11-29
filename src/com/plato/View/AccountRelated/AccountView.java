package plato.View.AccountRelated;

import plato.Model.AccountRelated.Account;

public class AccountView {
	public static void diplayPersonalInfo (Account account) {
		// TODO: 11/28/2020 AD
	}

	public static void displayPtsPlayerGainedFromGameCommand (int points) {
		System.out.println("You have earned %d points from this game.".formatted(points));
	}
}
