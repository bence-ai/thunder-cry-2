package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Hunter extends Actor {

    private int turnToTravel = 4;

    public Hunter(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return null;
    }

    @Override
    public void onUpdate() {
        if (turnToTravel == 0) {
            turnToTravel = 4;
            int[] moves = getRandomPosition();
            move(moves[0], moves[1]);
        }
        turnToTravel--;

    }

    private int[] getRandomPosition() {
        // TODO need to implement
        int x = 0;
        int y = 0;
        return new int[]{x, y};
    }
}
