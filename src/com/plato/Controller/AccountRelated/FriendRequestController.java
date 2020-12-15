package Controller.AccountRelated;

import Controller.MainController;
import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.FriendRequest;
import Model.AccountRelated.Gamer;
import View.AccountRelated.FriendRequestView;
import View.Menus.Menu;

import java.util.LinkedList;

public class FriendRequestController {
	private static FriendRequestController friendRequestController;

	public static FriendRequestController getInstance () {
		if (friendRequestController == null)
			friendRequestController = new FriendRequestController();
		return friendRequestController;
	}

	public void sendFrndRequest () {
		String usernameTo;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); usernameTo = Menu.getInputLine();

				if (usernameTo.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(usernameTo))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (usernameTo.equals(AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new CantSendFriendRendReqToYourselfException();

				if (Account.getAccount(usernameTo) instanceof Admin)
					throw new CantFriendRequestTheAdminException();

				if (((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).frndExists(usernameTo))
					throw new CantSendFriendReqToAlreadyFriendException(usernameTo);

				if (FriendRequest.frndReqExists(AccountController.getInstance().getCurrentAccLoggedIn().getUsername(), usernameTo))
					throw new FriendRequestAlreadyExistsException(usernameTo);
				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | CantSendFriendRendReqToYourselfException | CantFriendRequestTheAdminException |
					CantSendFriendReqToAlreadyFriendException | FriendRequestAlreadyExistsException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		Menu.displayAreYouSureMessage();
		if (Menu.getInputLine().trim().equalsIgnoreCase("y")) {
			((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).sendFrndReq(usernameTo);
			Menu.printSuccessfulOperation("You have successfully sent a friend request to " + usernameTo + ".");
		}
	}

	public void displayFrndReqsPlayerGotten () {
		LinkedList<String> frndReqs = new LinkedList<>() {{
			for (FriendRequest friendRequest : ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFriendRequestsGotten())
				add(friendRequest.getFromUsername());
		}};

		FriendRequestView.getInstance().displayFrndReqsPlayerGotten(frndReqs);
		if (frndReqs.size() > 0)
			MainController.enterAppropriateMenu();
	}

	public void acceptFriendReq () {
		String usernameFrom;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); usernameFrom = Menu.getInputLine();

				if (usernameFrom.trim().equalsIgnoreCase("/c")) return;

				if (!Account.accountExists(usernameFrom))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (!FriendRequest.frndReqExists(usernameFrom, AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new FriendRequestDoesntExistException();

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | FriendRequestDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		FriendRequest.concludeFrndReq(
				usernameFrom,
				AccountController.getInstance().getCurrentAccLoggedIn().getUsername(),
				true);
	}

	public void declineFriendReq () {
		String usernameFrom;
		while (true)
			try {
				Menu.printAskingForInput("Username:[/c to cancel] "); usernameFrom = Menu.getInputLine();

				if (usernameFrom.trim().equals("/c")) return;

				if (!Account.accountExists(usernameFrom))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (!FriendRequest.frndReqExists(usernameFrom, AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new FriendRequestDoesntExistException();

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | FriendRequestDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

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
