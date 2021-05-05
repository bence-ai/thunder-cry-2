package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item {

    public Sword(Cell cell, ItemType type, int property) {
        super(cell, type, property);
    }

    @Override
    public String getTileName() {
        return "sword";
    }

}
