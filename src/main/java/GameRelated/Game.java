package GameRelated;

import java.util.ArrayList;

import src.main.java.IndependentClasses._Interactor.java

public abstract class Game {

	//attributes
	public static ArrayList<Game> listOfGames = new ArrayList<>();
	private int gameID;
	private String name;

	//constructor

	public Game (String name) {
		this.gameID = IDGe;
		this.name = name;
		listOfGames.add(this);
	}

	//getters and setters

	public int getGameID () {
		return gameID;
	}

	public void setGameID (int gameID) {
		this.gameID = gameID;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	//other methods

	public abstract void setScores ();

	public abstract void executeGame ();

	public Game getGameByID (int gameID) {
		for (Game game : listOfGames) {
			if (game.getGameID() == gameID) {return game;}
		}
		return null;
	}

	public boolean doesIDExist (int gameID) {return false;}

	//TODO : getGameByID method
}