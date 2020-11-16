package plato.AccountRelated;

import java.util.LinkedList;

public class FriendRequest {
	private final Account from, to;

	private LinkedList<FriendRequest> allfriendRequests = new LinkedList<>();

	public FriendRequest (Account from, Account to) {
		this.from = from;
		this.to = to;
		allfriendRequests.addLast(this);
	}


}
