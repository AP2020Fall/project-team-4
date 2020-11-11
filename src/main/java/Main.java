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



		public static void listGames(){}
		//lists all games
		//gets command open <game_name> and calls the method below

		public static void processCommand(String command){}
		//process command open <game_name> and calls the execution method
		//change menu if needed


	public void scoreboard(String gameName , int gameID){}  //TODO gets logID or gameID as parameter

	public void details(){}

	public void showLog(int gameID , String name){}

	public void showWinsCount(){}  //fixme gets logID or gameID as parameter

	public void showPlayedCount(){}  //fixme gets logID or gameID as parameter

	public void addFavorites(int gameID , int logID){}

	public void runGame(){}   //fixme gets logID or gameID as parameter

	public void showPoints(){}   //fixme gets logID or gameID as parameter


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