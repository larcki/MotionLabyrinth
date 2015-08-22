package com.motionlabyrinth.leap;

import com.leapmotion.leap.*;
import com.motionlabyrinth.Main;
import javafx.application.Platform;

/**
 * Custom Leap Motion listener for binding device frame data to {@link UIPointer} object.
 *
 * @author lassi
 */
public class UpdaterListener extends Listener {

    @Override
    public void onFrame(Controller controller) {
        Vector foremostFinger = getFrontmostPointablePosition(controller);
        float fingerX = foremostFinger.getX() * Main.APP_WIDTH;
        float fingerY = (1 - foremostFinger.getY()) * Main.APP_HEIGHT;
        updateUIComponent(fingerX, fingerY);
        System.out.println(fingerX + ", " + fingerY);
    }

    private Vector getFrontmostPointablePosition(Controller controller) {
        Frame frame = controller.frame();
        InteractionBox iBox = frame.interactionBox();
        Pointable pointable = frame.pointables().frontmost();
        Vector leapPoint = pointable.stabilizedTipPosition();
        return iBox.normalizePoint(leapPoint, true);
    }

    private void updateUIComponent(float fingerX, float fingerY) {
        Platform.runLater(() -> {
            UIPointer uiPointer = UIPointer.getInstance();
            uiPointer.setPointableX(fingerX);
            uiPointer.setPointableY(fingerY);
            uiPointer.update();
        });
    }

}
