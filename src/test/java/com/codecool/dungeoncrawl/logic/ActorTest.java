package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.PlayerAvatar;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class ActorTest extends ApplicationTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);


    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BLUE_BOY);
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1) ,"Lajos", PlayerAvatar.BLUE_BOY);

        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1), "Lajos", PlayerAvatar.BLUE_GIRL);
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1), "Skeleton");
        player.move(1, 0);
        assertTrue(player.isThereEnemy());
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void standingOnItem() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Item item = new Sword(gameMap.getCell(1,1), ItemType.WEAPON, 50);
        assertTrue(player.standingOnItem());

    }

    @Test
    void pickUpItem() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Item item = new Sword(gameMap.getCell(1,1), ItemType.WEAPON, 50);
        player.pickUpItem(item);
        assertNull(gameMap.getCell(1,1).getItem());
        assertEquals("WEAPON",player.inventoryToString());
    }

    @Test
    void getEnemy() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1), "Skeleton");
        assertEquals("skeleton",player.getEnemy().getTileName());
    }

}