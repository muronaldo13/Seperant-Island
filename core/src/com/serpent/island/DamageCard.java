package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
 */

public class DamageCard extends Cards {
    private Element element;
    private String name;

    public DamageCard(Element element, String imgPath){
        super(imgPath);
        this.element = element;

        if(element == Element.AIR){
            name = "Gust";
        }
        else if(element == Element.EARTH){
            name = "Earthquake";
        }
        else if(element == Element.FIRE){
            name = "Fire Nova";
        }
        else if(element == Element.WATER){
            name = "Calling Tide";
        }
    }

    public String getName() {return name;}

    public float activate(Monster enemy){
        int damagePercentMargin = 10;
        if(element.getCounter() == enemy.getElement()){
            damagePercentMargin = 13;
        }
        else if(element.getCountered() == enemy.getElement()){
            damagePercentMargin = 7;
        }
        return enemy.takePercentageDamage(damagePercentMargin);
    }
}
