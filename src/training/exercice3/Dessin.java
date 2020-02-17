package training.exercice3;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Dessin extends Region {
    public Dessin(Shape shape) {
        this.setPrefSize(100, 100);
        this.setMaxSize(100, 100);
        this.setCenterShape(true);

        if(shape instanceof Circle) {
            Circle circle = (Circle) shape;
            circle.setFill(Color.RED);
            circle.setCenterX(200);
            circle.setCenterY(10);
            circle.setRadius(20);
            this.setShape(circle);
            this.setStyle("-fx-background-color: red");
        }
        else if(shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            rectangle.setFill(Color.GREEN);
            rectangle.setHeight(20);
            rectangle.setWidth(20);
            this.setShape(rectangle);
            this.setStyle("-fx-background-color: green");
        }
    }
}