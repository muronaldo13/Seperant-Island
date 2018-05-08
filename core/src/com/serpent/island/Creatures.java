package com.serpent.island;

import java.util.Random;

/**
 * Created by Nghia on 2018/4/23.
 */

public abstract class Creatures {
    protected float maxHP;
    protected float baseDamage;
    protected float baseDef;
    protected float currentHP;
    protected float currentDamage;
    protected float currentDef;
    protected Element element;
    protected boolean stun;
    protected String name;
    protected boolean skillActivated;

    public Creatures(String name, float maxHP, float baseDamage, float baseDef, Element element){
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.baseDamage = baseDamage;
        this.baseDef = baseDef;
        this.element = element;
        this.currentDamage = baseDamage;
        this.currentDef = baseDef;
        stun = false;
    }

    /**
     * Take damage value that is x% of creature's max HP
     * @param percentage damage percentage margin
     * @return damage taken
     */
    public float takePercentageDamage(float percentage){
        float dmg = maxHP*percentage/100;
        currentHP -= dmg;
        if (currentHP < 0) {
            currentHP = 0;
        }
        return dmg;
    }

    /**
     * Take damage normally
     * @param dmg damage dealt
     * @return damage taken
     */
    public float takeDamage(float dmg){
        dmg -= currentDef;
        currentHP -= dmg;
        if (currentHP < 0) {
            currentHP = 0;
        }
        return dmg;
    }

    /**
     * Calculate the damage the given creature is to deal
     * @param bossOrHero the creature to deal damage
     * @param mode "Attack" to process damage | "Reflect" to calculate reflect damage only
     * @return the damage value
     */
    public float calculateDamage(Creatures bossOrHero, String mode){
        //element countered damage calculation
        float damage = bossOrHero.getCurrentDamage();

        if(element.getCountered() == bossOrHero.getElement()){
            damage *= 0.9;
        }
        if(element.getCounter() == bossOrHero.getElement()){
            damage *= 1.1;
        }

        //25% chance to crit
        if(bossOrHero instanceof Hero){
            Random rd  = new Random();
            int value = rd.nextInt(100);
            if(value < 25) {
                damage *= 1.5;
            }
        }
        if (mode == "Attack") {
            return takeDamage(damage);
        }
        else {
            return damage;
        }
    }

    public void healAmount(float amount){
        if(currentHP + amount >= maxHP)
            currentHP = maxHP;
        else {
            currentHP += amount;
        }
    }

    public void healPercentage(int percentage){
        float amount = maxHP * percentage / 100;
        healAmount(amount);
    }

    public Element getElement() {
        return element;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public float getBaseDef() {
        return baseDef;
    }

    public float getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(float currentDamage) {
        this.currentDamage = currentDamage;
    }

    public float getCurrentDef() {
        return currentDef;
    }

    public void setCurrentDef(float currentDef) {
        this.currentDef = currentDef;
    }

    public boolean isStun() {
        return stun;
    }

    public void setStun(boolean stun) {
        this.stun = stun;
    }

    public abstract void activateSkill(Creatures appliedBossOrHero);

    public String getName() {
        return name;
    }
}