package com.codecool.dungeoncrawl.logic.items;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public enum WeaponType {
    HAND(createAvatar(0)),
    KNIFE(createAvatar(1)),
    SWORD(createAvatar(2)),
    AXE(createAvatar(3));

    private final Image avatarImage;

    WeaponType(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }

    public static Image createAvatar(int number) {
        Image avatarsImage = new Image("weapons.png");
        PixelReader reader = avatarsImage.getPixelReader();
        WritableImage newImage = null;
        int WIDTH = 24;

        if (number == 0) {
            newImage = new WritableImage(reader, 0, 0, WIDTH, WIDTH);
        } else if (number == 1) {
            newImage = new WritableImage(reader, WIDTH, 0, WIDTH, WIDTH);
        } else if (number == 2) {
            newImage = new WritableImage(reader, WIDTH*2, 0, WIDTH, WIDTH);
        } else if (number == 3) {
            newImage = new WritableImage(reader, WIDTH*3, 0, WIDTH, WIDTH);
        }else if (number == 4) {
            newImage = new WritableImage(reader, WIDTH*4, 0, WIDTH, WIDTH);
        } else if (number == 5) {
            newImage = new WritableImage(reader, WIDTH*5, 0, WIDTH, WIDTH);
        } else if (number == 6) {
            newImage = new WritableImage(reader, WIDTH*6, 0, WIDTH, WIDTH);
        } else if (number == 7) {
            newImage = new WritableImage(reader, WIDTH*7, 0, WIDTH, WIDTH);
        }


        avatarsImage = newImage;
        return avatarsImage;
    }
}
