package com.serpent.island;

import java.util.Random;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/**
 * Created by JohnFeng on 2018/4/27.
 */

public class DungeonA implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Sprite background;
    private Stage dungeonA_Battle;
    private Skin guiSkin;
    private Skin guiSkin2;
    private ArrayList<Hero> party = new ArrayList<Hero>();
    private ArrayList<Button> heroIcons = new ArrayList<Button>();
    private ArrayList<ProgressBar> heroHPBars = new ArrayList<ProgressBar>();
    private ArrayList<Cards> handCard = new ArrayList<Cards>();
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private ArrayList<ImageButton> monsterIcons = new ArrayList<ImageButton>();
    private ArrayList<ProgressBar> monsterHPBars = new ArrayList<ProgressBar>();
    private Table monsterTable;
    private Table heroTable;
    private Table cardTableA;
    private Table cardTableB;
    private float damageLabelPadding = 0;
    private Music bgm;
    public static boolean ReflectDamage = false;
    public static boolean IgnoreDmg = false;

    public DungeonA(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Sprite(new Texture(Gdx.files.internal("dungeonA.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiSkin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        guiSkin2 = new Skin(Gdx.files.internal("gui/clean-crispy-ui.json"));
        dungeonA_Battle = new Stage(new ScreenViewport());

        // Set up bgm
        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/battleBGM.ogg"));
        bgm.setLooping(true);
        bgm.setVolume(0.5f);
        bgm.play();

        // Set up the table for displaying cards
        cardTableA = new Table();
        cardTableA.setWidth(Gdx.graphics.getWidth());
        cardTableA.align(Align.left | Align.top);
        cardTableA.setPosition(0, Gdx.graphics.getHeight()/2-120);

        cardTableB = new Table();
        cardTableB.setWidth(Gdx.graphics.getWidth());
        cardTableB.align(Align.left | Align.top);
        cardTableB.setPosition(0, cardTableA.getTop()/2);

        dungeonA_Battle.addActor(cardTableA);
        dungeonA_Battle.addActor(cardTableB);
    }

    /**
     * Gives the player a fixed party of the specified heros
     */
    public void buildParty() {
        // Add hero to party
        party.add(new Hero("Andrew.Assasin", 750f, 90f, 12f ,Element.AIR
                , new Skill(Skill.INVIS, "The unit cannot be targeted by monsters", 5)));
        party.add(new Hero("Hira.SpellCaster", 600f, 110f, 6f, Element.FIRE
                , new Skill(Skill.SUMMONING, "Summon and play a damage card", 5)));
        party.add(new Hero("Jessica.Priest", 650f, 60f, 12f, Element.WATER
                , new Skill(Skill.REVIVE,"Revive all dead heroes", 6)));
        party.add(new Hero("Matt.Tank",900f, 70f, 30f, Element.EARTH
                , new Skill(Skill.TAUNT, "Attract all damage to this hero while\ngaining 50 armor", 3)));

        // Make the image buttons to display the heros
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("heroImgs/andrew.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("heroImgs/hira.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("heroImgs/jessica.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("heroImgs/matt.png"))))));

        // Apply listener to hero icons and build skill activation confirmation dialog
        for (int i = 0; i < party.size(); i++) {
            heroIcons.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int clickedHeroIndex = heroIcons.indexOf(event.getListenerActor());
                    Hero clickedHero = party.get(clickedHeroIndex);
                    // Display skill activation dialog
                    Dialog dialog = new Dialog("", guiSkin, "default") {
                        public void result(Object obj) {
                            // Activate skill
                            if (obj.equals(true)) {
                                //Todo
                            }
                        }
                    };

                    // Set up dialog title area
                    Label titleLabel = new Label("Character Detail", guiSkin);
                    titleLabel.setFontScale(1.3f);
                    titleLabel.setSize(20,20);
                    dialog.getTitleTable().add(titleLabel);

                    // Set up dialog content area
                    Label heroStatsLabel = new Label(clickedHero.getStats(), guiSkin);
                    heroStatsLabel.setFontScale(1.2f);
                    heroStatsLabel.setSize(350,250);
                    heroStatsLabel.setWrap(true);

                    // Skill activation button will only show when skill is activatable
                    if (clickedHero.getSkill().getCurrentCooldown() == 0) {
                        dialog.button("Activate Skill", true);
                    }
                    dialog.button("Cancel", false);
                    dialog.setSize(350, heroStatsLabel.getHeight());
                    dialog.getContentTable().add(heroStatsLabel);
                    dialog.getContentTable().align(Align.left);
                    dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth(), Gdx.graphics.getHeight()/2);
                    dialog.setScale(2f);
                    dungeonA_Battle.addActor(dialog);
                }
            });
        }
    }

    /**
     * Build the enemies for this dungeon
     */
    public void buildEnemy() {
        monsters.add(new Monster("Tiger", 2000f, 100f, 50f, Element.EARTH, null));
        monsterIcons.add(new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("monsterImgs/tiger.png"))))));
    }

    /**
     * Build the hp bars for heros and monsters in field
     */
    public void buildHPBar() {
        // Setting up Hero Hp bars
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/4 - 20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle heroBarStyle = new ProgressBar.ProgressBarStyle();
        heroBarStyle.background = drawable;

        pixmap = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        heroBarStyle.knobBefore = drawable;

        for (Hero hero : party) {
            ProgressBar hpBar = new ProgressBar(0f, hero.getMaxHP(), 1f, false, heroBarStyle);
            hpBar.setValue(hero.getCurrentHP());
            heroHPBars.add(hpBar);
        }

        // Monsters attribute are indicated by the color of health bar
        // Fire = Red, Water = Blue, Earth = brown, Air = light greenish blue

        pixmap = new Pixmap(Gdx.graphics.getWidth()/4 - 20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(174f/255, 172f/255, 172f/255, 1f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle monsterBarStyle = new ProgressBar.ProgressBarStyle();
        monsterBarStyle.background = drawable;

        for (Monster monster : monsters) {
            // Monsters attribute are indicated by the color of health bar
            // Fire = Red, Water = Blue, Earth = brown, Air = light greenish blue
            pixmap = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
            if (monster.getElement() == Element.FIRE) {
                pixmap.setColor(153f/255, 24f/255, 24f/255, 1f);
            }
            else if (monster.getElement() == Element.WATER) {
                pixmap.setColor(38f/255, 191f/255, 242f/255, 1f);
            }
            else if (monster.getElement() == Element.AIR) {
                pixmap.setColor(128f/255, 208f/255, 194f/255, 1f);
            }
            else {
                pixmap.setColor(174f/255, 136f/255, 39f/255, 1f);
            }
            pixmap.fill();
            drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
            pixmap.dispose();
            monsterBarStyle.knobBefore = drawable;
            ProgressBar hpBar = new ProgressBar(0f, monster.getMaxHP(), 1f, false, monsterBarStyle);
            hpBar.setValue(monster.getCurrentHP());
            monsterHPBars.add(hpBar);
        }
    }

    /**
     * Builds all the ui components of this screen
     */
    public void initialiseUIElements() {
        // Add monsters to stage
        monsterTable = new Table();
        monsterTable.setWidth(Gdx.graphics.getWidth());
        monsterTable.align(Align.center | Align.top);
        for (ImageButton monsterIcon : monsterIcons) {
            monsterTable.add(monsterIcon);
        }
        monsterTable.setPosition(0, Gdx.graphics.getHeight());
        dungeonA_Battle.addActor(monsterTable);

        // Add monster hp bar to stage
        Table monsterHpBarTable = new Table();
        monsterHpBarTable.setWidth(Gdx.graphics.getWidth());
        monsterHpBarTable.align(Align.center | Align.top);
        int monsterIndex = 0;
        for (ProgressBar hpBar : monsterHPBars) {
            monsterHpBarTable.add(hpBar).width(monsterIcons.get(monsterIndex).getWidth());
            monsterIndex++;
        }
        monsterHpBarTable.setPosition(0, Gdx.graphics.getHeight() - monsterIcons.get(0).getHeight());
        dungeonA_Battle.addActor(monsterHpBarTable);

        // Add heros to stage
        heroTable = new Table();
        heroTable.setHeight(Gdx.graphics.getWidth()/4 - 20);
        heroTable.setWidth(Gdx.graphics.getWidth());
        heroTable.align(Align.center);
        heroTable.padLeft(20);
        for (Button heroIcon : heroIcons) {
            heroTable.add(heroIcon).size(heroTable.getHeight(), heroTable.getHeight()).padRight(20);
        }
        heroTable.setPosition(0, Gdx.graphics.getHeight()/2 - 45);
        dungeonA_Battle.addActor(heroTable);

        // Add heros hp bar to stage
        Table heroHpBarTable = new Table();
        heroHpBarTable.setWidth(Gdx.graphics.getWidth());
        heroHpBarTable.align(Align.center | Align.top);
        heroHpBarTable.padLeft(20);
        for (ProgressBar hpBar : heroHPBars) {
            heroHpBarTable.add(hpBar).width(Gdx.graphics.getWidth()/4 - 20).padRight(20);
        }
        heroHpBarTable.setPosition(0,Gdx.graphics.getHeight()/2 - 50);
        dungeonA_Battle.addActor(heroHpBarTable);

        // Create and add return button to stage
        final TextButton returnButton = new TextButton("Surrender", guiSkin, "default");
        returnButton.setWidth(180);
        returnButton.setHeight(80);
        returnButton.setPosition(0, Gdx.graphics.getHeight() - returnButton.getHeight());
        returnButton.getLabel().setFontScale(2f);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Direct back to worldmap screen
                game.setScreen(new WorldMap(game));
                dispose();
            }
        });
        dungeonA_Battle.addActor(returnButton);

        // Create and add end button to stage
        final TextButton endTurnButton = new TextButton("END TURN", guiSkin, "default");
        endTurnButton.setWidth(200);
        endTurnButton.setHeight(80);
        endTurnButton.setPosition(Gdx.graphics.getWidth()-endTurnButton.getWidth() - 10, heroTable.getTop()+10);
        endTurnButton.getLabel().setFontScale(2f);
        // Apply listener to end turn button
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Player can only hold up to 10 cards
                if (handCard.size() < 10) {
                    addCardToDeck();
                }
                doDamage();
                endTurnButton.addAction(Actions.sequence(Actions.hide(), Actions.delay(2f), Actions.show()));
            }
        });
        dungeonA_Battle.addActor(endTurnButton);
    }

    /**
     * Add the starting cards to player's hand
     */
    public void buildCardDeck() {
        // Game begin with 5 cards in player's hand
        for (int i=0; i< 5; i++) {
            addCardToDeck();
        }
    }

    /**
     *
     */
    public void addCardToDeck(){
        final Cards newCard = Cards.generateRandomCard();
        handCard.add(newCard);

        // Display the added card in the field
        Button cardIcon = new Button(newCard.getCardImage());
        cardIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateCard(newCard);
            }
        });
        // Add to table A - one table can only hold up to 5 cards
        if (handCard.size() < 6) {
            cardTableA.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);

        }
        // More than 6 card so add to table B
        else {
            cardTableB.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
        }
    }

    /**
     * Reconstruct the card display area
     */
    public void rebuiltCardTable() {
        // Clear table before rebuilting
        cardTableA.clearChildren();
        cardTableB.clearChildren();

        for (final Cards card : handCard) {
            Button cardIcon = new Button(card.getCardImage());
            cardIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateCard(card);
                }
            });
            if (handCard.indexOf(card) < 6) {
                cardTableA.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
            }
            else {
                cardTableB.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
            }
        }
    }

    /**
     * Increase the specified hp bar based on the given damage value.
     * This function is only for hero's health bar as monster currently has no
     * way of healing lost hp
     * @param indexValue index indicating which hp bar is to be updated
     * @param healAmount the value of which the hp bar is increasing by
     */
    private void increaseHPBar(int indexValue, float healAmount){
        // Update the hp bar of hero at the specified index
        heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
        // Create a label to show the amount healed
        Label healLabel = new Label("+" + healAmount, guiSkin);
        healLabel.setFontScale(4f);
        healLabel.setColor(Color.GREEN);
        float x = heroTable.getCell(heroIcons.get(indexValue)).getActorX();
        healLabel.setPosition(x+20, heroTable.getTop()+10);
        healLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(healLabel)));
        dungeonA_Battle.addActor(healLabel);
    }

    /**
     * Decrease the specified hp bar based on the given damage value
     * @param indexValue index indicating which hp bar is to be updated
     * @param damage the value of which the hp bar is decreasing by
     */
    private void decreaseHPBar(int indexValue, float damage){
        Label damageLabel = new Label("-" + damage, guiSkin);
        damageLabel.setFontScale(4f);
        damageLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(damageLabel)));
        damageLabel.setColor(Color.RED);
        // Update monster's hp bar
        if(indexValue == -1){
            monsterHPBars.get(0).setValue(monsters.get(0).getCurrentHP());
            float x = monsterTable.getCell(monsterIcons.get(0)).getActorX();
            damageLabel.setPosition(x*2, Gdx.graphics.getHeight() - 40 - damageLabelPadding);
            damageLabelPadding += 60;
        }
        // Update the hp bar of hero at the specified index
        else{
            heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
            float x = heroTable.getCell(heroIcons.get(indexValue)).getActorX();
            damageLabel.setPosition(x+20, heroTable.getTop()+10);
        }
        dungeonA_Battle.addActor(damageLabel);
    }

    public void activateCard(Cards card){
        handCard.remove(handCard.indexOf(card));
        // According to API to table cell cannot be deleted, hence the display table need
        // be rebulit to reflect the change
        rebuiltCardTable();

        if(card instanceof BuffCard){
            ArrayList<Float> healAmounts = ((BuffCard)card).activate(party);
            // Display activated buff card (except heal) effect
            if (((BuffCard) card).getEffect() != "") {
                Label effectLabel = new Label(((BuffCard) card).getEffect(), guiSkin);
                effectLabel.setFontScale(3f);
                effectLabel.setColor(229f/255, 226f/255, 60f/255, 1);
                effectLabel.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/3);
                effectLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(effectLabel)));
                dungeonA_Battle.addActor(effectLabel);
            }
            // Meaning the buff card activated was heal
            if (healAmounts.size() != 0) {
                for (Hero hero : party) {
                    if (hero.getCurrentHP() > 0) {
                        int heroIndex = party.indexOf(hero);
                        float healAmount = healAmounts.get(heroIndex);
                        if (hero.getCurrentHP() + healAmount > hero.getMaxHP()) {
                            healAmount = Math.round(hero.getMaxHP() - hero.getCurrentHP());
                            hero.setCurrentHP(hero.getMaxHP());
                            increaseHPBar(heroIndex, healAmount);
                        }
                        else {
                            hero.setCurrentHP(hero.getMaxHP() + healAmounts.get(heroIndex));
                            increaseHPBar(heroIndex, healAmount);
                        }
                    }
                }
            }
        }
        else if(card instanceof TrapCard){
            // Only one enemy for now - hard coded solution (may be improved to allow player to choose target)
            ((TrapCard)card).activate(monsters.get(0));
        }
        else if(card instanceof DamageCard){
            decreaseHPBar(-1, ((DamageCard)card).activate(monsters.get(0)));
        }
        else{
            ((UltimateCard)card).activate(party,monsters.get(0));
        }
    }

    /**
     * Resets the stats of all monster and hero to their base state and decrement
     * their skill cooldown round
     */
    private void proceedNextRound() {
        // Reset heros
        for(Hero hero: party){
            hero.setCurrentDamage(hero.getBaseDamage());
            hero.setCurrentDef(hero.getBaseDef());
            hero.setStun(false);
            hero.setInvis(false);
            hero.getSkill().reduceCooldown(1);
            ReflectDamage = false;
            IgnoreDmg = false;
        }
        // Reset monsters
        for (Monster monster : monsters) {
            monster.setCurrentDamage(monster.getBaseDamage());
            monster.setCurrentDef(monster.getBaseDef());
            monster.setSilenced(false);
            monster.setStun(false);
        }
        damageLabelPadding = 0;
    }

    /**
     * Calculate, process and perform damages from heros to monster and vice versa
     */
    private void doDamage() {
        // Hero's attacking phase
        for(Hero hero : party) {
            // Only alive hero unit can perform attack
            if (hero.getCurrentHP() > 0) {
                // and the hero is not being hard cc
                if (!hero.isStun()) {
                    float damage = monsters.get(0).calculateDamage(hero, "Attack");
                    decreaseHPBar(-1, damage);
                }
            }
            // All monster dead, player win
            if (monsters.get(0).getCurrentHP() <= 0) {
                // Player wins, notify battle outcome
                Dialog dialog = new Dialog("Battle Outcome", guiSkin, "default") {
                    public void result(Object obj) {
                        if (obj.equals(true)) {
                            game.setScreen(new WorldMap(game));
                            dispose();
                        }
                    }
                };
                dialog.text("Congratulation! You Win!");
                dialog.button("Finish", true);
                dialog.setSize(320, 130);
                dialog.getContentTable().align(Align.center);
                dialog.getTitleTable().align(Align.center);
                dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth(), Gdx.graphics.getHeight()/2);
                dialog.setScale(2f);
                dungeonA_Battle.addActor(dialog);
            }
        }

        // Monster attacking phase
        if (monsters.get(0).getCurrentHP() > 0) {
            if (!monsters.get(0).isStun() || !IgnoreDmg) {
                Random rd = new Random();
                int targetIndex = rd.nextInt(party.size());
                // Choose another target if current target is already dead
                while (party.get(targetIndex).getCurrentHP() < 1) {
                    targetIndex = rd.nextInt(party.size());
                }
                if (!ReflectDamage) {
                    // Monster attacks the randomly selected target hero
                    decreaseHPBar(targetIndex, party.get(targetIndex).calculateDamage(monsters.get(0), "Attack"));
                }
                // Monster attack got reflected
                else {
                    float reflectDmg = party.get(targetIndex).calculateDamage(monsters.get(0), "Reflect");
                    monsters.get(0).takeDamage(reflectDmg);
                    decreaseHPBar(-1, reflectDmg);
                }

                // When all heros are dead, it's game over
                if (checkAllHeroesDead()) {
                    // Player loses, notify battle outcome
                    Dialog dialog = new Dialog("Battle Outcome", guiSkin, "default") {
                        public void result(Object obj) {
                            if (obj.equals(true)) {
                                game.setScreen(new WorldMap(game));
                                dispose();
                            }
                        }
                    };
                    dialog.text("GAME OVER!!! Play smarter next time!");
                    dialog.button("Finish", true);
                    dialog.setSize(320, 130);
                    dialog.getContentTable().align(Align.center);
                    dialog.getTitleTable().align(Align.center);
                    dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth(), Gdx.graphics.getHeight()/2);
                    dialog.setScale(2f);
                    dungeonA_Battle.addActor(dialog);
                }
            }
        }
        proceedNextRound();
    }

    /**
     * Determines whether all heros are dead or not
     * @return true if all heros are dead, false otherwise
     */
    private boolean checkAllHeroesDead() {
        int deadCount = 0;
        for(Hero hero: party){
            if(hero.getCurrentHP() == 0f)
                deadCount++;
        }
        if(deadCount == party.size()) {
            return true;
        }
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(dungeonA_Battle);

        buildParty();
        buildEnemy();
        buildHPBar();
        buildCardDeck();
        initialiseUIElements();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
        dungeonA_Battle.act();
        dungeonA_Battle.draw();
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
        guiSkin.dispose();
        guiSkin2.dispose();
        dungeonA_Battle.dispose();
        bgm.dispose();
    }

}
