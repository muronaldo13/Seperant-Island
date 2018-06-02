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


    public TrapCard(String name, String imgPath){
        super(imgPath, name);
    }

    public String getName() {return cardName;}

    public void activate(Monster enemy){
        if(cardName.equals(Stun)){
            enemy.setStun(true);
        }
        else if(cardName.equals(Silence)){
            enemy.setSilenced(true);
        }
        else if(cardName.equals(Reflecting)){
            enemy.setReflectDamage(true);
        }
        else if(cardName.equals(IgnoreDmg)){
            enemy.setStun(true);
        }
    }

    public String getEffect() {
        if (cardName == Stun) {
            return "All enemies are stunned for 1 round!";
        }
        else if (cardName == Silence) {
            return "All enemies are silenced for 1 round!";
        }
        else if (cardName == Reflecting) {
            return "Damage reflection barrier activated";
        }
        return "Ignoring all incoming damage in this turn";
    }
}
