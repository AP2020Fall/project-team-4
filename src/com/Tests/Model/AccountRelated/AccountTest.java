package Model.AccountRelated;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AccountTest {
	@AfterEach
	@BeforeEach
	public void clearAccounts () {
		Account.setAccounts(new LinkedList<>());
	}

	@Test
	public void editAccountFirstNameTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("fn", account.getFirstName());

		Account.getAccount("testAccUN").editField("first name", "editedFN");

		assertEquals("editedFN", account.getFirstName());
	}

	@Test
	public void editAccountLastNameTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("ln", account.getLastName());

		Account.getAccount("testAccUN").editField("last name", "editedLN");

		assertEquals("editedLN", account.getLastName());
	}

	@Test
	public void editAccountUsernameTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("testAccUN", account.getUsername());

		Account.getAccount("testAccUN").editField("username", "editedUN");

		assertEquals("editedUN", account.getUsername());
	}

	@Test
	public void editAccountPasswordTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("pw", account.getPassword());

		Account.getAccount("testAccUN").editField("password", "editedPW");

		assertEquals("editedPW", account.getPassword());
	}

	@Test
	public void editAccountEmailTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("test@gmail.com", account.getEmail());

		Account.getAccount("testAccUN").editField("email", "test2@gmail.com");

		assertEquals("test2@gmail.com", account.getEmail());
	}

	@Test
	public void editAccountPhoneNumTest () {
		Account.addAccount(Gamer.class, null,
				"fn",
				"ln", "testAccUN",
				"pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		assertEquals("00011122233", account.getPhoneNum());

		Account.getAccount("testAccUN").editField("phone num", "33322211100");

		assertEquals("33322211100", account.getPhoneNum());
	}

	@Test
	public void isEmailOkTest () {
		assertAll("checking is email ok",
				() -> assertFalse(Account.isEmailOK("")),
				() -> assertTrue(Account.isEmailOK("D@gmail.com")),
				() -> assertTrue(Account.isEmailOK("D.@gmail.com")),
				() -> assertTrue(Account.isEmailOK("D0@gmail.com")),
				() -> assertTrue(Account.isEmailOK("D_@gmail.com"))
		);
	}

	@Test
	public void isPhoneNumOkTest () {
		assertAll("checking is email ok",
				() -> assertFalse(Account.isPhoneNumOK("")),
				() -> assertTrue(Account.isPhoneNumOK("00011122233")),
				() -> assertFalse(Account.isPhoneNumOK("000111222332")),
				() -> assertTrue(Account.isPhoneNumOK("9999922233")),
				() -> assertFalse(Account.isPhoneNumOK("999992233"))
		);
	}

	@Test
	public void addAccountTest () {
		assertAll("add account tests",
				() -> {
					Account.addAccount(Admin.class, null, "1", "1", "1", "1", "1@gmail.com", "00011122233", 25);
					assertTrue(Account.accountExists("1"));
					assertTrue(Admin.adminHasBeenCreated());
					assertEquals("1", Admin.getAdmin().getUsername());
					assertEquals("1", Account.getAccount("1").getUsername());
				},
				() -> {
					Account.addAccount(Gamer.class, null, "2", "2", "2", "2", "2@gmail.com", "00011122233", 2);
					assertTrue(Account.accountExists("2"));
					assertEquals("2", Account.getAccount("2").getUsername());
				}
		);
	}

	@Test
	public void removeAccountTest () {
		Account.addAccount(Gamer.class, null, "1", "1", "1", "1", "1@gmail.com", "00011122233", 1);
		assumeTrue(Account.accountExists("1"));
		Account.removeAccount("1");
		assertFalse(Account.accountExists("1"));
	}
}
