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
        if (Direction.isMovingThisTurn()) {
            int[] moves = Direction.getRandomDirection();
            move(moves[0], moves[1]);
        }
    }


}
