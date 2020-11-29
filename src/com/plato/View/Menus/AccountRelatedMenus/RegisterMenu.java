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
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();

		options.add("Register");

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public boolean canBack () {
		return false;
	}
}
