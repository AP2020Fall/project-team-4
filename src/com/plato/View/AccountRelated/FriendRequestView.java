package View.AccountRelated;

import java.util.LinkedList;

public class FriendRequestView {
	private static FriendRequestView friendRequestView;

	public static FriendRequestView getInstance () {
		if (friendRequestView == null)
			friendRequestView = new FriendRequestView();
		return friendRequestView;
	}

	public void displayFrndReqsPlayerGotten (LinkedList<String> friendRequestsGottenUsernames) {
		if (friendRequestsGottenUsernames.size()==0) {
			System.out.println("You haven't received any friend requests.");
			return;
		}
		System.out.println("Friend requests: ");
		friendRequestsGottenUsernames.stream()
				.forEach(fromAccount -> System.out.printf("\t%s%n", fromAccount));
	}
}
