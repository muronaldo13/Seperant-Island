package com.serpent.island;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by JohnFeng on 2018/4/27.
 */

public class WorldMap implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Sprite worldmap_s;
    private Stage serpentIslandMain;
    private Music bgm;

    public WorldMap(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        worldmap_s = new Sprite(new Texture(Gdx.files.internal("background_imgs/worldmap.png")));
        worldmap_s.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Set up bgm
        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/worldBGM.ogg"));
        bgm.setLooping(true);
        bgm.play();

        serpentIslandMain = new Stage(new ScreenViewport(), batch);
    }

    @Override
    public void show() {
        // Stage should control inputs
        Gdx.input.setInputProcessor(serpentIslandMain);

        // Make the drawable for button
        Drawable dungeonA_D = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background_imgs/secreatisland.png"))));

        // Add the first dungeon's button to the stage
        ImageButton dungeonAButton = new ImageButton(dungeonA_D);
        dungeonAButton.setPosition(20, Gdx.graphics.getHeight()/10);

        // Add listeners to buttons
        dungeonAButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Direct to the dungeonA battle screen
                game.setScreen(new DungeonA(game));
                dispose();
            }
        });

        // Add the ui elements to stage
        serpentIslandMain.addActor(dungeonAButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        worldmap_s.draw(batch);
        batch.end();
        serpentIslandMain.act();
        serpentIslandMain.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        bgm.dispose();
    }
}
