package com.serpent.island;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Nghia on 2018/4/23.
 */

public class TrapCard extends Cards{
    public static final String Stun = "Stun";
    public static final String Silence = "Silence";
    public static final String Reflecting = "Reflecting Damage";
    public static final String IgnoreDmg = "Ignore Damage";

    private String name;

    public TrapCard(String name, String imgPath){
        super(imgPath);
        this.name = name;
    }

    public String getName() {return name;}

    public void activate(Monster enemy){
        if(name.equals(Stun)){
            enemy.setStun(true);
        }
        else if(name.equals(Silence)){
            enemy.setSilenced(true);
        }
        else if(name.equals(Reflecting)){
            DungeonA.ReflectDamage = true;
        }
        else if(name.equals(IgnoreDmg)){
            DungeonA.IgnoreDmg = true;
        }
    }
}
