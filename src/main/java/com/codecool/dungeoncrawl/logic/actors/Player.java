package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList();
    int mapLevel = 0;


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
    public void move(int dx, int dy) {
        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }

        Cell nextCell = cell.getNeighbor(dx, dy);
//        if (nextCell.getType().equals(CellType.STAIRS)) {
//            movingToNextLevel = true;
//            // TODO here we have to signal loading next level to the Game object
//
//        }
        if (nextCell.getActor() != null) {
            return;
        }
        if (nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            for (Item item : inventory
            ) {
                if (item instanceof Key) {
                    nextCell.setType(CellType.OPEN_DOOR);
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                    inventory.remove(item);
                    return;
                }
            }
        }
        if (nextCell.getType().isStepable()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }

    }

    @Override
    public void attack(int eventNumber, Actor actor) {
        if (eventNumber == 0) {
            playerAttackAction(actor);
        } else {
            playerMagicAction(eventNumber, actor);
        }
    }

    @Override
    public void onUpdate() {
        return;
    }

    public void playerAttackAction(Actor actor) {
        int physicalAttack = this.generateAttackDamage();
        actor.takeDamage(physicalAttack - actor.getDefense());
        System.out.println("Enemy take " + (physicalAttack - actor.getDefense()) + " dmg");
    }

    private void playerMagicAction(int eventNumber, Actor actor) {
        Spells playerMagic = this.getSpellList().get(eventNumber - 1);
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
        } else {
            for (Item item : inventory) {
                inventoryString = inventoryString + item.getType();
            }
        }
        return inventoryString;
    }

    public boolean isThereEnemy() {
        if (cell.getNeighbor(1, 0).getActor() != null)
            return true;
        else if (cell.getNeighbor(-1, 0).getActor() != null) {
            return true;
        } else if (cell.getNeighbor(0, 1).getActor() != null) {
            return true;
        } else if (cell.getNeighbor(0, -1).getActor() != null) {
            return true;
        } else return false;
    }

    public Actor getEnemy() {
        if (cell.getNeighbor(1, 0).getActor() != null)
            return cell.getNeighbor(1, 0).getActor();
        else if (cell.getNeighbor(-1, 0).getActor() != null) {
            return cell.getNeighbor(-1, 0).getActor();
        } else if (cell.getNeighbor(0, 1).getActor() != null) {
            return cell.getNeighbor(0, 1).getActor();
        } else if (cell.getNeighbor(0, -1).getActor() != null) {
            return cell.getNeighbor(0, -1).getActor();
        } else return null;
    }

    public int getMapLevel() {
        return mapLevel;
    }
}
