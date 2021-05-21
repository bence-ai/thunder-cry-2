package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

class CellTest extends ApplicationTest {
    GameMap map;
    Cell cell;

    @BeforeEach
    void setUp() {
        map = new GameMap(3, 3, CellType.FLOOR);
        cell = map.getCell(1, 0);
    }


    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertNull(cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertNull(cell.getNeighbor(0, 1));
    }

    @Test
    void cellItemHandling_setAndGetItem() {
        Item item = new Sword(null, ItemType.WEAPON,35);

        cell.setItem(item);

        assertEquals("sword",cell.getItem().getTileName());

    }
}