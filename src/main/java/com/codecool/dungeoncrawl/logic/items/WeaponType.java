package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    Hand(new BareHand(null, ItemType.WEAPON, 0)),
    Knife(new Knife(null, ItemType.WEAPON, 0)),
    Sword(new Sword(null, ItemType.WEAPON, 0));

    private Weapon weapon;

    WeaponType(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
