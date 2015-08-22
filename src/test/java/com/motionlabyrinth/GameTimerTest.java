package com.motionlabyrinth;

import org.junit.Rule;
import org.junit.Test;
import javafx.scene.control.Label;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GameTimerTest {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void startAndStop_shouldToggleLabelVisibility() {
        Label label = new Label();
        label.setVisible(false);
        GameTimer timer = new GameTimer(label);

        timer.start();
        assertTrue(label.isVisible());

        timer.stopAndHide();
        assertFalse(label.isVisible());
    }

}