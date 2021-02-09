package Controller.AccountRelated;

import Controller.Client;
import Controller.MyGson;
import Model.AccountRelated.Account;
import Model.AccountRelated.FriendRequest;
import Model.AccountRelated.Gamer;
import View.AccountRelated.FriendRequestView;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class FriendRequestController {
	private static FriendRequestController friendRequestController;

	public static FriendRequestController getInstance () {
		if (friendRequestController == null)
			friendRequestController = new FriendRequestController();
		return friendRequestController;
	}

	public void sendFrndRequest (String usernameTo) {
		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).sendFrndReq(usernameTo);
	}

	public void displayFrndReqsPlayerGotten () {
		LinkedList<String> frndReqs = new LinkedList<>() {{
			for (FriendRequest friendRequest : ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFriendRequestsGotten())
				add(friendRequest.getFromUsername());
		}};

		FriendRequestView.getInstance().displayFrndReqsPlayerGotten(frndReqs);
		if (frndReqs.size() > 0) {
//			MainController.enterAppropriateMenu();
		}
	}

	public void acceptFriendReq (String usernameFrom) {
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();
		try {
			Account loggedIn = MyGson.getGson().fromJson(dataInputStream.readUTF() , new TypeToken<Account>() {}.getType());
			FriendRequest.concludeFrndReq(
					usernameFrom,loggedIn.getUsername() ,
					true);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

	}

	public void declineFriendReq (String usernameFrom) {
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();
		try {
			Account loggedIn = MyGson.getGson().fromJson(dataInputStream.readUTF() , new TypeToken<Account>() {}.getType());
			FriendRequest.concludeFrndReq(
					usernameFrom,loggedIn.getUsername() ,
					false);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

	}

	private static class FriendRequestDoesntExistException extends Exception {
		public FriendRequestDoesntExistException () {
			super("You haven't gotten a friend request from this account.");
		}
	}

	private static class CantSendFriendReqToAlreadyFriendException extends Exception {
		public CantSendFriendReqToAlreadyFriendException (String alrFrndUsername) {
			super("You already are friends with " + alrFrndUsername);
		}
	}

	private static class CantFriendRequestTheAdminException extends Exception {
		public CantFriendRequestTheAdminException () {
			super("You can't send friend request to admin.");
		}
	}

	private static class CantSendFriendRendReqToYourselfException extends Exception {
		public CantSendFriendRendReqToYourselfException () {
			super("You can't send yourself a friend request");
		}
	}

	private static class FriendRequestAlreadyExistsException extends Exception {
		public FriendRequestAlreadyExistsException (String usernameTo) {
			super("You have already sent a friend request to " + usernameTo);
		}
	}
}
