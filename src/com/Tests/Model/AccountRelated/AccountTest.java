package Model.AccountRelated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class AccountTest {
	@Test
	public void editAccountFirstNameTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("fn", account.getFirstName());

		Account.getAccount("testAccUN").editField("first name", "editedFN");

		Assertions.assertEquals("editedFN", account.getFirstName());
	}

	@Test
	public void editAccountLastNameTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("ln", account.getLastName());

		Account.getAccount("testAccUN").editField("last name", "editedLN");

		Assertions.assertEquals("editedLN", account.getLastName());
	}

	@Test
	public void editAccountUsernameTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("testAccUN", account.getUsername());

		Account.getAccount("testAccUN").editField("username", "editedUN");

		Assertions.assertEquals("editedUN", account.getUsername());
	}

	@Test
	public void editAccountPasswordTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("pw", account.getPassword());

		Account.getAccount("testAccUN").editField("password", "editedPW");

		Assertions.assertEquals("editedPW", account.getPassword());
	}

	@Test
	public void editAccountEmailTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("test@gmail.com", account.getEmail());

		Account.getAccount("testAccUN").editField("email", "test2@gmail.com");

		Assertions.assertEquals("test2@gmail.com", account.getEmail());
	}

	@Test
	public void editAccountPhoneNumTest () {
		Account.addAccount(Gamer.class,
				"fn", "ln",
				"testAccUN", "pw",
				"test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("00011122233", account.getPhoneNum());

		Account.getAccount("testAccUN").editField("phone num", "33322211100");

		Assertions.assertEquals("33322211100", account.getPhoneNum());
	}

	@Test
	public void isEmailOkTest () {
		Assertions.assertAll("checking is email ok",
				() -> Assertions.assertFalse(Account.isEmailOK("")),
				() -> Assertions.assertTrue(Account.isEmailOK("D@gmail.com")),
				() -> Assertions.assertTrue(Account.isEmailOK("D.@gmail.com")),
				() -> Assertions.assertTrue(Account.isEmailOK("D0@gmail.com")),
				() -> Assertions.assertTrue(Account.isEmailOK("D_@gmail.com"))
		);
	}

	@Test
	public void isPhoneNumOkTest () {
		Assertions.assertAll("checking is email ok",
				() -> Assertions.assertFalse(Account.isPhoneNumOK("")),
				() -> Assertions.assertTrue(Account.isPhoneNumOK("00011122233")),
				() -> Assertions.assertFalse(Account.isPhoneNumOK("000111222332")),
				() -> Assertions.assertTrue(Account.isPhoneNumOK("9999922233")),
				() -> Assertions.assertFalse(Account.isPhoneNumOK("999992233"))
		);
	}

	@Test
	public void addAccountTest () {
		Assertions.assertAll("add account tests",
				() -> {
					Account.addAccount(Admin.class, "1", "1", "1", "1", "1@gmail.com", "00011122233", 25);
					Assertions.assertTrue(Account.accountExists("1"));
					Assertions.assertTrue(Admin.adminHasBeenCreated());
					Assertions.assertEquals("1", Admin.getAdmin().getUsername());
					Assertions.assertEquals("1", Account.getAccount("1").getUsername());
				},
				() -> {
					Account.addAccount(Gamer.class, "2", "2", "2", "2", "2@gmail.com", "00011122233", 2);
					Assertions.assertTrue(Account.accountExists("2"));
					Assertions.assertEquals("2", Account.getAccount("2").getUsername());
				}
		);
	}

	@Test
	public void removeAccountTest () {
		Account.addAccount(Gamer.class, "1", "1", "1", "1", "1@gmail.com", "00011122233",1);
		Assumptions.assumeTrue(Account.accountExists("1"));
		Account.removeAccount("1");
		Assertions.assertFalse(Account.accountExists("1"));
	}
}
