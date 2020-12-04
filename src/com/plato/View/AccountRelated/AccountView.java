package plato.View.AccountRelated;

import java.util.LinkedList;

public class AccountView {
	private static AccountView accountView;

	public static AccountView getInstance () {
		if (accountView == null)
			accountView = new AccountView();

		return accountView;
	}

	public void displayPersonalInfo (String username, String firstName, String lastName, String email, String phoneNum) {
		// TODO: TODODODODOODODODOOD
	}

	public void displayPtsPlayerGainedFromGameCommand (int points) {
		System.out.println("You have earned %d points from this game.".formatted(points));
	}

	public void displayEditableFields (LinkedList<String> editableFields) {
		System.out.println("Choose field to edit:");
		for (String field : editableFields) {
			System.out.printf("%d. %s%n", editableFields.indexOf(field) + 1, field);
		}
	}
}
