package View.Menus;

import java.util.LinkedList;

public class _2LoginRegisterMenu extends Menu {
	protected _2LoginRegisterMenu (boolean adminHasBeenCreated) {
		super("Login/Registry Menu");
		if (adminHasBeenCreated)
			this.inMenu = true;
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
