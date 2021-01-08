package Controller.AccountRelated;

import Model.AccountRelated.FriendRequest;
import Model.AccountRelated.Gamer;
import View.AccountRelated.FriendRequestView;

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
		FriendRequest.concludeFrndReq(
				usernameFrom,
				AccountController.getInstance().getCurrentAccLoggedIn().getUsername(),
				true);
	}

	public void declineFriendReq (String usernameFrom) {
		FriendRequest.concludeFrndReq(
				usernameFrom,
				AccountController.getInstance().getCurrentAccLoggedIn().getUsername(),
				false);
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
