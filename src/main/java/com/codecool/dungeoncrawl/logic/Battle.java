package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.Random;

public class Battle {
    Scene scene;
    BorderPane toolbar;
    Player player;
    Actor enemy;
    EventHandler<? super KeyEvent> moveEvent;
    GameMap map;

    public Battle(Scene scene, BorderPane toolbar, GameMap map) {
        this.scene = scene;
        this.toolbar = toolbar;
        this.map = map;
    }

    private void setToolbar(Player player, Actor enemy) {

    }

    public void fight(Player player, Actor enemy) {
        this.player = player;
        this.enemy = enemy;
        setToolbar(player, enemy);
        moveEvent = scene.getOnKeyPressed();
        scene.setOnKeyPressed(this::attack);
    }

    private void attack(KeyEvent keyEvent) {
        int playerAction = 0;
        if (keyEvent.getCode().isDigitKey()) {
            switch (keyEvent.getCode()) {
                case DIGIT0:
                    playerAction = 0;
                    break;
                case DIGIT1:
                    playerAction = 1;
                    break;
                case DIGIT2:
                    playerAction = 2;
                    break;
                case DIGIT3:
                    playerAction = 3;
                    break;
                case DIGIT4:
                    playerAction = 4;
                    break;
                case DIGIT5:
                    playerAction = 5;
                    break;
                case DIGIT6:
                    playerAction = 6;
                    break;
                case DIGIT7:
                    playerAction = 7;
                    break;
                case DIGIT8:
                    playerAction = 8;
                    break;
                case DIGIT9:
                    playerAction = 9;
                    break;
            }
        } else {
            return;
        }

        System.out.println("key: " + playerAction);
        player.attack(playerAction, enemy);


        if (isAlive(enemy)) {
            System.out.println("health enemy: " + enemy.getHealth());
            int enemySelect = new Random().nextInt(2);
            enemy.attack(enemySelect, player);
            if (!isAlive(player)) {
                gameOver(player);
            }
            System.out.println("health playa: " + player.getHealth());
        } else {
            gameOver(enemy);
        }
    }

    private boolean isAlive(Actor actor) {
        return actor.getHealth() > 0;
    }

    private void gameOver(Actor actor) {
        if (actor instanceof Player) {
            //TODO: this :D
            System.exit(0);
        }
        enemy.getCell().setActor(null);
        map.removeActor(actor);
        scene.setOnKeyPressed(moveEvent);
    }
}
