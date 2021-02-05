package Model.AccountRelated;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class FriendRequest {
	private static LinkedList<FriendRequest> allfriendRequests = new LinkedList<>();
	private final String fromUsername, toUsername;

	private FriendRequest (String fromUsername, String toUsername) {
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
	}

	public static void addFriendReq (String fromUsername, String toUsername) {
		allfriendRequests.add(new FriendRequest(fromUsername, toUsername));
	}

	public static void concludeFrndReq (String fromUsername, String toUsername, boolean accepted) {
		FriendRequest.getFriendReq(fromUsername, toUsername).conclude(accepted);
		allfriendRequests.removeIf(friendRequest ->
				friendRequest.fromUsername.equals(fromUsername) &&
						friendRequest.toUsername.equals(toUsername));
		if (accepted)
			allfriendRequests.removeIf(friendRequest ->
					friendRequest.fromUsername.equals(toUsername) &&
							friendRequest.toUsername.equals(fromUsername));
	}

	public static FriendRequest getFriendReq (String fromUsername, String toUsername) {
		return allfriendRequests.stream()
				.filter(friendRequest ->
						friendRequest.toUsername.equals(toUsername) &&
								friendRequest.fromUsername.equals(fromUsername)
				)
				.findAny().get();
	}

	public static LinkedList<FriendRequest> getFriendReq (String toUsername) {
		return allfriendRequests.stream()
				.filter(friendRequest -> friendRequest.toUsername.equals(toUsername))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static boolean frndReqExists (String usernameFrom) {
		return allfriendRequests.stream()
				.anyMatch(friendRequest -> friendRequest.fromUsername.equals(usernameFrom));
	}

	public static boolean frndReqExists (String fromUsername, String toUsername) {
		return allfriendRequests.stream()
				.anyMatch(friendRequest ->
						friendRequest.fromUsername.equals(fromUsername) &&
								friendRequest.toUsername.equals(toUsername));
	}

	public static LinkedList<FriendRequest> getAllfriendRequests () {
		return allfriendRequests;
	}

	public static void setAllfriendRequests (LinkedList<FriendRequest> allfriendRequests) {
		if (allfriendRequests == null)
			allfriendRequests = new LinkedList<>();
		FriendRequest.allfriendRequests = allfriendRequests;
	}

	private void conclude (boolean accepted) {
		if (accepted) {
			((Gamer) Account.getAccount(fromUsername)).addFrnd(toUsername);
			((Gamer) Account.getAccount(toUsername)).addFrnd(fromUsername);
		}
	}

	public String getFromUsername () {
		return fromUsername;
	}

	public String getToUsername () {
		return toUsername;
	}
}
