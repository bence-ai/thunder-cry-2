package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import javafx.scene.image.Image;

public abstract class Weapon extends Item{
    protected Image avatar;

    public Weapon(Cell cell, ItemType type, int property) {
        super(cell, type, property);
    }

    public Image getWeaponAvatar() {
        return avatar;
    }
}
