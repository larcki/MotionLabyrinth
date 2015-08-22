package com.motionlabyrinth.tiles;

import javafx.scene.layout.Pane;

/**
 * Represents single tile in game grid.
 *
 * @author lassi
 */
public class GameTile extends Pane {

    private TileHoverEvent tileHoverEvent;
    private boolean isHovered;
    private boolean enabled;

    /**
     * Set tile in hovered state and invoke associated hover event.
     */
    public void setHovered() {
        if (enabled) {
            isHovered = true;
            if (tileHoverEvent != null) {
                tileHoverEvent.hover();
            }
        }
    }

    /**
     * Set tile in enabled/disabled state.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set the event which will be invoked on setHovered method call.
     */
    public void setTileHoverEvent(TileHoverEvent tileHoverEvent) {
        this.tileHoverEvent = tileHoverEvent;
    }

    /**
     * Set the background colour of the tile.
     */
    public void setBackgroundColour(String colour) {
        setStyle("-fx-background-color: " + colour);
    }

    public boolean isHovered() {
        return isHovered;
    }

}
