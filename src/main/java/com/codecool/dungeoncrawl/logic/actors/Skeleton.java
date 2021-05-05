package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.util.Direction;

import java.util.ArrayList;
import java.util.Random;

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
    public void move(int dx, int dy){
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

        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.FLOOR)) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            } else if (nextCell.getActor().getTileName().equals("player") || nextCell.getActor().getTileName().equals("bandit")) {
                return;
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }

    }

    @Override
    public void onUpdate() {
        this.move(0,0);

    }
}
