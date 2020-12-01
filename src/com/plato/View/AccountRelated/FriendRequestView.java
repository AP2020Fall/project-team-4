package plato.View.AccountRelated;

import plato.Controller.AccountRelated.AccountController;
import plato.Model.AccountRelated.Account;
import plato.Model.AccountRelated.FriendRequest;
import plato.Model.AccountRelated.Gamer;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class FriendRequestView {
	public static void displayFrndReqsPlayerGotten (LinkedList<FriendRequest> friendRequestsGotten) {
		System.out.println("Friend requests: ");
		friendRequestsGotten.stream()
				.map(FriendRequest::getFrom)
				.forEach(fromAccount -> System.out.printf("\t%s%n", fromAccount.getUsername()));
	}
}
