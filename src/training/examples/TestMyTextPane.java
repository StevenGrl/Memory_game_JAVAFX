package training.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestMyTextPane extends Application {

	@Override
	public void start(Stage primaryStage) {
		MyTextPane mp = new MyTextPaneWithEvents();

		String[] story = {"un", "deux","trois","quatre","cinq",
				"six","sept","huit"};
		Color[] colors = {Color.CYAN,Color.ROSYBROWN,
				Color.GAINSBORO,
				Color.MAGENTA,Color.BISQUE};
		MyTextPane mp2 = new MyTextPane(story,colors);


		TilePane root = new TilePane();
		root.getChildren().add(mp);
		root.getChildren().add(mp2);

		Scene scene = new Scene(root);

		primaryStage.setTitle("Test BorderPane");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}