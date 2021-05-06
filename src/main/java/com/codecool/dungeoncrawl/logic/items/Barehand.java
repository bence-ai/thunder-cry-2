package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Barehand extends Weapon{
    public Barehand(Cell cell, ItemType type, int property) {
        super(cell, type, property);
        this.avatar = WeaponType.HAND.getAvatarImage();
    }

    @Override
    public String getTileName() {
        return "hand";
    }
}
