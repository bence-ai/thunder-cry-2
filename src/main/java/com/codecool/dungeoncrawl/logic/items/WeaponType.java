package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    BareHand(new BareHand(null, ItemType.WEAPON, 0)),
    Knife(new Knife(null, ItemType.WEAPON, 15)),
    Sword(new Sword(null, ItemType.WEAPON, 35));

    private final Weapon weapon;

    WeaponType(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
