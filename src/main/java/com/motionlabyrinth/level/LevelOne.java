package com.motionlabyrinth.level;

import com.motionlabyrinth.grid.GameGridBuilder;
import com.motionlabyrinth.tiles.FollowTile;
import com.motionlabyrinth.tiles.WallTile;

public class LevelOne implements Level {

    @Override
    public GameGridBuilder getBuilder() {
        return new GameGridBuilder(11, 11)
                .followTileType(FollowTile.class)
                .wallTileType(WallTile.class)
                .startPoint(5, 0)
                .down(2).left(2).down(2).right(1)
                .down(1).right(4).up(2).left(1)
                .up(2).right(3).down(6).left(1)
                .down(2).left(3).up(2).left(5)
                .down(2).right(3).down(2);
    }
}
