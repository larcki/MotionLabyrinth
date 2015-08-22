package com.motionlabyrinth.tiles;

public class FollowTile extends GameTile {

    public FollowTile() {
        setBackgroundColour("white");
        setTileHoverEvent(() -> setBackgroundColour("limegreen"));
    }
}
