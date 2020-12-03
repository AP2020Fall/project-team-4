package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class _2LoginRegisterMenu extends Menu {
	protected _2LoginRegisterMenu () {
		super("Login/Registry Menu");
	}

	@Override
	public LinkedList<String> getOptions () {

		LinkedList<String> options = new LinkedList<>() {{
			add("Register Gamer");
			add("Login");
			add("Delete Account");
		}};

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public boolean canGoToAccMenu () {
		return false;
	}

	@Override
	public boolean canBack () {
		return false;
	}
}
