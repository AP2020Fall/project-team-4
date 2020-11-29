package plato.View.Menus;

import java.util.HashMap;
import java.util.LinkedList;

public class LoginRegisterMenu extends Menu {
	protected LoginRegisterMenu () {
		super("Login/Registry Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>();

		options.add("Register");
		options.add("Login");
		options.add("Delete");

		options.addAll(super.getOptions());

		return options;
	}
}
