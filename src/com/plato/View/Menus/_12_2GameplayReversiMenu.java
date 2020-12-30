package View.Menus;

import Controller.GameRelated.GameController;

import java.util.Arrays;
import java.util.LinkedList;

public class _12_2GameplayReversiMenu extends Menu {
	protected _12_2GameplayReversiMenu () {
		super("Reversi Gameplay Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Place Disk",
				"Next Turn",
				"Whose turn now?",
				"Display available coordinates",
				"Display Board (Grid)",
				"Display disks",
				"Display scores",
				"Display final result"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		super.enter();
		// todo enter reversi game
	}

	@Override
	public void back () {
		super.back();
		GameController.getInstance().setCurrentGameInSession(null);
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
