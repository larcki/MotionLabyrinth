package com.motionlabyrinth.tiles;

public class WallTile extends GameTile {

    public WallTile() {
        setBackgroundColour("black");
        setTileHoverEvent(() -> setBackgroundColour("maroon"));
    }
}
