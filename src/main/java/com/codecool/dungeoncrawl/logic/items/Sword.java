package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Sword extends Weapon {

    public Sword(Cell cell, ItemType type, int property) {
        super(cell, type, property);
        this.avatar = WeaponType.SWORD.getAvatarImage();
    }

    public Sword() {
        super(new Cell(null, 0,0, CellType.FLOOR), ItemType.WEAPON, 100);
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
