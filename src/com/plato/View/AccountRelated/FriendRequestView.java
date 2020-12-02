package plato.View.AccountRelated;

import java.util.LinkedList;

public class FriendRequestView {
	public static void displayFrndReqsPlayerGotten (LinkedList<String> friendRequestsGottenUsernames) {
		System.out.println("Friend requests: ");
		friendRequestsGottenUsernames.stream()
				.forEach(fromAccount -> System.out.printf("\t%s%n", fromAccount));
	}
}
