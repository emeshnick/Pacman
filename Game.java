package Pacman;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import cs015.fnl.PacmanSupport.BoardLocation;

/*
 * This class deals with the timelines of the game and sets up the map.
 * Most of the heavy-lifting for game logic is done in this class and so are the
 * labels and the map itself. It instantiates the four ghosts and pacman.
 */
public class Game{
	
	private Pane _gamePane;
	private cs015.fnl.PacmanSupport.BoardLocation[][] _map;
	private Pacman _pacman;
	private SmartSquare[][] _mapArray;
	private Direction _dir;
	private int _score, _lives;
	private Label _scoreLabel, _livesLabel;
	private Ghost _one, _two, _three, _four;
	private LinkedList<Ghost> _ghostQueue;
	private Timeline _timeline, _penTimeline;

	public Game() {
		_mapArray = new SmartSquare[23][23];
		_gamePane = new Pane();
		_score = 0;
		_lives = 3;
		_scoreLabel = new Label("The score is: " + _score);
		_livesLabel = new Label(_lives + " lives left");
	 	_ghostQueue = new LinkedList<Ghost>(); 
		Pane pacPane = new Pane();
		_pacman = new Pacman(pacPane);
		Pane ghostPane = new Pane();
		_one = new Ghost(Color.RED, ghostPane);
		_two = new Ghost(Color.PINK, ghostPane);
		_three = new Ghost(Color.ORANGE, ghostPane);
		_four = new Ghost(Color.AQUAMARINE, ghostPane);
		this.setupMap();
		_gamePane.getChildren().addAll(ghostPane, pacPane);
		pacPane.setFocusTraversable(true);
	 	KeyHandler keyHandler = new KeyHandler();
	 	pacPane.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
	 	this.setupTimeline();
	 	this.setupGhostPen();
	}

	//Sets up the map of SmartSquares, including  dots and energizers based on the imported 2D array from support code.
	public void setupMap(){
		 _map = cs015.fnl.PacmanSupport.SupportMap.getMap();
		 for (int row = 0; row < 23; row++){
			 for (int col = 0; col < 23; col++){
				 if (_map[row][col] == BoardLocation.WALL){
					Rectangle rect = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
					rect.setFill(Color.DARKBLUE);
					rect.setY(row * Constants.SQUARE_SIZE);
					rect.setX(col * Constants.SQUARE_SIZE);
					_gamePane.getChildren().add(rect);
					rect.setFocusTraversable(false);
				 }
				 if (_map[row][col] == BoardLocation.DOT){
					 Pane squarePane = new Pane();
					 SmartSquare square = new SmartSquare(row, col, squarePane);
					 square.makeDot();
					 _gamePane.getChildren().add(squarePane);
					 _mapArray[row][col] = square;
					 squarePane.setFocusTraversable(false);
				 }
				 if (_map[row][col] == BoardLocation.ENERGIZER) {
					 Pane squarePane = new Pane();
					 SmartSquare square = new SmartSquare(row, col, squarePane);
					 square.makeEnergizer();
					 _gamePane.getChildren().add(squarePane);
					 _mapArray[row][col] = square;
				 }
				 if (_map[row][col] == BoardLocation.FREE){
					 Pane squarePane = new Pane();
					 SmartSquare square = new SmartSquare(row, col, squarePane);
					 _gamePane.getChildren().add(squarePane);
					 _mapArray[row][col] = square;
					 squarePane.setFocusTraversable(false);
				 }
				 if (_map[row][col] == BoardLocation.GHOST_START_LOCATION){
					 Pane squarePane = new Pane();
					 SmartSquare square = new SmartSquare(row, col, squarePane);
					 _gamePane.getChildren().add(squarePane);
					 _mapArray[row][col] = square;
					_one.setColumn(col);
					_one.setRow(row - 2);
					_two.setColumn(col + 1);
					_two.setRow(row);
					_ghostQueue.add(_two);
					_three.setColumn(col - 1);
					_three.setRow(row);
					_ghostQueue.add(_three);
					_four.setColumn(col);
					_four.setRow(row);
					_ghostQueue.add(_four);
					squarePane.setFocusTraversable(false);
				 }
				 if ( _map[row][col] == BoardLocation.PACMAN_START_LOCATION){
					Pane squarePane = new Pane();
				 	SmartSquare square = new SmartSquare(row, col, squarePane);
				 	_pacman.setColumn(col);
				 	_pacman.setRow(row);
				 	_gamePane.getChildren().add(squarePane);
				 	_mapArray[row][col] = square;
				 	squarePane.setFocusTraversable(false);
				 }
			 }
		 }
	}
	
