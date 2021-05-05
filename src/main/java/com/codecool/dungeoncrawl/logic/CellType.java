package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", true),
    FLOOR("floor", true),
    DIRTY_FLOOR("dirty_floor", true),
    GRASS_FLOOR("grass_floor", true),
    WALL("wall", false),
    TREE1("tree1", false),
    TREE2("tree2", false),
    TREE3("tree3", false),
    TREE4("tree4", false),
    WATER("water", false),
    BOTTOM_LAND("bottom_land", true),
    TOP_LAND("top_land", true),
    RIGHT_LAND("right_land", true),
    TOP_CORNER_LAND("top_right_corner_land", true),
    BOTTOM_CORNER_LAND("bottom_right_corner_land", true),
    OPEN_DOOR("open door", true),
    CLOSED_DOOR("closed door", false),
    STAIRS("stairs", true); // There is a square type "stairs down". Entering this square moves the player to a different map.

    private final String tileName;

    public boolean isStepable() {
        return isStepable;
    }

    public void setStepable(boolean stepable) {
        isStepable = stepable;
    }

    private boolean isStepable;

    CellType(String tileName, boolean isStepable) {
        this.isStepable = isStepable;
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }


}
