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
        ArrayList<Float> healAmouts = new ArrayList<Float>();
        for(Hero hero: heroList){
            if(cardName.equals(HEAL)){
                // Only alive hero will be healed
                if (!hero.isDead()) {
                    float healAmount = hero.healPercentage(effectMargin);
                    healAmouts.add(healAmount);
                }
                else {
                    healAmouts.add(0f);
                }
                effect = "HP increased by " + effectMargin + "% HP";
            }
            else if(cardName.equals(DAMAGE)){
                hero.increasePercentageDamage(effectMargin);
                effect = "Attack increased by " + effectMargin + "%";
            }
            else if(cardName.equals(DEFENSE)){
                hero.increasePercentageDef(effectMargin);
                effect = "Defence increased by " + effectMargin + "%";
            }
            else {
                hero.getSkill().reduceCooldown(effectMargin);
                effect = "Skill cooldown reduced by " + effectMargin + " rounds";
            }
        }
        return healAmouts;
    }
}
