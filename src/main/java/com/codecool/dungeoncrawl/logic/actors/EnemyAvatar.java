package com.codecool.dungeoncrawl.logic.actors;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public enum EnemyAvatar {
    SKELETON(createAvatar(0)),
    HUNTER(createAvatar(1)),
    BANDIT(createAvatar(2));

    private final Image avatarImage;

    EnemyAvatar(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public Image getEnemyAvatar() {
        return avatarImage;
    }

    public static Image createAvatar(int number) {
        Image avatarsImage = new Image("enemyAvatars.png");
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
