package View.Menus;

import View.GameRelated.BattleSea.BattleSeaView;

import java.util.Arrays;
import java.util.LinkedList;

public class _11GameMenu extends Menu {
	private String gameName;

	protected _11GameMenu (String gameName) {
		super("Game Menu");
		this.gameName = gameName;
	}

	public _11GameMenu () {
		super("Game Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		options = new LinkedList<>(Arrays.asList(
				"Show scoreboard",
				"Show Points",
				"Details",
				"Show log of game",
				"Show wins count",
				"Show played count",
				"Add to favorites",
				"Continue previous games",
				"Run Game"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void back () {
		super.back();
		gameName = "";
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public void enter () {
		super.enter();
		gameName = gameName.trim();
		if (gameName.toLowerCase().startsWith("b")) {
			BattleSeaView.getInstance().initGameMenuView();
		}
		else if (gameName.toLowerCase().startsWith("r")) {
			// todo go to reversi menu
		}
	}

	@Override
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return true;
	}

	public String getGameName () {
		return gameName;
	}

	public void setGameName (String gameName) {
		this.gameName = gameName;
	}
}
