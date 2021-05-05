package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Battle {
    Scene scene;
    BorderPane toolbar;

    public Battle(Scene scene, BorderPane toolbar) {
        this.scene = scene;
        this.toolbar = toolbar;
    }

    private void setToolbar(Player player, Actor enemy) {

    }

    public void fight(Player player, Actor enemy) {
        setToolbar(player, enemy);
    }
}
