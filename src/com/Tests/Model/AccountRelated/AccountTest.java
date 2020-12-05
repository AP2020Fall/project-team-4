package Model.AccountRelated;

import Controller.MainController;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class AccountTest {
	@BeforeEach
	public void deserialize () {
		try {
			MainController.getInstance().deserialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editFieldTest () {
		Assumptions.assumeTrue(Account.accountExists("dreww"));
		Account account = Account.getAccount("dreww");

		Assertions.assertEquals("pw", account.getPassword());

		Account.getAccount("dreww").editField("password", "editedPW");

		Assertions.assertEquals("editedPW", account.getPassword());
	}

	@AfterEach
	public void serialize () {
		try {
			MainController.getInstance().serialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
