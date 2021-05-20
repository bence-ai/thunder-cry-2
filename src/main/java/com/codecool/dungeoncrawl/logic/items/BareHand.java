package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BareHand extends Weapon{
    public BareHand(Cell cell, ItemType type, int property) {
        super(cell, type, property);

        this.avatar = WeaponAvatar.HAND.getAvatarImage();
    }

    @Override
    public String getTileName() {
        return "hand";
    }
}
