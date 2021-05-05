package com.codecool.dungeoncrawl.logic.actors;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public enum PlayerAvatar {
    BLUE_BOY(createAvatar(2)),
    BROWN_BOY(createAvatar(4)),
    GREEN_BOY(createAvatar(6)),
    RED_BOY(createAvatar(0)),
    PINK_GIRL(createAvatar(1)),
    ORANGE_GIRL(createAvatar(5)),
    BLUE_GIRL(createAvatar(3)),
    GREEN_GIRL(createAvatar(7));

    private final Image avatarImage;

    PlayerAvatar(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public Image getCharacterAvatar() {
        return avatarImage;
    }

    public static Image createAvatar(int number) {
        Image avatarsImage = new Image("avatars.png");
        PixelReader reader = avatarsImage.getPixelReader();
        WritableImage newImage = null;

        if (number == 0) {
            newImage = new WritableImage(reader, 0, 0, 96, 96);
        } else if (number == 1) {
            newImage = new WritableImage(reader, 96, 0, 96, 96);
        } else if (number == 2) {
            newImage = new WritableImage(reader, 192, 0, 96, 96);
        } else if (number == 3) {
            newImage = new WritableImage(reader, 288, 0, 96, 96);
        }else if (number == 4) {
            newImage = new WritableImage(reader, 0, 96, 96, 96);
        } else if (number == 5) {
            newImage = new WritableImage(reader, 96, 96, 96, 96);
        } else if (number == 6) {
            newImage = new WritableImage(reader, 192, 96, 96, 96);
        } else if (number == 7) {
            newImage = new WritableImage(reader, 288, 96, 96, 96);
        }

        avatarsImage = newImage;
        return avatarsImage;
    }
}
