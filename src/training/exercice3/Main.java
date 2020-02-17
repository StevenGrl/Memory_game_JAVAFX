package training.exercice3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        StackPane root = new StackPane();
        HBox topBox = new HBox();
        topBox.getChildren().add(new Dessin(new Circle()));
        topBox.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(topBox);

        HBox botBox = new HBox();
        botBox.getChildren().add(new Dessin(new Rectangle()));
        botBox.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(botBox);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
