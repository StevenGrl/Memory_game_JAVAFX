package memory.models;

import java.util.List;
import java.util.Random;

public class Manager {

    private static List<Player> players;
    private static int nbPlayers;
    private static Player currentPlayer;
    private static int nbPairs;

    public Manager(List<Player> players, int nbPairs) {
        Random rand = new Random(System.currentTimeMillis());
        this.setPlayers(players);
        this.setNbPlayers(players.size());
        Manager.currentPlayer = getPlayers().get(rand.nextInt(getPlayers().size()));
        Manager.nbPairs = nbPairs;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        Manager.players = players;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        Manager.nbPlayers = nbPlayers;
    }


    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setNextPlayer() {
        System.out.println("Previous player : " + Manager.getCurrentPlayer());
        int nextPlayerIndex;
        nextPlayerIndex = getPlayers().indexOf(Manager.currentPlayer) + 1;
        if (nextPlayerIndex >= getPlayers().size()) {
            nextPlayerIndex = 0;
        }
        Manager.currentPlayer = getPlayers().get(nextPlayerIndex);
        System.out.println("Next player : " + Manager.getCurrentPlayer());
    }

    public static void incrementScore() {
        Manager.getCurrentPlayer().setScore(Manager.getCurrentPlayer().getScore() + 1);
        System.out.println(Manager.getCurrentPlayer().getName() + " : " + Manager.getCurrentPlayer().getScore());
    }

    public static boolean isGameOver() {
        int totalScore = 0;
        for (int i = 0; i < players.size(); i++) {
            totalScore += players.get(i).getScore();
        }
        return totalScore == nbPairs;
    }

    public static Player getBestPlayer() {
        Player bestPlayer = getPlayers().get(0);
        for (int i = 1; i < getPlayers().size(); i++) {
            if (bestPlayer.getScore() < getPlayers().get(i).getScore()) {
                bestPlayer = getPlayers().get(i);
            }
        }
        return bestPlayer;
    }
}
