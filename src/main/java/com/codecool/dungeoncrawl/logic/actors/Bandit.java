package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bandit extends Actor{
    public Bandit(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    @Override
    public String getTileName() {
        return "bandit";
    }

    @Override
    public void move(int dx, int dy) {

    }
}
