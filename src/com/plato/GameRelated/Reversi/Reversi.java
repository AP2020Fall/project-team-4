package plato.GameRelated.Reversi;
import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Reversi extends Game {

    public static String[][] board = new String[8][8];
    private static LinkedList<PlayerReversi> players = new LinkedList<>();
    private static ArrayList<String> scoreboard = new ArrayList<>();
    private int numberOfWhite;
    private int numberOfBlack;

    public Reversi(ArrayList<Gamer> gamers) {
        super(gamers);
        players = new LinkedList<>();
        players.addLast(new PlayerReversi(gamers.get(0)));
        players.addLast(new PlayerReversi(gamers.get(1)));
        numberOfWhite = 2;
        numberOfBlack = 2;
    }

    @Override
    public void setScores() {

    }

    @Override
    public void concludeGame() {   //fixme need method for showing the result when game ends
         // set end time
        // set Conclusion
        // remove game from gamesInSession
        // add to logs
        // add to scoreboard
    }

    @Override
    public boolean gameEnded() {   //fixme show result?
        return false;
    }

    @Override
    public String[][] getBoard() {
        return board;
    }

    @Override
    public String getGameName() {
        return null;
    }

    public void emptyBoard(){
        for(int i=0 ; i<8 ; i++){
            for(int j=0 ; j<8 ; j++){
                board[i][j] = " ";
            }
        }
    }

    public void showAvailableCoordinates(){}

    public void placeDisk(int x , int y , PlayerReversi player){}

    public void showDisks(){}

    public void getScore(){}

    public static LinkedList<PlayerReversi> getPlayers () {
        return players;
    }

    public boolean canPlayerPlaceDisk(PlayerReversi player){return true;}






}
