package plato.View.GameRelated;

import plato.Model.GameRelated.BattleSea.BattleSea;
import plato.Model.GameRelated.Reversi.Reversi;
import plato.View.Menus.GameRelatedMenus.GameMenu;
import plato.View.Menus.Menu;

import java.util.LinkedList;

public class GameView {
	public static void displayTurn () {
		// TODO: 11/28/2020 AD  
	}

	public static void displayPtsPlayerGainedFromGame (int points, String gameName) {
		System.out.println("You have earned %d points from %s.".formatted(points, gameName));
	}

	public static void displaySuccessfulFaveGameAdditionMessage (String gameName) {
		System.out.printf("%s was added to your fave games successfully%n", gameName);
	}

	public static void displayGameHowToPlay (String gameName) {
		System.out.println(gameName.equals(BattleSea.class.getSimpleName()) ? BattleSea.getDetails(): Reversi.getDetails());
	}

	public static void displayScoreboardOfGame (String gameName, LinkedList<String> scoreBoard) {
		System.out.printf("%s Scoreboard:%n", gameName);
		scoreBoard.forEach(System.out::println);
	}
}
