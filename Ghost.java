package Pacman;

import java.util.LinkedList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * Class of the Ghost which implements the MazeObject interface and 
 * controls the BFS movement of the ghost.
 */
public class Ghost implements MazeObject{

	private Rectangle _ghost;
	private Direction _ghostDirection;
	
	public Ghost(Color color, Pane ghostPane){
		_ghost = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_ghost.setFill(color);
		_ghostDirection = null;
		ghostPane.getChildren().add(_ghost);
	}
	
	//Method to get the current column of the Ghost
	public int getColumn() {
		return (int) (_ghost.getX() / Constants.SQUARE_SIZE);
	}
	
	//Method to set the column of the Ghost
	public void setColumn(int col){
		_ghost.setX(col * Constants.SQUARE_SIZE);
	}
	
	//Method to get the current row of the Ghost
	public int getRow(){
		return (int) (_ghost.getY() / Constants.SQUARE_SIZE);
	}
	
	//Method to set the row of the Ghost
	public void setRow(int row){
		_ghost.setY(row * Constants.SQUARE_SIZE);
	}
	
	//Method to set the color of the ghost.
	public void setColor(Color color) {
		_ghost.setFill(color);
	}
	
	//Method to move the Ghost based on a breadth first search algorithm
	public void moveGhost(BoardCoordinate target, SmartSquare[][] mapArray) {
		BoardCoordinate cur = new BoardCoordinate(this.getRow(), this.getColumn(), false);
		double closest = 33;
		Direction newDir = null;
		Direction[][] directionArray = new Direction[23][23];
		int leftCol = cur.getColumn() - 1;
		int rightCol = cur.getColumn() + 1;
		//deals with the wrapping of the ghost to not get an error.	
		if (leftCol < 0){
			leftCol = 22;
		} 
		if (rightCol > 22){
			rightCol = 0;
		}
		//Queue of BoardCoordinates to go through each one
		LinkedList<BoardCoordinate> queue = new LinkedList<BoardCoordinate>();
		if (_ghostDirection != Direction.RIGHT) {
			if  (mapArray[cur.getRow()][leftCol] != null){
				queue.addLast(new BoardCoordinate(cur.getRow(), leftCol, false));
				directionArray[cur.getRow()][leftCol] = Direction.LEFT;
			}
		}
		if (_ghostDirection != Direction.LEFT) {
			if  (mapArray[cur.getRow()][rightCol] != null){
				queue.addLast(new BoardCoordinate(cur.getRow(), rightCol, false));
				directionArray[cur.getRow()][rightCol] = Direction.RIGHT;
			}
		}
		if (_ghostDirection != Direction.UP) {
			if  (mapArray[cur.getRow() + 1][cur.getColumn()] != null){
				queue.addLast(new BoardCoordinate(cur.getRow() + 1, cur.getColumn(), false));
				directionArray[cur.getRow() + 1][cur.getColumn()] = Direction.DOWN;
			}
		}
		if (_ghostDirection != Direction.DOWN) {
			if  (mapArray[cur.getRow() - 1][cur.getColumn()] != null){
				queue.addLast(new BoardCoordinate(cur.getRow() - 1, cur.getColumn(), false));
				directionArray[cur.getRow() - 1][cur.getColumn()] = Direction.UP;
			}
		}
		while (!queue.isEmpty()) {
			cur = queue.removeFirst();
			leftCol = cur.getColumn() - 1;
			rightCol = cur.getColumn() + 1;
			if (leftCol < 0){
				leftCol = 22;
			} 
			if (rightCol > 22){
				rightCol = 0;
			}
			int curCol = cur.getColumn();
			int upRow = cur.getRow() - 1;
			int downRow = cur.getRow() + 1;
			int curRow = cur.getRow();
			double currentDist = Math.sqrt((curRow - target.getRow()) * (curRow - target.getRow())
					+ (curCol - target.getColumn()) * (curCol - target.getColumn()));
			//Checks to see if the current square is the closest to the target
			if (currentDist < closest) {
				closest = currentDist;
				newDir = directionArray[cur.getRow()][cur.getColumn()];	
			}
				if  (mapArray[curRow][leftCol] != null){
					if (directionArray[curRow][leftCol] == null){
						queue.addLast(new BoardCoordinate(curRow, leftCol, false));
						directionArray[curRow][leftCol] = directionArray[curRow][curCol];
					}
				}
				if (mapArray[curRow][rightCol] != null){
					if (directionArray[curRow][rightCol] == null){
						queue.addLast(new BoardCoordinate(curRow, rightCol, false));
						directionArray[curRow][rightCol] = directionArray[curRow][curCol];
					}
				}

				if  (mapArray[downRow][curCol] != null){
					if (directionArray[downRow][curCol] == null){
						queue.addLast(new BoardCoordinate(downRow, curCol, false));
						directionArray[downRow][curCol] = directionArray[curRow][curCol];
					}
				}
				if  (mapArray[upRow][curCol] != null){
					if (directionArray[upRow][curCol] == null){
						queue.addLast(new BoardCoordinate(upRow, curCol, false));
						directionArray[upRow][curCol] = directionArray[curRow][curCol];
					}	
				}
			}
		if (newDir == Direction.RIGHT){
			if (this.getColumn() + 1 > 22){
				this.setColumn(0);
			} else {
				this.setColumn(this.getColumn() + 1);
			}
		}
		if (newDir == Direction.LEFT){
			if (this.getColumn() - 1 < 0){
				this.setColumn(22);
			} else {
				this.setColumn(this.getColumn() - 1);
			}
		}
		if (newDir == Direction.UP){
			this.setRow(this.getRow() - 1);
		}
		if (newDir == Direction.DOWN){
			this.setRow(this.getRow() + 1);
		}
		_ghostDirection = newDir;			
	}
	
	//Method of the MazeObject interface	
	@Override
	public int collide() {
		return 0;
	}

	//Method of the MazeObject interface
	@Override
	public GhostMode changeMode(GhostMode mode) {
		return mode;
	}
}