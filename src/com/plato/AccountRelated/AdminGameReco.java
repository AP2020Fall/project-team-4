package plato.AccountRelated;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AdminGameReco {
	private final String gameName;
	private final Gamer gamer;

	private static LinkedList<AdminGameReco> recommendations = new LinkedList<>();

	public AdminGameReco (String gameName, Gamer gamer) {
		this.gameName = gameName;
		this.gamer = gamer;
		recommendations.addLast(this);
	}

	public static LinkedList<AdminGameReco> getRecommendations (Gamer gamer) {
		return (LinkedList<AdminGameReco>) recommendations.stream()
				.filter(recommendations -> recommendations.getGamer().getUsername().equals(gamer.getUsername()))
				.collect(Collectors.toList());
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
