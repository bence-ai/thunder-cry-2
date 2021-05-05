package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.util.Direction;

import java.util.Random;

public class Bandit extends Actor{
    Random random = new Random();
    public Bandit(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    @Override
    public String getTileName() {
        return "bandit";
    }


    @Override
    public void onUpdate() {
        for (int i = 0; i < 1; i++) {
            int[] moves = getRandomDirection();
            this.move(moves[0],moves[1]);
        }
    }
}
