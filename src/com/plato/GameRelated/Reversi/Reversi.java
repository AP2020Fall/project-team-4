package plato.GameRelated.Reversi;
import plato.AccountRelated.Gamer;
import plato.GameRelated.Game;

import java.util.ArrayList;

public class Reversi extends Game {

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
        return new String[0][];
    }

    @Override
    public String getGameName() {
        return null;
    }

    public void showAvailableCoordinates(){}

    public void placeDisk(int x , int y , Gamer gamer){}

    public void showDisks(){}

    public void getScore(){}




}
