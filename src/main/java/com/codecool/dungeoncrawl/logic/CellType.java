package com.codecool.dungeoncrawl.logic;

public enum CellType {
    PLAYER("player"),
    EMPTY("empty"),
    FLOOR("floor"),
    DIRTY_FLOOR("dirty_floor"),
    GRASS_FLOOR("grass_floor"),
    WALL("wall"),
    TREE1("tree1"),
    TREE2("tree2"),
    TREE3("tree3"),
    TREE4("tree4"),
    CHARACTER("character"),
    WATER("water"),
    BOTTOM_LAND("bottom_land"),
    TOP_LAND("top_land"),
    RIGHT_LAND("right_land"),
    TOP_CORNER_LAND("top_right_corner_land"),
    BOTTOM_CORNER_LAND("bottom_right_corner_land"),
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
