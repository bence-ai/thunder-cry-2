package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapName) {
        InputStream is = null;
        switch (mapName) {
            case "tutorial":
                is = MapLoader.class.getResourceAsStream("/tutorial_map.txt");
                break;
            case "first_level":
                is = MapLoader.class.getResourceAsStream("/first.txt");
                break;
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
                            cell.setType(CellType.CHARACTER);
                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
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
