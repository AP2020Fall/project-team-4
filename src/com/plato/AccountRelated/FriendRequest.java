package plato.AccountRelated;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class FriendRequest {
	private final Gamer from, to;

	private static LinkedList<FriendRequest> allfriendRequests = new LinkedList<>();

	public FriendRequest (Gamer from, Gamer to) {
		this.from = from;
		this.to = to;
		allfriendRequests.addLast(this);
	}

	public void conclude (boolean accepted) {
		if (accepted) {
			from.addFrnd(to);
			to.addFrnd(from);
		}
		allfriendRequests.remove(this);
	}

	public Gamer getFrom () {
		return from;
	}

	public Gamer getTo () {
		return to;
	}

	public LinkedList<FriendRequest> getAllfriendRequests () {
		return allfriendRequests;
	}

	public static FriendRequest getFriendReq (Gamer from, Gamer to) {
		return allfriendRequests.stream()
				.filter(friendRequest -> friendRequest.to.equals(to) && friendRequest.from.equals(from))
				.findAny().get();
	}

	public static LinkedList<FriendRequest> getFriendReq (Gamer to) {
		return (LinkedList<FriendRequest>) allfriendRequests.stream()
				.filter(friendRequest -> friendRequest.to.equals(to))
				.collect(Collectors.toList());
	}

	public static boolean frndReqExists (String usernameFrom) {
		return allfriendRequests.stream()
				.anyMatch(friendRequest -> friendRequest.getFrom().getUsername().equals(usernameFrom));
	}
}
