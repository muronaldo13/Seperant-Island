package com.serpent.island;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

/**
 * Created by Nghia on 2018/4/23.
 */

public abstract class Cards {


    protected String cardName;
    private String imgPath;
    private Drawable cardImage;

    public Cards(String imgPath, String cardName){
        this.cardName = cardName;
        this.imgPath = imgPath;
        cardImage = new TextureRegionDrawable(new TextureRegion(new Texture(imgPath)));
    }
    public String getCardName() {
        return cardName;
    }
    public Drawable getCardImage() {
        return cardImage;
    }

    public static Cards generateRandomCard(){
        Random rd = new Random();
        int value = rd.nextInt(101);
        if(value < 5){
            return new UltimateCard("cardArts/execution.png");
        }
        else if (value >= 5 && value < 10){
            return generateRandomDamageCard();
        }
        else if(value >= 10 && value < 50){
            return generateRandomTrapCard();
        }
        else{
            return generateRandomBuffCard();
        }
    }

    public static BuffCard generateRandomBuffCard() {
        Random rd = new Random();
        int value = rd.nextInt(4);
        switch(value){
            case 0:
                return new BuffCard(BuffCard.HEAL,generateRandomStat(20,50), "cardArts/heal.png");
            case 1:
                return new BuffCard(BuffCard.COOLDOWN,generateRandomStat(1,3), "cardArts/cooldownreduce.png");
            case 2:
                return new BuffCard(BuffCard.DAMAGE,generateRandomStat(20,100), "cardArts/damagebuff.png");
            case 3:
                return new BuffCard(BuffCard.DEFENSE,generateRandomStat(20,100), "cardArts/defense.png");
        }
        return null;
    }

    public static DamageCard generateRandomDamageCard(){
        Random rd = new Random();
        int value = rd.nextInt(4);
        switch (value){
            case 0:
                return new DamageCard(Element.WATER, "cardArts/callingtide.png");
            case 1:
                return new DamageCard(Element.FIRE, "cardArts/firenova.png");
            case 2:
                return new DamageCard(Element.EARTH, "cardArts/earthquake.png");
            case 3:
                return new DamageCard(Element.AIR, "cardArts/gust.png");
        }
        return null;
    }

    public static TrapCard generateRandomTrapCard(){
        Random rd = new Random();
        int value = rd.nextInt(4);
        switch (value){
            case 0:
                return new TrapCard(TrapCard.IgnoreDmg, "cardArts/barrier.png");
            case 1:
                return new TrapCard(TrapCard.Reflecting, "cardArts/reflectdmg.png");
            case 2:
                return new TrapCard(TrapCard.Silence, "cardArts/silence.png");
            case 3:
                return new TrapCard(TrapCard.Stun, "cardArts/stonegaze.png");
        }
        return null;
    }

    private static int generateRandomStat(int from, int to){
        Random rd = new Random();
        return rd.nextInt(to-from + 1) + from;
    }
}

