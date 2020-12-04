package plato.Controller.AccountRelated;

import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.Admin;
import plato.Model.AccountRelated.FriendRequest;
import plato.Model.AccountRelated.Gamer;
import plato.View.AccountRelated.FriendRequestView;
import plato.View.Menus.Menu;

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
				System.out.print("Username:[/cancel to cancel filling form] "); usernameTo = Menu.getInputLine();

				if (usernameTo.trim().toLowerCase().equals("/cancel")) return;

				if (((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).frndExists(usernameTo))
					throw new CantSendFriendReqToAlreadyFriendException(usernameTo);

				if (!Account.accountExists(usernameTo))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (Account.getAccount(usernameTo) instanceof Admin)
					throw new CantFriendRequestTheAdminException();
				break;
			} catch (CantSendFriendReqToAlreadyFriendException | AccountController.NoAccountExistsWithUsernameException | CantFriendRequestTheAdminException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).sendFrndReq(usernameTo);
	}

	public void displayFrndReqsPlayerGotten () {
		FriendRequestView.getInstance().displayFrndReqsPlayerGotten(new LinkedList<>() {{
			for (FriendRequest friendRequest : ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()).getFriendRequestsGotten())
				add(friendRequest.getFrom().getUsername());
		}});
		//todo enter submenu to be able to accept/decline friend requests
	}

	public void acceptFriendReq () {
		String usernameFrom;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); usernameFrom = Menu.getInputLine();

				if (usernameFrom.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.accountExists(usernameFrom))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (!FriendRequest.frndReqExists(usernameFrom, AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new FriendRequestDoesntExistException();

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | FriendRequestDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		FriendRequest.getFriendReq(((Gamer) Account.getAccount(usernameFrom)), ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()))
				.conclude(true);
	}

	public void declineFriendReq () {
		String usernameFrom;
		while (true)
			try {
				System.out.print("Username:[/cancel to cancel filling form] "); usernameFrom = Menu.getInputLine();

				if (usernameFrom.trim().toLowerCase().equals("/cancel")) return;

				if (!Account.accountExists(usernameFrom))
					throw new AccountController.NoAccountExistsWithUsernameException();

				if (!FriendRequest.frndReqExists(usernameFrom, AccountController.getInstance().getCurrentAccLoggedIn().getUsername()))
					throw new FriendRequestDoesntExistException();

				break;
			} catch (AccountController.NoAccountExistsWithUsernameException | FriendRequestDoesntExistException e) {
				Menu.printErrorMessage(e.getMessage());
			}

		FriendRequest.getFriendReq(((Gamer) Account.getAccount(usernameFrom)), ((Gamer) AccountController.getInstance().getCurrentAccLoggedIn()))
				.conclude(false);
	}

	private static class FriendRequestDoesntExistException extends Exception {
		public FriendRequestDoesntExistException () {
			super("You haven't gotten a friend request from this account.");
		}
	}

	private static class CantSendFriendReqToAlreadyFriendException extends Exception {
		public CantSendFriendReqToAlreadyFriendException (String alrFriendUsername) {
			super("You already are friends with " + alrFriendUsername);
		}
	}

	private static class CantFriendRequestTheAdminException extends Exception {
		public CantFriendRequestTheAdminException () {
			super("You can't send friend request to admin.");
		}
	}
}
