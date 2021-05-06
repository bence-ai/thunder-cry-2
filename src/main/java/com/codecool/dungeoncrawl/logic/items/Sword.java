package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Weapon {

    public Sword(Cell cell, ItemType type, int property) {
        super(cell, type, property);
        this.avatar = WeaponType.SWORD.getAvatarImage();
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
