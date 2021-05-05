package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Bandit;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private ArrayList<Skeleton> skeletonList = new ArrayList<>();
    private ArrayList<Bandit> banditList = new ArrayList<>();

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

    public ArrayList<Bandit> getBandits() {
        return banditList;
    }

    public void setBandits(Bandit bandit) {
        this.banditList.add(bandit);
    }

    public void setSkeletons(Skeleton skeleton) {
        this.skeletonList.add(skeleton);
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletonList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
