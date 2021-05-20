package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    HAND(new BareHand(null, ItemType.WEAPON, 0)),
    KNIFE(new Knife(null, ItemType.WEAPON, 0)),
    SWORD(new Sword(null, ItemType.WEAPON, 0));

    private Weapon weapon;

    WeaponType(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
