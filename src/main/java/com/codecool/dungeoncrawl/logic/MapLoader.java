package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Magic.Spells;
import com.codecool.dungeoncrawl.logic.actors.Bandit;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String level) {
        switch(level) {
            case "tutorial":
                String source = "/tutorial_map.txt";
        }
        InputStream is = MapLoader.class.getResourceAsStream("/tutorial_map.txt");
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
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setSkeletons(new Skeleton(cell, "skeleton", 475, 170, 20, 75));
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
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell, ItemType.WEAPON, 35);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            map.setBandits(new Bandit(cell, "Bandita Lajos", 375, 135, 30, 135));
                            bandit.setWeapon(new Sword(cell, ItemType.WEAPON, 35));
                            bandit.getCell().setItem(null);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'q':
                            cell.setType(CellType.FLOOR.STAIRS);
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
