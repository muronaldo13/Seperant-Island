package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
 * Edited by John on 2018/4/24
 *  - Added getStats & getSkillInfo functions
 */

public class Hero extends Creatures {
    private boolean invis;
    private boolean taunt;
    private Skill skill;

    public Hero(String name, float maxHP, float baseDamage, float baseDef,  Element element, Skill skill) {
        super(name, maxHP, baseDamage, baseDef,element);
        this.skill = skill;
        invis = false;
        taunt = false;
    }

    public void increaseDamage(float damage){
        this.currentDamage += damage;
    }

    public void increasePercentageDamage(int percentage){
        this.currentDamage += (currentDamage * percentage / 100);
    }

    public void increaseDef(float defValue){
        this.currentDef += defValue;
    }

    public void increasePercentageDef(int percentage){
        this.currentDef += (currentDef * percentage / 100);
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean isInvis() {
        return invis;
    }

    public void setInvis(boolean invis) {
        this.invis = invis;
    }

    public boolean isTaunt() {
        return taunt;
    }

    public void setTaunt(boolean taunt) {
        this.taunt = taunt;
    }

    @Override
    public void activateSkill(Creatures appliedBoss) {
        if (skill.getName().equals(Skill.SUMMONING)) {
            DamageCard.generateRandomDamageCard().activate((Monster) appliedBoss);
        }
        else if(skill.getName().equals(Skill.TAUNT)){
            setTaunt(true);
        }
        else if(skill.getName().equals(Skill.REVIVE)){
            //TODO
        }
        else if(skill.getName().equals(Skill.INVIS)) {
            setInvis(true);
        }
    }

    public String getStats() {
        return "Name: " + name + "\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef + "\nSkill: " + skill.getName()
                + "\nCD: " + skill.getCurrentCooldown() + "\n" + skill.getDescription();
    }
}