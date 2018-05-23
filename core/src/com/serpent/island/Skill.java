package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Skill {
    public static final String TAUNT = "Taunt";
    public static final String SUMMONING = "Spell Summoning";
    public static final String REVIVE = "Resurrection";
    public static final String INVIS = "Invisible";
    public static final String LEECH = "Leech";
    public static final String ENTANGLE = "Entangle";
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

    public String getDescription() { return description; }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void resetCooldown() {
        currentCooldown = totalCooldown;
    }

    public String getSkillFXPath() {
        String path = "";
        if (name == TAUNT) {
            path = "sound_effects/taunt_FX.wav";
        }
        else if (name == REVIVE) {
            path = "sound_effects/revive_FX.mp3";
        }
        else if (name == INVIS) {
            path = "sound_effects/invisible_FX.ogg";
        }
        else if (name == LEECH) {
            path = "sound_effects/leech_FX.mp3";
        }
        else if (name == ENTANGLE) {
            path = "sound_effects/entangle_FX.mp3";
        }
        return path;
    }
}