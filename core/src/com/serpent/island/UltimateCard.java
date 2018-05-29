package com.serpent.island;



/**
 * Created by Nghia on 2018/4/23.
 */

public class UltimateCard extends Cards {
    public UltimateCard(String imgPath){
        super(imgPath, "Energy Burst");
    }

    public void activate(DungeonA dungeon){
        float padding = 0;
        for(Hero hero: dungeon.party){
            hero.activateSkill(dungeon,padding);
            padding+= 50;
        }
    }

    public String getEffect() {
        return "Releasing hero's potential energy!";
    }
}
