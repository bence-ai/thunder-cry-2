package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.util.Direction;

import java.util.Random;

public class Bandit extends Actor{
    Random random = new Random();
    public Bandit(Cell cell, String name, int health, int manaPoint, int defense, int attack) {
        super(cell, name, health, manaPoint, defense, attack);
    }

    @Override
    public String getTileName() {
        return "bandit";
    }

    @Override
    public void move(int dx, int dy) {
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
        if (cell.getNeighbor(dx, dy) == null) {
            return;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().isStepable()) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            } else if (nextCell.getActor().getTileName().equals("player") || nextCell.getActor().getTileName().equals("skeleton")) {
                return;
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    @Override
    public void onUpdate() {
        this.move(0,0);
    }
}
