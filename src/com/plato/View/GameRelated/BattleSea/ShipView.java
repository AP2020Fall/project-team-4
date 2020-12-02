package plato.View.GameRelated.BattleSea;

import plato.Model.GameRelated.BattleSea.Ship;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipView {
	/*
	used for changing editing board
	 */
	public static void displayShipsWithNamesForEditing (Ship[] board) {
		LinkedList<Character> shipNames = new LinkedList<>() {{
			add('A');
			add('B');
			add('C');
			add('D');
			add('E');
			add('F');
		}};
		Iterator<Character> shipNamesIt = shipNames.iterator();

		System.out.println("Ships: ");
		for (Ship ship : board)
			System.out.printf("\t%s, StartX=%d StartY=%d%n", shipNamesIt.next(), ship.getLeftMostX(), ship.getTopMostY());
	}

	/*
	used for mid-gameplay
	 */
	public static void displayShips (LinkedList<Ship> ships) {
		HashMap<String, AtomicInteger> shipSizes_Count = new HashMap<>();
		for (Ship ship : ships) {
			String key = "%d %d".formatted(ship.getL_SIZE(), ship.getS_SIZE());

			if (shipSizes_Count.containsKey(key))
				shipSizes_Count.get(key).incrementAndGet();
			else
				shipSizes_Count.put(key, new AtomicInteger(1));
		}

		System.out.println("|L Size|S Size|Count|");
		shipSizes_Count.forEach((key, value) -> System.out.printf("|  %d   |  %d   |  %d  |%n",
				Integer.parseInt(key.split(" ")[0]),
				Integer.parseInt(key.split(" ")[1]),
				value.get()
		));
	}
}
