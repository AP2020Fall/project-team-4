package plato.GameRelated.Reversi;
import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;

public class Reversi extends Game {

    public static String[][] board = new String[8][8];

    public Reversi(ArrayList<Gamer> players) {
        super(players);
    }

    @Override
    public void setScores() {

    }

    @Override
    public void concludeGame() {   //fixme need method for showing the result when game ends

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

    public boolean canPlayerPlaceDisk(PlayerReversi player){return true;}




}
