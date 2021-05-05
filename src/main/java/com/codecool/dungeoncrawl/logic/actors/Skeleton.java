package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.util.Direction;

import java.util.ArrayList;


public class Skeleton extends Actor {
    private Direction direction = Direction.NORTH;

    public Skeleton(Cell cell, String name) {
        super(cell, name);
        this.health = 400;
        this.maxHealth = this.health;
        this.manaPoint = 80;
        this.maxManaPoint = this.manaPoint;
        this.defense = 15;
        this.maxDefense = this.defense;
        this.attack = 75;
        this.maxAttack = this.attack;
        this.spellList = new ArrayList<Spells>();
        this.spellList.add(Spells.FIRE);


    }

    @Override
    public String getTileName() {
        return "skeleton";
    }


    @Override
    public void onUpdate() {

        int[] moves = Direction.getRandomDirection(direction);
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
