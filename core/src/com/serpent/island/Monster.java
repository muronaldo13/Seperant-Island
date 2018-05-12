package com.serpent.island;

import java.util.ArrayList;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Monster extends Creatures{
    private ArrayList<Skill> skillList;
    private boolean silenced;
    private Hero tauntingSource;

    public Monster(String name, float maxHP, float baseDamage, float baseDef, Element element) {
        super(name, maxHP, baseDamage, baseDef, element);
        skillList = new ArrayList<Skill>();
    }

    public void addSkill(Skill skill) {
        skillList.add(skill);
    }

    public ArrayList<Skill> getSkillList() { return skillList; }

    public void setSilenced(boolean silenced) {
        this.silenced = silenced;
    }

    public boolean isSilenced() { return silenced; }

    public Hero getTauntingSource() { return tauntingSource; }

    public void setTauntingSource(Hero source) {
        tauntingSource = source;
    }

    public void reduceCooldown() {
        if (!skillList.isEmpty()) {
            for (Skill skill : skillList) {
                skill.reduceCooldown(1);
            }
        }
    }

    @Override
    public void activateSkill(DungeonA dungeon) {
        for(Skill skill: skillList){
            if (skill.getCurrentCooldown() == 0 && !silenced) {
                if(skill.getName().equals(Skill.INVIS)){
                    for(Hero hero: dungeon.party){
                        hero.setStun(true);
                    }
                }
                if(!DungeonA.ReflectDamage) {
                    if (tauntingSource != null) {
                        Hero source = dungeon.party.get(dungeon.party.indexOf(tauntingSource));
                        if (skill.getName().equals(Skill.LEECH)) {
                            source.takeDamage(100f);
                            dungeon.decreaseHPBar(dungeon.party.indexOf(source), 100f, "Hero", null);
                            healAmount(100f);
                            dungeon.increaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster");
                        }
                    } else {
                        for (Hero hero : dungeon.party) {
                            if (!hero.isDead()) {
                                if (skill.getName().equals(Skill.LEECH)) {
                                    if (!hero.isDead()) {
                                        hero.takeDamage(100f);
                                        dungeon.decreaseHPBar(dungeon.party.indexOf(hero), 100f, "Hero", null);
                                        healAmount(100f);
                                        dungeon.increaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster");
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    if(skill.getName().equals(Skill.LEECH)){
                        takeDamage(100f);
                        dungeon.decreaseHPBar(dungeon.monsters.indexOf(0), 100f, "Monster", "Reflect Damage");
                        healAmount(100f);
                        dungeon.increaseHPBar(dungeon.monsters.indexOf(0), 100f, "Monster");
                    }
                }
            }
            skill.resetCooldown();
            dungeon.checkWinningCondition();
        }
    }

    public String getStats() {
        String stats = "Name: " + name + "\nElement: "+ element.name() +"\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef;
        for (Skill skill : skillList) {
            stats += "\nSkill: " + skill.getName() + "\nRemaining CoolDown: " + skill.getCurrentCooldown();
        }
        return stats;
    }
}