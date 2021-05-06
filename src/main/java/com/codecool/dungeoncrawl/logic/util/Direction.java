package com.codecool.dungeoncrawl.logic.util;

import java.util.Random;

public enum Direction {
    NORTH,SOUTH,WEST,EAST;

    public static int[] getRandomDirection () {
        Random random = new Random();
        int dx = 0;
        int dy = 0;
        int randMove = random.nextInt(4);
        switch (randMove) {
            case 0:
                dy = -1;
                break;
            case 1:
                dy = 1;
                break;
            case 2:
                dx = -1;
                break;
            case 3:
                dx = 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + randMove);
        }
        return new int[]{dx,dy};
    }

    public static boolean isMovingThisTurn() {
        Random random = new Random();
        int chanceToMove = random.nextInt(11);
        return chanceToMove > 7;
    }
}

