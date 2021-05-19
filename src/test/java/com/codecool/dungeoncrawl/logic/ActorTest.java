package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorTest {
    static GameMap map;
    static Player player;

    @BeforeAll
    public void start() {
        Stage stage = new Stage();
        map = new GameMap(3, 3, CellType.FLOOR);
        map = MapLoader.loadMap(66, null);
        BorderPane borderPane = new BorderPane();
        player = new Player(new Cell(map, 5, 5, CellType.FLOOR), "test_player", null);
        Canvas canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        borderPane.setCenter(canvas);
        stage.setScene(new Scene(borderPane));
        stage.show();
    }

    @Test
    void moveUpdatesCells() {
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, map.getCell(1, 1).getActor());
        assertEquals(player, map.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        map.getCell(2, 1).setType(CellType.WALL);

        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Skeleton skeleton = new Skeleton(map.getCell(2, 1), "Skeleton");
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, map.getCell(2, 1).getActor());
    }
}