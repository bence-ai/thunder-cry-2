package com.codecool.dungeoncrawl.logic.Magic;

public enum Spells {
    FIRE (new Magic("Fire", 30, 120, SpellType.BLACK)),
    SHADOW_BOLT (new Magic("Shadow bolt", 50, 120, SpellType.BLACK)),
    THUNDER (new Magic("Thunder", 55, 185, SpellType.BLACK)),
    BLIZZARD (new Magic("Blizzard", 85, 275, SpellType.BLACK)),
    METEOR (new Magic("Meteor", 165, 450, SpellType.BLACK)),

    SMALL_HEAL (new Magic(" Flesh Heal", 25, 150, SpellType.WHITE)),
    BIG_HEAL (new Magic("Greater Heal", 55, 400, SpellType.WHITE)),

    ZOMBIE_BITE (new Magic("Zombie Kiss", 50, 150, SpellType.ZOMBIE)),

    CURSE (new Magic("Curse of Weakness", 60, 1, SpellType.CURSE));

    Spells(Magic magick) {
        this.magic = magick;

    }
    private final Magic magic;

    public Magic getMagick() {
        return magic;
    }
}
