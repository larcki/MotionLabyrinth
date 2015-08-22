package com.motionlabyrinth.level;

import com.motionlabyrinth.grid.GameGridBuilder;

/**
 * Represents the game level building logic.
 *
 * @author lassi
 */
public interface Level {

    /**
     * Initialize and return a builder for the level.
     */
    GameGridBuilder getBuilder();

}
