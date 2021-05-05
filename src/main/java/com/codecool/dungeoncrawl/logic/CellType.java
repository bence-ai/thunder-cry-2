package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    OPEN_DOOR("open door"),
    CLOSED_DOOR("closed door"),
    STAIRS("stairs"); // There is a square type "stairs down". Entering this square moves the player to a different map.

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
