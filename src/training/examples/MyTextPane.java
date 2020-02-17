package training.examples;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

// un BorderPane a un layout de type BorderLayout (m�me principe que Swing)
public class MyTextPane extends BorderPane {

	public static final int TOP = 0;
	public static final int LEFT = 1;
	public static final int CENTER = 2;
	public static final int RIGHT = 3;
	public static final int BOTTOM = 4;


	public static final int NB_LABELS = 8;
	public static final int NB_ZONES = 5;
	protected final static String[] DEFAULT_STORY = {"Un jour",
			"dans l'autobus",
			"qui porte la",
			"lettre S",
			"Je vis",
			"un foutriquet",
			"de je ne sais",
			"quelle esp�ce"};

	protected final static Color DEFAULT_COLORS[] = {
			Color.YELLOW, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA};


	private Label[] lab = new Label[NB_LABELS];
	private Button but = new Button("click me");


	public MyTextPane() {
		this(MyTextPane.DEFAULT_STORY, DEFAULT_COLORS);
	}

	public MyTextPane(String[] story, Color[] col) throws IllegalArgumentException{
		if (story.length != NB_LABELS){
			throw (new IllegalArgumentException("First argument should have length " +
			 NB_LABELS));
		}

		if (col.length != NB_ZONES){
			throw (new IllegalArgumentException("Second argument should have length" +
					" " +
					NB_ZONES));
		}
		/*Cr�ation d'une bordure*/
		BorderStroke bs = new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN);
		Border b = new Border(bs);

		/* initialisation des labels */
		for (int i = 0; i < lab.length; i++) {
			lab[i] = new Label(story[i]);
			lab[i].setAlignment(Pos.CENTER);
			lab[i].setBorder(b);

		}

		/* Cr�ation de r�gions qui serviront de "glues"*/
		Region[] glues = new Region[3];
		for (int i = 0; i < glues.length; i++) {
			glues[i] = new Region();
			HBox.setHgrow(glues[i], Priority.ALWAYS);
			VBox.setVgrow(glues[i], Priority.ALWAYS);
		}

		// Une boite pour chacune des r�gions
		Pane[] boxes = new Pane[NB_ZONES];

		// Top
		boxes[0] = new HBox();
		boxes[0].getChildren().addAll(lab[0], glues[0], lab[1]);
		this.setTop(boxes[0]);

		// Left
		boxes[1] = new HBox();
		boxes[1].getChildren().addAll(lab[2]);
		this.setLeft(boxes[1]);

		// Center
		boxes[2] = new VBox();
		boxes[2].getChildren().addAll(lab[3], glues[1], lab[4]);
		((VBox) boxes[2]).setAlignment(Pos.CENTER);
		this.setCenter(boxes[2]);

		// Right
		boxes[3] = new HBox();
		boxes[3].getChildren().addAll(lab[5]);
		((HBox) boxes[3]).setAlignment(Pos.BOTTOM_CENTER);
		this.setRight(boxes[3]);

		// Bottom
		boxes[4] = new HBox();
		boxes[4].getChildren().addAll(lab[6], glues[2], lab[7]);
		this.setBottom(boxes[4]);

		// Ajout des couleurs de fond des bo�tes
		for (int i = 0; i < col.length; i++) {
			Background bg = new Background(new BackgroundFill(col[i],
					CornerRadii.EMPTY, Insets.EMPTY));
			boxes[i].setBackground(bg);
		}


	}

	public Label getLabel(int i){
		return lab[i];
	}

}
