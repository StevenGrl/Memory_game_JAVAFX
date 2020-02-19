package memory;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import memory.models.Manager;
import memory.models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {
    private static final int MAX_PLAYERS = 4;
    private static int NUMBER_OF_PAIRS = 32;
    private static int NUMBER_PER_ROW;
    private static Tile selected = null;
    private static int clickCount = 2;
    private static int nbPlayers = 1;
    private static boolean isNbMaxPlayersReached = false;

    private Parent createContent(Manager manager) {
        VBox root = new VBox();
        root.setPrefSize(800, 800);
        int nb = 1;
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(nb)));
            tiles.add(new Tile(String.valueOf(nb)));
            nb++;
        }
        Collections.shuffle(tiles);

        GridPane grid = new GridPane();
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setOnMouseEntered((MouseEvent t) -> {
                tile.setBackground(new Background(new BackgroundFill(Color.rgb(220, 220, 220), CornerRadii.EMPTY, Insets.EMPTY)));
            });
            tile.setOnMouseExited((MouseEvent t) -> {
                tile.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
            });
            grid.add(tile, (i % NUMBER_PER_ROW), (i / NUMBER_PER_ROW));
        }

        grid.setPadding(new Insets(15));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.TOP_CENTER);

        VBox boxPlayers = new VBox();
        boxPlayers.setAlignment(Pos.TOP_RIGHT);
        boxPlayers.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(4))));
        for (int i = 0; i < manager.getNbPlayers(); i++) {
            boxPlayers.getChildren().add(Manager.getPlayers().get(i).getBox());
        }
        boxPlayers.setPadding(new Insets(15));
        boxPlayers.setPrefWidth(100000);

        root.getChildren().addAll(boxPlayers, grid);

        return root;
    }

    private Parent createUserFields(Stage primaryStage) {
        List<TextField> labels = new ArrayList<>();
        StackPane root = new StackPane();
        root.setPrefSize(800, 800);

        VBox fieldsBox = new VBox();
        fieldsBox.setPadding(new Insets(20, 50, 20, 50));
        Label label = new Label("Joueur 1 :");
        TextField textField = new TextField();
        textField.setMaxWidth(150);
        labels.add(textField);
        fieldsBox.getChildren().addAll(label, textField);
        fieldsBox.setSpacing(10);
        fieldsBox.setAlignment(Pos.CENTER);

        VBox errorBox = new VBox();
        errorBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);

        Button addPlayerBtn = new Button("+");
        addPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nbPlayers < MAX_PLAYERS) {
                    Label labelPlayers = new Label("Joueur " + (nbPlayers + 1) + " :");
                    TextField textField = new TextField();
                    textField.setMaxWidth(150);
                    labels.add(textField);
                    fieldsBox.getChildren().addAll(labelPlayers, textField);
                    nbPlayers++;
                } else if (!isNbMaxPlayersReached) {
                    Label nbJoueursMaxReached = new Label("Nombre de joueurs maximum atteint");
                    nbJoueursMaxReached.setFont(Font.font(25));
                    nbJoueursMaxReached.setTextFill(Color.RED);
                    errorBox.getChildren().add(nbJoueursMaxReached);
                    isNbMaxPlayersReached = true;
                }
            }
        });

        Button removePlayerBtn = new Button("-");
        removePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nbPlayers == 1) {
                    Label nbMinJoueurs = new Label("Il doit rester au moins 1 joueur");
                    nbMinJoueurs.setFont(Font.font(25));
                    nbMinJoueurs.setTextFill(Color.RED);
                    errorBox.getChildren().add(nbMinJoueurs);
                } else {
                    nbPlayers--;
                    fieldsBox.getChildren().remove(fieldsBox.getChildren().size() - 1);
                    fieldsBox.getChildren().remove(fieldsBox.getChildren().size() - 1);
                }
            }
        });
        addPlayerBtn.setMinWidth(30);
        removePlayerBtn.setMinWidth(30);
        buttonBox.getChildren().addAll(addPlayerBtn, removePlayerBtn);
        buttonBox.setSpacing(10);

        HBox nbCardsBox = new HBox();
        Label nbCardsLabel = new Label("Nombre de paires : ");
        final ChoiceBox nbCardsChoice = new ChoiceBox(FXCollections.observableArrayList(8, 21, 32, 40, 78));
        nbCardsChoice.setValue(8);

        nbCardsBox.getChildren().addAll(nbCardsLabel, nbCardsChoice);
        nbCardsBox.setAlignment(Pos.CENTER);
        nbCardsBox.setSpacing(10);

        HBox submitBox = new HBox();
        Button submit = new Button("Lancer la partie");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorBox.getChildren().removeAll(errorBox.getChildren());
                List<Player> players = new ArrayList<>();
                for (int i = 0; i < nbPlayers; i++) {
                    if (labels.get(i).getText().isBlank()) {
                        Label emptyPlayer = new Label("Il y a au moins un joueur vide, veuillez le(s) remplir");
                        emptyPlayer.setFont(Font.font(25));
                        emptyPlayer.setTextFill(Color.RED);
                        errorBox.getChildren().add(emptyPlayer);
                        return;
                    }
                    HBox playerBox = new HBox();
                    Label playerLabel = new Label();
                    playerBox.getChildren().add(playerLabel);
                    players.add(new Player(labels.get(i).getText(), playerBox));
                }
                NUMBER_OF_PAIRS = Integer.valueOf(nbCardsChoice.getSelectionModel().selectedItemProperty().getValue().toString());
                Manager manager = new Manager(players, NUMBER_OF_PAIRS);
                setNumberPerRow(NUMBER_OF_PAIRS);
                primaryStage.setScene(new Scene(createContent(manager)));
            }
        });
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().add(submit);

        VBox mainBox = new VBox();
        mainBox.setSpacing(20);
        mainBox.getChildren().addAll(fieldsBox, buttonBox, nbCardsBox, errorBox, submitBox);
        mainBox.setAlignment(Pos.CENTER);

        root.getChildren().add(mainBox);

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
                open(() -> {
                });
            } else {
                open(() -> {
                    if (!hasSameValue(selected)) {
                        selected.close();
                        this.close();
                        Manager.setNextPlayer();
                    } else {
                        selected.text.setFill(Color.GREY);
                        this.text.setFill(Color.GREY);
                        Manager.incrementScore();
                        System.out.println("game over : " + Manager.isGameOver());
                        if (Manager.isGameOver()) {
                            System.out.println("And the best player iiiiiiiiiiiiiiiiiis : " + Manager.getBestPlayer().getName());
                        }
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
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();

        }

        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            ft.setToValue(0);
            ft.play();
        }

        public void closeOnStart() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.01), text);
            ft.setToValue(0);
            ft.play();
        }

        public boolean hasSameValue(Tile other) {
            return text.getText().equals(other.text.getText());
        }
    }

    public void setNumberPerRow(int nbPairs) {
        NUMBER_PER_ROW = (int) Math.sqrt(NUMBER_OF_PAIRS * 2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createUserFields(primaryStage)));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
