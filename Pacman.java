package Pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/*
 * This class is the Pacman that moves throughout the game and is represented
 * by a yellow ellipse. The constructor takes a pane as an argument.
 */
public class Pacman {

	private Ellipse _pacman;
	
	public Pacman(Pane pacPane){
		_pacman = new Ellipse(Constants.PACMAN_SIZE, Constants.PACMAN_SIZE);
		_pacman.setFill(Color.YELLOW);
		pacPane.getChildren().add(_pacman);
	}
	
	//Returns the column using ellipse getCenter method.	
	public int getColumn() {
		return (int) ((_pacman.getCenterX() - Constants.ELLIPSE_OFFSET) / Constants.SQUARE_SIZE);
	}

	//Sets the column using the ellipse's method.
	public void setColumn(int col){
		_pacman.setCenterX(col * Constants.SQUARE_SIZE + Constants.ELLIPSE_OFFSET);
	}

	//Returns the Row using ellipse getCenter method.
	public int getRow(){
		return (int) ((_pacman.getCenterY() - Constants.ELLIPSE_OFFSET) / Constants.SQUARE_SIZE);
	}
	
	//Sets the Row using the ellipse's method.	
	public void setRow(int row){
		_pacman.setCenterY(row * Constants.SQUARE_SIZE + Constants.ELLIPSE_OFFSET);
	}	
}