package Model.AccountRelated;

import Controller.MyGson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static Controller.Client.getClient;
import static java.lang.Boolean.parseBoolean;

public class Admin extends Account {
	protected Admin (String pfp, String firstName, String lastName, String username, String password, String email, String phoneNum) {
		super(pfp, firstName, lastName, username, password, email, phoneNum);
	}

	public static Admin getAdmin () {
		try {
			return MyGson.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return getAccounts().stream()
//				.filter(account -> account instanceof Admin)
//				.map(account -> ((Admin) account))
//				.findAny().get();
		return null;
	}

	public static void setAdmin (Admin admin) {
		if (admin != null)
			getAccounts().add(admin);
	}

	public static boolean adminHasBeenCreated () {
		try {
			DataOutputStream dataOutputStream = getClient().getDataOutputStream();
			dataOutputStream.writeUTF("adminHasBeenCreated");
			dataOutputStream.flush();
			DataInputStream dataInputStream = getClient().getDataInputStream();
			return parseBoolean(dataInputStream.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return getAccounts().stream()
//				.anyMatch(account -> account instanceof Admin);
		return false;
	}
}

