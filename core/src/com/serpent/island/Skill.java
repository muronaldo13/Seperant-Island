package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Skill {
    public static final String TAUNT = "TAUNTING";
    public static final String SUMMONING = "SPELL_SUMMONING";
    public static final String REVIVE = "REVIVE_HERO";
    public static final String INVIS = "INVISIBLE";
    public static final String LEECH = "LEECH";
    public static final String Entangle = "ENTANGLE";
    private int totalCooldown;
    private int currentCooldown;
    private String name;
    private String description;

    public Skill(String name, String description, int totalCooldown) {
        this.name = name;
        this.totalCooldown = totalCooldown;
        this.currentCooldown = totalCooldown;
        this.description = description;
    }

    public void reduceCooldown(int rounds){
        if (currentCooldown > 0) {
            currentCooldown -= rounds;
            // Prevent cooldown from becoming negative integer
            if (currentCooldown < 0) {
                currentCooldown = 0;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription() { return description; }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    public int getTotalCooldown() {
        return totalCooldown;
    }
}