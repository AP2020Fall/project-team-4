package plato.Model.AccountRelated;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class FriendRequest {
	private final Gamer from, to;

	private static LinkedList<FriendRequest> allfriendRequests = new LinkedList<>();

	private FriendRequest (Gamer from, Gamer to) {
		this.from = from;
		this.to = to;
		allfriendRequests.addLast(this);
	}

	public static void addFriendReq (Gamer from, Gamer to) {
		allfriendRequests.add(new FriendRequest(from, to));
	}

	public void conclude (boolean accepted) {
		if (accepted) {
			from.addFrnd(to);
			to.addFrnd(from);
		}
		allfriendRequests.remove(this);
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

	public static boolean frndReqExists (String usernameFrom, String usernameTo) {
		return allfriendRequests.stream()
				.anyMatch(friendRequest -> friendRequest.getFrom().getUsername().equals(usernameFrom) && friendRequest.getTo().getUsername().equals(usernameTo));
	}

	public Gamer getFrom () {
		return from;
	}

	public Gamer getTo () {
		return to;
	}

	public static LinkedList<FriendRequest> getAllfriendRequests () {
		return allfriendRequests;
	}

	public static void setAllfriendRequests (LinkedList<FriendRequest> allfriendRequests) {
		FriendRequest.allfriendRequests = allfriendRequests;
	}
}
