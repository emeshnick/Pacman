package Pacman;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/*
 * This class is a square on the map on which MazeObjects can be contained in
 * the MazeObject ArrayList. 
 */
public class SmartSquare {

	private ArrayList<MazeObject> _mazeObjects;
	private Rectangle _rect;
	private double _xLoc;
	private double _yLoc;
	private Pane _squarePane;

	public SmartSquare(int row, int col, Pane squarePane) {
		_rect = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_rect.setFill(Color.BLACK);
		_mazeObjects = new ArrayList<MazeObject>();
		_squarePane = squarePane;
		_squarePane.getChildren().add(_rect);
		_yLoc = row * Constants.SQUARE_SIZE;
		_xLoc = col * Constants.SQUARE_SIZE;
		_rect.setY(_yLoc);
		_rect.setX(_xLoc);
	}

	//Method to create a new Dot on the square.
	public void makeDot() {
		Dot dot = new Dot();
		_mazeObjects.add(dot);
		
	}
	
	//Method to create a new Energizer on the square.
	public void makeEnergizer() {
		Energizer energizer = new Energizer();
		_mazeObjects.add(energizer);
	}
	
	//Method to return the Arraylist that holds all of the MazeObjects on the the square
	public ArrayList<MazeObject> getArray() {
		return _mazeObjects;
	}
	
/*
 * Class of the Dot that some SmartSquares contain at the beginning of the game.
 */
private class Dot implements MazeObject{

	private Ellipse _dot;

	public Dot(){
		_dot = new Ellipse(_xLoc + Constants.ELLIPSE_OFFSET, _yLoc + Constants.ELLIPSE_OFFSET,
				Constants.DOT_SIZE, Constants.DOT_SIZE);
		_dot.setFill(Color.WHITE);
		_squarePane.getChildren().add(_dot);
	}

	//MazeObject method that removes dot logically and returns the score
	@Override
	public int collide() {
		_squarePane.getChildren().remove(_dot);
		_mazeObjects.remove(this);
		return 10;  
	}

	//MazeObject method that returns the same mode entered
	@Override
	public GhostMode changeMode(GhostMode mode) {
		return mode;
	}

	
}

/*
 * Class that is the energizer, which when Pacman collides with it it changes the mode
 * and adds to the score.
 */
private class Energizer implements MazeObject {

	private Ellipse _energizer;	
	
	public Energizer(){
		_energizer = new Ellipse(_xLoc + Constants.ELLIPSE_OFFSET, _yLoc + Constants.ELLIPSE_OFFSET,
				Constants.ENERGIZER_SIZE, Constants.ENERGIZER_SIZE);
		_energizer.setFill(Color.WHITE);
		_squarePane.getChildren().add(_energizer);
	}
	
	//MazeObject method that returns the energizer's score and removes it graphically and logically
	@Override
	public int collide() {
		_squarePane.getChildren().remove(_energizer);
		_mazeObjects.remove(this);
		return 100;
	}

	//MazeObject method that returns Frightened ghost mode
	@Override
	public GhostMode changeMode(GhostMode mode) {
		return GhostMode.FRIGHTENED;
	}

	
}

}