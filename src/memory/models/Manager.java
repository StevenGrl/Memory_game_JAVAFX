package memory.models;

import java.util.List;

public class Manager {

    private List<Player> players;
    private int nbPlayers;
    private static Player currentPlayer;

    public Manager(List<Player> players) {
        this.setPlayers(players);
        this.setNbPlayers(players.size());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public int nbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public void createPlayer(String playerName, int score, int rank){
        players.add(new Player(playerName, score, rank));
    }


    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Manager.currentPlayer = currentPlayer;
    }
}
