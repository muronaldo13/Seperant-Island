package com.serpent.island;

import java.util.ArrayList;

/**
 * Created by Nghia on 2018/4/23.
 */

public class BuffCard extends Cards {
    public static final String HEAL = "HEAL_BUFF";
    public static final String DAMAGE = "DAMAGE_BUFF";
    public static final String DEFENSE = "DEFENSE_BUFF";
    public static final String COOLDOWN = "REDUCE_COOLDOWN";
    private int effectMargin;
    private String effect;

    public BuffCard(String buffName, int effectMargin, String imgPath){
        super(imgPath,buffName);
        this.effectMargin = effectMargin;
    }

    public String getEffect() { return effect; }

    public ArrayList<Float> activate(ArrayList<Hero> heroList) {
        ArrayList<Float> effectAmount = new ArrayList<Float>();
        for(Hero hero: heroList){
            if(cardName.equals(HEAL)){
                // Only alive hero will be healed
                if (!hero.isDead()) {
                    float healAmount = hero.healPercentage(effectMargin);
                    effectAmount.add(healAmount);
                }
                else {
                    effectAmount.add(0f);
                }
                effect = "HP increased by " + effectMargin + "% HP";
            }
            else if(cardName.equals(DAMAGE)){
                if(!hero.isDead()) {
                    float damage = hero.increasePercentageDamage(effectMargin);
                    effectAmount.add(damage);
                    effect = "Attack increased by " + effectMargin + "%";
                }
                else {
                    effectAmount.add(0f);
                }
            }
            else if(cardName.equals(DEFENSE)){
                if(!hero.isDead()) {
                    float def = hero.increasePercentageDef(effectMargin);
                    effectAmount.add(def);
                    effect = "Defence increased by " + effectMargin + "%";
                }
                else {
                    effectAmount.add(0f);
                }
            }
            else {
                if(!hero.isDead()) {
                    effectAmount.add((float)effectMargin);
                    hero.getSkill().reduceCooldown(effectMargin);
                    effect = "Skill cooldown reduced by " + effectMargin + " rounds";
                }
            }
        }
        return effectAmount;
    }
}
