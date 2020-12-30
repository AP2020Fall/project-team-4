package View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _13UserInfoEditingMenu extends Menu {
	protected _13UserInfoEditingMenu () {
		super("User Info Editing Menu");
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options = new LinkedList<>(Arrays.asList(
				"Change password",
				"Edit Personal info"
		));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public void enter () {
		super.enter();
		// todo display a list of editable info of user
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

