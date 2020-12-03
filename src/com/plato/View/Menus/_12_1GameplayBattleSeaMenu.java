package plato.View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _12_1GameplayBattleSeaMenu extends Menu {
	/*
	 *	Used in getOptions()
	 *	i=1 -> Board editing phase
	 * 	i=2 -> Bombing phase 		=> todo set after board is finalized
	 */
	private int phase = 1;

	protected _12_1GameplayBattleSeaMenu () {
		super("BattleSea Gameplay Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		switch (phase) {
			case 1 -> options = new LinkedList<>(Arrays.asList(
					"Generate a Random Board",
					"Choose between 5 randomly generated boards",
					"Display on-trial Board",
					"Move ship",
					"Change ship direction"
			));
			case 2 -> options = new LinkedList<>(Arrays.asList(
					"Boom (Throw Bomb)",
					"Display all my ships",
					"Display all my booms",
					"Display all my opponent’s booms",
					"Display all my correct booms",
					"Display all my opponent’s correct booms",
					"Display all my incorrect booms",
					"Display all my opponent’s incorrect booms",
					"Display all my boomed ships",
					"Display all my opponent’s boomed ships",
					"Display all my unboomed ships",
					"Display my board",
					"Display my opponent’s board"
			));
			default -> options = new LinkedList<>();
		}

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return false;
	}
}