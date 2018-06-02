package com.serpent.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import javax.smartcardio.Card;

/**
 * Created by Nghia on 2018/4/23.
 */

public class Hero extends Creatures {
    private boolean invis;
    private boolean taunt;
    private Skill skill;
    private int stunDuration;

    public Hero(String name, float maxHP, float baseDamage, float baseDef,  Element element, Skill skill) {
        super(name, maxHP, baseDamage, baseDef,element);
        this.skill = skill;
        invis = false;
        taunt = false;
        stunDuration = 0;
    }

    public int getStunDuration() {
        return stunDuration;
    }

    public void setStunDuration(int stunDuration) {
        this.stunDuration = stunDuration;
    }

    public String getSkillEffect() {
        return skillEffect;
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


    public Skill activateSkill( DungeonA dungeon, float padding) {
        if(!stunned) {
            Label effectLabel = new Label(skillEffect += skill.getName(), dungeon.getGuiSkin());
            effectLabel.setFontScale(3f);
            effectLabel.setColor(Color.VIOLET);
            effectLabel.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 3 - padding);
            effectLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(1f),
                    Actions.fadeOut(1f), Actions.removeActor(effectLabel)));
            dungeon.getStage().addActor(effectLabel);
            if (skill.getName().equals(Skill.SUMMONING)) {
                DamageCard damageCard = DamageCard.generateRandomDamageCard();
                dungeon.activateCard(damageCard, true);
                skillEffect = "Summoning random damage card!";
            } else if (skill.getName().equals(Skill.TAUNT)) {
                this.increaseDef(50f);
                for (Monster monster : dungeon.monsters) {
                    monster.setTauntingSource(this);
                }
                skillEffect = "Taunting enemies!";
                dungeon.spawnParticleAtIcons(ParticleSystem.Type.TAUNT,true,dungeon.getHeroIcons().get(dungeon.party.indexOf(this)));
            } else if (skill.getName().equals(Skill.REVIVE)) {
                for (int i = 0; i < dungeon.party.size(); i++) {
                    Hero hero = dungeon.party.get(i);
                    if (hero != this) {
                        if (hero.isDead()) {
                            float newHP = hero.getMaxHP() / 3;
                            hero.setCurrentHP(newHP);
                            dungeon.getHeroIcons().get(i).setColor(Color.WHITE);
                            dungeon.increaseHPBar(dungeon.party.indexOf(hero), newHP, "Hero",0);
                            dungeon.spawnParticleAtIcons(ParticleSystem.Type.REVIVE,true,dungeon.getHeroIcons().get(dungeon.party.indexOf(hero)));
                        }
                    }
                }
                skillEffect = "Reviving dead allies!";
            } else if (skill.getName().equals(Skill.INVIS)) {
                dungeon.getHeroIcons().get(dungeon.party.indexOf(this)).setColor(Color.LIGHT_GRAY);
                this.setInvis(true);
                skillEffect = "Becoming invisible!";
                dungeon.spawnParticleAtIcons(ParticleSystem.Type.INVIS,true, dungeon.getHeroIcons().get(dungeon.party.indexOf(this)));
            }

            skill.resetCooldown();
            dungeon.checkWinningCondition();
            return skill;
        }
        return null;
    }

    public String getStats() {
        return "Name: " + name + "\nElement: " + element.name() + "\nHP: " + currentHP +"/" + maxHP + "\nATK Point: " + currentDamage
                + "\nDEF Point: " + currentDef + "\nSkill: " + skill.getName()
                + "\nRemaining CoolDown: " + skill.getCurrentCooldown() + "\n" + skill.getDescription();
    }
}