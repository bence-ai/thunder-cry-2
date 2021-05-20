package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Knife extends Weapon{
    public Knife(Cell cell, ItemType type, int property) {
        super(cell, type, property);
        this.avatar = WeaponAvatar.KNIFE.getAvatarImage();
    }

    @Override
    public String getTileName() {
        return "knife";
    }
}
