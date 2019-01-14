README Pacman

HANDIN:

This is my final handin for Pacman.

DESIGN CHOICES:

I designed Pacman using the following classes: App, PaneOrganizer, Game, SmartSquare,
MazeObject, Ghost, Pacman, BoardCoordinate, Direction, GhostMode, Constants.

The App class instantiates the PaneOrganizer and the Stage.

The PaneOrganizer class instantiates the Game class and the BorderPane.

The Game class has a setupMap method which creates the Pacman map out of a 2D array of
of type SmartSquare. It has a setupTimeline method and a setupGhostPen method which add 
a timeline to the four Ghosts and Pacman, and another to the GhostPen. It has a getLabel 
method which returns an HBox and adds two labels. There is also a getNode method that 
returns the pane that all nodes of the game were added to. The Game class contains the 
private Keyhandler class, which sets the direction of Pacman based on the keys pressed.
There is a private GhostPenHandler class which controls a queue of ghosts when they are 
in the pen and releases them one at a time. The private TimeHandler class has the method
changeMode, which counts down to change the ghosts' mode from chase to scatter, it has an
updateLabel mode to change the label that holds the score and the lives left. It has a
movePacman method to move pacman based on the direction selected. The checkCollision method
checks to see if there are objects in the place that Pacman has moved and changes the board,
score and lives accordingly. The moveGhosts method removes and adds the ghosts from the 
square they were in logically. The checkGameOver method stops the two timelines, keyhandler, 
and changes the label to indicate game over when the lives are 0. 

The SmartSquare class has an ArrayList of type MazeObject, and a getArray method to return
that ArrayList. It has a makeDot and makeEnergizer method to add them logically to the square.
It contains the private classes Dot and Energizer which both implement the MazeObject interface.

The MazeObject interface has the methods colide, which returns and integer to change the score, 
and the changeMode method, which takes a ghostMode as a parameter and returns a GhostMode.

The Ghost class has a get and set method for the Row and Columns which use the property of the 
of the Rectangle class. The moveGhost method uses a 2D array of Direction enums and a queue of 
BoardCoordinates in order to create a Breadth First Search algorithm by checking each square of 
the map and seeing which will take the Ghost the closes to its target. The Ghost also implements 
the MazeObject interface.

The Pacman class has a get and set method for both the rows and the columns to change the y and 
x position of Pacman.

The BoardCoordinate class takes a row, column and boolean isTarget as parameters, and has a 
checkValidity method to see if they coordinates are within the bounds.

The Direction enum has enums to represent directions.

The GhostMode enum has enums to represent the mode of the ghosts in the game.

The Constants class contains constants to refer to varius aspects of the objects in the game.

