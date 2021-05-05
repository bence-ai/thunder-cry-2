package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.magic.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Hunter extends Actor {

    private int turnToTravel = 3;
    private int maxX;
    private int maxY;


    public Hunter(Cell cell, String name) {
        super(cell, name);
        this.health = 375;
        this.maxHealth = this.health;
        this.manaPoint = 135;
        this.maxManaPoint = this.manaPoint;
        this.defense = 32;
        this.maxDefense = this.defense;
        this.attack = 135;
        this.maxAttack = this.attack;
        this.spellList = new ArrayList<Spell>();
        this.spellList.add(Spell.FIRE);

    }
    @Override
    public String getTileName() {
        return "hunter";
    }

    @Override
    public void onUpdate() {
        if (turnToTravel == 0) {
            turnToTravel = 3;
            int[] moves = getRandomPosition();
            move(moves[0], moves[1]);
        }
        turnToTravel--;

    }

    private int[] getRandomPosition() {
        // TODO need to implement -> started to implement it, will be kinda tricky need to rethink it to be good
        Random r = new Random();
        int x = 0;
        int y = 0;
        int maxTravelX = maxX - cell.getX();
        int maxTravelY = maxY - cell.getY();
        int travelX = r.nextInt(maxTravelX);
        int travelY = r.nextInt(maxTravelY);
        x = x + travelX;
        y = y + travelY;
        return new int[] {x,y};
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}
