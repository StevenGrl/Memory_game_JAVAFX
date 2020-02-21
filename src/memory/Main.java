package memory;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    private static Button nextPlayer = new Button("Joueur suivant");
    private static Button swapButton = new Button("Échanger");
    private static Button quitButton = new Button("Quitter");
    private static Color bgTile;
    private static String theme;
    private static boolean isSwapActivated = false;
    private static Tile[] tilesToSwap = new Tile[2];

    //Page de création de la partie ===============================================================================//
    private Parent createUserFields(Stage primaryStage) {
        List<TextField> labels = new ArrayList<>();
        StackPane root = new StackPane();
        root.setPrefSize(800, 800);

        //Création joueurs
        Label playerLabel = new Label("Choix des joueurs : ");
        VBox playerBox = new VBox();
        playerBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        playerBox.setSpacing(5);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setMaxWidth(400);
        playerBox.setPadding(new Insets(15));
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
                    //gestion des erreurs du formulaire
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
                //gestion des erreurs du formulaire
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

        playerBox.getChildren().addAll(fieldsBox, buttonBox);


        //Box niveau partie
        Label gameLabel = new Label("Niveau de difficulté : ");
        VBox gameBox = new VBox();
        gameBox.setMaxWidth(400);
        playerBox.setPadding(new Insets(15));
        gameBox.setMinHeight(100);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        gameBox.setSpacing(10);

        HBox nbCardsBox = new HBox();
        Label nbCardsLabel = new Label("Nombre de paires : ");
        final ChoiceBox nbCardsChoice = new ChoiceBox(FXCollections.observableArrayList(8, 21, 32, 40));
        nbCardsChoice.setValue(8);

        nbCardsBox.getChildren().addAll(nbCardsLabel, nbCardsChoice);
        nbCardsBox.setAlignment(Pos.CENTER);
        nbCardsBox.setSpacing(10);


        HBox gameModeBox = new HBox();
        Label gameModeLabel = new Label("Mode de jeu : ");
        final ChoiceBox gameModeChoice = new ChoiceBox(FXCollections.observableArrayList("Sans bombes", "Avec bombes"));
        gameModeChoice.setValue("Sans bombes");
        gameModeBox.setAlignment(Pos.CENTER);
        gameModeBox.setSpacing(10);
        gameModeBox.getChildren().addAll(gameModeLabel, gameModeChoice);

        gameBox.getChildren().addAll(nbCardsBox, gameModeBox);

        //Box Parametres
        Label paramsLabel = new Label("Paramètres : (facultatif)");
        VBox paramsBox = new VBox();
        paramsBox.setMaxWidth(400);
        paramsBox.setMinHeight(150);
        paramsBox.setAlignment(Pos.CENTER);
        paramsBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        paramsBox.setSpacing(10);
        HBox colorBox = new HBox();
        Label colorLabel = new Label("Couleur du joueur actif : ");
        final ChoiceBox colorChoice = new ChoiceBox(FXCollections.observableArrayList("Bleu", "Rouge", "Jaune", "Gris"));
        colorChoice.setValue("Bleu");
        colorBox.setAlignment(Pos.CENTER);

        colorBox.getChildren().addAll(colorLabel, colorChoice);

        HBox colorTileBox = new HBox();
        Label colorTileLabel = new Label("Couleur du dos de carte : ");
        final ChoiceBox colorTileChoice = new ChoiceBox(FXCollections.observableArrayList("Bleu", "Rouge", "Jaune", "Gris"));
        colorTileChoice.setValue("Bleu");
        colorTileBox.setAlignment(Pos.CENTER);

        colorTileBox.getChildren().addAll(colorTileLabel, colorTileChoice);

        HBox themeBox = new HBox();
        Label themeLabel = new Label("Choisir un thème : ");
        final ChoiceBox themeChoice = new ChoiceBox(FXCollections.observableArrayList("Pokemon", "Rick & Morty"));
        themeChoice.setValue("Pokemon");
        themeBox.setAlignment(Pos.CENTER);

        themeBox.getChildren().addAll(themeLabel, themeChoice);

        paramsBox.getChildren().addAll(colorBox, colorTileBox, themeBox);

        //gestion bouton
        HBox submitBox = new HBox();
        Button submit = new Button("Lancer la partie");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //gestion des erreurs du formulaire
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
                    VBox playerBox = new VBox();
                    Label playerLabel = new Label();
                    playerBox.getChildren().add(playerLabel);
                    players.add(new Player(labels.get(i).getText(), playerBox));
                }

                //Gestion parametre de partie
                NUMBER_OF_PAIRS = Integer.valueOf(nbCardsChoice.getSelectionModel().selectedItemProperty().getValue().toString());
                boolean isGameWithBombs = false;
                if (gameModeChoice.getSelectionModel().getSelectedIndex() == 1) {
                    isGameWithBombs = true;
                }

                Color bgCurrent = Color.rgb(116, 208, 241);
                if (colorChoice.getSelectionModel().getSelectedIndex() == 1) {
                    bgCurrent = Color.rgb(217, 136, 128);
                } else if (colorChoice.getSelectionModel().getSelectedIndex() == 2) {
                    bgCurrent = Color.rgb(247, 220, 111);
                } else if (colorChoice.getSelectionModel().getSelectedIndex() == 3) {
                    bgCurrent = Color.rgb(153, 163, 164);
                }

                bgTile = Color.rgb(133, 193, 233);
                if (colorTileChoice.getSelectionModel().getSelectedIndex() == 1) {
                    bgTile = Color.rgb(217, 136, 128);
                } else if (colorTileChoice.getSelectionModel().getSelectedIndex() == 2) {
                    bgTile = Color.rgb(247, 220, 111);
                } else if (colorTileChoice.getSelectionModel().getSelectedIndex() == 3) {
                    bgTile = Color.rgb(153, 163, 164);
                }

                theme = "Pokemon";
                if (themeChoice.getSelectionModel().getSelectedIndex() == 1) {
                    theme = "Rick & Morty";
                }

                Manager manager = new Manager(players, NUMBER_OF_PAIRS, isGameWithBombs, bgCurrent);
                setNumberPerRow();
                primaryStage.setScene(new Scene(createContent(primaryStage, manager)));
            }
        });
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().add(submit);

        VBox mainBox = new VBox();
        mainBox.setSpacing(20);
        mainBox.getChildren().addAll(playerLabel, playerBox , gameLabel, gameBox, paramsLabel, paramsBox, errorBox, submitBox, quitButton);
        mainBox.setAlignment(Pos.CENTER);

        root.getChildren().add(mainBox);

        //bouton exit
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        return root;
    }

    //page du jeu ================================================================================================//
    private Parent createContent(Stage primaryStage, Manager manager) {
        System.out.println("game over : " + Manager.isGameOver());
        VBox root = new VBox();
        root.setPrefSize(800, 800);
        int nb = 1;
        //Création du tableau avec les cartes
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(nb)));
            tiles.add(new Tile(String.valueOf(nb)));
            nb++;
        }
        //Ajout des bombes si besoin
        if (Manager.isIsGameWithBombs()) {
            for (int i = 0; i < NUMBER_PER_ROW; i++) {
                tiles.add(new Tile("Bomb"));
            }
        }

        Collections.shuffle(tiles);

        //Création de l'effet grille et gestion des effets souris
        GridPane grid = new GridPane();
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setOnMouseEntered((MouseEvent t) -> {
                tile.setBackground(new Background(new BackgroundFill(Color.rgb(220, 220, 220), CornerRadii.EMPTY, Insets.EMPTY)));
            });
            tile.setOnMouseExited((MouseEvent t) -> {
                if (!tile.isOpen()) {
                    tile.setBackground(new Background(new BackgroundFill(bgTile, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    tile.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            });
            grid.add(tile, (i % NUMBER_PER_ROW), (i / NUMBER_PER_ROW));
        }

        grid.setPadding(new Insets(15));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.TOP_CENTER);

        //gestion affichage joueur
        HBox boxPlayers = new HBox();
        boxPlayers.setAlignment(Pos.TOP_CENTER);
        boxPlayers.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        boxPlayers.setSpacing(5);
        boxPlayers.setMaxWidth(600);
        boxPlayers.setPadding(new Insets(15));
        for (int i = 0; i < manager.getNbPlayers(); i++) {
            boxPlayers.getChildren().add(Manager.getPlayers().get(i).getBox());
        }
        boxPlayers.setPadding(new Insets(15));
        boxPlayers.setSpacing(5);

        //Gestion bouton
        HBox footer = new HBox();
        footer.setPadding(new Insets(5));
        footer.setSpacing(30);
        footer.setAlignment(Pos.CENTER);
        footer.setPrefHeight(500);

        Button replayButton = new Button("Recommencer");
        Button menuButton = new Button("Accueil");

        HBox leftFooter = new HBox();
        VBox centerFooter = new VBox();
        HBox centerTopFooter = new HBox();
        HBox centerBottomFooter = new HBox();
        HBox rightFooter = new HBox();

        leftFooter.setAlignment(Pos.BOTTOM_LEFT);
        centerFooter.setAlignment(Pos.BOTTOM_CENTER);
        centerTopFooter.setAlignment(Pos.TOP_CENTER);
        centerBottomFooter.setAlignment(Pos.BOTTOM_CENTER);
        rightFooter.setAlignment(Pos.BOTTOM_RIGHT);

        Region vSpacer = new Region();
        Region hSpacer = new Region();
        Region hSpacer2 = new Region();
        VBox.setVgrow(vSpacer, Priority.ALWAYS);
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        HBox.setHgrow(hSpacer2, Priority.ALWAYS);

        leftFooter.getChildren().add(menuButton);
        centerTopFooter.setSpacing(10);
        centerTopFooter.getChildren().addAll(swapButton, nextPlayer);
        centerBottomFooter.getChildren().add(replayButton);

        centerFooter.setSpacing(10);
        rightFooter.getChildren().add(quitButton);

        if (nbPlayers > 1) {
            nextPlayer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    clickCount = 2;
                    nextPlayer.setDisable(true);
                    Manager.setNextPlayer();
                    if (Manager.getCurrentPlayer() == Manager.getWorstPlayer() && Manager.getBestPlayer().getScore() != 0) {
                        swapButton.setDisable(false);
                    } else {
                        swapButton.setDisable(true);
                    }
                }
            });
            nextPlayer.setDisable(true);
            //footer.getChildren().addAll(swapButton, nextPlayer, replayButton, menuButton, quitButton);
            centerFooter.getChildren().addAll(centerTopFooter, vSpacer, centerBottomFooter);
        } else {
            //footer.getChildren().addAll(swapButton, replayButton, menuButton, quitButton);
            centerFooter.getChildren().addAll(vSpacer, centerBottomFooter);
        }

        footer.getChildren().addAll(leftFooter, hSpacer, centerFooter, hSpacer2, rightFooter);

        //action bouton menu
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nbPlayers = 1;
                Manager.resetScore();
                primaryStage.setScene(new Scene(createUserFields(primaryStage)));
            }
        });

        //action bouton recommencer
        replayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Manager.resetScore();
                Manager.refreshAllLabel();
                primaryStage.setScene(new Scene(createContent(primaryStage, manager)));
            }
        });

        //gestion bouton echange
        if (Manager.getCurrentPlayer() == Manager.getWorstPlayer() && Manager.getBestPlayer().getScore() != 0) {
            swapButton.setDisable(false);
        } else {
            swapButton.setDisable(true);
        }
        swapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (clickCount == 2) {
                    isSwapActivated = true;
                }
            }
        });
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(boxPlayers, grid, footer);

        //bouton quitter
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        return root;
    }

    //Class Tile =============================================================================================//
    private static class Tile extends StackPane {
        private int id;
        private ImageView imageView;

        Tile(String value) {
            Rectangle border = new Rectangle(50, 60);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            setBackground(new Background(new BackgroundFill(bgTile, CornerRadii.EMPTY, Insets.EMPTY)));

            if (value.equals("Bomb")) {
                this.id = -1;
                Image img = new Image("file:src/memory/img/bomb.png", 40, 40, false, false);
                imageView = new ImageView(img);
            } else {
                this.id = Integer.parseInt(value);
                Image img = new Image(getUrl() + value + getExtension(), 40, 40, false, false);
                imageView = new ImageView(img);
            }
            getChildren().addAll(border, imageView);
            closeOnStart();

            setOnMouseClicked(this::handleMouseClick);
        }

        //action sur les cartes
        public void handleMouseClick(MouseEvent event) {
            if (isOpen() || clickCount == 0 || Manager.isGameOver()) {
                return;
            }
            swapButton.setDisable(true);
            if (isSwapActivated) {
                getSwapTile(event);
            } else {
                clickCount--;
                if (selected == null && !isBomb()) {
                    selected = this;
                    open(() -> {
                    });
                } else if (!isBomb()) {
                    open(() -> {
                        if (!hasSameValue(selected)) {
                            selected.close();
                            this.close();
                            if (nbPlayers == 1) {
                                clickCount = 2;
                                Manager.setNextPlayer();
                            }
                        } else {
                            Manager.incrementScore();
                            if (Manager.isGameOver()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Fin de la partie");
                                alert.setHeaderText("Nous avons un vainqueur !");
                                alert.setContentText("Bravo " + Manager.getBestPlayer().getName() + " !");

                                alert.show();
                            }
                            clickCount = 2;

                        }
                        selected = null;
                    });
                }
                if (isBomb()) {
                    open(() -> {});
                    if (selected != null && !selected.isBomb()) selected.close();
                    Manager.incrementBomb();
                    if (nbPlayers == 1) {
                        clickCount = 2;
                        Manager.setNextPlayer();
                    } else {
                        clickCount = 0;
                    }
                    selected = null;
                }
                PauseTransition wait = new PauseTransition(Duration.seconds(0.5));
                wait.setOnFinished((e) -> {
                    if (clickCount == 0) {
                        nextPlayer.setDisable(false);
                    }
                    wait.playFromStart();
                });
                wait.play();
            }
        }

        public String getUrl() {
            return theme.equals("Pokemon") ? "https://pokeres.bastionbot.org/images/pokemon/" : "https://rickandmortyapi.com/api/character/avatar/";
        }

        public String getExtension() {
            return theme.equals("Pokemon") ? ".png" : ".jpeg";
        }

        public boolean isBomb() {
            return this.id == -1;
        }

        public boolean isOpen() {
            return imageView.getOpacity() == 1;
        }

        // découvre une carte
        public void open(Runnable action) {
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            FadeTransition ft = new FadeTransition(Duration.seconds(0.01), imageView);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        // cache une carte
        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.3), imageView);
            ft.setToValue(0);
            ft.play();
            this.setBackground(new Background(new BackgroundFill(bgTile, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        // cache les cartes au lancement du jeu
        public void closeOnStart() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.01), imageView);
            ft.setToValue(0);
            ft.play();
        }


        public boolean hasSameValue(Tile other) {
            return this.id == other.id;
        }
    }

    //gère l'action "clique" sur une carte pour l'échanger
    public static void getSwapTile(MouseEvent event) {
        if (clickCount == 2) {
            tilesToSwap[0] = (Tile) event.getSource();
            tilesToSwap[0].setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            clickCount--;
            PauseTransition wait = new PauseTransition(Duration.seconds(0.5));
            wait.setOnFinished((e) -> {
                wait.playFromStart();
            });
            wait.play();
        } else {
            tilesToSwap[1] = (Tile) event.getSource();
            tilesToSwap[1].setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            tilesToSwap[0].setBorder(Border.EMPTY);
            tilesToSwap[1].setBorder(Border.EMPTY);
            swapTile(tilesToSwap[0], tilesToSwap[1]);
            clickCount = 2;
            isSwapActivated = false;
            PauseTransition wait = new PauseTransition(Duration.seconds(0.5));
            wait.setOnFinished((e) -> {
                wait.playFromStart();
            });
            wait.play();
        }
    }

    //échange 2 cartes
    public static void swapTile(Tile t1, Tile t2) {
        Integer temp = GridPane.getRowIndex(t1);
        GridPane.setRowIndex(t1, GridPane.getRowIndex(t2));
        GridPane.setRowIndex(t2, temp);

        temp = GridPane.getColumnIndex(t1);
        GridPane.setColumnIndex(t1, GridPane.getColumnIndex(t2));
        GridPane.setColumnIndex(t2, temp);
    }

    //formule du nombre de carte par ligne et du nombre de bombes
    public void setNumberPerRow() {
        NUMBER_PER_ROW = (int) Math.sqrt(NUMBER_OF_PAIRS * 2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createUserFields(primaryStage)));
        primaryStage.setTitle("Memory");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}