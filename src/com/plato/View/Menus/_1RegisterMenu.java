package View.Menus;

import java.util.LinkedList;

public class _1RegisterMenu extends Menu {
	protected _1RegisterMenu (boolean adminHasBeenCreated) {
		super("Registry Menu");
		if (!adminHasBeenCreated)
			this.inMenu = true;
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>() {{
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
