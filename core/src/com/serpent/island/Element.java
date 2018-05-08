package com.serpent.island;

/**
 * Created by Nghia on 2018/4/23.
 */

public enum Element {
    FIRE,
    WATER,
    EARTH,
    AIR;

    private Element counter;
    private Element countered;

    static{
        FIRE.counter = EARTH;
        FIRE.countered = WATER;
        WATER.counter = FIRE;
        WATER.countered = AIR;
        AIR.counter = WATER;
        AIR.countered = EARTH;
        EARTH.counter = AIR;
        EARTH.countered = FIRE;
    }

    public Element getCounter(){
        return counter;
    }

    public Element getCountered(){
        return countered;
    }
}