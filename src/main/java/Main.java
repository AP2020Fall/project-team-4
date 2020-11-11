import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	// regexes

	private static Menu currentMenu; // FIXME: initialize

	// io stuff
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
	private static StringBuilder outputSB = new StringBuilder();

	public static void main (String[] args) {

	}

	private static Matcher getMatcher (String text, String regex) {
		return Pattern.compile(regex).matcher(text);
	}
}
enum Menu {
	ACC_MENU,
	MAIN_MENU,
	GAMES_MENU,
	FRIENDS_PAGE,
	REGISTER_MENU,
	LOGIN_MENU,
	GAME_MENU; // fixme might need adding one for each game
	private static Menu back;

	public static void setBack (Menu back) {
		Menu.back = back;
	}

	public static Menu getBack () {
		return back;
	}
}