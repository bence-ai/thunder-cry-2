package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.PlayerAvatar;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.magic.Spell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


class ActorTest extends ApplicationTest {

    private GameMap gameMap;


    @BeforeEach
    void setUp() {
       gameMap = new GameMap(3, 3, CellType.FLOOR);

    }


    @Test
    void gameMap_getMapDetails_getCorrectWidthAndHeight() {
        assertEquals(3,gameMap.getWidth());
        assertEquals(3, gameMap.getHeight());
    }



    @Test
    void playerMove_moveThePlayer_moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BLUE_BOY);
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void playerMove_moveToWall_cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1) ,"Lajos", PlayerAvatar.BLUE_BOY);

        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void playerMove_moveTo_OutOfBoundCell_cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1), "Lajos", PlayerAvatar.BLUE_GIRL);
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void playerMove_moveToTakenCell_cannotMoveIntoAnotherActor() {
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
    void checkForItem_standingOnItem_True() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Item item = new Sword(gameMap.getCell(1,1), ItemType.WEAPON, 50);
        assertTrue(player.standingOnItem());
    }

    @Test
    void pickUpItem_pickingUpAvailableItem_itemAddedAndRemovedFromCell() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        assertNull(gameMap.getCell(1,1).getWeapon());
        Item item = new Sword(gameMap.getCell(1,1), ItemType.WEAPON, 50);
        assertEquals("sword", item.getTileName());
        assertEquals(50, item.getProperty());
        player.pickUpItem(item);
        assertNull(gameMap.getCell(1,1).getItem());
        assertEquals("WEAPON",player.inventoryToString());
    }

    @Test
    void playerCellCheck_getEnemyFromCells_returnEnemy() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1), "Skeleton");
        assertEquals("skeleton",player.getEnemy().getTileName());
    }

    @Test
    void spellListHandling_addSpells_spellListUpdated() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        player.addSpell(Spell.FIRE);
        assertEquals("[FIRE, THUNDER, SMALL_HEAL, FIRE]",player.getSpellList().toString());
    }

    @Test
    void getCell() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        assertEquals(1,player.getCell().getX());
        assertEquals(1,player.getCell().getY());
    }

//    @Test // how to test methods with random factor
//    void onUpdate_enemyMove_shouldMoveButNotAlways () {
//        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1), "Skeleton");
//        skeleton.onUpdate();
//        assertNull(gameMap.getCell(2,1).getActor());
//        Bandit bandit = new Bandit(gameMap.getCell(2,2), "Bandit");
//        assertEquals("Bandit", bandit.getName());
//    }

    @Test
    void getMapLevel_getMapLevelThenIncreaseAndGetMapLevel_shouldReturnLevelTwo () {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        assertEquals(0,player.getMapLevel());
        player.setMapLevel(2);
        assertEquals(2,player.getMapLevel());
    }

    @Test

    void testItemsTileNames() {
        Item hand = new BareHand(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("hand",hand.getTileName());
        Item breastplate = new Breastplate(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("armour", breastplate.getTileName());
        Item elixir = new Elixir(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("elixir", elixir.getTileName() );
        Item helmet = new Helmet(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("helm", helmet.getTileName());
        Item knife = new Knife(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("knife", knife.getTileName());
        Item potion = new Potion(gameMap.getCell(1,1), ItemType.WEAPON, 0);
        assertEquals("potion", potion.getTileName());
        Item key = new Key(gameMap.getCell(1,1), ItemType.KEY,0);
        assertEquals("key", key.getTileName());
    }

    @Test
    void actorMove_moveNonPlayerActor_shouldMove () {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1), "Skeleton");
        skeleton.move(0,1);
        assertNull(gameMap.getCell(2,1).getActor());
        assertNotNull(gameMap.getCell(2,2).getActor());
    }

    @Test
    void takeDamage_thenHeal_shouldTakeDamageThenHeal() {
        Player player = new Player(gameMap.getCell(1, 1), "Lajos", PlayerAvatar.BROWN_BOY);
        player.takeDamage(50);
        assertEquals(450,player.getHealth());
        player.healHP(20);
        assertEquals(470, player.getHealth());
    }

}