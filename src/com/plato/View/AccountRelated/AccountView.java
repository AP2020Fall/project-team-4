package View.AccountRelated;

import View.Menus.Menu;

import java.util.LinkedList;

public class AccountView {
	private static AccountView accountView;

	public static AccountView getInstance () {
		if (accountView == null)
			accountView = new AccountView();

		return accountView;
	}

	public void displayPersonalInfo (String username, String firstName, String lastName, String email, String phoneNum) {
		System.out.printf("Username: %s%n", username);
		System.out.printf("First Name: %s\tLast Name: %s%n", firstName, lastName);
		System.out.printf("Email: %s\tPhone Number: %s%n", email, phoneNum);
	}

	public void displayPersonalInfo (String username, String firstName, String lastName, String email, String phoneNum, double money) {
		System.out.printf("Username: %s\tMoney: %.01f%n", username, money);
		System.out.printf("First Name: %s\tLast Name: %s%n", firstName, lastName);
		System.out.printf("Email: %s\tPhone Number: %s%n", email, phoneNum);
	}

	public void displayEditableFields (LinkedList<String> editableFields) {
		for (String field : editableFields) {
			System.out.printf("%d. %s%n", editableFields.indexOf(field) + 1, field);
		}
	}
}
