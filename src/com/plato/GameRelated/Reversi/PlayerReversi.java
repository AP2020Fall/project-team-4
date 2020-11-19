package plato.GameRelated.Reversi;

import plato.AccountRelated.Gamer;
import plato.GameRelated.BattleSea.PlayerBattleSea;
import plato.GameRelated.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayerReversi extends Player {

    private final String color;

    public PlayerReversi(Gamer gamer , String color) {
        super(gamer);
        this.color = color;
    }

    @Override
    public Player getPlayer(Gamer gamer){return null;}

    @Override
    public PlayerReversi getOpponent () {
        return (PlayerReversi) super.getOpponent();
    }

    public String getColor() {
        return color;
    }
}


