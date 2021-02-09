package Model.AccountRelated;

import Controller.Client;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Controller.MyGson.getGson;

public class FriendRequest {
	//private static LinkedList<FriendRequest> allfriendRequests = new LinkedList<>();
	private final String fromUsername, toUsername;

	private FriendRequest (String fromUsername, String toUsername) {
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
	}

	public static void addFriendReq (String fromUsername, String toUsername) {
		getAllfriendRequests().add(new FriendRequest(fromUsername, toUsername));
	}

	public static void concludeFrndReq (String fromUsername, String toUsername, boolean accepted) {
		FriendRequest.getFriendReq(fromUsername, toUsername).conclude(accepted);
		LinkedList<FriendRequest> friendRequests = new LinkedList<>();
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();
		try {
			dataOutputStream.writeUTF("getAllFriendRequests");
			dataOutputStream.flush();

			friendRequests.addAll(getGson().fromJson(dataInputStream.readUTF(), new TypeToken<LinkedList<FriendRequest>>() {}.getType()));
			friendRequests.removeIf(friendRequest ->
					friendRequest.fromUsername.equals(fromUsername) &&
							friendRequest.toUsername.equals(toUsername));
			if (accepted)
				getAllfriendRequests().removeIf(friendRequest ->
						friendRequest.fromUsername.equals(toUsername) &&
								friendRequest.toUsername.equals(fromUsername));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static FriendRequest getFriendReq (String fromUsername, String toUsername) {
		return getAllfriendRequests().stream()
				.filter(friendRequest ->
						friendRequest.toUsername.equals(toUsername) &&
								friendRequest.fromUsername.equals(fromUsername)
				)
				.findAny().get();
	}

	public static LinkedList<FriendRequest> getFriendReq (String toUsername) {
		return getAllfriendRequests().stream()
				.filter(friendRequest -> friendRequest.toUsername.equals(toUsername))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static boolean frndReqExists (String usernameFrom) {
		return getAllfriendRequests().stream()
				.anyMatch(friendRequest -> friendRequest.fromUsername.equals(usernameFrom));
	}

	public static boolean frndReqExists (String fromUsername, String toUsername) {
		return getAllfriendRequests().stream()
				.anyMatch(friendRequest ->
						friendRequest.fromUsername.equals(fromUsername) &&
								friendRequest.toUsername.equals(toUsername));
	}

	public static LinkedList<FriendRequest> getAllfriendRequests () {
		LinkedList<FriendRequest> allFriendRequests = new LinkedList<>();
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();

		try {
			dataOutputStream.writeUTF("getAllFriendRequests_");
			dataOutputStream.flush();
			allFriendRequests.addAll(getGson().fromJson(dataInputStream.readUTF(), new TypeToken<LinkedList<FriendRequest>>() {}.getType()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return allFriendRequests;
	}

//	public static void setAllfriendRequests (LinkedList<FriendRequest> allfriendRequests) {
//		if (allfriendRequests == null)
//			allfriendRequests = new LinkedList<>();
//		FriendRequest.allfriendRequests = allfriendRequests;
//	}

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
