package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Hunter extends Actor {

    private int turnToTravel = 3;
    private int maxX;
    private int maxY;

    public Hunter(Cell cell, String name, int health, int manaPoint, int defense, int attack, int mapWidth, int mapHeight) {
        super(cell, name, health, manaPoint, defense, attack);
        this.maxX = mapWidth-1;
        this.maxY = mapHeight-1;
    }

    @Override
    public String getTileName() {
        return "hunter";
    }

    @Override
    public void onUpdate() {
        if (turnToTravel == 0) {
            turnToTravel = 3;
            int[] moves = getRandomPosition();
            move(moves[0], moves[1]);
        }
        turnToTravel--;

    }

    private int[] getRandomPosition() {
        // TODO need to implement -> started to implement it, will be kinda tricky need to rethink it to be good
        Random r = new Random();
        int x = 0;
        int y = 0;
        int maxTravelX = maxX - cell.getX();
        int maxTravelY = maxY - cell.getY();
        int travelX = r.nextInt(maxTravelX);
        int travelY = r.nextInt(maxTravelY);
        x = x + travelX;
        y = y + travelY;
        return new int[] {x,y};
    }

}
