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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
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
    protected ArrayList<Hero> party;
    private ArrayList<Button> heroIcons;
    private ArrayList<ProgressBar> heroHPBars;
    private ArrayList<Cards> handCard;
    protected ArrayList<Monster> monsters;
    private ArrayList<Button> monsterIcons;
    private ArrayList<ProgressBar> monsterHPBars;
    private Table monsterTable;
    private Table heroTable;
    private Table cardTableA;
    private Table cardTableB;
    private float monsterDamageLabelPadding;
    public float monsterHealingLabelPadding;
    private Music bgm;
    private int actBuffCardCount;
    private int actTrapCardCount;
    private int actDamageCardCount;
    private int actUltimateCardCount;
    private Dialog cardInfoDialog;
    private ParticleSystem particleSystem;

    public DungeonA(Game game) {
        // variable initialisation
        this.game = game;
        batch = new SpriteBatch();
        background = new Sprite(new Texture(Gdx.files.internal("background_imgs/dungeonA.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiSkin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        dungeonA_Battle = new Stage(new ScreenViewport());
        party = new ArrayList<Hero>();
        heroIcons = new ArrayList<Button>();
        heroHPBars = new ArrayList<ProgressBar>();
        handCard = new ArrayList<Cards>();
        monsters = new ArrayList<Monster>();
        monsterIcons = new ArrayList<Button>();
        monsterHPBars = new ArrayList<ProgressBar>();
        monsterDamageLabelPadding = -60;
        monsterHealingLabelPadding = -60;
        actBuffCardCount = 0;
        actDamageCardCount = 0;
        actTrapCardCount = 0;
        actUltimateCardCount = 0;
        particleSystem = new ParticleSystem();
        particleSystem.init();
        // Set up bgm
        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/battleBGM.ogg"));
        bgm.setLooping(true);
        bgm.setVolume(0.2f);
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
        party.add(new Hero("Andrew.Assasin", 620f, 90f, 12f ,Element.AIR
                , new Skill(Skill.INVIS, "The unit cannot be targeted by monsters", 5)));
        party.add(new Hero("Hira.SpellCaster", 600f, 110f, 6f, Element.FIRE
                , new Skill(Skill.SUMMONING, "Summon and play a damage card", 5)));
        party.add(new Hero("Jessica.Priest", 650f, 60f, 12f, Element.WATER
                , new Skill(Skill.REVIVE,"Revive all dead heroes", 6)));
        party.add(new Hero("Matt.Tank",750f, 70f, 30f, Element.EARTH
                , new Skill(Skill.TAUNT, "Attract all damage to this hero while\ngaining 50 armor", 3)));

        // Make the image buttons to display the heros
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("hero_Imgs/andrew.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("hero_Imgs/hira.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("hero_Imgs/jessica.png"))))));
        heroIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("hero_Imgs/matt.png"))))));

        // Apply listener to hero icons and build skill activation confirmation dialog
        for (int i = 0; i < party.size(); i++) {
            heroIcons.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    final int clickedHeroIndex = heroIcons.indexOf(event.getListenerActor());
                    makeCharacterDialog(clickedHeroIndex);
                }
            });
        }
    }

    private void makeCharacterDialog(final int characterIndex){
        // Display skill activation dialog
        Dialog dialog;
        // Set up dialog title area
        Label titleLabel = new Label("Character Detail", guiSkin);
        titleLabel.setFontScale(1.3f);
        titleLabel.setSize(25,25);
        Label characterStatsLabel;
        if (characterIndex != -1) {
            dialog = new Dialog("", guiSkin, "default") {
                public void result(Object obj) {
                    // Activate skill
                    if (obj.equals(true)) {
                        party.get(characterIndex).activateSkill(DungeonA.this,0);
                        String skillFXPath = party.get(characterIndex).getSkill().getSkillFXPath();
                        if (skillFXPath != "") {
                            Music heroSkillFX = Gdx.audio.newMusic(Gdx.files.internal(skillFXPath));
                            heroSkillFX.setOnCompletionListener(new Music.OnCompletionListener()
                            {
                                public void onCompletion(Music music) {
                                    music.dispose();
                                }
                            });
                            heroSkillFX.play();
                        }
                    }
                }
            };
            // Set up dialog content area
            characterStatsLabel = new Label(party.get(characterIndex).getStats(), guiSkin);
            characterStatsLabel.setSize(350,280);
            // Skill activation button will only show when skill is activatable
            if (party.get(characterIndex).getSkill().getCurrentCooldown() == 0
                    && !party.get(characterIndex).isDead() && !party.get(characterIndex).isStun()) {
                dialog.button("Activate Skill", true);
            }
        }
        else{
            dialog = new Dialog("", guiSkin, "default");
            characterStatsLabel = new Label(monsters.get(0).getStats(), guiSkin);
            characterStatsLabel.setSize(350,350);
        }
        dialog.getTitleTable().add(titleLabel);
        characterStatsLabel.setFontScale(1.2f);
        characterStatsLabel.setWrap(true);
        dialog.button("Cancel", false);
        dialog.setSize(350, characterStatsLabel.getHeight());
        dialog.getContentTable().add(characterStatsLabel);
        dialog.getContentTable().align(Align.left);
        dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth(), Gdx.graphics.getHeight()/2);
        dialog.setScale(2f);
        dungeonA_Battle.addActor(dialog);
    }

    /**
     * Build the enemies for this dungeon
     */
    public void buildEnemy() {
        Monster tigerA = new Monster("Tiger A", 2000, 100f, 50f, Element.EARTH);
        tigerA.addSkill(new Skill(Skill.LEECH, "Absorb 100 health from hero/s", 3));
        tigerA.addSkill(new Skill(Skill.ENTANGLE, "Stuns hero/s for 3 rounds", 6));
        monsters.add(tigerA);
        monsterIcons.add(new Button(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("monster_Imgs/tiger.png"))))));
        monsterIcons.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                makeCharacterDialog(-1);
            }
        });
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
        for (Button monsterIcon : monsterIcons) {
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
                    addCardToDeck(true);
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
        for (int i = 0; i < 5; i++) {
            addCardToDeck(false);
        }
    }

    /**
     *
     */
    public void addCardToDeck(boolean singleAdd){

        final Cards newCard = Cards.generateRandomCard();
        handCard.add(newCard);
        // Display the added card in the field
        final Button cardIcon = new Button(newCard.getCardImage());
        cardIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                makeCardInfoDialog(newCard);
            }
        });
        // Add to table A - one table can only hold up to 5 cards
        if (handCard.size() < 6) {
            cardTableA.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
            cardTableA.validate();
            final Vector2 location = cardIcon.localToParentCoordinates(new Vector2(cardTableA.getX(),cardTableA.getY()));
            cardIcon.addAction(Actions.moveTo(location.x+1200,-360));
            if(singleAdd)
                cardIcon.addAction(Actions.moveTo(location.x,-360,0.8f));
            else {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        cardIcon.addAction(Actions.moveTo(location.x,-360,0.8f));
                    }
                }, 1.5f);
            }
        }
        // More than 6 card so add to table B
        else {
            cardTableB.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
            cardTableB.validate();
            Vector2 location = cardIcon.localToParentCoordinates(new Vector2(cardTableB.getX(),cardTableB.getY()));
            cardIcon.addAction(Actions.sequence(Actions.moveTo(location.x+1200,-360),Actions.moveTo(location.x,-360,0.8f)));
        }
    }

    /**
     * Reconstruct the card display area
     */
    public void rebuiltCardTable(int index) {
        // Clear table before rebuilting

        cardTableA.clearChildren();
        cardTableB.clearChildren();
        int addCardIndex = 0;
        for (final Cards card : handCard) {
            Button cardIcon = new Button(card.getCardImage());
            cardIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    makeCardInfoDialog(card);
                }
            });
            if (addCardIndex < 5) {
                cardTableA.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
                addCardIndex++;

            }
            else {
                cardTableB.add(cardIcon).width(cardTableA.getWidth() / 5).height(cardTableA.getWidth() / 3);
                addCardIndex++;
            }
        }
    }

    private void makeCardInfoDialog(final Cards card){
        cardInfoDialog = new Dialog("",guiSkin);
        cardInfoDialog.setSize(56,150);
        Image image = new Image();
        image.setDrawable(card.getCardImage());
        TextButton cancelButton = new TextButton("Cancel", guiSkin, "default");
        cancelButton.getLabel().setFontScale(3);
        cardInfoDialog.button(cancelButton, false);
        cardInfoDialog.getContentTable().add(image);
        cardInfoDialog.show(dungeonA_Battle);
        image.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateCard(card, false);
                cardInfoDialog.hide();
            }
        });
    }

    /**
     * Increase the specified hp bar based on the given damage value.
     * @param indexValue index indicating which hp bar is to be updated
     * @param healAmount the value of which the hp bar is increasing by
     * @param type of healing unit, Monster or Hero
     */
    public void increaseHPBar(int indexValue, float healAmount, String type, float labelPadding){
        // Update the hp bar of hero at the specified index
        heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
        // Create a label to show the amount healed
        float positionX = 0;
        float positionY = 0;
        if (type == "Monster") {
            monsterHPBars.get(indexValue).setValue(monsters.get(indexValue).getCurrentHP());
            positionX = monsterTable.getCell(monsterIcons.get(0)).getActorX();
            positionY = Gdx.graphics.getHeight() - 40 - labelPadding;
        }
        else {
            // Update the hp bar of hero at the specified index
            heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
            positionX = heroTable.getCell(heroIcons.get(indexValue)).getActorX() + 20;
            positionY = heroTable.getTop() + 10;
        }
        displayCardEffectLabel("+" + healAmount, Color.GREEN,positionX,positionY) ;
    }

    public void displayBuffCardEffectLabel(ArrayList<Float> buffAmounts, String type) {
        for (int i = 0; i < heroIcons.size(); i++) {
            String content = null;
            Color color = null;
            if (type.equals("damage")) {
                content = "+" + buffAmounts.get(i) + " DMG";
                color = Color.ORANGE;
            } else if (type.equals("def")) {
                content = "+" + buffAmounts.get(i) + " DEF";
                color = Color.MAGENTA;
            } else if (type.equals("cd")){
                content = "+" + String.valueOf(Math.round(buffAmounts.get(0))) + " CD";
                color = Color.GRAY;
            }
            float positionX = heroTable.getCell(heroIcons.get(i)).getActorX() + 20;
            float positionY = heroTable.getTop() + 10;
            displayCardEffectLabel(content, color, positionX, positionY);
        }
    }

    public void displayCardEffectLabel(String content, Color color, float positionX, float positionY){
        Label label = new Label(content,guiSkin);
        label.setFontScale(3f);
        label.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(label)));
        label.setColor(color);
        label.setPosition(positionX,positionY);
        dungeonA_Battle.addActor(label);
    }

    /**
     * Decrease the specified hp bar based on the given damage value
     * @param indexValue index indicating which hp bar is to be updated
     * @param damage the value of which the hp bar is decreasing by
     * @param type of damaged unit, Monster or Hero
     */
    public void decreaseHPBar(int indexValue, float damage, String type, String name, float damageLabelPadding){
        Label damageLabel = new Label("-" + damage +  (name == null? "":" (" + name +")"), guiSkin);

        damageLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f),
                Actions.fadeOut(0.5f), Actions.removeActor(damageLabel)));
        damageLabel.setColor(Color.RED);
        // Update monster's hp bar

        if(type == "Monster"){
            damageLabel.setFontScale(4f);
            monsterHPBars.get(indexValue).setValue(monsters.get(indexValue).getCurrentHP());
            float x = monsterTable.getCell(monsterIcons.get(indexValue)).getActorX();
            damageLabel.setPosition(x*2, Gdx.graphics.getHeight() - 40 - damageLabelPadding);
            Color monsterColor = monsterIcons.get(indexValue).getColor();
            monsterIcons.get(indexValue).addAction(Actions.sequence(Actions.color(Color.RED, 0.5f), Actions.color(monsterColor, 0.5f)));
        }
        // Update the hp bar of hero at the specified index
        else{
            if(damageLabelPadding >0) {
                damageLabel.setFontScale(2.2f);
            }
            else {
                damageLabel.setFontScale(4f);
            }
            heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
            float x = heroTable.getCell(heroIcons.get(indexValue)).getActorX();
            damageLabel.setPosition(x+20, heroTable.getTop()+10 + damageLabelPadding);
            if(party.get(indexValue).isDead())
                heroIcons.get(indexValue).setColor(Color.DARK_GRAY);
            else {
                Color heroColor = heroIcons.get(indexValue).getColor();
                heroIcons.get(indexValue).addAction(Actions.sequence(Actions.color(Color.RED, 0.5f), Actions.color(heroColor, 0.5f)));
            }

        }
        dungeonA_Battle.addActor(damageLabel);
    }

    /**
     * Activate the card clicked by the player, however only one card from each type of cards
     * (Buff, Damage, Trap,Ultimate) can be activated. According to API table cell cannot be deleted,
     * hence the two display table needs to be rebuilt to display player handcards correctly.
     * @param card card to be activated
     */
    public void activateCard(Cards card, boolean castedSpell){
        if(card instanceof BuffCard){
            if (actBuffCardCount < 1) {
                ArrayList<Float> buffAmounts = ((BuffCard) card).activate(party);
                Label effectLabel = new Label(((BuffCard) card).getEffect(), guiSkin);
                Music buffCardFX;
                if (card.getCardName() == BuffCard.HEAL) {
                    for (int i = 0; i< party.size(); i++) {
                        if (!party.get(i).isDead()) {
                            float healAmount = buffAmounts.get(i);
                            increaseHPBar(i, healAmount, "Hero",0);
                        }
                    }
                    buffCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/heal_FX.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.HEAL,true,null);
                }
                else if (card.getCardName() == BuffCard.DAMAGE) {
                    displayBuffCardEffectLabel(buffAmounts,"damage");
                    buffCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/attackBuff_FX.wav"));
                    spawnParticleAtIcons(ParticleSystem.Type.DAMAGE_BUFF,true, null);
                }
                else if (card.getCardName() == BuffCard.DEFENSE) {
                    displayBuffCardEffectLabel(buffAmounts,"def");
                    buffCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/defenceBuff_FX.wav"));
                    spawnParticleAtIcons(ParticleSystem.Type.DEF_BUFF,true,null);
                }
                else {
                    displayBuffCardEffectLabel(buffAmounts,"cd");
                    buffCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/reduceCD_FX.wav"));
                    spawnParticleAtIcons(ParticleSystem.Type.COOLDOWN,true,null);
                }
                buffCardFX.setOnCompletionListener(new Music.OnCompletionListener()
                {
                    public void onCompletion(Music music) {
                        music.dispose();
                    }
                });
                buffCardFX.play();
                effectLabel.setFontScale(3f);
                effectLabel.setColor(229f / 255, 226f / 255, 60f / 255, 1);
                effectLabel.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 3);
                effectLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f),
                        Actions.fadeOut(0.5f), Actions.removeActor(effectLabel)));
                dungeonA_Battle.addActor(effectLabel);
                actBuffCardCount++;
                int index = handCard.indexOf(card);
                handCard.remove(index);
                rebuiltCardTable(index);
            }
            else {
                notifyCardActivationFailure("Buff");
            }
        }
        else if(card instanceof TrapCard){
            if (actTrapCardCount < 1) {
                // Only one enemy for now - hard coded solution (may be improved to allow player to choose target)
                ((TrapCard) card).activate(monsters.get(0));
                actTrapCardCount++;
                Music trapCardFX;
                if (((TrapCard) card).getName() == TrapCard.Silence) {
                    trapCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/silence_FX.mp3"));
                    monsterIcons.get(0).setColor(166/255f, 74/255f, 226/255f, 1f);
                    spawnParticleAtIcons(ParticleSystem.Type.SILENCED,false,null);
                }
                else if (((TrapCard) card).getName() == TrapCard.Stun) {
                    trapCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/stoneGaze_FX.mp3"));
                    monsterIcons.get(0).setColor(Color.YELLOW);
                    spawnParticleAtIcons(ParticleSystem.Type.STUN,false,null);
                }
                else if (((TrapCard) card).getName() == TrapCard.IgnoreDmg) {
                    trapCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/barrier_FX.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.BARRIER,true,null);
                }
                else {
                    trapCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/reflectDmg_FX.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.REFLECTION,true,null);
                }
                trapCardFX.setOnCompletionListener(new Music.OnCompletionListener()
                {
                    public void onCompletion(Music music) {
                        music.dispose();
                    }
                });
                trapCardFX.play();

                Label effectLabel = new Label(((TrapCard) card).getEffect(), guiSkin);
                effectLabel.setFontScale(3f);
                effectLabel.setColor(229f / 255, 226f / 255, 60f / 255, 1);
                effectLabel.setPosition(Gdx.graphics.getWidth() / 7, Gdx.graphics.getHeight() / 3);
                effectLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f),
                        Actions.fadeOut(0.5f), Actions.removeActor(effectLabel)));
                dungeonA_Battle.addActor(effectLabel);

                int index = handCard.indexOf(card);
                handCard.remove(index);
                rebuiltCardTable(index);
            }
            else {
                notifyCardActivationFailure("Trap");
            }
        }
        else if(card instanceof DamageCard){
            if (actDamageCardCount < 1 || castedSpell) {
                Music damageCardFX;
                // Play damage card sound
                if (((DamageCard) card).getName() == "Gust") {
                    damageCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/gustSound.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.GUST,false,null);
                }
                else if (((DamageCard) card).getName() == "Earthquake") {
                    damageCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/earthquake.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.EARTHQUAKE,false,null);
                }
                else if (((DamageCard) card).getName() == "Fire Nova") {
                    damageCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/firenova.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.FIRENOVA,false,null);
                }
                else {
                    damageCardFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/tidecalling.mp3"));
                    spawnParticleAtIcons(ParticleSystem.Type.TIDECALLING,false,null);
                }
                damageCardFX.setOnCompletionListener(new Music.OnCompletionListener()
                {
                    public void onCompletion(Music music) {
                        music.dispose();
                    }
                });
                damageCardFX.play();
                for (Monster monster : monsters) {
                    float damage = ((DamageCard) card).activate(monster);
                    decreaseHPBar(monsters.indexOf(monster),damage , "Monster", ((DamageCard) card).getName(),monsterDamageLabelPadding += 60);
                    if(checkWinningCondition())
                        return;
                }
                if (!castedSpell) {
                    actDamageCardCount++;
                    int index = handCard.indexOf(card);
                    handCard.remove(index);
                    rebuiltCardTable(index);
                }
            }
            else {
                notifyCardActivationFailure("Damage");
            }
        }
        else{
            if (actUltimateCardCount < 1) {
                ((UltimateCard) card).activate(this);
                actUltimateCardCount++;
                int index = handCard.indexOf(card);
                handCard.remove(index);
                rebuiltCardTable(index);
            }
            else {
                notifyCardActivationFailure("Ultimate");
            }
        }
    }

    /**
     * Displays a notification label to notify player about card activation
     * failure. Message varies depending on the given cardtype parameter
     * @param cardtype activation failure cardtype
     */
    private void notifyCardActivationFailure(String cardtype) {
        String message;
        if (cardtype == "Buff") {
            message = "Activation Failed! Only one Buff Card can be activated per turn!";
        }
        else if (cardtype == "Trap") {
            message = "Activation Failed! Only one Trap Card can be activated per turn!";
        }
        else if (cardtype == "Damage") {
            message = "Activation Failed! Only one Damage Card can be activated per turn!";
        }
        else {
            message = "Activation Failed! Only one Ultimate Card can be activated per turn!";
        }
        Label failureLabel = new Label(message, guiSkin);
        failureLabel.setFontScale(3f);
        failureLabel.setWrap(true);
        failureLabel.setWidth(Gdx.graphics.getWidth()/2);
        failureLabel.setAlignment(Align.center);
        failureLabel.setColor(Color.RED);
        failureLabel.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 3);
        failureLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(failureLabel)));
        dungeonA_Battle.addActor(failureLabel);
    }

    /**
     * Calculate, process and perform damages from heros to monster and vice versa
     */
    private void doDamage() {
        // Hero's attacking phase
        for(Hero hero : party) {
            // Only alive hero unit can perform attack
            if (!hero.isDead()) {
                // and the hero is not being hard cc
                if (!hero.isStun()) {
                    float damage = monsters.get(0).calculateDamage(hero, "Attack");
                    decreaseHPBar(0, damage, "Monster", hero.getName(),monsterDamageLabelPadding+=60);
                    // Audio dispose control
                    Music heroAttackFX = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/attack_FX.mp3"));
                    heroAttackFX.setOnCompletionListener(new Music.OnCompletionListener()
                    {
                        public void onCompletion(Music music) {
                            music.dispose();
                        }
                    });
                    heroAttackFX.play();
                    spawnParticleAtIcons(ParticleSystem.Type.HEROATTACK,false,null);
                }
            }
        }
        if(checkWinningCondition()) return;
        // Monster attacking phase
        for (Monster monster : monsters) {
            if (!monster.isStun()) {
                if(!monster.isSilenced()) {
                    Skill activatedSkill = monster.activateSkill(this);
                    if (activatedSkill != null) {
                        Music monsterSkillFX = Gdx.audio.newMusic(Gdx.files.internal(activatedSkill.getSkillFXPath()));
                        monsterSkillFX.setOnCompletionListener(new Music.OnCompletionListener()
                        {
                            public void onCompletion(Music music) {
                                music.dispose();
                            }
                        });
                        monsterSkillFX.play();
                    }
                }
                int targetIndex;
                if (monster.getTauntingSource() != null) {
                    targetIndex = party.indexOf(monster.getTauntingSource());
                }
                else {
                    Random rd = new Random();
                    targetIndex = rd.nextInt(party.size());

                    // Choose another target if current target is already dead
                    // or currently invisible to monsters
                    while (party.get(targetIndex).getCurrentHP() <= 0.0f ||
                            party.get(targetIndex).isInvis()) {
                        targetIndex = rd.nextInt(party.size());
                    }
                }

                if (!monster.isReflectDamage()) {
                    // Monster attacks the selected target hero
                    decreaseHPBar(targetIndex, party.get(targetIndex).calculateDamage(monster, "Attack"), "Hero", null,0);
                    spawnParticleAtIcons(ParticleSystem.Type.TIGERATTACK,true,heroIcons.get(targetIndex));

                }
                // Monster attack got reflected
                else {
                    float reflectDmg = party.get(targetIndex).calculateDamage(monster, "Reflect");
                    monster.takeDamage(reflectDmg);
                    decreaseHPBar(monsters.indexOf(monster), reflectDmg, "Monster", monster.getName(),monsterDamageLabelPadding+=60);
                }
            }
        }
        if(checkWinningCondition()) return;
        proceedNextRound();
    }

    public boolean checkWinningCondition(){
        // When all heros are dead, it's game over
        // All monster dead, player win
        if(monsters.get(0).getCurrentHP() <= 0 || checkAllHeroesDead()) {
            Dialog dialog = null;
            if (monsters.get(0).getCurrentHP() <= 0) {
                // Player wins, change bgm and notify battle outcome
                bgm.stop();
                bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/victoryBGM.ogg"));
                bgm.play();
                dialog = new Dialog("Battle Outcome", guiSkin, "default") {
                    public void result(Object obj) {
                        if (obj.equals(true)) {
                            game.setScreen(new WorldMap(game));
                            dispose();
                        }
                    }
                };
                dialog.text("Congratulation! You Win!");
            } else if (checkAllHeroesDead()) {
                Gdx.app.error("Heroes", "DEAD");
                // Player loses, change bgm and notify battle outcome
                bgm.stop();
                bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/gameoverBGM.ogg"));
                bgm.play();
                dialog = new Dialog("Battle Outcome", guiSkin, "default") {
                    public void result(Object obj) {
                        if (obj.equals(true)) {
                            game.setScreen(new WorldMap(game));
                            dispose();
                        }
                    }
                };
                dialog.text("GAME OVER!!! Play smarter next time!");
            }
            dialog.button("Finish", true);
            dialog.setSize(320, 130);
            dialog.getContentTable().align(Align.center);
            dialog.getTitleTable().align(Align.center);
            dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth(), Gdx.graphics.getHeight() / 2);
            dialog.setScale(2f);
            dungeonA_Battle.addActor(dialog);
            return true;
        }
        return false;
    }

    /**
     * Resets the stats of all monster and hero to their base state and decrement
     * their skill cooldown round
     */
    private void proceedNextRound() {
        // Reset monsters
        for (int i = 0 ; i< monsters.size();i++) {
            Monster monster = monsters.get(i);
            monster.reduceCooldown();
            monster.setCurrentDamage(monster.getBaseDamage());
            monster.setCurrentDef(monster.getBaseDef());
            monster.setSilenced(false);
            final int finalI = i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    monsterIcons.get(finalI).setColor(Color.WHITE);
                }
            },1.3f);
            monster.setStun(false);
            monster.setTauntingSource(null);
            monster.setReflectDamage(false);
        }
        // Reset heros
        for(int i = 0 ; i<party.size();i++){
            final Hero hero = party.get(i);
            if(hero.getStunDuration() >0)
                hero.setStunDuration(hero.getStunDuration()-1);
            if(hero.getStunDuration() == 0) {
                hero.setStun(false);
                final int finalI = i;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(!hero.isDead()) {
                            heroIcons.get(finalI).setColor(Color.WHITE);
                        }else{
                            heroIcons.get(finalI).setColor(Color.DARK_GRAY);
                        }
                    }
                }, 1.3f);
            }
            hero.setCurrentDamage(hero.getBaseDamage());
            hero.setCurrentDef(hero.getBaseDef());
            hero.setInvis(false);
            hero.getSkill().reduceCooldown(1);
        }
        monsterDamageLabelPadding = -60;
        monsterHealingLabelPadding = -60;
        actBuffCardCount = 0;
        actTrapCardCount = 0;
        actDamageCardCount = 0;
        actUltimateCardCount = 0;
    }

    public void spawnParticleAtIcons(ParticleSystem.Type type, boolean heroes, Button oneHero) {
        if (heroes) {
            int y = Gdx.graphics.getHeight()/2 - 45;
            if(oneHero == null){
                for (int i = 0 ; i< heroIcons.size();i++) {
                    if(!party.get(i).isDead()) {
                            int x = (int) heroIcons.get(i).getX();
                            int index = particleSystem.spawn(type);
                            particleSystem.getPosition()[index].set(x, y);
                    }
                }
            }else{
                int x = (int) oneHero.getX();
                int i = particleSystem.spawn(type);
                particleSystem.getPosition()[i].set(x, y);
            }
        } else {
            for (Button monsterIcon : monsterIcons) {
                int x = (int) monsterIcon.getX();
                int i = particleSystem.spawn(type);
                particleSystem.getPosition()[i].set(x, Gdx.graphics.getHeight() - monsterIcons.get(0).getHeight());
            }
        }
    }

    /**
     * Determines whether all heros are dead or not
     * @return true if all heros are dead, false otherwise
     */
    private boolean checkAllHeroesDead() {
        int deadCount = 0;
        for(Hero hero: party){
            if(hero.isDead())
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
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
        dungeonA_Battle.act();
        dungeonA_Battle.draw();
        float deltaTime = Gdx.graphics.getDeltaTime();
        particleSystem.update(deltaTime);
        particleSystem.render(batch);
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

    public ArrayList<Button> getHeroIcons() {
        return heroIcons;
    }

    @Override
    public void dispose() {
        batch.dispose();
        guiSkin.dispose();
        bgm.dispose();
        dungeonA_Battle.dispose();
        particleSystem.dispose();
    }

    public Skin getGuiSkin() {
        return guiSkin;
    }

    public Stage getStage() {
        return dungeonA_Battle;
    }
}