	//Sets up the timeline and sets it to infinite, sets the duration and creates new TimeHandler.
	public void setupTimeline(){
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION),
				new TimeHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}
	
	//Sets up the label and its spacing and returns an HBox	
	public HBox getLabel(){
		HBox hbox = new HBox();
		hbox.setSpacing(210);
		hbox.getChildren().addAll(_livesLabel, _scoreLabel);
		return hbox;	
	}
	
	//Returns the pane in which the game is contained graphically.
	public Pane getNode() {
		return _gamePane;
	}
	
	//Sets up the timeline for the GhostPen.
	private void setupGhostPen() {
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.PEN_DURATION),
				new GhostPenHandler());
		_penTimeline = new Timeline(kf);
		_penTimeline.setCycleCount(Animation.INDEFINITE);
		_penTimeline.play();
	}

/*
 * Class that deals with changing the direction the pacman is moving upon key input.
 */
private class KeyHandler implements EventHandler<KeyEvent> {

	@Override
	public void handle(KeyEvent event) {
		KeyCode keyPressed = event.getCode();
		if (keyPressed == KeyCode.RIGHT){
			if (_mapArray[_pacman.getRow()][_pacman.getColumn() + 1] != null) {
				_dir = Direction.RIGHT;
			}
		}
		if (keyPressed == KeyCode.LEFT){
			if (_mapArray[_pacman.getRow()][_pacman.getColumn() - 1] != null) {
				_dir = Direction.LEFT;
			}
		}
		if (keyPressed == KeyCode.DOWN){
			if (_mapArray[_pacman.getRow() + 1][_pacman.getColumn()] != null) {
				_dir = Direction.DOWN;
			}
		}
		if (keyPressed == KeyCode.UP){
			if (_mapArray[_pacman.getRow() - 1][_pacman.getColumn()] != null) {
				_dir = Direction.UP;
			}
		}
	}	
}

/*
 * Class that deals with the GhostPen timeline. Its handle method uses the ghost queue
 * to move ghosts from inside to outside the pen based on first in first out logic.
 */
private class GhostPenHandler implements EventHandler<ActionEvent> {


	@Override
	public void handle(ActionEvent event) {
		if (!_ghostQueue.isEmpty()){
			Ghost releasedGhost = _ghostQueue.removeFirst();
			releasedGhost.setRow(8);
			releasedGhost.setColumn(11);
		}
	}
	
}

/*
 * This class deals with the movement of the Ghost and Pacman based on their timeline.
 * It also checks for MazeObjects in Pacman's path and updates the game based on the 
 * objects.
 */
private class TimeHandler implements EventHandler<ActionEvent>{

	GhostMode _mode = GhostMode.CHASE;
	int _modeCounter = 0;
	int _frightenedCounter = 0;
	
	@Override
	public void handle(ActionEvent event) {
		this.changeMode();
		this.updateLabel();
		this.movePacman();
		this.checkCollision();
		this.checkGameOver();
		this.moveGhosts();
		this.checkCollision();
		this.checkGameOver();
	}
	
	//Ends the game if pacman has no lives left.
	private void checkGameOver(){
		if (_lives == 0){
			_livesLabel.setText("GAME OVER");
			_timeline.stop();
			_penTimeline.stop();
			
		}
	}

