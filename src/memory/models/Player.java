package memory.models;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Player {
    private String name;
    private Integer score;
    private Integer rank;
    private HBox box;

    public Player(String name, Integer score, Integer rank, HBox playerBox) {
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.setBox(playerBox);
    }

    public Player(String name, HBox playerBox) {
        this(name, 0, 0, playerBox);
    }

    @Override
    public String toString(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer score() {
        return score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer rank() {
        return rank;
    }

    public String getLabel() {
        return this.getName() + " score :" + this.getScore() + " rank : " + this.getRank();
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        Label label = (Label) box.getChildren().get(0);
        label.setText(this.getLabel());
        box.getChildren().removeAll(box.getChildren());
        box.getChildren().add(label);
        this.box = box;
    }
}
