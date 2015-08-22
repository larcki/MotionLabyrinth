package com.motionlabyrinth;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Component for updating in-game time score.
 *
 * @author lassi
 */
public class GameTimer {

    private final static double TIMER_RATE = 0.1;

    private Label label;
    private double periodInSeconds;
    private Timeline timeline;

    /**
     * @param label label to represent the timing value.
     */
    public GameTimer(Label label) {
        this.label = label;
    }

    public void start() {
        periodInSeconds = 0;
        label.setVisible(true);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), actionEvent -> updateTimer()),
                new KeyFrame(Duration.seconds(TIMER_RATE)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stopAndHide() {
        timeline.stop();
        label.setVisible(false);
    }

    private void updateTimer() {
        periodInSeconds += TIMER_RATE;
        label.setText(String.format("%1$,.1f", periodInSeconds));
    }

}
