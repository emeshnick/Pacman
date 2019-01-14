package Pacman;

/*
 * This interface is used to organize all of the objects that
 * Pacman runs into during the game.
 */
public interface MazeObject{
	
	//Abstract method for collision with pacman to update the score
	public abstract int collide();
	
	//Abstract method to change the ghost mode
	public abstract GhostMode changeMode(GhostMode mode);
}
