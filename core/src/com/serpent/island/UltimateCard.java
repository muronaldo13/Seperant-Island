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
    private Drawable cardImage;

    public UltimateCard(String imgPath){
        super(imgPath);
    }

    public void activate(ArrayList<Hero> heroList, Monster enemy){
//        for(Hero hero: heroList){
//            hero.activateSkill(enemy);
//        }
//        if(enemy.getCurrentHP() == (enemy.getMaxHP() * 15/100)){
//            enemy.setCurrentHP(0);
//        }
    }
}
