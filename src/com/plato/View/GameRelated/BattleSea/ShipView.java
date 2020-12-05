package View.GameRelated.BattleSea;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipView {
	private static ShipView shipView;

	public static ShipView getInstance () {
		if (shipView == null)
			shipView = new ShipView();
		return shipView;
	}

	/*
	used for changing editing board
	 */
	public void displayShipsWithNamesForEditing (LinkedList<String> shipsAndTheirStartingCoords) { // every string is in form -> "startx starty"
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
		for (String shipInfo : shipsAndTheirStartingCoords)
			System.out.printf("\t%s, StartX=%d StartY=%d%n", shipNamesIt.next(), shipInfo.split(" ")[0], shipInfo.split(" ")[1]);
	}

	/*
	used for mid-gameplay
	 */
	public void displayShips (LinkedList<String> shipsSizes) { // every string is in form -> "L_size S_size"
		HashMap<String, AtomicInteger> shipSizes_Count = new HashMap<>();
		for (String shipSizes : shipsSizes) {
			if (shipSizes_Count.containsKey(shipSizes))
				shipSizes_Count.get(shipSizes).incrementAndGet();
			else
				shipSizes_Count.put(shipSizes, new AtomicInteger(1));
		}

		System.out.println("|L Size|S Size|Count|");
		shipSizes_Count.forEach((key, value) -> System.out.printf("|  %d   |  %d   |  %d  |%n",
				Integer.parseInt(key.split(" ")[0]),
				Integer.parseInt(key.split(" ")[1]),
				value.get()
		));
	}
}
