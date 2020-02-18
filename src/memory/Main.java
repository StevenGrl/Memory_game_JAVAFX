package memory;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {
    private static final int NUMBER_OF_PAIRS = 8;
    private static final int NUMBER_PER_ROW = 4;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600,600);
        char c = 'A';
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));
            c++;
        }
        Collections.shuffle(tiles);

        for(int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(50 * (i % NUMBER_PER_ROW));
            tile.setTranslateY(60 * (i / NUMBER_PER_ROW));
            root.getChildren().add(tile);
        }

        return root;
    }

    private static class Tile extends StackPane {
        Tile(String value) {
            Rectangle border = new Rectangle(50, 60);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text();
            text.setText(value);
            text.setFont(Font.font(30));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
