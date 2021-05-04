package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList();

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
