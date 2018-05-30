package com.serpent.island;

import java.util.Random;

/**
 * Created by Nghia on 2018/4/23.
 */

public abstract class Creatures {
    protected String name;
    protected float maxHP;
    protected float baseDamage;
    protected float baseDef;
    protected Element element;
    protected float currentHP;
    protected float currentDamage;
    protected float currentDef;
    protected boolean stunned;
    protected float critDamage;
    protected String skillEffect;
    public Creatures(String name, float maxHP, float baseDamage, float baseDef, Element element){
        this.name = name;
        this.maxHP = maxHP;
        this.baseDamage = baseDamage;
        this.baseDef = baseDef;
        this.element = element;
        this.currentHP = maxHP;
        this.currentDamage = baseDamage;
        this.currentDef = baseDef;
        stunned = false;
        critDamage = 1.5f;
        skillEffect = name + " activated ";
    }

    public String getName() {
        return name;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public float getBaseDef() {
        return baseDef;
    }

    public Element getElement() {
        return element;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    public float getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(float currentDamage) {
        this.currentDamage = currentDamage;
    }

    public float increasePercentageDamage(int percentage){
        float damage = currentDamage * percentage / 100;
        this.currentDamage += (damage);
        return damage;
    }

    public float getCurrentDef() {
        return currentDef;
    }

    public void setCurrentDef(float currentDef) {
        this.currentDef = currentDef;
    }

    public void increaseDef(float defValue){
        this.currentDef += defValue;
    }

    public float increasePercentageDef(int percentage){
        float def = currentDef * percentage / 100;
        this.currentDef += (def);
        return def;
    }

    public boolean isStun() { return stunned; }

    public void setStun(boolean stun) {
        stunned = stun;
    }

    public boolean isDead() {
        if (currentHP <= 0f) {
            return true;
        }
        return false;
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
                damage *= critDamage;
            }
        }
        if (mode == "Attack") {
            return takeDamage(damage);
        }
        else {
            return damage;
        }
    }

    /**
     * Take damage normally
     * @param dmg damage dealt
     * @return damage taken
     */
    public float takeDamage(float dmg){
        dmg -= currentDef;
        float saveHP = currentHP;
        currentHP -= dmg;
        if (currentHP < 0) {
            currentHP = 0;
            return saveHP;
        }
        return dmg;
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

    public float healAmount(float amount){
        if(currentHP + amount >= maxHP) {
            float saveCurrent = currentHP;
            currentHP = maxHP;
            return maxHP - saveCurrent;
        }

        else {
            currentHP += amount;
            return amount;
        }
    }

    public float healPercentage(float percentage){
        float amount = maxHP * percentage / 100;
        return healAmount(amount);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Creatures)) {
            return false;
        }
        else {
            return ((Creatures) o).getName().equals(this.getName());
        }
    }
}