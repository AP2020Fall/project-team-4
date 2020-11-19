package plato.GameRelated.Reversi;
import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Reversi extends Game {

    private static String[][] board = new String[8][8];
    private static LinkedList<PlayerReversi> players = new LinkedList<>();
    private static ArrayList<String> scoreboard = new ArrayList<>();
    private ArrayList<String> moves = new ArrayList<>();
    private int numberOfWhite = 2;
    private int numberOfBlack = 2;

    public Reversi(ArrayList<Gamer> gamers) {
        super(gamers);
        players = new LinkedList<>();
        players.addLast(new PlayerReversi(gamers.get(0) , "black"));
        players.addLast(new PlayerReversi(gamers.get(1) , "white"));
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

    public void emptyBoard(){
        for(int i=0 ; i<8 ; i++){
            for(int j=0 ; j<8 ; j++){
                //fixme 4 starting disks
                board[i][j] = " ";
            }
        }
    }
  
    public ArrayList<String> getAvailableCoordinates(){return null;}

    public void placeDisk(int x , int y){}

    //is called after next turn
    private boolean canPlayerPlaceAnyDisks(){return true;}

    private boolean canPlayerPlaceDiskHere(int x , int y){return true;}

    private boolean doesAnyDiskChangeColor(int x , int y , Direction dir){return true;}

    private boolean doesAnyDiskChangeColor(int x , int y){return true;}

    //gets start and destination and changes the color of in between disks
    private void changeColor(int startx , int starty , int destx , int desty){}

    private void checkDirections(int x , int y) {
        for (Direction dir : Direction.values()) {
            if (doesAnyDiskChangeColor(x, y, dir)) {changeColor(x, y, x + dir.getDeltaX(), y + dir.getDeltaY());}
        }

    }

    public static LinkedList<PlayerReversi> getPlayers () {
        return players;
    }

    public void addMove(int x , int y , String color){}

    public ArrayList<String> getMoves() {
        return moves;
    }

    private boolean isBoardFull(){return false;}

    private boolean hasPlayerMoved(){return true;}
}

enum Direction{
    UP(0, -1),
    UP_RIGHT(1,-1),
    RIGHT(1,0),
    DOWN_RIGHT(1,1),
    DOWN(0,1),
    DOWN_LEFT(-1,1),
    LEFT(-1,0),
    UP_LEFT(-1,-1);

    private final int deltaX, deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
