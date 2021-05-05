package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Hunter;
import com.codecool.dungeoncrawl.logic.actors.PlayerAvatar;
import com.codecool.dungeoncrawl.logic.items.WeaponType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Game {
    Font defaultFont = new Font("Pixeled Regular", 12);
    Font nameFont = new Font("Pixeled Regular", 15);
    Random random = new Random();
    private final Stage stage;
    private BorderPane borderPane;
    Battle battle;
    GameMap map;
    GraphicsContext context;
    Canvas canvas;
    Scene scene;
    BorderPane toolbar;

    Label name = new Label();
    Label healthLabel = new Label();
    Label mannaLabel = new Label();
    Label defenseLabel = new Label();
    Label enemyName = new Label();
    Label enemyHealthLabel = new Label();
    Label enemyMannaLabel = new Label();
    Label enemyDefenseLabel = new Label();
    Label infoLabel = new Label();
    BorderPane weaponAvatar = new BorderPane();



    public Game(Stage stage, String name) {
        this.stage = stage;
        this.name.setText(name);
    }

    public void play() {
        canvas = tutorial();
        context = canvas.getGraphicsContext2D();

        this.toolbar = setToolbar();
        borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setTop(toolbar);

        this.scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::onKeyPressed);
        refresh();

        stage.setScene(scene);
        stage.setFullScreen(true);
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case F: // Start a fight with nearby enemy
                if (map.getPlayer().isThereEnemy()) {
                    Battle battle = new Battle(scene, toolbar, map);
                    battle.fight(map.getPlayer(), map.getPlayer().getEnemy());
                }
                break;
            case UP:
                map.getPlayer().move(0, -1);
                update();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                update();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                update();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                update();
                break;
            case E: // Pick-up items
                checkForItem();
                break;
        }
        refresh();
    }



    /**
     * checking if there is an item at the current position, if so then picking it up
     */
    private void checkForItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            switch (map.getPlayer().getCell().getItem().getType()) {
                case WEAPON:
                    map.getPlayer().setWeapon(map.getPlayer().getCell().getItem());
                    map.getPlayer().getCell().setItem(null);
                    System.out.println("player ATk: " + map.getPlayer().getAttack() +
                            " player Sword Attack: " + map.getPlayer().getWeapon().getProperty() +
                            " player whole damage: " + map.getPlayer().generateAttackDamage());
                    refresh();
                    break;
                case ARMOUR:
                    map.getPlayer().setDefense(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    System.out.println(map.getPlayer().getDefense());
                    refresh();
                    break;
                case POTION:
                    map.getPlayer().healHP(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;

                case ELIXIR:
                    map.getPlayer().restoreMP(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                default:
                    map.getPlayer().pickUpItem(map.getPlayer().getCell().getItem());
                    map.getPlayer().getCell().setItem(null);
                    System.out.println(map.getPlayer().inventoryToString());  // test print
                    refresh();
            }

        }
    }

    private BorderPane setToolbar() {
        BorderPane toolbar = new BorderPane();
        GridPane characterInfo = new GridPane();
        characterInfo.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        characterInfo.setPadding(new Insets(10));
        GridPane enemyInfo = new GridPane();
        enemyInfo.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        enemyInfo.setPadding(new Insets(10));

        Image avatarImage = PlayerAvatar.BLUE_BOY.getCharacterAvatar();
        BorderPane playerAvatar = new BorderPane();
        playerAvatar.setCenter(new ImageView(avatarImage));
        playerAvatar.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        BorderPane enemyAvatar = new BorderPane();

        infoLabel.setFont(nameFont);
        name.setFont(nameFont);
        name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + ")");
        healthLabel.setFont(defaultFont);
        mannaLabel.setFont(defaultFont);
        defenseLabel.setFont(defaultFont);
        name.setTextFill(Color.WHITE);
        healthLabel.setTextFill(Color.WHITE);
        mannaLabel.setTextFill(Color.WHITE);
        defenseLabel.setTextFill(Color.WHITE);
        characterInfo.add(name, 0, 0);
        characterInfo.add(healthLabel, 0, 1);
        characterInfo.add(mannaLabel, 0, 2);
        characterInfo.add(defenseLabel, 0, 3);

        if (map.getPlayer().getWeapon() == null) {
            weaponAvatar.setCenter(new ImageView(WeaponType.HAND.getAvatarImage()));
            characterInfo.add(weaponAvatar, 1, 0);
        }

        for (int i = 0; i < map.getPlayer().getSpellList().size(); i++) {
            BorderPane image = new BorderPane();
            image.setCenter(new ImageView(map.getPlayer().getSpellList().get(i).getAvatarImage()));
            characterInfo.add(image, i+1, 4);
        }

        enemyName.setFont(nameFont);
        enemyHealthLabel.setFont(defaultFont);
        enemyMannaLabel.setFont(defaultFont);
        enemyDefenseLabel.setFont(defaultFont);
        enemyName.setTextFill(Color.WHITE);
        enemyHealthLabel.setTextFill(Color.WHITE);
        enemyMannaLabel.setTextFill(Color.WHITE);
        enemyDefenseLabel.setTextFill(Color.WHITE);
        enemyInfo.add(enemyName, 0, 0);
        enemyInfo.add(enemyHealthLabel, 0, 1);
        enemyInfo.add(enemyMannaLabel, 0, 2);
        enemyInfo.add(enemyDefenseLabel, 0, 3);

        BorderPane actorsStats = new BorderPane();
        actorsStats.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        actorsStats.setLeft(characterInfo);
        actorsStats.setRight(enemyInfo);
        infoLabel.setTextFill(Color.WHITE);
        actorsStats.setCenter(infoLabel);

        toolbar.setStyle("-fx-border-color : #ffffff; -fx-border-width : 5 0 ");
        toolbar.setLeft(playerAvatar);
        toolbar.setCenter(actorsStats);
        toolbar.setRight(enemyAvatar);
        toolbar.setMaxWidth(1920);
        return toolbar;
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        infoLabel.setText("");
        name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + ")");
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        mannaLabel.setText("Manna: " + map.getPlayer().getMana());
        defenseLabel.setText("Defense: " + map.getPlayer().getDefense());
        if (map.getPlayer().isThereEnemy()) {
            enemyName.setText(map.getPlayer().getEnemy().getName() + "(" + map.getPlayer().getEnemy().getAttack() + ")");
            enemyHealthLabel.setText("Health:" + map.getPlayer().getEnemy().getHealth());
            enemyMannaLabel.setText("Manna:" + map.getPlayer().getEnemy().getMana());
            enemyDefenseLabel.setText("Defense:" + map.getPlayer().getEnemy().getDefense());
        } else {
            enemyName.setText("");
            enemyHealthLabel.setText("");
            enemyMannaLabel.setText("");
            enemyDefenseLabel.setText("");
        }
    }

    private Canvas tutorial() {
        map = MapLoader.loadMap("tutorial");
        for(Actor enemy: map.getEnemyList()) {
            if(enemy instanceof Hunter) {
                ((Hunter) enemy).setMaxX(map.getWidth());
                ((Hunter) enemy).setMaxY(map.getHeight());
            }
        }
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);

        return canvas;
    }

    private void checkForStairs() {
        if (map.getPlayer().getCell().getType().equals(CellType.STAIRS)) {
            map = MapLoader.loadMap("level1.txt");
        }
    }


    private void update() {
        checkForStairs();
        map.updateActor();
    }
}
