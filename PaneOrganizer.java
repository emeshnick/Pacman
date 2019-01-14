package Pacman;

import javafx.scene.layout.BorderPane;

/*
 * The PaneOrganizer class instantiates a BorderPane and
 * a game, and sets the label returned from the game to the
 * bottom of the BorderPane.
 */
public class PaneOrganizer{

private BorderPane _root;
private Game _game;

	public PaneOrganizer() {
		_root = new BorderPane();
		_game = new Game();	
		_root.getChildren().add(_game.getNode());
		_root.setBottom(_game.getLabel());
	}

	//Returns the BorderPane
	public BorderPane getRoot() {
		return _root;
	}
}