package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.util.Direction;


public class Bandit extends Actor{
    Direction direction = Direction.SOUTH;
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
            if(Direction.isMovingThisTurn()){
                int[] moves = Direction.getRandomDirection();
                this.move(moves[0],moves[1]);
            }
        }
    }
}
