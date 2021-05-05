package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Bandit;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String level) {
        InputStream is = null;
        switch(level) {
            case "tutorial":
                is = MapLoader.class.getResourceAsStream("/tutorial_map.txt");
        }
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'd':
                            cell.setType(CellType.DIRTY_FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setSkeleton(new Skeleton(cell, "skeleton", 475, 170, 20, 75));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell, "Jocó", 500, 230, 23, 85));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, ItemType.KEY, 1);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Armour(cell, ItemType.ARMOUR, 7);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell, ItemType.POTION, 135);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            new Elixir(cell, ItemType.ELIXIR, 75);
                            break;
                        case 'W':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell, ItemType.WEAPON, 35);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            Bandit bandit = new Bandit(cell, "Lajos Bandita", 375, 200, 17, 105);
                            bandit.setWeapon(new Sword(cell, ItemType.WEAPON, 35));
                            bandit.getCell().setItem(null);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'q':
                            cell.setType(CellType.FLOOR.STAIRS);
                            break;
                        case 'w':
                            cell.setType(CellType.WATER);
                            break;
                        case '0':
                            cell.setType(CellType.TREE1);
                            break;
                        case '1':
                            cell.setType(CellType.TREE2);
                            break;
                        case '2':
                            cell.setType(CellType.TREE3);
                            break;
                        case '3':
                            cell.setType(CellType.TREE4);
                            break;
                        case '╚':
                            cell.setType(CellType.TOP_CORNER_LAND);
                            break;
                        case '╔':
                            cell.setType(CellType.BOTTOM_CORNER_LAND);
                            break;
                        case '═':
                            cell.setType(CellType.TOP_LAND);
                            break;
                        case '-':
                            cell.setType(CellType.BOTTOM_LAND);
                            break;
                        case '│':
                            cell.setType(CellType.RIGHT_LAND);
                            break;
                        case '┘':
                            cell.setType(CellType.TOP_CORNER_INVERSE);
                            break;
                        case '┐':
                            cell.setType(CellType.BOTTOM_CORNER_INVERSE);
                            break;
                        case 'g':
                            cell.setType(CellType.GRASS_FLOOR);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
