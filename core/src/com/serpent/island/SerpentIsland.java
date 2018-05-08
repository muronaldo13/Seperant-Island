package com.serpent.island;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

public class SerpentIsland extends Game {

	@Override
	public void create () {
		// Game initialisation
		setScreen(new WorldMap(this));
	}
}
