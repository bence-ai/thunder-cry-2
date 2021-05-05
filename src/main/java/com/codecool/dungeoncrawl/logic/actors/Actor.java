package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.magic.Spell;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected Weapon weapon;
    protected String name;
    protected int maxHealth;
    protected int health;
    protected int maxManaPoint;
    protected int manaPoint;
    protected int maxAttack;
    protected int attack;
    protected int maxDefense;
    protected int defense;
    protected ArrayList<Spell> spellList;
    protected Image avatar;

    private final Random random = new Random();

    public Actor(Cell cell, String name) {
        this.cell = cell;
        this.cell.setActor(this);
        this.name = name;
        this.health = 500;
        this.maxHealth = health;
        this.manaPoint = 200;
        this.maxManaPoint = manaPoint;
        this.defense = 25;
        this.maxDefense = defense;
        this.attack = 75;
        this.maxAttack = attack;
    }

    public  void move(int dx, int dy) {
        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().isStepable() ) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public void attack(int eventNumber, Actor actor){
        switch (eventNumber){
            case 0:
                actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
                System.out.println("Player taken dmg " + (this.generateAttackDamage() - actor.getDefense()));
                break;
            case 1:
                if (spellList == null){
                    actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
                    System.out.println("Player taken dmg " + (this.generateAttackDamage() - actor.getDefense()));
                    break;
                }
                int randomMagic = random.nextInt(spellList.size());

                Spell actorMagic = spellList.get(randomMagic);
                if (this.getMana() < actorMagic.getMagick().getCost()) {
                    actor.takeDamage(this.generateAttackDamage() - actor.getDefense());
                    System.out.println("Player taken dmg " + (this.generateAttackDamage() - actor.getDefense()));
                    break;
                }
                this.reduceMP(actorMagic.getMagick().getCost());
                switch (actorMagic.getMagick().getType()) {
                    case BLACK:
                        actor.takeDamage(actorMagic.getMagick().generateDamage());
                        System.out.println("Player magic dmg " + (actorMagic.getMagick().generateDamage()));
                        break;
                    case WHITE:
                        this.healHP(actorMagic.getMagick().generateDamage());
                        System.out.println("Enemy heal dmg " + (actorMagic.getMagick().generateDamage()));
                        break;
                    case ZOMBIE:
                        actor.takeDamage(actorMagic.getMagick().generateDamage());
                        this.healHP(actorMagic.getMagick().generateDamage());
                        break;
                }
        }
    }


    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


    public Weapon getWeapon() {
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

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxManaPoint(int maxManaPoint) {
        this.maxManaPoint = maxManaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public void setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setMaxDefense(int maxDefense) {
        this.maxDefense = maxDefense;
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

    public Image getAvatar() {
        return avatar;
    }

    public abstract void onUpdate();

}
