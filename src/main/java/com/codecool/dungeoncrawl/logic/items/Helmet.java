package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Helmet extends Item{
    public Helmet(Cell cell, ItemType type, int property) {
        super(cell, type, property);
    }

    @Override
    public String getTileName() {
        return "helm";
    }
}
