package training.examples;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;



public class MyTextPaneWithEvents extends MyTextPane {

	public MyTextPaneWithEvents(){
		this(MyTextPane.DEFAULT_STORY, MyTextPane.DEFAULT_COLORS);
	}

	public MyTextPaneWithEvents(String[] story, Color[] colors){
		super(story,colors);

		// Ajout d'un �couteur de clic sur les labels, de sorte que chaque fois
		// qu'on clique sur un label, son texte apparaisse dans une fen�tre d'alerte
		for (int i = 0; i < MyTextPane.NB_ZONES ; i++){
			this.getLabel(i).addEventHandler(MouseEvent.MOUSE_CLICKED,
					new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							Alert popup=
									new Alert(Alert.AlertType.INFORMATION,"you just clicked on " + ((Label)mouseEvent.getSource()).getText());
							popup.showAndWait();
						}
					});
		}
	}
}
