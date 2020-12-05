package Model.AccountRelated;

import Controller.IDGenerator;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AdminGameReco {
	private final String recoID;
	private final String gameName;
	private Gamer gamer;

	private static LinkedList<AdminGameReco> recommendations = new LinkedList<>();

	private AdminGameReco (String gameName, Gamer gamer) {
		this.recoID = IDGenerator.generateNext();
		this.gameName = gameName;
		this.gamer = gamer;
	}

	public static void addReco (String gameName, Gamer gamer) {
		recommendations.addLast(new AdminGameReco(gameName, gamer));
	}

	public static void removeReco (String recoID) {
		recommendations.remove(getRecommendation(recoID));
	}

	public static LinkedList<AdminGameReco> getRecommendations (Gamer gamer) {
		return (LinkedList<AdminGameReco>) recommendations.stream()
				.filter(recommendations -> recommendations.getGamer().getUsername().equals(gamer.getUsername()))
				.collect(Collectors.toList());
	}

	public static AdminGameReco getRecommendation (String recoID) {
		return recommendations.stream()
				.filter(reco -> reco.recoID.equals(recoID))
				.findAny().get();
	}

	public static boolean recommendationExists (String recoID) {
		return recommendations.stream()
				.anyMatch(reco -> reco.recoID.equals(recoID));
	}

	public static boolean recommendationExists (Gamer gamer, String gameName) {
		return recommendations.stream()
				.anyMatch(reco -> reco.gamer.getUsername().equals(gamer.getUsername()) && reco.gameName.equals(gameName));
	}

	public static LinkedList<AdminGameReco> getRecommendations () {
		return recommendations;
	}

	public static void setRecommendations (LinkedList<AdminGameReco> recommendations) {
		AdminGameReco.recommendations = recommendations;
	}

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