	//Counts down to change the ghost mode from Chase to Scatter or from Frightened to Chase.
	private void changeMode(){
		if (_mode == GhostMode.CHASE || _mode == GhostMode.SCATTER){
			_modeCounter = _modeCounter + 1;
			if (_modeCounter < 30) {
				_mode = GhostMode.CHASE;
			} else {
				_mode = GhostMode.SCATTER;
			}
			if ( _modeCounter > 40){
				_modeCounter = 0;
			}
		}
		if (_mode == GhostMode.FRIGHTENED) {
			_frightenedCounter = _frightenedCounter + 1;
			if (_frightenedCounter > 20){
				_one.setColor(Color.RED);
				_two.setColor(Color.PINK);
				_three.setColor(Color.ORANGE);
				_four.setColor(Color.AQUAMARINE);
				_frightenedCounter = 0;
				_mode = GhostMode.CHASE;
			}
		}
	}

	//Moves the Ghosts using their methods, and removing and adding them from the SmartSquare ArrayList
	private void moveGhosts() {
		int pacmanY = _pacman.getRow();
		int pacmanX = _pacman.getColumn();
		_mapArray[_one.getRow()][_one.getColumn()].getArray().remove(_one);
		_mapArray[_two.getRow()][_two.getColumn()].getArray().remove(_two);
		_mapArray[_three.getRow()][_three.getColumn()].getArray().remove(_three);
		_mapArray[_four.getRow()][_four.getColumn()].getArray().remove(_four);
		if (_mode == GhostMode.CHASE){
			_one.moveGhost(new BoardCoordinate(pacmanY, pacmanX - 3, true), _mapArray);
			_two.moveGhost(new BoardCoordinate(pacmanY + 3, pacmanX, true), _mapArray);
			_three.moveGhost(new BoardCoordinate(pacmanY -1, pacmanX + 1, true), _mapArray);
			_four.moveGhost(new BoardCoordinate(pacmanY, pacmanX, true), _mapArray);
		}
		if (_mode == GhostMode.SCATTER) {	
			_one.moveGhost(new BoardCoordinate(3, 3, true), _mapArray);
			_two.moveGhost(new BoardCoordinate(19, 3, true), _mapArray);
			_three.moveGhost(new BoardCoordinate(3, 19, true), _mapArray);
			_four.moveGhost(new BoardCoordinate(19, 19, true), _mapArray);
		}
		if (_mode == GhostMode.FRIGHTENED){
			_one.moveGhost(new BoardCoordinate((int)(22 * Math.random()), (int)(22 * Math.random()), true), _mapArray);
			_two.moveGhost(new BoardCoordinate((int)(22 * Math.random()), (int)(22 * Math.random()), true), _mapArray);
			_three.moveGhost(new BoardCoordinate((int)(22 * Math.random()), (int)(22 * Math.random()), true), _mapArray);
			_four.moveGhost(new BoardCoordinate((int)(22 * Math.random()), (int)(22 * Math.random()), true), _mapArray);
		}
		_mapArray[_one.getRow()][_one.getColumn()].getArray().add(_one);
		_mapArray[_two.getRow()][_two.getColumn()].getArray().add(_two);
		_mapArray[_three.getRow()][_three.getColumn()].getArray().add(_three);
		_mapArray[_four.getRow()][_four.getColumn()].getArray().add(_four);
	}

	//Updates the label based on the new score and lives.
	private void updateLabel() {
		_scoreLabel.setText("The score is: " + _score );
		_livesLabel.setText("Live: " + _lives);
	}

