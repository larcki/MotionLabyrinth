package com.motionlabyrinth;

import com.leapmotion.leap.Controller;
import com.motionlabyrinth.leap.UpdaterListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author lassi
 */
public class Main extends Application {

    public static final int APP_WIDTH = 1024;
    public static final int APP_HEIGHT = 640;

    private Controller leapController;
    private UpdaterListener listener;

    @Override
    public void start(Stage primaryStage) throws Exception {
        leapController = new Controller();
        listener = new UpdaterListener();
        leapController.addListener(listener);
        Parent root = FXMLLoader.load(getClass().getResource("/GameController.fxml"));
        primaryStage.setTitle("Motion Labyrinth");
        primaryStage.setScene(new Scene(root, APP_WIDTH, APP_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
