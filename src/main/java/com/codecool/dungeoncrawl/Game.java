package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Hunter;
import com.codecool.dungeoncrawl.logic.items.WeaponType;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game {
    Background TOOLBOX_FILL_COLOR = new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY));
    Background BLACK_FILL_COLOR = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY));

    Font LOGO_FONT = new Font("Vehicle Breaks Down Regular", 180);
    Font SMALL_FONT = new Font("Pixeled Regular", 12);
    Font MEDIUM_FONT = new Font("Pixeled Regular", 15);
    Font LARGE_FONT = new Font("Pixeled Regular", 25);
    Font EXTRA_LARGE_FONT = new Font("Pixeled Regular", 65);

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
    BorderPane enemyAvatar = new BorderPane();



    public Game(Stage stage, String name) {
        this.stage = stage;
        this.name.setText(name);
    }

    public void play() {
        canvas = tutorial();
        context = canvas.getGraphicsContext2D();

        this.toolbar = setToolbar();
        borderPane.setCenter(canvas);
        borderPane.setTop(toolbar);
        borderPane.setBottom(null);

        scene.setOnKeyPressed(this::onKeyPressed);
        refresh();
    }

    public void loader() {
        VBox loaderPage = new VBox();
        loaderPage.setBackground(BLACK_FILL_COLOR);

        borderPane = new BorderPane();

        Label logo = new Label("ThunderCry");
        logo.setFont(LOGO_FONT);
        logo.setTextFill(Color.WHITE);
        loaderPage.getChildren().add(logo);

        Label title = new Label("and the lost Island");
        title.setFont(EXTRA_LARGE_FONT);
        title.setTextFill(Color.BLACK);
        title.setPadding(new Insets(-70,0,0,0));
        FadeTransition titleFadeIn = new FadeTransition(Duration.seconds(10), title);
        titleFadeIn.setFromValue(0);
        titleFadeIn.setToValue(1);

        loaderPage.getChildren().add(title);
        borderPane.setCenter(loaderPage);
        loaderPage.setAlignment(Pos.CENTER);

        Label pressButton = new Label("Press any button to start...");
        pressButton.setTextFill(Color.BLACK);
        pressButton.setFont(MEDIUM_FONT);
        FadeTransition pressAnimation = new FadeTransition(Duration.seconds(1), pressButton);
        pressAnimation.setFromValue(0);
        pressAnimation.setToValue(1);
        pressAnimation.setAutoReverse(true);
        pressAnimation.setCycleCount(50);
        borderPane.setBottom(pressButton);

        borderPane.setAlignment(pressButton, Pos.CENTER);

        this.scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::playGame);

        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        titleFadeIn.play();
        title.setTextFill(Color.AQUA);
        pressAnimation.play();
    }

    private void playGame(KeyEvent keyEvent) {
        this.play();

    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case F: // Start a fight with nearby enemy
                if (map.getPlayer().isThereEnemy()) {
                    infoLabel.setText("Prepare for the battle and choose an action!");
                    battle = new Battle(scene, infoLabel, healthLabel, mannaLabel, enemyHealthLabel, enemyMannaLabel, map);
                    battle.fight(map.getPlayer(), map.getPlayer().getEnemy());
                }
                break;
            case UP:
                map.getPlayer().move(0, -1);
                update();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                update();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                update();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                update();
                refresh();
                break;
            case E: // Pick-up items
                checkForItem();
                refresh();
                break;
        }
    }



    /**
     * checking if there is an item at the current position, if so then picking it up
     */
    private void checkForItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            switch (map.getPlayer().getCell().getItem().getType()) {
                case WEAPON:
                    map.getPlayer().setWeapon(map.getPlayer().getCell().getWeapon());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                case ARMOUR:
                    map.getPlayer().setDefense(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
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
                    refresh();
            }

        }
    }

    private BorderPane setToolbar() {
        BorderPane toolbar = new BorderPane();
        GridPane characterInfo = new GridPane();
        characterInfo.setBackground(TOOLBOX_FILL_COLOR);
        characterInfo.setPadding(new Insets(10));
        GridPane enemyInfo = new GridPane();
        enemyInfo.setBackground(TOOLBOX_FILL_COLOR);
        enemyInfo.setPadding(new Insets(10));

        BorderPane playerAvatar = new BorderPane();
        playerAvatar.setCenter(new ImageView(map.getPlayer().getAvatar()));
        playerAvatar.setBackground(TOOLBOX_FILL_COLOR);

        infoLabel.setFont(LARGE_FONT);

        name.setFont(MEDIUM_FONT);
        name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + ")");
        healthLabel.setFont(SMALL_FONT);
        mannaLabel.setFont(SMALL_FONT);
        defenseLabel.setFont(SMALL_FONT);
        name.setTextFill(Color.WHITE);
        healthLabel.setTextFill(Color.WHITE);
        mannaLabel.setTextFill(Color.WHITE);
        defenseLabel.setTextFill(Color.WHITE);
        characterInfo.add(name, 0, 0);
        characterInfo.add(healthLabel, 0, 1);
        characterInfo.add(mannaLabel, 0, 2);
        characterInfo.add(defenseLabel, 0, 3);

        BorderPane weaponImage = new BorderPane();
        Label weaponKey = new Label("0");
        weaponKey.setFont(SMALL_FONT);
        BorderPane weapon = new BorderPane();
        weapon.setLeft(weaponKey);
        if (map.getPlayer().getWeapon() == null) {
            weaponImage.setCenter(new ImageView(WeaponType.HAND.getAvatarImage()));
            weapon.setRight(weaponImage);
            weaponAvatar.setCenter(weapon);
            characterInfo.add(weaponAvatar, 2, 0);
        } else {
            weaponImage.setCenter(new ImageView(map.getPlayer().getWeapon().getWeaponAvatar()));
            weapon.setRight(weaponImage);
            weaponAvatar.setCenter(weapon);
        }

        for (int i = 0; i < map.getPlayer().getSpellList().size(); i++) {
            BorderPane spellImage = new BorderPane();
            spellImage.setCenter(new ImageView(map.getPlayer().getSpellList().get(i).getAvatarImage()));
            Label spellKey = new Label(" " + (i+1));
            spellKey.setFont(SMALL_FONT);
            BorderPane spell = new BorderPane();
            spell.setLeft(spellKey);
            spell.setRight(spellImage);
            characterInfo.add(spell, i+2, 2);
        }

        enemyName.setFont(MEDIUM_FONT);
        enemyHealthLabel.setFont(SMALL_FONT);
        enemyMannaLabel.setFont(SMALL_FONT);
        enemyDefenseLabel.setFont(SMALL_FONT);
        enemyName.setTextFill(Color.WHITE);
        enemyHealthLabel.setTextFill(Color.WHITE);
        enemyMannaLabel.setTextFill(Color.WHITE);
        enemyDefenseLabel.setTextFill(Color.WHITE);
        enemyInfo.add(enemyName, 0, 0);
        enemyInfo.add(enemyHealthLabel, 0, 1);
        enemyInfo.add(enemyMannaLabel, 0, 2);
        enemyInfo.add(enemyDefenseLabel, 0, 3);

        BorderPane actorsStats = new BorderPane();
        actorsStats.setBackground(TOOLBOX_FILL_COLOR);
        actorsStats.setLeft(characterInfo);
        actorsStats.setRight(enemyInfo);
        infoLabel.setTextFill(Color.WHITE);
        actorsStats.setCenter(infoLabel);

        toolbar.setStyle("-fx-border-color : #ffffff; -fx-border-width : 5 0 ");
        toolbar.setLeft(playerAvatar);
        toolbar.setCenter(actorsStats);
        enemyAvatar.setBackground(TOOLBOX_FILL_COLOR);
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
        if (map.getPlayer().getWeapon() == null) {
            name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + ")");
            weaponAvatar.setCenter(new ImageView(WeaponType.HAND.getAvatarImage()));
        } else {
            name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + map.getPlayer().getWeapon().getProperty() + ")");
            weaponAvatar.setCenter(new ImageView(map.getPlayer().getWeapon().getWeaponAvatar()));
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        mannaLabel.setText("Manna: " + map.getPlayer().getMana());
        defenseLabel.setText("Defense: " + map.getPlayer().getDefense());
        infoLabel.setText("");
        if (map.getPlayer().standingOnItem()) {
            String itemName = map.getPlayer().getCell().getItem().getTileName();
            infoLabel.setText("Press [E] to pick up: " + itemName);
        }
        if (map.getPlayer().isThereEnemy()) {
            infoLabel.setText("PRESS [F] TO FIGHT!");
            enemyName.setText(map.getPlayer().getEnemy().getName() + "(" + map.getPlayer().getEnemy().getAttack() + ")");
            enemyHealthLabel.setText("Health:" + map.getPlayer().getEnemy().getHealth());
            enemyMannaLabel.setText("Manna:" + map.getPlayer().getEnemy().getMana());
            enemyDefenseLabel.setText("Defense:" + map.getPlayer().getEnemy().getDefense());
            enemyAvatar.setCenter(new ImageView(map.getPlayer().getEnemy().getAvatar()));
        } else {
            enemyName.setText("");
            enemyHealthLabel.setText("");
            enemyMannaLabel.setText("");
            enemyDefenseLabel.setText("");
            enemyAvatar.setCenter(null);
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
