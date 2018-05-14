package com.serpent.island;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * Created by Nghia on 2018/4/23.
 */

public class UltimateCard extends Cards {
    public UltimateCard(String imgPath){
        super(imgPath, "Energy Burst");
    }

    public void activate(DungeonA dungeon){
        for(Hero hero: dungeon.party){
            hero.activateSkill(dungeon);
        }
    }

    public String getEffect() {
        return "Releasing hero's potential energy!";
    }
}
