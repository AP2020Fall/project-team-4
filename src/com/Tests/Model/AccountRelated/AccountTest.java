package Model.AccountRelated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {
	@Test
	public void editPasswordTest () {
		// create arbitrary account
		Account.addAccount(Gamer.class, "1", "2", "testAccUN", "pw", "test@gmail.com", "00011122233", 50);

		Account account = Account.getAccount("testAccUN");

		Assertions.assertEquals("pw", account.getPassword());

		Account.getAccount("testAccUN").editField("password", "editedPW");

		Assertions.assertEquals("editedPW", account.getPassword());
	}
}
