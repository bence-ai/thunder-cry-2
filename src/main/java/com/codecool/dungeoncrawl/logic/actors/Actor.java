package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

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
    private ArrayList<Spells> spellList;

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

    public abstract void move(int dx, int dy);

    public void actorFightActions(int eventNumber, Actor actor){
        switch (eventNumber){
            case 0:
                actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
            case 1:
                if (spellList.size() == 0){
                    actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
                    break;
                }
                int randomMagic = random.nextInt(spellList.size());
                Spells actorMagic = spellList.get(randomMagic);
                if (this.getManaPoint() < actorMagic.getMagick().getCost()) {
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
        return random.nextInt(((this.attack+12 + this.weapon.getProperty())) - ((this.attack-12) + this.weapon.getProperty())) + (this.attack-12 +
        this.weapon.getProperty());
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

    public int getMaxManaPoint() {
        return maxManaPoint;
    }

    public int getManaPoint() {
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

    public void setSpellList(ArrayList<Spells> spellList) {
        this.spellList = spellList;
    }


    public ArrayList<Spells> getSpellList() {
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
}
