package Pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the  main class where your Pacman game will start.
  * The main method of this application calls the App constructor. You
  * will need to fill in the constructor to instantiate your game.
  *
  * Class comments here...
  *
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
    	stage.setTitle("Pacman");
    	PaneOrganizer paneOrganizer = new PaneOrganizer();
    	Scene scene = new Scene(paneOrganizer.getRoot(),
    			Constants.APP_WIDTH, Constants.APP_HEIGHT);
    	stage.setScene(scene);
    	stage.show();
        // Create top-level object, set up the scene, and show the stage here.
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
