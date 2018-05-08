package com.serpent.island;

import java.util.ArrayList;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Monster extends Creatures{
    private ArrayList<Skill> skillList;
    private boolean silenced;

    public Monster(String name, float maxHP, float baseDamage, float baseDef, Element element, ArrayList<Skill> skillList) {
        super(name, maxHP, baseDamage, baseDef, element);
        this.skillList = skillList;
    }

    public String getStats() {
        String stats = "Name: " + name + "\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef;
        for (Skill skill : skillList) {
            stats += "\nSkill: " + skill.getName() + "\nCD: " + skill.getCurrentCooldown();
        }
        return stats;
    }

    @Override
    public void activateSkill(Creatures appliedHero) {
//        for(Skill skill: skillList){
//            if (skill.getCurrentCooldown() == 0) {
//                if(!((Hero)appliedHero).isInvis()) {
//                    if (skill.getName().equals(Skill.LEECH)) {
//                        appliedHero.takeDamage(100);
//                        healAmount(100);
//                        skill.setCurrentCooldown(skill.getTotalCooldown());
//                    } else if (skill.getName().equals(Skill.Entangle)) {
//                        appliedHero.setStun(true);
//                        skill.setCurrentCooldown(skill.getTotalCooldown());
//                    }
//                }
//            }
//        }
    }

    public void setSilenced(boolean silenced) {
        this.silenced = silenced;
    }

    public boolean isSilenced() {
        return silenced;
    }

}