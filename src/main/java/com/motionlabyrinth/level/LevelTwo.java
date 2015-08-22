package com.motionlabyrinth.level;

import com.motionlabyrinth.grid.GameGridBuilder;
import com.motionlabyrinth.tiles.FollowTile;
import com.motionlabyrinth.tiles.WallTile;

public class LevelTwo implements Level {
    @Override
    public GameGridBuilder getBuilder() {
        return new GameGridBuilder(13, 13)
                .followTileType(FollowTile.class)
                .wallTileType(WallTile.class)
                .startPoint(0, 6)
                .right(4).down(2).right(2).up(4)
                .left(4).up(3).right(8).down(10)
                .left(3).up(1).left(3).down(2).left(4);
    }
}
