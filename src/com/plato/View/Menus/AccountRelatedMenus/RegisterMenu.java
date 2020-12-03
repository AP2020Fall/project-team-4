package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class RegisterMenu extends Menu {
	protected RegisterMenu () {
		super("Registry Menu");
		this.inMenu = true;

		new LoginRegisterMenu().setParent(this);
	}

	@Override
	public boolean canBack () {
		return false;
	}
}
