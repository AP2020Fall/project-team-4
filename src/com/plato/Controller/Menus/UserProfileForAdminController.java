package Controller.Menus;

import Model.AccountRelated.Gamer;

public class UserProfileForAdminController {
	private static Gamer gamer;

	public static void setGamer (Gamer gamer) {
		UserProfileForAdminController.gamer = gamer;
	}
}
