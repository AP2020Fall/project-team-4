package plato.View.Menus.GameRelatedMenus;

import plato.View.Menus.Menu;

import java.util.HashMap;
import java.util.LinkedList;

public class GameplayBattleSeaMenu extends Menu {
	/*
	 *	Used in getOptions()
	 *	i=1 -> Board editing phase
	 * 	i=2 -> Bombing phase 		=> todo set after board is finalized
	 */
	private int phase = 1;

	protected GameplayBattleSeaMenu () {
		super("BattleSea Gameplay Menu");
	}
}
