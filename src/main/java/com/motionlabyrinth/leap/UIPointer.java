package com.motionlabyrinth.leap;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

/**
 * Singleton object whose coordinates gets updated on device's frame change event.
 * Can be used to bind UI component's coordinates.
 *
 * @author lassi
 */
public class UIPointer {

    private static UIPointer instance;

    private FrameChangedEvent frameChangedEvent;
    private FloatProperty pointableX = new SimpleFloatProperty();
    private FloatProperty pointableY = new SimpleFloatProperty();

    public static UIPointer getInstance() {
        if (instance == null) {
            instance = new UIPointer();
        }
        return instance;
    }

    /**
     * Update {@link FrameChangedEvent} associated with this object.
     */
    public void update() {
        if (frameChangedEvent != null) {
            frameChangedEvent.invoke();
        } else {
            System.out.println("You have not set an event for UIPointer");
        }
    }

    public void setFrameChangedEvent(FrameChangedEvent frameChangedEvent) {
        this.frameChangedEvent = frameChangedEvent;
    }

    public void removeFrameChangedEvent() {
        this.frameChangedEvent = null;
    }

    public float getPointableX() {
        return pointableX.get();
    }

    public FloatProperty pointableXProperty() {
        return pointableX;
    }

    public void setPointableX(float pointableX) {
        this.pointableX.set(pointableX);
    }

    public float getPointableY() {
        return pointableY.get();
    }

    public FloatProperty pointableYProperty() {
        return pointableY;
    }

    public void setPointableY(float pointableY) {
        this.pointableY.set(pointableY);
    }
}
