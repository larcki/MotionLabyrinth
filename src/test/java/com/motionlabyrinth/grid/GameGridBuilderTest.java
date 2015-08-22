package com.motionlabyrinth.grid;

import com.motionlabyrinth.tiles.FollowTile;
import com.motionlabyrinth.tiles.GameTile;
import com.motionlabyrinth.tiles.WallTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameGridBuilderTest {

    @Test
    public void shouldPutTilesToCoordinateMap() {
        GameGridBuilder builder = new GameGridBuilder(4, 4).followTileType(GameTile.class)
                .startPoint(2, 2)   // adds 1
                .up(1)              // adds 1
                .right(2);          // adds 2
        assertEquals(4, builder.getTileCoordinateMap().size());
    }

    @Test
    public void downAndUp_shouldAffectRowIndex() {
        GameGridBuilder builder = new GameGridBuilder(10, 10).followTileType(GameTile.class).startPoint(0, 0);
        assertEquals(0, builder.getRowIndex()); // starting point
        builder.down(5);
        assertEquals(5, builder.getRowIndex()); // 0 + 5
        builder.up(5);
        assertEquals(0, builder.getRowIndex());
    }

    @Test
    public void leftAndRight_shouldAffectColumnIndex() {
        GameGridBuilder builder = new GameGridBuilder(10, 10).followTileType(GameTile.class).startPoint(0, 0);
        assertEquals(0, builder.getColumnIndex()); // starting point
        builder.right(5);
        assertEquals(5, builder.getColumnIndex()); // 0 + 5
        builder.left(5);
        assertEquals(0, builder.getColumnIndex());
    }

    @Test
    public void isPointPopulatedByFollowTile_shouldResolveFollowTileCoordinates() {
        GameGridBuilder builder = new GameGridBuilder(2, 2).followTileType(FollowTile.class).wallTileType(WallTile.class).startPoint(0, 0)
                .down(2).right(2);
        assertTrue(builder.isPointPopulatedByFollowTile(0, 0));
        assertTrue(builder.isPointPopulatedByFollowTile(0, 1));
        assertTrue(builder.isPointPopulatedByFollowTile(0, 2));
        assertTrue(builder.isPointPopulatedByFollowTile(1, 2));
        assertTrue(builder.isPointPopulatedByFollowTile(2, 2));

        assertFalse(builder.isPointPopulatedByFollowTile(1, 0));
        assertFalse(builder.isPointPopulatedByFollowTile(2, 0));
        assertFalse(builder.isPointPopulatedByFollowTile(1, 1));
        assertFalse(builder.isPointPopulatedByFollowTile(2, 1));
    }

    @Test
    public void newInstanceOfWallTile_shouldCreateNewInstance() {
        GameTile wallTile = new GameGridBuilder(2, 2).wallTileType(GameTile.class).newInstanceOfWallTile();
        assertEquals(GameTile.class, wallTile.getClass());
    }

    @Test(expected = IllegalStateException.class)
    public void newInstanceOfWallTile_shouldThrowExceptionIfNotSet() {
        new GameGridBuilder(1, 1).newInstanceOfWallTile();
    }


}