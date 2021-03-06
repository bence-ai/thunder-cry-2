package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.magic.Spell;
import javafx.scene.control.Label;
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
        if (this.cell != null) {
            this.cell.setActor(this);
        }
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


    public void move(int dx, int dy) {
        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().isStepable()) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }


    public void attack(int eventNumber, Actor actor, Label infoLabel){
        switch (eventNumber){
            case 0:
                int enemyDamage = this.generateAttackDamage() - actor.getDefense();
                actor.takeDamage(enemyDamage);
                infoLabel.setText(infoLabel.getText() + "\n" + "You have taken " + enemyDamage + " physical damage");
                break;
            case 1:
                if (spellList == null){
                    int noSpellDmg = this.generateAttackDamage() - actor.getDefense();
                    actor.takeDamage(noSpellDmg);
                    infoLabel.setText(infoLabel.getText() + "\n" + "You have taken " + noSpellDmg + " physical damage");
                    break;
                }
                int randomMagic = random.nextInt(spellList.size());

                Spell actorMagic = spellList.get(randomMagic);
                if (this.getMana() < actorMagic.getMagick().getCost()) {
                    int enemyPhysicalDmg = this.generateAttackDamage() - actor.getDefense();
                    actor.takeDamage(enemyPhysicalDmg);
                    infoLabel.setText(infoLabel.getText() + "\n" + "You have taken " + enemyPhysicalDmg + " physical damage");
                    break;
                }
                this.reduceMP(actorMagic.getMagick().getCost());
                switch (actorMagic.getMagick().getType()) {
                    case BLACK:
                        int enemyMagicDmg = actorMagic.getMagick().generateDamage();
                        actor.takeDamage(enemyMagicDmg);
                        infoLabel.setText(infoLabel.getText() + "\n" + "You have taken " + enemyMagicDmg + " " +actorMagic.getMagick().getName()  + "  damage");
                        break;
                    case WHITE:
                        int enemyHeal = actorMagic.getMagick().generateDamage();
                        this.healHP(enemyHeal);
                        infoLabel.setText(infoLabel.getText()+ "\n" + "Enemy healed by: " + enemyHeal);
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
            return random.nextInt(((this.attack + 12)) - ((this.attack - 12))) + (this.attack - 12);
        }
        return random.nextInt(((this.attack + 12 + this.weapon.getProperty())) - ((this.attack - 12) + this.weapon.getProperty())) + (this.attack - 12 + this.weapon.getProperty());
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        this.health = Math.max(0, this.health);
    }

    public void healHP(int healing) {
        this.health += healing;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public void restoreMP(int restore) {
        this.manaPoint += restore;
        if (this.manaPoint > this.maxManaPoint) {
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

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return manaPoint;
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

    public void addSpell(Spell spell) {
        this.spellList.add(spell);
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

    public void setHealth( int hp) {
        this.health = hp;
    };

    public void setManaPoint(int mp) {
        this.manaPoint = mp;
    }
}
