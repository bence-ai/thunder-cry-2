package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("character", new Tile(27, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("hunter", new Tile(24,0));
        // FLOORS
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("dirty_floor", new Tile(1, 0));
        tileMap.put("grass_floor", new Tile(5, 0));
        // DECOR
        tileMap.put("tree1", new Tile(0, 1));
        tileMap.put("tree2", new Tile(1, 1));
        tileMap.put("tree3", new Tile(2, 1));
        tileMap.put("tree4", new Tile(4, 1));
        //WATER
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("bottom_land", new Tile(10, 5));
        tileMap.put("top_land", new Tile(9, 5));
        tileMap.put("right_land", new Tile(11, 5));
        tileMap.put("top_right_corner_land", new Tile(8, 4));
        tileMap.put("bottom_right_corner_land", new Tile(11, 4));
        tileMap.put("bottom_right_corner_inverse", new Tile(14, 4));
        tileMap.put("left_corner_land", new Tile(9, 4));
        tileMap.put("top_right_corner_inverse", new Tile(13, 4));
        tileMap.put("river", new Tile(12,5));
        tileMap.put("river_end", new Tile(14,5));
        tileMap.put("bridge", new Tile(6,5));
        //KEY
        tileMap.put("key", new Tile(18,23));
        //ITEMS
        tileMap.put("armour", new Tile(0,23));
        tileMap.put("potion", new Tile(17,25));
        tileMap.put("elixir", new Tile(16,25));
        tileMap.put("sword", new Tile(0,30));
        tileMap.put("bandit", new Tile(25,9));
        //DOORS
        tileMap.put("open door", new Tile(0,3));
        tileMap.put("closed door", new Tile(1,3));
        tileMap.put("stairs", new Tile(9,25));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
