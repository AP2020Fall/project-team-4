package plato.View.GameRelated.BattleSea;

import plato.Model.GameRelated.BattleSea.Bomb;

import java.util.LinkedList;

public class BombView {
	public static void displayBombs (LinkedList<Bomb> bombs) {
		System.out.println("| X\t| Y\t|");
		bombs.forEach(bomb -> System.out.printf("| %d\t| %d\t|", bomb.getX(), bomb.getY()));
	}
}