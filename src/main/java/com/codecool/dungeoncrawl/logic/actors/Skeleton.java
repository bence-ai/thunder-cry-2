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
        int[] moves = getRandomDirection();
        move(moves[0], moves[1]);
    }

    public int[] getNextDirection() {
        int dx = 0;
        int dy = 0;
        switch (this.direction) {
            case NORTH:
                dx = 0;
                dy = -1;
                this.direction = Direction.EAST;
                break;
            case SOUTH:
                dx = 0;
                dy = 1;
                this.direction = Direction.WEST;
                break;
            case WEST:
                dx = -1;
                dy = 0;
                this.direction = Direction.NORTH;
                break;
            case EAST:
                dx = 1;
                dy = 0;
                this.direction = Direction.SOUTH;
                break;
        }
        int[] directionCoordinates = {dx, dy};

        return directionCoordinates;
    }


}
