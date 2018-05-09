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
import com.badlogic.gdx.graphics.g2d.Animation;
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
    protected ArrayList<Hero> party;
    private ArrayList<Button> heroIcons;
    private ArrayList<ProgressBar> heroHPBars;
    private ArrayList<Cards> handCard;
    protected ArrayList<Monster> monsters;
    private ArrayList<ImageButton> monsterIcons;
    private ArrayList<ProgressBar> monsterHPBars;
    private Table monsterTable;
    private Table heroTable;
    private Table cardTableA;
    private Table cardTableB;
    private float damageLabelPadding;
    private Music bgm;
    private Music damageSound;
    private int actBuffCardCount;
    private int actTrapCardCount;
    private int actDamageCardCount;
    private int actUltimateCardCount;
    public static boolean ReflectDamage = false;
    public static boolean IgnoreDmg = false;

    public DungeonA(Game game) {
        // variable initialisation
        this.game = game;
        batch = new SpriteBatch();
        background = new Sprite(new Texture(Gdx.files.internal("dungeonA.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiSkin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        dungeonA_Battle = new Stage(new ScreenViewport());
        party = new ArrayList<Hero>();
        heroIcons = new ArrayList<Button>();
        heroHPBars = new ArrayList<ProgressBar>();
        handCard = new ArrayList<Cards>();
        monsters = new ArrayList<Monster>();
        monsterIcons = new ArrayList<ImageButton>();
        monsterHPBars = new ArrayList<ProgressBar>();
        damageLabelPadding = 0;
        actBuffCardCount = 0;
        actDamageCardCount = 0;
        actTrapCardCount = 0;
        actUltimateCardCount = 0;

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
                    final int clickedHeroIndex = heroIcons.indexOf(event.getListenerActor());
                    Hero clickedHero = party.get(clickedHeroIndex);

                    // Display skill activation dialog
                    Dialog dialog = new Dialog("", guiSkin, "default") {
                        public void result(Object obj) {
                            // Activate skill
                            if (obj.equals(true)) {
                                party.get(clickedHeroIndex).activateSkill(DungeonA.this);
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
                    if (clickedHero.getSkill().getCurrentCooldown() == 0
                            && clickedHero.getCurrentHP() > 0f) {
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
        Monster tigerA = new Monster("Tiger A", 2000f, 100f, 50f, Element.EARTH);
        tigerA.addSkill(new Skill(Skill.LEECH, "Abosrb 100 health from hero/s", 3));
        tigerA.addSkill(new Skill(Skill.ENTANGLE, "Stuns hero/s", 5));
        monsters.add(tigerA);
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
                activateCard(newCard, false);
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
        int addCardIndex = 0;
        for (final Cards card : handCard) {
            Button cardIcon = new Button(card.getCardImage());
            cardIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateCard(card, false);
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

    /**
     * Increase the specified hp bar based on the given damage value.
     * @param indexValue index indicating which hp bar is to be updated
     * @param healAmount the value of which the hp bar is increasing by
     * @param type of healing unit, Monster or Hero
     */
    public void increaseHPBar(int indexValue, float healAmount, String type){
        // Update the hp bar of hero at the specified index
        heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
        // Create a label to show the amount healed
        Label healLabel = new Label("+" + healAmount, guiSkin);
        healLabel.setFontScale(4f);
        healLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(healLabel)));
        healLabel.setColor(Color.GREEN);
        if (type == "Monster") {
            monsterHPBars.get(indexValue).setValue(monsters.get(indexValue).getCurrentHP());
            float x = monsterTable.getCell(monsterIcons.get(0)).getActorX();
            healLabel.setPosition(x*2, Gdx.graphics.getHeight() - 40);
        }
        else {
            // Update the hp bar of hero at the specified index
            heroHPBars.get(indexValue).setValue(party.get(indexValue).getCurrentHP());
            float x = heroTable.getCell(heroIcons.get(indexValue)).getActorX();
            healLabel.setPosition(x + 20, heroTable.getTop() + 10);
        }
        dungeonA_Battle.addActor(healLabel);
    }

    /**
     * Decrease the specified hp bar based on the given damage value
     * @param indexValue index indicating which hp bar is to be updated
     * @param damage the value of which the hp bar is decreasing by
     * @param type of damaged unit, Monster or Hero
     */
    public void decreaseHPBar(int indexValue, float damage, String type, String name){
        Label damageLabel = new Label("-" + damage +  (name == null? "":" (" + name +")"), guiSkin);
        damageLabel.setFontScale(4f);
        damageLabel.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.fadeOut(0.5f), Actions.removeActor(damageLabel)));
        damageLabel.setColor(Color.RED);
        // Update monster's hp bar
        if(type == "Monster"){
            monsterHPBars.get(indexValue).setValue(monsters.get(indexValue).getCurrentHP());
            float x = monsterTable.getCell(monsterIcons.get(indexValue)).getActorX();
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

    /**
     * Activate the card clicked by the player, however only one card from each type of cards
     * (Buff, Damage, Trap,Ultimate) can be activated. According to API table cell cannot be deleted,
     * hence the two display table needs to be rebuilt to display player handcards correctly.
     * @param card card to be activated
     */
    public void activateCard(Cards card, boolean castedSpell){
        if(card instanceof BuffCard){
            if (actBuffCardCount < 1) {
                ArrayList<Float> healAmounts = ((BuffCard) card).activate(party);
                // Display activated buff card (except heal) effect
                if (((BuffCard) card).getEffect() != "") {
                    Label effectLabel = new Label(((BuffCard) card).getEffect(), guiSkin);
                    effectLabel.setFontScale(3f);
                    effectLabel.setColor(229f / 255, 226f / 255, 60f / 255, 1);
                    effectLabel.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 3);
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
                                increaseHPBar(heroIndex, healAmount, "Hero");
                            } else {
                                hero.setCurrentHP(hero.getMaxHP() + healAmounts.get(heroIndex));
                                increaseHPBar(heroIndex, healAmount, "Hero");
                            }
                        }
                    }
                }
                actBuffCardCount++;
                handCard.remove(handCard.indexOf(card));
                rebuiltCardTable();
            }
            // Notify player about activation failure
            else {
                notifyCardActivationFailure("Buff");
            }
        }
        else if(card instanceof TrapCard){
            if (actTrapCardCount < 1) {
                // Only one enemy for now - hard coded solution (may be improved to allow player to choose target)
                ((TrapCard) card).activate(monsters.get(0));
                actTrapCardCount++;
                handCard.remove(handCard.indexOf(card));
                rebuiltCardTable();
            }
            else {
                notifyCardActivationFailure("Trap");
            }
        }
        else if(card instanceof DamageCard){
            if (actDamageCardCount < 1 || castedSpell) {
                // Play damage card sound
                if (((DamageCard) card).getName() == "Gust") {
                    damageSound = Gdx.audio.newMusic(Gdx.files.internal("cardSound/gustSound.mp3"));
                }
                else if (((DamageCard) card).getName() == "Earthquake") {
                    damageSound = Gdx.audio.newMusic(Gdx.files.internal("cardSound/earthquakeSound.mp3"));
                }
                if (((DamageCard) card).getName() == "Fire Nova") {
                    damageSound = Gdx.audio.newMusic(Gdx.files.internal("cardSound/firenovaSound.mp3"));
                }
                else {
                    damageSound = Gdx.audio.newMusic(Gdx.files.internal("cardSound/tidecallingSound.mp3"));
                }
                damageSound.play();
                for (Monster monster : monsters) {
                    decreaseHPBar(monsters.indexOf(monster), ((DamageCard) card).activate(monster), "Monster", ((DamageCard) card).getName());
                }
                if (!castedSpell) {
                    actDamageCardCount++;
                    handCard.remove(handCard.indexOf(card));
                    rebuiltCardTable();
                }
            }
            else {
                notifyCardActivationFailure("Damage");
            }
        }
        else{
            if (actUltimateCardCount < 1) {
                ((UltimateCard) card).activate(party, monsters.get(0));
                actUltimateCardCount++;
                handCard.remove(handCard.indexOf(card));
                rebuiltCardTable();
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
            if (hero.getCurrentHP() > 0) {
                // and the hero is not being hard cc
                if (!hero.isStun()) {
                    float damage = monsters.get(0).calculateDamage(hero, "Attack");
                    decreaseHPBar(0, damage, "Monster", hero.getName());
                }
            }
            // All monster dead, player win
            if (monsters.get(0).getCurrentHP() <= 0) {
                // Player wins, change bgm and notify battle outcome
                bgm.stop();
                bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/victoryBGM.ogg"));
                bgm.play();
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
        for (Monster monster : monsters) {
            if (!monster.isStun() || !IgnoreDmg) {
                int targetIndex;

                if (monster.getTauntingSource() != null) {
                    targetIndex = party.indexOf(monster.getTauntingSource());
                }
                else {
                    Random rd = new Random();
                    targetIndex = rd.nextInt(party.size());
                    // If the last standing hero is invisible
                    // then the monster won't attack
                    if (checkAliveHeroes() == 1) {
                        targetIndex = -1;
                    }
                    // Choose another target if current target is already dead
                    // or currently invisible to monsters
                    else {
                        while (party.get(targetIndex).getCurrentHP() <= 0.0f ||
                                party.get(targetIndex).isInvis()) {
                            targetIndex = rd.nextInt(party.size());
                        }
                    }
                }

                if (targetIndex != -1) {
                    if (!ReflectDamage) {
                        // Monster attacks the selected target hero
                        decreaseHPBar(targetIndex, party.get(targetIndex).calculateDamage(monster, "Attack"), "Hero", null);
                    }
                    // Monster attack got reflected
                    else {
                        float reflectDmg = party.get(targetIndex).calculateDamage(monster, "Reflect");
                        monster.takeDamage(reflectDmg);
                        decreaseHPBar(monsters.indexOf(monster), reflectDmg, "Monster", monster.getName());
                    }

                    // When all heros are dead, it's game over
                    if (checkAllHeroesDead()) {
                        // Player loses, change bgm and notify battle outcome
                        bgm.stop();
                        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/gameoverBGM.ogg"));
                        bgm.play();
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
                        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth(), Gdx.graphics.getHeight() / 2);
                        dialog.setScale(2f);
                        dungeonA_Battle.addActor(dialog);
                    }
                }
            }
        }
        proceedNextRound();
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
            monster.reduceCooldown();
            if (!monster.isSilenced()) {
                for (Skill skill : monster.getSkillList()) {
                    if (skill.getCurrentCooldown() == 0) {
                        monster.activateSkill(this);
                    }
                }
            }
            monster.setCurrentDamage(monster.getBaseDamage());
            monster.setCurrentDef(monster.getBaseDef());
            monster.setSilenced(false);
            monster.setStun(false);
            monster.setTauntingSource(null);
        }
        damageLabelPadding = 0;
        actBuffCardCount = 0;
        actTrapCardCount = 0;
        actDamageCardCount = 0;
        actUltimateCardCount = 0;
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

    /**
     * Check how many hero are still alive
     * @return the number of alive hero/s
     */
    private int checkAliveHeroes() {
        int count = 0;
        for(Hero hero: party){
            if(hero.getCurrentHP() > 0f)
                count++;
        }
        return count;
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
        bgm.dispose();
        if (damageSound != null) {
            damageSound.dispose();
        }
        dungeonA_Battle.dispose();
    }
}
