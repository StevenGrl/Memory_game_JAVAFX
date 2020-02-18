package memory.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Player {
    private final StringProperty name;
    private final IntegerProperty score;
    private final IntegerProperty rank;

    public Player(StringProperty name, IntegerProperty score, IntegerProperty rank) {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }

    @Override
    public String toString(){
        return getName();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Integer getScore() {
        return score.get();
    }

    public void setScore(Integer score) {
        this.score.set(score);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public Integer getRank() {
        return rank.get();
    }

    public void setRank(Integer rank) {
        this.rank.set(rank);
    }

    public IntegerProperty rankProperty() {
        return rank;
    }
}
