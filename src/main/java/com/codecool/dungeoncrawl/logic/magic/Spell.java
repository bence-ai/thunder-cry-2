package com.codecool.dungeoncrawl.logic.magic;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public enum Spell {
    FIRE (new Magic("Fire", 30, 120, SpellType.BLACK), createImage(0)),
    THUNDER (new Magic("Thunder", 55, 185, SpellType.BLACK), createImage(1)),
    BLIZZARD (new Magic("Blizzard", 85, 275, SpellType.BLACK), createImage(2)),
    METEOR (new Magic("Meteor", 165, 450, SpellType.BLACK), createImage(3)),

    SMALL_HEAL (new Magic(" Flesh Heal", 25, 150, SpellType.WHITE), createImage(4)),
    BIG_HEAL (new Magic("Greater Heal", 55, 400, SpellType.WHITE), createImage(5)),

    ZOMBIE_BITE (new Magic("Zombie Kiss", 50, 150, SpellType.ZOMBIE), createImage(0)),
    SHADOW_BOLT (new Magic("Shadow bolt", 50, 120, SpellType.BLACK), createImage(0)),

    CURSE (new Magic("Curse of Weakness", 60, 1, SpellType.CURSE), createImage(0));

    Spell(Magic magick, Image image) {
        this.magic = magick;
        this.avatarImage = image;

    }
    private final Magic magic;
    private final Image avatarImage;

    public Image getAvatarImage() {
        return avatarImage;
    }

    public Magic getMagick() {
        return magic;
    }

    public static Image createImage(int number) {
        Image avatarsImage = new Image("spells.png");
        PixelReader reader = avatarsImage.getPixelReader();
        WritableImage newImage = null;

        if (number == 0) {
            newImage = new WritableImage(reader, 0, 0, 16, 16);
        } else if (number == 1) {
            newImage = new WritableImage(reader, 16, 0, 16, 16);
        } else if (number == 2) {
            newImage = new WritableImage(reader, 32, 0, 16, 16);
        } else if (number == 3) {
            newImage = new WritableImage(reader, 48, 0, 16, 16);
        }else if (number == 4) {
            newImage = new WritableImage(reader, 64, 0, 16, 16);
        } else if (number == 5) {
            newImage = new WritableImage(reader, 80, 0, 16, 16);
        } else if (number == 6) {
            newImage = new WritableImage(reader, 96, 0, 16, 16);
        } else if (number == 7) {
            newImage = new WritableImage(reader, 102, 0, 16, 16);
        }

        avatarsImage = newImage;
        return avatarsImage;
    }
}
