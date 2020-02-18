package memory.models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private List<Player> players;
    private final IntegerProperty nbPlayers;

    public Manager(List<Player> players, IntegerProperty nbPlayers) {
        this.players = players;
        this.nbPlayers = nbPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNbPlayers() {
        return nbPlayers.get();
    }

    public IntegerProperty nbPlayersProperty() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers.set(nbPlayers);
    }

    public void createPlayer(StringProperty playerName, IntegerProperty score, IntegerProperty rank){
        players.add(new Player(playerName,score,rank));
    }




}
