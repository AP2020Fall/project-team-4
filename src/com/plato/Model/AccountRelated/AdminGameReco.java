package Model.AccountRelated;

import Controller.Client;
import Controller.IDGenerator;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static Controller.MyGson.getGson;

public class AdminGameReco {
	//private static LinkedList<AdminGameReco> recommendations = new LinkedList<>();
	private final String recoID;
	private final String gameName;
	private final Gamer gamer;

	private AdminGameReco (String gameName, Gamer gamer) {
		this.recoID = IDGenerator.generateNext();
		this.gameName = gameName;
		this.gamer = gamer;
	}

	public static void addReco (String gameName, Gamer gamer) {
		getRecommendations().addLast(new AdminGameReco(gameName, gamer));
	}

	public static void removeReco (String recoID) {
		getRecommendations().remove(getRecommendation(recoID));
	}

	public static void removeReco (String gameName, Gamer gamer) {
		getRecommendations().remove(getRecommendation(gamer, gameName));
	}

	public static LinkedList<AdminGameReco> getRecommendations (Gamer gamer) {
		return getRecommendations().stream()
				.filter(recommendations -> recommendations.getGamer().getUsername().equals(gamer.getUsername()))
				.sorted(Comparator.comparing(AdminGameReco::getGameName))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static AdminGameReco getRecommendation (String recoID) {
		return getRecommendations().stream()
				.filter(reco -> reco.recoID.equals(recoID))
				.findAny().get();
	}

	public static AdminGameReco getRecommendation (Gamer gamer, String gameName) {
		return getRecommendations().stream()
				.filter(reco -> reco.getGamer().getUsername().equals(gamer.getUsername()) && reco.getGameName().equals(gameName))
				.findAny().get();
	}

	public static boolean recommendationExists (String recoID) {
		return getRecommendations().stream()
				.anyMatch(reco -> reco.recoID.equals(recoID));
	}

	public static boolean recommendationExists (Gamer gamer, String gameName) {
		return getRecommendations().stream()
				.anyMatch(reco -> reco.gamer.getUsername().equals(gamer.getUsername()) && reco.gameName.equalsIgnoreCase(gameName));
	}

	public static LinkedList<AdminGameReco> getRecommendations () {
		LinkedList<AdminGameReco> adminGameRecos = new LinkedList<>();
		DataOutputStream dataOutputStream = Client.getClient().getDataOutputStream();
		DataInputStream dataInputStream = Client.getClient().getDataInputStream();
		try {
			dataOutputStream.writeUTF("getAdminGameRecos");
			dataOutputStream.flush();

			adminGameRecos.addAll(getGson().fromJson(dataInputStream.readUTF(), new TypeToken<LinkedList<AdminGameReco>>() {}.getType()));
	}


		} catch (IOException e) {
			e.printStackTrace();
		}
		return adminGameRecos;
	}

//	public static void setRecommendations (LinkedList<AdminGameReco> recommendations) {
//		if (recommendations == null)
//			recommendations = new LinkedList<>();
//		AdminGameReco.getRecommendations() = recommendations;
//	}

	public Gamer getGamer () {
		return gamer;
	}

	public String getGamerUsername () {
		return gamer.getUsername();
	}

	public String getGameName () {
		return gameName;
	}

	public String getRecoID () {
		return recoID;
	}
}
