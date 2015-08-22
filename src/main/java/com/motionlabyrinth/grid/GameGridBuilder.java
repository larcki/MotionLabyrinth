package com.motionlabyrinth.grid;

import com.motionlabyrinth.tiles.GameTile;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for grid.
 *
 * @author lassi
 */
public class GameGridBuilder {

    private Map<Coordinate, GameTile> tileCoordinateMap = new HashMap<>();
    private Class<? extends GameTile> followTileType;
    private Class<? extends GameTile> wallTileType;

    private int gridWidth;
    private int gridHeight;

    private int startColumnIndex;
    private int startRowIndex;

    private int columnIndex;
    private int rowIndex;

    /**
     * Constructor with buildable grid's width and height.
     */
    public GameGridBuilder(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    /**
     * Concrete type of the followed {@link GameTile}.
     */
    public GameGridBuilder followTileType(Class<? extends GameTile> type) {
        this.followTileType = type;
        return this;
    }

    /**
     * Concrete type of the wall (or avoidable) {@link GameTile}.
     */
    public GameGridBuilder wallTileType(Class<? extends GameTile> type) {
        this.wallTileType = type;
        return this;
    }

    /**
     * Starting point of the first follow game tile.
     */
    public GameGridBuilder startPoint(int x, int y) {
        startColumnIndex = x;
        startRowIndex = y;
        columnIndex = x;
        rowIndex = y;
        tileCoordinateMap.put(new Coordinate(columnIndex, rowIndex), newInstanceOf(followTileType));
        return this;
    }

    /**
     * Add follow-typed game tile(s) left from last position.
     *
     * @param distance amount of tiles to add.
     */
    public GameGridBuilder left(int distance) {
        return putNewGameTileToMap(distance, () -> columnIndex--);
    }

    /**
     * Add follow-typed game tile(s) right from last position.
     *
     * @param distance amount of tiles to add.
     */
    public GameGridBuilder right(int distance) {
        return putNewGameTileToMap(distance, () -> columnIndex++);
    }

    /**
     * Add follow-typed game tile(s) down from last position.
     *
     * @param distance amount of tiles to add.
     */
    public GameGridBuilder down(int distance) {
        return putNewGameTileToMap(distance, () -> rowIndex++);
    }

    /**
     * Add follow-typed game tile(s) up from last position.
     *
     * @param distance amount of tiles to add.
     */
    public GameGridBuilder up(int distance) {
        return putNewGameTileToMap(distance, () -> rowIndex--);
    }

    /**
     * @return new instance of wall tile.
     */
    public GameTile newInstanceOfWallTile() {
        return newInstanceOf(wallTileType);
    }

    public boolean isPointPopulatedByFollowTile(int x, int y) {
        return getTileCoordinateMap().keySet().stream().anyMatch(coordinate -> coordinate.x == x && coordinate.y == y);
    }

    private GameGridBuilder putNewGameTileToMap(int distance, CoordinateIndexUpdater updater) {
        for (int i = 0; i < distance; i++) {
            updater.updateIndex();
            tileCoordinateMap.put(new Coordinate(columnIndex, rowIndex), newInstanceOf(followTileType));
        }
        return this;
    }

    private GameTile newInstanceOf(Class<? extends GameTile> gameTile) {
        if (gameTile == null) {
            throw new IllegalStateException("game tile not set!");
        }
        try {
            return gameTile.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Could not instantiate new game tile");
        }
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public Map<Coordinate, GameTile> getTileCoordinateMap() {
        return tileCoordinateMap;
    }

    public int getStartColumnIndex() {
        return startColumnIndex;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public Class<? extends GameTile> getWallTileType() {
        return wallTileType;
    }

    /**
     * Coordinate represent two points.
     */
    public class Coordinate {
        public int x;
        public int y;

        protected Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Event for updating column/row index on every new game tile injection.
     */
    interface CoordinateIndexUpdater {
        void updateIndex();
    }

}
