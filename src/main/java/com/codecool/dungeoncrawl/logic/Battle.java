package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.util.Random;

public class Battle {
    Scene scene;
    Label infoLabel;
    Label healthLabel;
    Label manaLabel;
    Label enemyHealthLabel;
    Label enemyManaLabel;
    Player player;
    Actor enemy;
    EventHandler<? super KeyEvent> moveEvent;
    GameMap map;

    public Battle(Scene scene, Label infoLabel, Label healthLabel, Label manaLabel, Label enemyHealthLabel, Label enemyManaLabel, GameMap map) {
        this.scene = scene;
        this.infoLabel = infoLabel;
        this.healthLabel = healthLabel;
        this.manaLabel = manaLabel;
        this.enemyHealthLabel = enemyHealthLabel;
        this.enemyManaLabel = enemyManaLabel;
        this.map = map;
    }

    public void fight(Player player, Actor enemy) {

        this.player = player;
        this.enemy = enemy;
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
        player.attack(playerAction, enemy, infoLabel);

        if (isAlive(enemy)) {
            battleRefresh();
            System.out.println("health enemy: " + enemy.getHealth());
            int enemySelect = new Random().nextInt(2);
            enemy.attack(enemySelect, player, infoLabel);
            if(!isAlive(player)) {

                gameOver(player);
            }
            battleRefresh();
            System.out.println("health playa: " + player.getHealth());
        } else {
            battleRefresh();
            gameOver(enemy);
            infoLabel.setText("Enemy has died, You won the battle!" + "\n" + " Your mana and health restored by 20%");
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
        player.restoreAfterBattle();
        enemy.getCell().setActor(null);
        map.removeActor(actor);
        scene.setOnKeyPressed(moveEvent);
    }

    private void battleRefresh() {
        healthLabel.setText("Health: " + player.getHealth());
        enemyHealthLabel.setText("Health: " + enemy.getHealth());
        manaLabel.setText("Manna: " + player.getMana());
        enemyManaLabel.setText("Manna: " + enemy.getMana());
    }
}
