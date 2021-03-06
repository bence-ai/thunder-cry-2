package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.magic.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Hunter extends Actor {

    private int turnToTravel = 3;


    public Hunter(Cell cell, String name) {
        super(cell, name);
        this.health = 550;
        this.maxHealth = this.health;
        this.manaPoint = 135;
        this.maxManaPoint = this.manaPoint;
        this.defense = 15;
        this.maxDefense = this.defense;
        this.attack = 135;
        this.maxAttack = this.attack;
        this.spellList = new ArrayList<>();
        this.spellList.add(Spell.SHADOW_BOLT);
        this.avatar = EnemyAvatar.HUNTER.getEnemyAvatar();
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
        int x = r.nextInt(11) - 5;
        int y = r.nextInt(11) - 5;
        return new int[] {x,y};
    }
}
