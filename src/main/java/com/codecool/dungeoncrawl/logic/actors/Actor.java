package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.magic.Spell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private Item weapon;
    private String name;
    private int maxHealth;
    private int health;
    private int maxManaPoint;
    private int manaPoint;
    private int maxAttack;
    private int attack;
    private int maxDefense;
    private int defense;
    private ArrayList<Spell> spellList;

    private final Random random = new Random();

    public Actor(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        this.cell = cell;
        this.cell.setActor(this);
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxManaPoint = manaPoint;
        this.manaPoint = manaPoint;
        this.maxDefense = defense;
        this.defense = defense;
        this.maxAttack = attack;
        this.attack = attack;
    }

    public  void move(int dx, int dy) {

        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().isStepable()) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            } else if (!nextCell.getActor().getTileName().equals("player") || nextCell.getActor().getTileName().equals("bandit")) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }

    };

    public void attack(int eventNumber, Actor actor){
        switch (eventNumber){
            case 0:
                actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
            case 1:
                if (spellList == null){
                    actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
                    break;
                }
                int randomMagic = random.nextInt(spellList.size());
                Spell actorMagic = spellList.get(randomMagic);
                if (this.getMana() < actorMagic.getMagick().getCost()) {
                    break;
                }
                this.reduceMP(actorMagic.getMagick().getCost());
                switch (actorMagic.getMagick().getType()) {
                    case BLACK:
                        actor.takeDamage(actorMagic.getMagick().generateDamage());
                        break;
                    case WHITE:
                        this.healHP(actorMagic.getMagick().generateDamage());
                    case ZOMBIE:
                        actor.takeDamage(actorMagic.getMagick().generateDamage());
                        this.healHP(actorMagic.getMagick().generateDamage());
                        break;
                }
        }
    }


    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }


    public Item getWeapon() {
        return weapon;
    }

    public int generateAttackDamage() {
        if (this.weapon == null) {
            return random.nextInt(((this.attack+12)) - ((this.attack-12))) + (this.attack-12);
        }
        return random.nextInt(((this.attack+12 + this.weapon.getProperty())) - ((this.attack-12) + this.weapon.getProperty())) + (this.attack-12 + this.weapon.getProperty());
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        this.health = Math.max(0, this.health);
    }

    public void healHP(int healing) {
        this.health += healing;
        if( this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public void restoreMP(int restore) {
        this.manaPoint += restore;
        if( this.manaPoint > this.maxManaPoint) {
            this.manaPoint = this.maxManaPoint;
        }
    }

    public void reduceMP(int cost) {
        this.manaPoint -= cost;
        this.manaPoint = Math.max(0, this.manaPoint);
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxMana() {
        return maxManaPoint;
    }

    public int getMana() {
        return manaPoint;
    }

    public int getMaxDefense() {
        return maxDefense;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setDefense(int defense) {
        this.defense += defense;
    }

    public void setSpellList(ArrayList<Spell> spellList) {
        this.spellList = spellList;
    }


    public ArrayList<Spell> getSpellList() {
        return spellList;
    }


    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public abstract void onUpdate();

    public int[] getRandomDirection() {
        int dx = 0;
        int dy = 0;
        int randMove = random.nextInt(4);
        switch (randMove) {
            case 0:
                dx = 0;
                dy = -1;
                break;
            case 1:
                dx = 0;
                dy = 1;
                break;
            case 2:
                dx = -1;
                dy = 0;
                break;
            case 3:
                dx = 1;
                dy = 0;
                break;
        }
        int[] directionCoordinates = {dx,dy};

        return directionCoordinates;
    }
}
