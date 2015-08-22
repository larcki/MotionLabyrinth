package com.motionlabyrinth.grid;

import com.motionlabyrinth.tiles.GameTile;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Set;

/**
 * Grid component that represents the game tile arrangement.
 *
 * @author lassi
 */
public class GameGrid extends GridPane {

    private Set<GameTile> tileChildren;
    private LevelEvent levelCompletedEvent;
    private LevelEvent levelStartEvent;
    private boolean started;
    private GameGridBuilder builder;

    /**
     * Constructs grid using builder.
     */
    public GameGrid(GameGridBuilder builder) {
        this.builder = builder;
        setLayoutX(0.5);
        setLayoutY(0.5);
        setOpacity(0.35);
        setStyle("-fx-background-color: lightgray");

        initGridConstraints();
        initFollowGameTiles();
        initWallGameTiles();
        initStartTile();
        initGoalTile();
    }

    /**
     * Checks which game tiles movable is hovering on and marks them as hovered.
     *
     * @param movable object whose position is used to determine hovering.
     */
    public void checkTouches(Rectangle movable) {
        getGameTiles().forEach(gameTile -> {
            if (movableHoversTile(movable, gameTile)) {
                gameTile.setHovered();
            }
        });
    }

    /**
     * Returns the count of hovered game tiles matching builder's wall game tile type.
     */
    public long getCountOfHoveredWallTiles() {
        return getGameTiles().stream().filter(child -> builder.getWallTileType().isInstance(child) && child.isHovered()).count();
    }

    /**
     * Add blur effect to this GameGrid
     */
    public void blur() {
        GaussianBlur blurEffect = new GaussianBlur();
        setEffect(blurEffect);
    }

    private Set<GameTile> getGameTiles() {
        if (tileChildren == null) {
            tileChildren = getChildrenAsTiles();
        }
        return tileChildren;
    }

    private Set<GameTile> getChildrenAsTiles() {
        Set<GameTile> tiles = new HashSet<>();
        getChildren().forEach(child -> {
            if (child instanceof GameTile) {
                tiles.add((GameTile) child);
            }
        });
        return tiles;
    }

    private boolean movableHoversTile(Rectangle movable, GameTile child) {
        return movable.getX() + movable.getWidth() > child.getLayoutX() && movable.getX() < child.getLayoutX() + child.getLayoutBounds().getWidth()
                && movable.getY() + movable.getHeight() > child.getLayoutY() && movable.getY() < child.getLayoutY() + child.getLayoutBounds().getHeight();
    }

    private void initGridConstraints() {
        for (int i = 0; i <= builder.getGridWidth(); i++) {
            getColumnConstraints().add(createColumnConstraint());
        }
        for (int i = 0; i <= builder.getGridHeight(); i++) {
            getRowConstraints().add(createRowConstraint());
        }
    }

    private void initFollowGameTiles() {
        builder.getTileCoordinateMap().forEach((coordinate, tile) -> {
            checkGridSize(coordinate);
            addTile(tile, coordinate.x, coordinate.y);
        });
    }

    private void checkGridSize(GameGridBuilder.Coordinate coordinate) {
        if (coordinate.x > builder.getGridWidth()) {
            throw new IndexOutOfBoundsException();
        }
        if (coordinate.y > builder.getGridHeight()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void initWallGameTiles() {
        for (int i = 0; i < getColumnConstraints().size(); i++) {
            for (int j = 0; j < getRowConstraints().size(); j++) {
                if (!builder.isPointPopulatedByFollowTile(i, j)) {
                    GameTile wallTile = builder.newInstanceOfWallTile();
                    addTile(wallTile, i, j);
                }
            }
        }
    }

    private void initStartTile() {
        GameTile startTile = new GameTile();
        startTile.setEnabled(true);
        startTile.setBackgroundColour("yellow");
        startTile.setTileHoverEvent(() -> {
            if (!started) {
                started = true;
                getGameTiles().forEach(tile -> tile.setEnabled(true));
                if (levelStartEvent != null) {
                    levelStartEvent.invoke();
                }
            }
        });
        addTile(startTile, builder.getStartColumnIndex(), builder.getStartRowIndex());
    }

    private void initGoalTile() {
        GameTile goalTile = new GameTile();
        goalTile.setTileHoverEvent(() -> {
            if (levelCompletedEvent != null) {
                levelCompletedEvent.invoke();
            }
        });
        addTile(goalTile, builder.getColumnIndex(), builder.getRowIndex());
    }

    private void addTile(GameTile child, int columnIndex, int rowIndex) {
        super.add(child, columnIndex, rowIndex);
        tileChildren = null;
    }

    private ColumnConstraints createColumnConstraint() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        return columnConstraints;
    }

    private RowConstraints createRowConstraint() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        return rowConstraints;
    }

    public void setLevelCompletedEvent(LevelEvent levelCompletedEvent) {
        this.levelCompletedEvent = levelCompletedEvent;
    }

    public void setLevelStartEvent(LevelEvent levelStartEvent) {
        this.levelStartEvent = levelStartEvent;
    }

}
