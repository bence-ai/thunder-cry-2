package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList();


    public Player(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    public String getTileName() {
        return "player";
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
        item.getCell().setItem(null);
    }

    @Override
    public void move(int dx, int dy){
        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            // TODO check inventory for KEY, then OPEN the door and remove the key
        }
        if (nextCell.getType().equals(CellType.FLOOR)) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            } else if (nextCell.getActor().getTileName().equals("skeleton") || nextCell.getActor().getTileName().equals("bandit")) {
                return;
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }

    }

    @Override
    public void actorFightActions(int eventNumber, Actor actor) {
        if (eventNumber == 0) {
            playerAttackAction(actor);
        } else {
            playerMagicAction(eventNumber,actor);
        }
    }

    public void playerAttackAction(Actor actor) {
        int physicalAttack = this.generateAttackDamage();
        actor.takeDamage(physicalAttack - actor.getDefense());
        System.out.println("Enemy take " + (physicalAttack-actor.getDefense()) + " dmg");
    }

    private void playerMagicAction(int eventNumber,Actor actor) {
        Spells playerMagic = this.getSpellList().get(eventNumber-1);
        int magicCost = playerMagic.getMagick().getCost();
        if (this.getManaPoint() < magicCost) {
            System.out.println("not enough mana");
            return;
        }
        int magicDamage = playerMagic.getMagick().generateDamage();
        this.reduceMP(magicCost);
        switch (playerMagic.getMagick().getType()) {
            case BLACK:
                actor.takeDamage(magicDamage);
                break;
            case WHITE:
                this.healHP(magicDamage);
            case ZOMBIE:
                actor.takeDamage(magicDamage);
                this.healHP(magicDamage);
        }
    }

    public String inventoryToString() {
        String inventoryString = "";
        if (inventory.size() == 0) {
            inventoryString = "Empty";
        }
        else {
            for (Item item : inventory) {
                inventoryString = inventoryString + item.getType();
            }
        }
        return inventoryString;
    }
}
