package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
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

    public Skill getSkill() {
        return skill;
    }

    public boolean isInvis() {
        return invis;
    }

    public void setInvis(boolean invis) {
        this.invis = invis;
    }

    @Override
    public void activateSkill( DungeonA dungeon) {
        if (skill.getName().equals(Skill.SUMMONING)) {
            dungeon.activateCard(DamageCard.generateRandomDamageCard(), true);
        }
        else if(skill.getName().equals(Skill.TAUNT)){
            this.increaseDef(50f);
            for (Monster monster : dungeon.monsters) {
                monster.setTauntingSource(this);
            }
        }
        else if(skill.getName().equals(Skill.REVIVE)){
            for (Hero hero : dungeon.party) {
                if (hero != this) {
                    if (hero.isDead()) {
                        float newHP = hero.getMaxHP()/3;
                        hero.setCurrentHP(newHP);
                        dungeon.increaseHPBar(dungeon.party.indexOf(hero), newHP, "Hero");
                    }
                }
            }
        }
        else if(skill.getName().equals(Skill.INVIS)) {
            this.setInvis(true);
        }

        skill.resetCooldown();
        dungeon.checkWinningCondition();
    }

    public String getStats() {
        return "Name: " + name + "\nElement: " + element.name() + "\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef + "\nSkill: " + skill.getName()
                + "\nRemaining CoolDown: " + skill.getCurrentCooldown() + "\n" + skill.getDescription();
    }
}