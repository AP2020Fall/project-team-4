package plato.View.AccountRelated;

import plato.Model.AccountRelated.Account;

import java.util.LinkedList;

public class AccountView {
	public static void displayPersonalInfo (Account account) {
		// TODO: TODODODODOODODODOOD
	}

	public static void displayPtsPlayerGainedFromGameCommand (int points) {
		System.out.println("You have earned %d points from this game.".formatted(points));
	}

	public static void displayEditableFields (LinkedList<String> editableFields) {
		System.out.println("Choose field to edit:");
		for (String field : editableFields) {
			System.out.printf("%d. %s%n", editableFields.indexOf(field) + 1, field);
		}
	}
}
