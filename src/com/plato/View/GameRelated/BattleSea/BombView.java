package plato.View.GameRelated.BattleSea;

import java.util.LinkedList;

public class BombView {
	public static void displayBombs (LinkedList<String> bombs) { // every string is in form -> "x y"
		System.out.println("| X\t| Y\t|");
		bombs.forEach(bomb -> System.out.printf("| %d\t| %d\t|",
				Integer.parseInt(bomb.split(" ")[0]),
				Integer.parseInt(bomb.split(" ")[1])
		));
	}
}