	//Checks for MazeObjects in the same column and row as pacman and updates the game based on which MazeObject 
	// is in the square.
	public void checkCollision(){
		ArrayList<MazeObject> squareArrayList = _mapArray[_pacman.getRow()][_pacman.getColumn()].getArray();
		for (int i = 0; i < squareArrayList.size(); i++){
			_mode = squareArrayList.get(i).changeMode(_mode);
			_score = _score + squareArrayList.get(i).collide();	
			if(_mode == GhostMode.FRIGHTENED){
				if(squareArrayList.contains(_one)){
					_score = _score + 200;
					_mapArray[_one.getRow()][_one.getColumn()].getArray().remove(_one);
					_one.setRow(11);
					_one.setColumn(11);
					_mapArray[_one.getRow()][_one.getColumn()].getArray().add(_one);
					_ghostQueue.addLast(_one);
				}
				if(squareArrayList.contains(_two)){
					_score = _score + 200;
					_mapArray[_two.getRow()][_two.getColumn()].getArray().remove(_two);
					_two.setRow(11);
					_two.setColumn(11);
					_mapArray[_two.getRow()][_two.getColumn()].getArray().add(_two);
					_ghostQueue.addLast(_two);
				}
				if(squareArrayList.contains(_three)){
					_score = _score + 200;
					_mapArray[_three.getRow()][_three.getColumn()].getArray().remove(_three);
					_three.setRow(11);
					_three.setColumn(11);
					_mapArray[_three.getRow()][_three.getColumn()].getArray().add(_three);
					_ghostQueue.addLast(_three);
				}
				if(squareArrayList.contains(_four)){
					_score = _score + 200;
					_mapArray[_four.getRow()][_four.getColumn()].getArray().remove(_four);	
					_four.setRow(11);
					_four.setColumn(11);
					_mapArray[_four.getRow()][_four.getColumn()].getArray().add(_four);
					_ghostQueue.addLast(_four);
				}
				_one.setColor(Color.BLUE);
				_two.setColor(Color.BLUE);
				_three.setColor(Color.BLUE);
				_four.setColor(Color.BLUE);
			}
			//Resets location of Ghost and Pacman if they collide during Chase mode.
			if (_mode == GhostMode.CHASE || _mode == GhostMode.SCATTER){
				if(squareArrayList.contains(_one) || squareArrayList.contains(_two) || squareArrayList.contains(_three)
						|| squareArrayList.contains(_four)){
					_lives = _lives - 1;
					_mapArray[_one.getRow()][_one.getColumn()].getArray().remove(_one);
					_mapArray[_two.getRow()][_two.getColumn()].getArray().remove(_two);
					_mapArray[_three.getRow()][_three.getColumn()].getArray().remove(_three);
					_mapArray[_four.getRow()][_four.getColumn()].getArray().remove(_four);
					_pacman.setRow(17);
					_pacman.setColumn(11);
					_one.setRow(8);
					_one.setColumn(11);
					_two.setRow(11);
					_two.setColumn(10);
					_three.setRow(11);
					_three.setColumn(12);
					_four.setRow(11);
					_four.setColumn(11);
					_ghostQueue.addLast(_two);
					_ghostQueue.addLast(_three);
					_ghostQueue.addLast(_four);
					_mapArray[_one.getRow()][_one.getColumn()].getArray().add(_one);
					_mapArray[_two.getRow()][_two.getColumn()].getArray().add(_two);
					_mapArray[_three.getRow()][_three.getColumn()].getArray().add(_three);
					_mapArray[_four.getRow()][_four.getColumn()].getArray().add(_four);
				}
			}	
		}
	}
	
	//Moves pacman's location based on the direction that pacman is moving as long as there is a SmartSquare.
	public void movePacman(){
		if (_dir == Direction.RIGHT){
			if (_pacman.getColumn() + 1 > 22){
				_pacman.setColumn(0);
			}else if (_mapArray[_pacman.getRow()][_pacman.getColumn() + 1] != null) {
				_pacman.setColumn(_pacman.getColumn() + 1);
			} 
		}
		if (_dir == Direction.LEFT){
			if (_pacman.getColumn() - 1 < 0) {
				_pacman.setColumn(22);
			} else if (_mapArray[_pacman.getRow()][_pacman.getColumn() - 1] != null) {
				_pacman.setColumn(_pacman.getColumn() - 1);
			} 
		}
		if (_dir == Direction.UP){
			if (_mapArray[_pacman.getRow() - 1][_pacman.getColumn()] != null) {
				_pacman.setRow(_pacman.getRow() - 1);
			} 
		}
		if (_dir == Direction.DOWN){
			if (_mapArray[_pacman.getRow() + 1][_pacman.getColumn()] != null) {
				_pacman.setRow(_pacman.getRow() + 1);
			}
		}
	}
	}
}
