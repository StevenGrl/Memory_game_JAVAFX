package memory.models;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Player {
    private String name;
    private Integer score;
    private Integer nbBombe;
    private VBox box;

    public Player(String name, Integer score, Integer nbBombe, VBox playerBox) {
        this.name = name;
        this.score = score;
        this.nbBombe = nbBombe;
        this.setBox(playerBox);
    }

    public Player(String name, VBox playerBox) {
        this(name, 0,0, playerBox);
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

    public Integer getNbBombe() {
        return nbBombe;
    }

    public void setNbBombe(Integer nbBombe) {
        this.nbBombe = nbBombe;
    }

    public String getLabel() {
        return this.getName() + "\nScore : " + this.getScore();
    }

    public VBox getBox() {
        return box;
    }

    //box contenant les infos joueurs
    public void setBox(VBox box) {
        Label label = (Label) box.getChildren().get(0);
        label.setText(this.getLabel());
        box.getChildren().removeAll(box.getChildren());
        box.getChildren().add(label);
        box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        box.setPadding(new Insets(3));
        box.setMinHeight(55);
        this.box = box;
    }


    public void setBackground(Color color) {
        this.getBox().setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), Insets.EMPTY)));
    }
}
