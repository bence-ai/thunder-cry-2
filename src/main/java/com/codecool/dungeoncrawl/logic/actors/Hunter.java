package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Hunter extends Actor {

    private int turnToTravel = 4;


    public Hunter(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    @Override
    public String getTileName() {
            return null;
    }

    @Override
    public void move(int dx, int dy) {
        // TODO
        Random newRandom = new Random();
    }

    @Override
    public void onUpdate() {

    }
}
