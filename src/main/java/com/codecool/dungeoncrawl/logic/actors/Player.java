package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList();

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    private Item weapon;

    public Player(Cell cell) {
        super(cell);
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
        if (nextCell.getType().equals(CellType.STAIRS)) {
            return;
            // TODO here we have to signal loading next level to the Game object

        }
            if (nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            for (Item item: inventory
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
        if (nextCell.getType().equals(CellType.FLOOR) || nextCell.getType().equals(CellType.OPEN_DOOR)) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            } else if (nextCell.getActor().getTileName().equals("skeleton")) {
                return;
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
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

    public boolean isThereEnemy() {
        if (cell.getNeighbor(1,0).getActor() != null)
            return true;
        else if (cell.getNeighbor(-1,0).getActor() != null){
            return true;
        }
        else if(cell.getNeighbor(0,1).getActor() != null) {
            return true;
        }
        else if (cell.getNeighbor(0,-1).getActor() != null) {
            return true;
        }
        else return false;
    }

    public Actor getEnemy(){
        if (cell.getNeighbor(1,0).getActor() != null)
            return cell.getNeighbor(1,0).getActor();
        else if (cell.getNeighbor(-1,0).getActor() != null){
            return cell.getNeighbor(-1,0).getActor();
        }
        else if(cell.getNeighbor(0,1).getActor() != null) {
            return cell.getNeighbor(0,1).getActor();
        }
        else if (cell.getNeighbor(0,-1).getActor() != null) {
            return cell.getNeighbor(0,-1).getActor();
        }
        else return null;
    }
}
