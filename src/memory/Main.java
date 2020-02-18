package memory;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {
    private static final int NUMBER_OF_PAIRS = 8;
    private static final int NUMBER_PER_ROW = 4;
    private static Tile selected = null;
    private static int clickCount = 2;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600, 600);
        char c = 'A';
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));
            c++;
        }
        Collections.shuffle(tiles);

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(50 * (i % NUMBER_PER_ROW));
            tile.setTranslateY(60 * (i / NUMBER_PER_ROW));
            root.getChildren().add(tile);
        }

        return root;
    }

    private static class Tile extends StackPane {
        private Text text = new Text();

        Tile(String value) {
            Rectangle border = new Rectangle(50, 60);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setText(value);
            text.setFont(Font.font(30));

            setOnMouseClicked(this::handleMouseClick);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            closeOnStart();
        }

        public void handleMouseClick(MouseEvent event) {
            if (isOpen() || clickCount == 0) {
                return;
            }
            clickCount--;
            if (selected == null) {
                selected = this;
                open(() -> {});
            } else {
                open(() -> {
                    if (!hasSameValue(selected)) {
                        selected.close();
                        this.close();
                    }
                    selected = null;
                    clickCount = 2;
                });
            }
        }

        public boolean isOpen() {
            return text.getOpacity() == 1;
        }

        public void open(Runnable action) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.3), text);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();

        }

        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(1.5), text);
            ft.setToValue(0);
            ft.play();
        }

        public void closeOnStart() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.1), text);
            ft.setToValue(0);
            ft.play();
        }

            public boolean hasSameValue(Tile other) {
                System.out.println(text.getText());
                System.out.println(other.text.getText());
            return text.getText().equals(other.text.getText());
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
