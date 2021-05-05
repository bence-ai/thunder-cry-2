package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private ArrayList<Actor> skeletonList = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeletonList.add(skeleton);
    }

    public ArrayList<Actor> getSkeleton() {
        return skeletonList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * It will call onUpdate method on each Actor in the actorList(should rename)
     * We use this to update the enemy movement in the whole map turn by turn.
     *     */
    public void updateActor() {
        for (Actor enemy : skeletonList) {
            enemy.onUpdate();
        }
    }
}
