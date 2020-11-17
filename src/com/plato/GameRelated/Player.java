package plato.GameRelated;

import plato.AccountRelated.Gamer;

abstract public class Player {

    private final Gamer gamer;
    private int score = 0;

    protected Player(Gamer gamer) {
        this.gamer = gamer;
    }


}
