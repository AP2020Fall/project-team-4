package plato.AccountRelated;

import plato.IDGenerator;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AdminGameReco {
	private final String recoID;
	private final String gameName;
	private final Gamer gamer;

	private static LinkedList<AdminGameReco> recommendations = new LinkedList<>();

	public AdminGameReco (String gameName, Gamer gamer) {
		this.recoID = IDGenerator.generateNext();
		this.gameName = gameName;
		this.gamer = gamer;
		recommendations.addLast(this);
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

	public static LinkedList<AdminGameReco> getRecommendations () {
		return recommendations;
	}

	public Gamer getGamer () {
		return gamer;
	}

	public String getGameName () {
		return gameName;
	}
}
