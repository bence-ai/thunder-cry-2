package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private int currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;
    private String state_name;

    public GameState(String name, int currentMap, Date savedAt, PlayerModel player) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.state_name = name;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int    currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @Override
    public String toString() {
        return "💾  " + state_name + "  🕘  " + savedAt;
    }
}
