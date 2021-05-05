package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.util.Direction;


public class Skeleton extends Actor {
    private Direction direction = Direction.NORTH;

    public Skeleton(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }


    @Override
    public void onUpdate() {
        if (Direction.isMovingThisTurn()) {
            int[] moves = Direction.getRandomDirection();
            move(moves[0], moves[1]);
        }
    }


}
