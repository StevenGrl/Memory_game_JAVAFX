package memory.models;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Manager {

    private static List<Player> players;
    private static int nbPlayers;
    private static Player currentPlayer;
    private static int nbPairs;
    private static final Color bgNotCurrent = Color.WHITE;
    private static final Color bgCurrent = Color.rgb(116, 208, 241);

    public Manager(List<Player> players, int nbPairs) {
        Random rand = new Random(System.currentTimeMillis());
        this.setPlayers(players);
        this.setNbPlayers(players.size());
        Manager.currentPlayer = getPlayers().get(rand.nextInt(getPlayers().size()));
        currentPlayer.setBackground(bgCurrent);
        Manager.nbPairs = nbPairs;

        refreshAllLabel();
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
        currentPlayer.setBackground(bgNotCurrent);
        int nextPlayerIndex;
        nextPlayerIndex = getPlayers().indexOf(Manager.currentPlayer) + 1;
        if (nextPlayerIndex >= getPlayers().size()) {
            nextPlayerIndex = 0;
        }
        Manager.currentPlayer = getPlayers().get(nextPlayerIndex);
        currentPlayer.setBackground(bgCurrent);
        System.out.println("Next player : " + Manager.getCurrentPlayer());
    }

    public static void incrementScore() {
        currentPlayer.setScore(currentPlayer.getScore() + 1);
        refreshLabel();
    }

    public static void decrementScore() {
        currentPlayer.setScore(currentPlayer.getScore() - 1);
        refreshLabel();
    }

    public static void refreshLabel() {
        Label lab = (Label) Manager.currentPlayer.getBox().getChildren().get(0);
        lab.setText(Manager.currentPlayer.getLabel());
        Manager.currentPlayer.getBox().getChildren().removeAll(Manager.currentPlayer.getBox().getChildren());
        Manager.currentPlayer.getBox().getChildren().add(lab);
    }

    public static void refreshAllLabel() {
        for (int i = 0; i < getPlayers().size(); i++) {
            Label lab = (Label) Manager.getPlayers().get(i).getBox().getChildren().get(0);
            lab.setText(Manager.getPlayers().get(i).getLabel());
            Manager.getPlayers().get(i).getBox().getChildren().removeAll(Manager.getPlayers().get(i).getBox().getChildren());
            Manager.getPlayers().get(i).getBox().getChildren().add(lab);
        }
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

    public static void resetScore() {
        for (int i = 0; i < getPlayers().size(); i++) {
            getPlayers().get(i).setScore(0);
            System.out.println(getPlayers().get(i).getScore());
        }
    }
}
