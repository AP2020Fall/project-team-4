package Model.AccountRelated;

import javafx.scene.image.Image;

public class Admin extends Account {
	protected Admin (Image pfp, String firstName, String lastName, String username, String password, String email, String phoneNum) {
		super(pfp, firstName, lastName, username, password, email, phoneNum);
	}

	// for serialization
	public Admin () {
		super();
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static Admin getAdmin () {
		return getAccounts().stream()
				.filter(account -> account instanceof Admin)
				.map(account -> ((Admin) account))
				.findAny().get();
	}

	public static void setAdmin (Admin admin) {
		getAccounts().add(admin);
	}

	public static boolean adminHasBeenCreated () {
		return getAccounts().stream()
				.anyMatch(account -> account instanceof Admin);
	}

/*
	adminPic.setImage(Admin.get.admin()).getAdminPic()
*/

}

