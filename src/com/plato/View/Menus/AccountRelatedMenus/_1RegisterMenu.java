package plato.View.Menus.AccountRelatedMenus;

import plato.View.Menus.Menu;

import java.util.LinkedList;

public class _1RegisterMenu extends Menu {
	protected _1RegisterMenu () {
		super("Registry Menu");
		this.inMenu = true;

		new _2LoginRegisterMenu().setParent(this);
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>(){{
			add("Register Admin");
		}};

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public boolean canBack () {
		return false;
	}

	@Override
	public boolean canGoToAccMenu () {
		return false;
	}
}
