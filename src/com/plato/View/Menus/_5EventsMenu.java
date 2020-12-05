package View.Menus;

import java.util.Arrays;
import java.util.LinkedList;

public class _5EventsMenu extends Menu {
	private final boolean isForAdmin;

	protected _5EventsMenu (boolean isForAdmin) {
		super("Events Menu");
		this.isForAdmin = isForAdmin;
	}

	@Override
	public LinkedList<String> getOptions () {
		LinkedList<String> options;

		if (!isForAdmin) // for gamer account
			options = new LinkedList<>(Arrays.asList(
					"View event info",
					"Participate in event",
					"Show Events participating in",
					"Stop participating in Event"
			));

		else // for admin account
			options = new LinkedList<>(Arrays.asList(
					"Edit Event",
					"Remove Event"
			));

		options.addAll(super.getOptions());

		return options;
	}

	@Override
	public boolean canBack () {
		return true;
	}

	@Override
	public boolean canGoToAccMenu () {
		return true;
	}
}
