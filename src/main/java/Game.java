import java.util.ArrayList;

public class Game {

    //attributes
    public static ArrayList<Game> listOfGames = new ArrayList<Game>();
    private int gameID;
    private String name;

    //constructor

    public Game(int gameID, String name) {
        this.gameID = gameID;
        this.name = name;
    }

    //getters and setters

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //other methods

    public void setScores(int gameID, String name) {}

    public void executeGame(int gameID, String name) {}

    //TODO : getGameByID method
}