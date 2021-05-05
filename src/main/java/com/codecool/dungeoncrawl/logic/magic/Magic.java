package com.codecool.dungeoncrawl.logic.magic;

import java.util.Random;

public class Magic {
    private String name;
    private int cost;
    private int damage;
    private SpellType type;
    private Random random = new Random();

    public Magic(String name, int cost, int damage, SpellType type) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.type = type;
    }

    public int generateDamage() {
        return random.nextInt((this.damage+5) - (this.damage-5)) + this.damage-5;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public SpellType getType() {
        return type;
    }
}
