package com.serpent.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Monster extends Creatures{
    private ArrayList<Skill> skillList;
    private boolean silenced;
    private Hero tauntingSource;
    private boolean reflectDamage;

    public Monster(String name, float maxHP, float baseDamage, float baseDef, Element element) {
        super(name, maxHP, baseDamage, baseDef, element);
        skillList = new ArrayList<Skill>();
    }

    public void addSkill(Skill skill) {
        skillList.add(skill);
    }

    public boolean isReflectDamage() {
        return reflectDamage;
    }

    public void setReflectDamage(boolean reflectDamage) {
        this.reflectDamage = reflectDamage;
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

    public Skill activateSkill(DungeonA dungeon) {
        for(Skill skill: skillList) {
            if (skill.getCurrentCooldown() == 0 && !silenced && !stunned) {
                Label effectLabel = new Label(skillEffect + skill.getName(), dungeon.getGuiSkin());
                effectLabel.setFontScale(3f);
                effectLabel.setColor(Color.VIOLET);
                effectLabel.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 3);
                effectLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(1f),
                        Actions.fadeOut(1f), Actions.removeActor(effectLabel)));
                dungeon.getStage().addActor(effectLabel);
                skill.resetCooldown();
                dungeon.checkWinningCondition();
                if (skill.getName().equals(Skill.ENTANGLE)) {
                    for (int i = 0 ;i <dungeon.party.size();i++) {
                        Hero hero = dungeon.party.get(i);
                        if(!hero.isDead() && !hero.isInvis()) {
                            hero.setStun(true);
                            hero.setStunDuration(3);
                            dungeon.getHeroIcons().get(i).setColor(Color.YELLOW);
                            dungeon.spawnParticleAtIcons(ParticleSystem.Type.ENTANGLE,true,dungeon.getHeroIcons().get(i));
                        }
                    }

                }
                else if (!reflectDamage) {
                    if (tauntingSource != null) {
                        Hero source = dungeon.party.get(dungeon.party.indexOf(tauntingSource));
                        if (skill.getName().equals(Skill.LEECH)) {
                            source.takeDamage(100f);
                            dungeon.decreaseHPBar(dungeon.party.indexOf(source), 100f, "Hero", Skill.LEECH,60);
                            healAmount(100f);
                            dungeon.increaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster",dungeon.monsterHealingLabelPadding+=60);
                            dungeon.spawnParticleAtIcons(ParticleSystem.Type.LEECH,true,dungeon.getHeroIcons().get(dungeon.party.indexOf(source)));
                        }

                    } else {
                        for (int i = 0; i < dungeon.party.size(); i++) {
                            Hero hero = dungeon.party.get(i);
                            if (!hero.isDead() && !hero.isInvis()) {
                                if (skill.getName().equals(Skill.LEECH)) {
                                    hero.takeDamage(100f);
                                    dungeon.decreaseHPBar(dungeon.party.indexOf(hero), 100f, "Hero", Skill.LEECH,60);
                                    healAmount(100f);
                                    dungeon.increaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster",dungeon.monsterHealingLabelPadding+=60);
                                    dungeon.spawnParticleAtIcons(ParticleSystem.Type.LEECH,true,dungeon.getHeroIcons().get(i));
                                }
                            }
                        }

                    }
                }
                else {
                    if (skill.getName().equals(Skill.LEECH)) {
                        takeDamage(100f);
                        dungeon.decreaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster", Skill.LEECH,60);
                        healAmount(100f);
                        dungeon.increaseHPBar(dungeon.monsters.indexOf(this), 100f, "Monster",dungeon.monsterHealingLabelPadding+=60);
                        dungeon.spawnParticleAtIcons(ParticleSystem.Type.LEECH,false,null);
                    }
                }
                return skill;
            }
        }
        return null;
    }


    public String getStats() {
        String stats = "Name: " + name + "\nElement: "+ element.name() +"\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef;
        for (Skill skill : skillList) {
            stats += "\nSkill: " + skill.getName() + "\nRemaining CoolDown: " + skill.getCurrentCooldown() + "\n"+skill.getDescription();
        }
        return stats;
    }
}