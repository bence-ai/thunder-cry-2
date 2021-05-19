package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.awt.*;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int mp;
    private int defense;
    private String weapon;
    private String avatar;
    private int x;
    private int y;


    public PlayerModel(String playerName, String playerAvatar, int mp, int hp, String weapon, int defense ,int x, int y) {
        this.playerName = playerName;
        this.avatar = playerAvatar;
        this.mp = mp;
        this.hp = hp;
        this.weapon = weapon;
        this.defense = defense;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.mp = player.getMana();
        this.hp = player.getHealth();
        this.defense = player.getDefense();
        this.weapon = player.getWeapon().getClass().getName();
        this.avatar = player.getAvatarText();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String  getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getAvatar() {
        return avatar;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
