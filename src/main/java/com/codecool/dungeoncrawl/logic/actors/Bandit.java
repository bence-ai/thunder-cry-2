package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.util.Direction;

import java.util.ArrayList;


public class Bandit extends Actor{
    Direction direction = Direction.SOUTH;
    public Bandit(Cell cell, String name) {
        super(cell, name);
        this.health = 325;
        this.maxHealth = this.health;
        this.manaPoint = 125;
        this.maxManaPoint = this.manaPoint;
        this.defense = 15;
        this.maxDefense = this.defense;
        this.attack = 110;
        this.maxAttack = this.attack;
        this.spellList = new ArrayList<Spells>();
        this.spellList.add(Spells.SMALL_HEAL);
        this.spellList.add(Spells.SHADOW_BOLT);
        this.weapon = new Sword(cell, ItemType.WEAPON, 25);
        this.weapon.getCell().setItem(null);
    }

    @Override
    public String getTileName() {
        return "bandit";
    }


    @Override
    public void onUpdate() {
        for (int i = 0; i < 1; i++) {
            int[] moves = Direction.getRandomDirection(direction);
            this.move(moves[0],moves[1]);
        }
    }
}
