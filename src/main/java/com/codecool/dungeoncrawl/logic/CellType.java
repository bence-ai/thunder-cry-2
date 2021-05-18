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
    BRIDGE("bridge", true),
    BOTTOM_LAND("bottom_land", false),
    TOP_LAND("top_land", false),
    RIGHT_LAND("right_land", false),
    LEFT_CORNER_LAND("left_corner_land", false),
    TOP_CORNER_LAND("top_right_corner_land", false),
    BOTTOM_CORNER_LAND("bottom_right_corner_land", false),
    BOTTOM_CORNER_INVERSE("bottom_right_corner_inverse", false),
    TOP_CORNER_INVERSE("top_right_corner_inverse", false),
    OPEN_DOOR("open door", true),
    CLOSED_DOOR("closed door", false),
    STAIRS("stairs", true),
    RIVER("river", false),
    RIVER_END("river_end", false);

    private final String tileName;

    public boolean isStepable() {
        return stepable;
    }

    public void setStepable(boolean stepable) {
        this.stepable = stepable;
    }

    private boolean stepable;

    CellType(String tileName, boolean isStepable) {
        this.stepable = isStepable;

        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
