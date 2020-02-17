package training.exercice2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SemanticEvents extends Application {

    private Button[] buttons = new Button[3];
    private static Label topLabel = new Label();

    private Button quitButton = new Button("Quit");

    private HBox createButtonsBox(StackPane root){
        HBox content = new HBox();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("Button " + (i + 1));
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Button btn = (Button) event.getSource();
                    changeTopLabel(btn.getText());
                }
            });
            content.getChildren().addAll(buttons[i]);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(20);
        }
        return content;
    }

    private HBox createQuitButton() {
        this.quitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        HBox box = new HBox();
        box.getChildren().add(quitButton);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        return box;
    }

    private HBox createTopLabel(String text) {
        HBox box = new HBox();
        topLabel.setText(text);
        box.getChildren().add(topLabel);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }

    private void changeTopLabel(String text) {
        topLabel.setText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Semantic events");
        StackPane root = new StackPane();

        root.getChildren().add(createTopLabel("North label"));
        root.getChildren().add(createButtonsBox(root));
        root.getChildren().add(createQuitButton());
        root.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setScene(new Scene(root, 500, 150));
        primaryStage.show();
    }
}
