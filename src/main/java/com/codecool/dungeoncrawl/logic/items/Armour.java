package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Armour extends Item{

    public Armour(Cell cell, ItemType type, int property) {
        super(cell, type, property);
    }

    @Override
    public String getTileName() {
        return "armour";
    }
}
