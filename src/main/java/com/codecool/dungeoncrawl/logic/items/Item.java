package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private ItemType type;

    private final int property;

    // TODO Need to implement Drawable for every item differently, return tileName for drawTile method

    public Item(Cell cell, ItemType type, int property) {
        this.cell = cell;
        if (cell != null) {
            cell.setItem(this);
        }
        this.type = type;
        this.property = property;
    }

    public int getProperty() {
        return property;
    }


    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }



}
