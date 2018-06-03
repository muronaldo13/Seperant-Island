package com.serpent.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



/**
 * Created by toyty004 on 8/5/18.
 */

public class ParticleSystem {
    public static final int MAX_PARTICLES = 128;
    public static final float PARTICLE_LIFETIME = 0.7f;
    enum Type {NONE, DAMAGE_BUFF, HEAL, DEF_BUFF, COOLDOWN, ENTANGLE, LEECH, TAUNT, REVIVE, INVIS,STUN, SILENCED, GUST, FIRENOVA, EARTHQUAKE, TIDECALLING, HEROATTACK, TIGERATTACK, BARRIER, REFLECTION}

    //Buff cards
    private Texture defBuffSprite;
    private Texture damageBuffSprite;
    private Texture healSprite;
    private Texture cooldownSprite;

    private TextureRegion[] defBuffFrame = new TextureRegion[10];
    private TextureRegion[] damageBuffFrame = new TextureRegion[10];
    private TextureRegion[] healFrame = new TextureRegion[10];
    private TextureRegion[] cooldownFrame = new TextureRegion[13];
    // Trap cards
    private Texture stunSprite;
    private Texture silencedSprite;
    private Texture reflectDmgSprite;
    private Texture barrierSprite;

    private TextureRegion[] stunFrame = new TextureRegion[14];
    private TextureRegion[] silencedFrame = new TextureRegion[15];
    private TextureRegion[] reflectDmgFrame = new TextureRegion[12];
    private TextureRegion[] barrierFrame = new TextureRegion[16];
    // Damage cards
    private Texture gustSprite;
    private Texture firenovaSprite;
    private Texture earthquakeSprite;
    private Texture tidecallingSprite;

    private TextureRegion[] gustFrame = new TextureRegion[27];
    private TextureRegion[] firenovaFrame = new TextureRegion[10];
    private TextureRegion[] earthquakeFrame = new TextureRegion[17];
    private TextureRegion[] tidecallingFrame = new TextureRegion[26];
    // Normal attack
    private Texture heroAttackSprite;
    private Texture tigerAttackSprite;

    private TextureRegion[] heroAttackFrame = new TextureRegion[12];
    private TextureRegion[] tigerAttackFrame = new TextureRegion[7];
    // Hero skills
    private Texture reviveSprite;
    private Texture tauntSprite;
    private Texture invisSprite;

    private TextureRegion[] reviveFrame = new TextureRegion[15];
    private TextureRegion[] tauntFrame = new TextureRegion[20];
    // Monster skills
    private Texture entangleSprite;
    private Texture leechSprite;

    private TextureRegion[] entangleFrame = new TextureRegion[18];
    private TextureRegion[] leechFrame = new TextureRegion[8];
    private TextureRegion[] invisFrame = new TextureRegion[12];

    private Vector2[] position;
    private float[] lifeTime = new float[MAX_PARTICLES];
    private Type[] type ;

    public void init(){
        defBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/defense_buff.png"));
        damageBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/attack_buff.png"));
        healSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png"));
        entangleSprite = new Texture(Gdx.files.internal("particle_Spritesheet/entangle.png"));
        defBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/defense_buff.png"));
        leechSprite = new Texture(Gdx.files.internal("particle_Spritesheet/leech.png"));
        reviveSprite = new Texture(Gdx.files.internal("particle_Spritesheet/revive.png"));
        stunSprite = new Texture(Gdx.files.internal("particle_Spritesheet/stun.png"));
        silencedSprite = new Texture(Gdx.files.internal("particle_Spritesheet/silence.png"));
        cooldownSprite = new Texture(Gdx.files.internal("particle_Spritesheet/cooldown.png"));
        gustSprite = new Texture(Gdx.files.internal("particle_Spritesheet/gust_ss.png"));
        firenovaSprite = new Texture(Gdx.files.internal("particle_Spritesheet/firenova.png"));
        earthquakeSprite = new Texture(Gdx.files.internal("particle_Spritesheet/earthquake.png"));
        tidecallingSprite = new Texture(Gdx.files.internal("particle_Spritesheet/tide_calling.png"));
        reflectDmgSprite = new Texture(Gdx.files.internal("particle_Spritesheet/reflection.png"));
        barrierSprite = new Texture(Gdx.files.internal("particle_Spritesheet/barrier.png"));
        tauntSprite = new Texture(Gdx.files.internal("particle_Spritesheet/taunt.png"));
        heroAttackSprite = new Texture(Gdx.files.internal("particle_Spritesheet/hero_attack.png"));
        tigerAttackSprite = new Texture(Gdx.files.internal("particle_Spritesheet/tiger_attack.png"));
        invisSprite = new Texture(Gdx.files.internal("particle_Spritesheet/invis.png"));

        defBuffFrame[0] = new TextureRegion(defBuffSprite,0,0,192,192);
        defBuffFrame[1] = new TextureRegion(defBuffSprite,192,0,192,192);
        defBuffFrame[2] = new TextureRegion(defBuffSprite,384,0,192,192);
        defBuffFrame[3] = new TextureRegion(defBuffSprite,576,0,192,192);
        defBuffFrame[4] = new TextureRegion(defBuffSprite,768,0,192,192);
        defBuffFrame[5] = new TextureRegion(defBuffSprite,0,192,192,192);
        defBuffFrame[6] = new TextureRegion(defBuffSprite,192,192,192,192);
        defBuffFrame[7] = new TextureRegion(defBuffSprite,384,192,192,192);
        defBuffFrame[8] = new TextureRegion(defBuffSprite,576,192,192,192);
        defBuffFrame[9] = new TextureRegion(defBuffSprite,768,192,192,192);

        damageBuffFrame[0] = new TextureRegion(damageBuffSprite,0, 0,192,192);
        damageBuffFrame[1] = new TextureRegion(damageBuffSprite,192, 0,192,192);
        damageBuffFrame[2] = new TextureRegion(damageBuffSprite,384, 0,192,192);
        damageBuffFrame[3] = new TextureRegion(damageBuffSprite,576, 0,192,192);
        damageBuffFrame[4] = new TextureRegion(damageBuffSprite,768, 0,192,192);
        damageBuffFrame[5] = new TextureRegion(damageBuffSprite,0, 192,192,192);
        damageBuffFrame[6] = new TextureRegion(damageBuffSprite,192, 192,192,192);
        damageBuffFrame[7] = new TextureRegion(damageBuffSprite,384, 192,192,192);
        damageBuffFrame[8] = new TextureRegion(damageBuffSprite,576, 192,192,192);
        damageBuffFrame[9] = new TextureRegion(damageBuffSprite,768, 192,192,192);

        cooldownFrame[0] = new TextureRegion(cooldownSprite,0, 0,192,192);
        cooldownFrame[1] = new TextureRegion(cooldownSprite,192, 0,192,192);
        cooldownFrame[2] = new TextureRegion(cooldownSprite,384, 0,192,192);
        cooldownFrame[3] = new TextureRegion(cooldownSprite,576, 0,192,192);
        cooldownFrame[4] = new TextureRegion(cooldownSprite,768, 0,192,192);
        cooldownFrame[5] = new TextureRegion(cooldownSprite,0, 192,192,192);
        cooldownFrame[6] = new TextureRegion(cooldownSprite,192, 192,192,192);
        cooldownFrame[7] = new TextureRegion(cooldownSprite,384, 192,192,192);
        cooldownFrame[8] = new TextureRegion(cooldownSprite,576, 192,192,192);
        cooldownFrame[9] = new TextureRegion(cooldownSprite,768, 192,192,192);
        cooldownFrame[10] = new TextureRegion(cooldownSprite,0, 384,192,192);
        cooldownFrame[11] = new TextureRegion(cooldownSprite,192, 384,192,192);
        cooldownFrame[12] = new TextureRegion(cooldownSprite,384, 384,192,192);

        entangleFrame[0] = new TextureRegion(entangleSprite,0, 0,192,192);
        entangleFrame[1] = new TextureRegion(entangleSprite,192, 0,192,192);
        entangleFrame[2] = new TextureRegion(entangleSprite,384, 0,192,192);
        entangleFrame[3] = new TextureRegion(entangleSprite,576, 0,192,192);
        entangleFrame[4] = new TextureRegion(entangleSprite,768, 0,192,192);
        entangleFrame[5] = new TextureRegion(entangleSprite,0, 192,192,192);
        entangleFrame[6] = new TextureRegion(entangleSprite,192, 192,192,192);
        entangleFrame[7] = new TextureRegion(entangleSprite,384, 192,192,192);
        entangleFrame[8] = new TextureRegion(entangleSprite,576, 192,192,192);
        entangleFrame[9] = new TextureRegion(entangleSprite,768, 192,192,192);
        entangleFrame[10] = new TextureRegion(entangleSprite,0, 384,192,192);
        entangleFrame[11] = new TextureRegion(entangleSprite,192, 384,192,192);
        entangleFrame[12] = new TextureRegion(entangleSprite,384, 384,192,192);
        entangleFrame[13] = new TextureRegion(entangleSprite,576, 384,192,192);
        entangleFrame[14] = new TextureRegion(entangleSprite,768, 384,192,192);
        entangleFrame[15] = new TextureRegion(entangleSprite,0, 576,192,192);
        entangleFrame[16] = new TextureRegion(entangleSprite,192, 576,192,192);
        entangleFrame[17] = new TextureRegion(entangleSprite,384, 576,192,192);

        healFrame[0] = new TextureRegion(healSprite,0, 0,192,192);
        healFrame[1] = new TextureRegion(healSprite,192, 0,192,192);
        healFrame[2] = new TextureRegion(healSprite,384, 0,192,192);
        healFrame[3] = new TextureRegion(healSprite,576, 0,192,192);
        healFrame[4] = new TextureRegion(healSprite,768, 0,192,192);
        healFrame[5] = new TextureRegion(healSprite,0, 192,192,192);
        healFrame[6] = new TextureRegion(healSprite,192, 192,192,192);
        healFrame[7] = new TextureRegion(healSprite,384, 192,192,192);
        healFrame[8] = new TextureRegion(healSprite,576, 192,192,192);
        healFrame[9] = new TextureRegion(healSprite,768, 192,192,192);

        leechFrame[0] = new TextureRegion(leechSprite,0, 0,192,192);
        leechFrame[1] = new TextureRegion(leechSprite,192, 0,192,192);
        leechFrame[2] = new TextureRegion(leechSprite,384, 0,192,192);
        leechFrame[3] = new TextureRegion(leechSprite,576, 0,192,192);
        leechFrame[4] = new TextureRegion(leechSprite,768, 0,192,192);
        leechFrame[5] = new TextureRegion(leechSprite,0, 192,192,192);
        leechFrame[6] = new TextureRegion(leechSprite,192, 192,192,192);
        leechFrame[7] = new TextureRegion(leechSprite,384, 192,192,192);

        reviveFrame[0] = new TextureRegion(reviveSprite,0, 0,192,192);
        reviveFrame[1] = new TextureRegion(reviveSprite,192, 0,192,192);
        reviveFrame[2] = new TextureRegion(reviveSprite,384, 0,192,192);
        reviveFrame[3] = new TextureRegion(reviveSprite,576, 0,192,192);
        reviveFrame[4] = new TextureRegion(reviveSprite,768, 0,192,192);
        reviveFrame[5] = new TextureRegion(reviveSprite,0, 192,192,192);
        reviveFrame[6] = new TextureRegion(reviveSprite,192, 192,192,192);
        reviveFrame[7] = new TextureRegion(reviveSprite,384, 192,192,192);
        reviveFrame[8] = new TextureRegion(reviveSprite,576, 192,192,192);
        reviveFrame[9] = new TextureRegion(reviveSprite,768, 192,192,192);
        reviveFrame[10] = new TextureRegion(reviveSprite,0, 384,192,192);
        reviveFrame[11] = new TextureRegion(reviveSprite,192, 384,192,192);
        reviveFrame[12] = new TextureRegion(reviveSprite,384, 384,192,192);
        reviveFrame[13] = new TextureRegion(reviveSprite,576, 384,192,192);
        reviveFrame[14] = new TextureRegion(reviveSprite,768, 384,192,192);

        stunFrame[0] = new TextureRegion(stunSprite,0, 0,192,192);
        stunFrame[1] = new TextureRegion(stunSprite,192, 0,192,192);
        stunFrame[2] = new TextureRegion(stunSprite,384, 0,192,192);
        stunFrame[3] = new TextureRegion(stunSprite,576, 0,192,192);
        stunFrame[4] = new TextureRegion(stunSprite,768, 0,192,192);
        stunFrame[5] = new TextureRegion(stunSprite,0, 192,192,192);
        stunFrame[6] = new TextureRegion(stunSprite,192, 192,192,192);
        stunFrame[7] = new TextureRegion(stunSprite,384, 192,192,192);
        stunFrame[8] = new TextureRegion(stunSprite,576, 192,192,192);
        stunFrame[9] = new TextureRegion(stunSprite,768, 192,192,192);
        stunFrame[10] = new TextureRegion(stunSprite,0, 384,192,192);
        stunFrame[11] = new TextureRegion(stunSprite,192, 384,192,192);
        stunFrame[12] = new TextureRegion(stunSprite,384, 384,192,192);
        stunFrame[13] = new TextureRegion(stunSprite,576, 384,192,192);

        silencedFrame[0] = new TextureRegion(silencedSprite,0, 0,192,192);
        silencedFrame[1] = new TextureRegion(silencedSprite,192, 0,192,192);
        silencedFrame[2] = new TextureRegion(silencedSprite,384, 0,192,192);
        silencedFrame[3] = new TextureRegion(silencedSprite,576, 0,192,192);
        silencedFrame[4] = new TextureRegion(silencedSprite,768, 0,192,192);
        silencedFrame[5] = new TextureRegion(silencedSprite,0, 192,192,192);
        silencedFrame[6] = new TextureRegion(silencedSprite,192, 192,192,192);
        silencedFrame[7] = new TextureRegion(silencedSprite,384, 192,192,192);
        silencedFrame[8] = new TextureRegion(silencedSprite,576, 192,192,192);
        silencedFrame[9] = new TextureRegion(silencedSprite,768, 192,192,192);
        silencedFrame[10] = new TextureRegion(silencedSprite,0, 384,192,192);
        silencedFrame[11] = new TextureRegion(silencedSprite,192, 384,192,192);
        silencedFrame[12] = new TextureRegion(silencedSprite,384, 384,192,192);
        silencedFrame[13] = new TextureRegion(silencedSprite,576, 384,192,192);
        silencedFrame[14] = new TextureRegion(silencedSprite,768, 384,192,192);

        gustFrame[0] = new TextureRegion(gustSprite,0, 0,192,192);
        gustFrame[1] = new TextureRegion(gustSprite,192, 0,192,192);
        gustFrame[2] = new TextureRegion(gustSprite,384, 0,192,192);
        gustFrame[3] = new TextureRegion(gustSprite,576, 0,192,192);
        gustFrame[4] = new TextureRegion(gustSprite,768, 0,192,192);
        gustFrame[5] = new TextureRegion(gustSprite,0, 192,192,192);
        gustFrame[6] = new TextureRegion(gustSprite,192, 192,192,192);
        gustFrame[7] = new TextureRegion(gustSprite,384, 192,192,192);
        gustFrame[8] = new TextureRegion(gustSprite,576, 192,192,192);
        gustFrame[9] = new TextureRegion(gustSprite,768, 192,192,192);
        gustFrame[10] = new TextureRegion(gustSprite,0, 384,192,192);
        gustFrame[11] = new TextureRegion(gustSprite,192, 384,192,192);
        gustFrame[12] = new TextureRegion(gustSprite,384, 384,192,192);
        gustFrame[13] = new TextureRegion(gustSprite,576, 384,192,192);
        gustFrame[14] = new TextureRegion(gustSprite,768, 384,192,192);
        gustFrame[15] = new TextureRegion(gustSprite,0, 576,192,192);
        gustFrame[16] = new TextureRegion(gustSprite,192, 576,192,192);
        gustFrame[17] = new TextureRegion(gustSprite,384, 576,192,192);
        gustFrame[18] = new TextureRegion(gustSprite,576, 576,192,192);
        gustFrame[19] = new TextureRegion(gustSprite,768, 576,192,192);
        gustFrame[20] = new TextureRegion(gustSprite,0, 768,192,192);
        gustFrame[21] = new TextureRegion(gustSprite,192, 768,192,192);
        gustFrame[22] = new TextureRegion(gustSprite,384, 768,192,192);
        gustFrame[23] = new TextureRegion(gustSprite,576, 768,192,192);
        gustFrame[24] = new TextureRegion(gustSprite,768, 768,192,192);
        gustFrame[25] = new TextureRegion(gustSprite,0, 960,192,192);
        gustFrame[26] = new TextureRegion(gustSprite,192, 960,192,192);

        firenovaFrame[0] = new TextureRegion(firenovaSprite,0, 0,192,192);
        firenovaFrame[1] = new TextureRegion(firenovaSprite,192, 0,192,192);
        firenovaFrame[2] = new TextureRegion(firenovaSprite,384, 0,192,192);
        firenovaFrame[3] = new TextureRegion(firenovaSprite,576, 0,192,192);
        firenovaFrame[4] = new TextureRegion(firenovaSprite,768, 0,192,192);
        firenovaFrame[5] = new TextureRegion(firenovaSprite,0, 192,192,192);
        firenovaFrame[6] = new TextureRegion(firenovaSprite,192, 192,192,192);
        firenovaFrame[7] = new TextureRegion(firenovaSprite,384, 192,192,192);
        firenovaFrame[8] = new TextureRegion(firenovaSprite,576, 192,192,192);
        firenovaFrame[9] = new TextureRegion(firenovaSprite,768, 192,192,192);

        earthquakeFrame[0] = new TextureRegion(earthquakeSprite,0, 0,192,192);
        earthquakeFrame[1] = new TextureRegion(earthquakeSprite,192, 0,192,192);
        earthquakeFrame[2] = new TextureRegion(earthquakeSprite,384, 0,192,192);
        earthquakeFrame[3] = new TextureRegion(earthquakeSprite,576, 0,192,192);
        earthquakeFrame[4] = new TextureRegion(earthquakeSprite,768, 0,192,192);
        earthquakeFrame[5] = new TextureRegion(earthquakeSprite,0, 192,192,192);
        earthquakeFrame[6] = new TextureRegion(earthquakeSprite,192, 192,192,192);
        earthquakeFrame[7] = new TextureRegion(earthquakeSprite,384, 192,192,192);
        earthquakeFrame[8] = new TextureRegion(earthquakeSprite,576, 192,192,192);
        earthquakeFrame[9] = new TextureRegion(earthquakeSprite,768, 192,192,192);
        earthquakeFrame[10] = new TextureRegion(earthquakeSprite,0, 384,192,192);
        earthquakeFrame[11] = new TextureRegion(earthquakeSprite,192, 384,192,192);
        earthquakeFrame[12] = new TextureRegion(earthquakeSprite,384, 384,192,192);
        earthquakeFrame[13] = new TextureRegion(earthquakeSprite,576, 384,192,192);
        earthquakeFrame[14] = new TextureRegion(earthquakeSprite,768, 384,192,192);
        earthquakeFrame[15] = new TextureRegion(earthquakeSprite,0, 576,192,192);
        earthquakeFrame[16] = new TextureRegion(earthquakeSprite,192, 576,192,192);

        tidecallingFrame[0] = new TextureRegion(tidecallingSprite,0, 0,192,192);
        tidecallingFrame[1] = new TextureRegion(tidecallingSprite,192, 0,192,192);
        tidecallingFrame[2] = new TextureRegion(tidecallingSprite,384, 0,192,192);
        tidecallingFrame[3] = new TextureRegion(tidecallingSprite,576, 0,192,192);
        tidecallingFrame[4] = new TextureRegion(tidecallingSprite,768, 0,192,192);
        tidecallingFrame[5] = new TextureRegion(tidecallingSprite,0, 192,192,192);
        tidecallingFrame[6] = new TextureRegion(tidecallingSprite,192, 192,192,192);
        tidecallingFrame[7] = new TextureRegion(tidecallingSprite,384, 192,192,192);
        tidecallingFrame[8] = new TextureRegion(tidecallingSprite,576, 192,192,192);
        tidecallingFrame[9] = new TextureRegion(tidecallingSprite,768, 192,192,192);
        tidecallingFrame[10] = new TextureRegion(tidecallingSprite,0, 384,192,192);
        tidecallingFrame[11] = new TextureRegion(tidecallingSprite,192, 384,192,192);
        tidecallingFrame[12] = new TextureRegion(tidecallingSprite,384, 384,192,192);
        tidecallingFrame[13] = new TextureRegion(tidecallingSprite,576, 384,192,192);
        tidecallingFrame[14] = new TextureRegion(tidecallingSprite,768, 384,192,192);
        tidecallingFrame[15] = new TextureRegion(tidecallingSprite,0, 576,192,192);
        tidecallingFrame[16] = new TextureRegion(tidecallingSprite,192, 576,192,192);
        tidecallingFrame[17] = new TextureRegion(tidecallingSprite,384, 576,192,192);
        tidecallingFrame[18] = new TextureRegion(tidecallingSprite,576, 576,192,192);
        tidecallingFrame[19] = new TextureRegion(tidecallingSprite,768, 576,192,192);
        tidecallingFrame[20] = new TextureRegion(tidecallingSprite,0, 768,192,192);
        tidecallingFrame[21] = new TextureRegion(tidecallingSprite,192, 768,192,192);
        tidecallingFrame[22] = new TextureRegion(tidecallingSprite,384, 768,192,192);
        tidecallingFrame[23] = new TextureRegion(tidecallingSprite,576, 768,192,192);
        tidecallingFrame[24] = new TextureRegion(tidecallingSprite,768, 768,192,192);
        tidecallingFrame[25] = new TextureRegion(tidecallingSprite,0, 960,192,192);

        reflectDmgFrame[0] = new TextureRegion(reflectDmgSprite,0, 0,192,192);
        reflectDmgFrame[1] = new TextureRegion(reflectDmgSprite,192, 0,192,192);
        reflectDmgFrame[2] = new TextureRegion(reflectDmgSprite,384, 0,192,192);
        reflectDmgFrame[3] = new TextureRegion(reflectDmgSprite,576, 0,192,192);
        reflectDmgFrame[4] = new TextureRegion(reflectDmgSprite,768, 0,192,192);
        reflectDmgFrame[5] = new TextureRegion(reflectDmgSprite,0, 192,192,192);
        reflectDmgFrame[6] = new TextureRegion(reflectDmgSprite,192, 192,192,192);
        reflectDmgFrame[7] = new TextureRegion(reflectDmgSprite,384, 192,192,192);
        reflectDmgFrame[8] = new TextureRegion(reflectDmgSprite,576, 192,192,192);
        reflectDmgFrame[9] = new TextureRegion(reflectDmgSprite,768, 192,192,192);
        reflectDmgFrame[10] = new TextureRegion(reflectDmgSprite,0, 384,192,192);
        reflectDmgFrame[11] = new TextureRegion(reflectDmgSprite,192, 384,192,192);

        barrierFrame[0] = new TextureRegion(barrierSprite,0, 0,192,192);
        barrierFrame[1] = new TextureRegion(barrierSprite,192, 0,192,192);
        barrierFrame[2] = new TextureRegion(barrierSprite,384, 0,192,192);
        barrierFrame[3] = new TextureRegion(barrierSprite,576, 0,192,192);
        barrierFrame[4] = new TextureRegion(barrierSprite,768, 0,192,192);
        barrierFrame[5] = new TextureRegion(barrierSprite,0, 192,192,192);
        barrierFrame[6] = new TextureRegion(barrierSprite,192, 192,192,192);
        barrierFrame[7] = new TextureRegion(barrierSprite,384, 192,192,192);
        barrierFrame[8] = new TextureRegion(barrierSprite,576, 192,192,192);
        barrierFrame[9] = new TextureRegion(barrierSprite,768, 192,192,192);
        barrierFrame[10] = new TextureRegion(barrierSprite,0, 384,192,192);
        barrierFrame[11] = new TextureRegion(barrierSprite,192, 384,192,192);
        barrierFrame[12] = new TextureRegion(barrierSprite,384, 384,192,192);
        barrierFrame[13] = new TextureRegion(barrierSprite,576, 384,192,192);
        barrierFrame[14] = new TextureRegion(barrierSprite,768, 384,192,192);
        barrierFrame[15] = new TextureRegion(barrierSprite,0, 576,192,192);

        tauntFrame[0] = new TextureRegion(tauntSprite,0, 0,192,192);
        tauntFrame[1] = new TextureRegion(tauntSprite,192, 0,192,192);
        tauntFrame[2] = new TextureRegion(tauntSprite,384, 0,192,192);
        tauntFrame[3] = new TextureRegion(tauntSprite,576, 0,192,192);
        tauntFrame[4] = new TextureRegion(tauntSprite,768, 0,192,192);
        tauntFrame[5] = new TextureRegion(tauntSprite,0, 192,192,192);
        tauntFrame[6] = new TextureRegion(tauntSprite,192, 192,192,192);
        tauntFrame[7] = new TextureRegion(tauntSprite,384, 192,192,192);
        tauntFrame[8] = new TextureRegion(tauntSprite,576, 192,192,192);
        tauntFrame[9] = new TextureRegion(tauntSprite,768, 192,192,192);
        tauntFrame[10] = new TextureRegion(tauntSprite,0, 384,192,192);
        tauntFrame[11] = new TextureRegion(tauntSprite,192, 384,192,192);
        tauntFrame[12] = new TextureRegion(tauntSprite,384, 384,192,192);
        tauntFrame[13] = new TextureRegion(tauntSprite,576, 384,192,192);
        tauntFrame[14] = new TextureRegion(tauntSprite,768, 384,192,192);
        tauntFrame[15] = new TextureRegion(tauntSprite,0, 576,192,192);
        tauntFrame[16] = new TextureRegion(tauntSprite,192, 576,192,192);
        tauntFrame[17] = new TextureRegion(tauntSprite,384, 576,192,192);
        tauntFrame[18] = new TextureRegion(tauntSprite,576, 576,192,192);
        tauntFrame[19] = new TextureRegion(tauntSprite,768, 576,192,192);

        heroAttackFrame[0] = new TextureRegion(heroAttackSprite,0, 0,192,197);
        heroAttackFrame[1] = new TextureRegion(heroAttackSprite,192, 0,192,197);
        heroAttackFrame[2] = new TextureRegion(heroAttackSprite,384, 0,192,197);
        heroAttackFrame[3] = new TextureRegion(heroAttackSprite,576, 0,192,197);
        heroAttackFrame[4] = new TextureRegion(heroAttackSprite,768, 0,192,197);
        heroAttackFrame[5] = new TextureRegion(heroAttackSprite,0, 197,192,197);
        heroAttackFrame[6] = new TextureRegion(heroAttackSprite,192, 197,192,197);
        heroAttackFrame[7] = new TextureRegion(heroAttackSprite,384, 197,192,197);
        heroAttackFrame[8] = new TextureRegion(heroAttackSprite,576, 197,192,197);
        heroAttackFrame[9] = new TextureRegion(heroAttackSprite,768, 197,192,197);
        heroAttackFrame[10] = new TextureRegion(heroAttackSprite,0, 197,192,197);
        heroAttackFrame[11] = new TextureRegion(heroAttackSprite,192, 394,192,197);

        tigerAttackFrame[0] = new TextureRegion(tigerAttackSprite,0, 0,192,192);
        tigerAttackFrame[1] = new TextureRegion(tigerAttackSprite,192, 0,192,192);
        tigerAttackFrame[2] = new TextureRegion(tigerAttackSprite,384, 0,192,192);
        tigerAttackFrame[3] = new TextureRegion(tigerAttackSprite,576, 0,192,192);
        tigerAttackFrame[4] = new TextureRegion(tigerAttackSprite,768, 0,192,192);
        tigerAttackFrame[5] = new TextureRegion(tigerAttackSprite,0, 192,192,192);
        tigerAttackFrame[6] = new TextureRegion(tigerAttackSprite,192, 192,192,192);

        invisFrame[0] = new TextureRegion(invisSprite,0, 0,192,197);
        invisFrame[1] = new TextureRegion(invisSprite,192, 0,192,197);
        invisFrame[2] = new TextureRegion(invisSprite,384, 0,192,197);
        invisFrame[3] = new TextureRegion(invisSprite,576, 0,192,197);
        invisFrame[4] = new TextureRegion(invisSprite,768, 0,192,197);
        invisFrame[5] = new TextureRegion(invisSprite,0, 197,192,197);
        invisFrame[6] = new TextureRegion(invisSprite,192, 197,192,197);
        invisFrame[7] = new TextureRegion(invisSprite,384, 197,192,197);
        invisFrame[8] = new TextureRegion(invisSprite,576, 197,192,197);
        invisFrame[9] = new TextureRegion(invisSprite,768, 197,192,197);
        invisFrame[10] = new TextureRegion(invisSprite,0, 197,192,197);
        invisFrame[11] = new TextureRegion(invisSprite,192, 394,192,197);

        position = new Vector2[MAX_PARTICLES];
        type = new Type[MAX_PARTICLES];
        for(int i = 0; i < MAX_PARTICLES; i++){
            type[i] = Type.NONE;
            position[i] = new Vector2();
            lifeTime[i] = MAX_PARTICLES;
        }
    }

    public void dispose(){
        defBuffSprite.dispose();
    }

    public int spawn(Type t){
        if(t== null) return -1;
        int i = 1;
        for(int free = 0; free < MAX_PARTICLES; free++){
            if(type[free] == Type.NONE){
                i = free;
                break;
            }
        }
        if (i < 0) return -1;
        type[i] = t;
        position[i] = new Vector2(0,0);
        lifeTime[i] = PARTICLE_LIFETIME;
        return i;
    }

    public void update(float deltaTime){
        for(int i = 0; i <  MAX_PARTICLES; i++){
            if(type[i] == Type.NONE){
                continue;
            }
            else if(lifeTime[i] <= 0){
                type[i] = Type.NONE;
                continue;
            }
            else{
                lifeTime[i] -= deltaTime;
            }
        }

    }

    public void render(SpriteBatch batch){
        for(int i = 0 ; i< MAX_PARTICLES; i++){
            if(type[i] == Type.NONE)
                continue;
            else{
                int frameLength = 0;
                TextureRegion[] texture = null;
                int width = 0;
                int height = 0;
                if (type[i] == Type.DAMAGE_BUFF){
                    frameLength = damageBuffFrame.length;
                    texture = damageBuffFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.DEF_BUFF){
                    frameLength = defBuffFrame.length;
                    texture = defBuffFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.ENTANGLE){
                    frameLength = entangleFrame.length;
                    texture = entangleFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.COOLDOWN){
                    frameLength = cooldownFrame.length;
                    texture = cooldownFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.TAUNT){
                    frameLength = tauntFrame.length;
                    texture = tauntFrame;
                    width = (int)(texture[0].getRegionWidth() * 1.8);
                    height = (int)(texture[0].getRegionHeight() * 1.8);
                }
                else if(type[i] == Type.GUST){
                    frameLength = gustFrame.length;
                    texture = gustFrame;
                    width = texture[0].getRegionWidth() *3;
                    height = texture[0].getRegionHeight()*3;
                }
                else if(type[i] == Type.FIRENOVA){
                    frameLength = firenovaFrame.length;
                    texture = firenovaFrame;
                    width = texture[0].getRegionWidth()*3;
                    height = texture[0].getRegionHeight()*3;
                }
                else if(type[i] == Type.EARTHQUAKE){
                    frameLength = earthquakeFrame.length;
                    texture = earthquakeFrame;
                    width = texture[0].getRegionWidth()*3;
                    height = texture[0].getRegionHeight()*3;
                }
                else if(type[i] == Type.TIDECALLING){
                    frameLength = tidecallingFrame.length;
                    texture = tidecallingFrame;
                    width = texture[0].getRegionWidth()*3;
                    height = texture[0].getRegionHeight()*3;
                }
                else if(type[i] == Type.HEAL){
                    frameLength = healFrame.length;
                    texture = healFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.LEECH){
                    frameLength = leechFrame.length;
                    texture = leechFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.REVIVE){
                    frameLength = reviveFrame.length;
                    texture = reviveFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if(type[i] == Type.STUN){
                    frameLength = stunFrame.length;
                    texture = stunFrame;
                    width = texture[0].getRegionWidth()*2;
                    height = texture[0].getRegionHeight()*2;
                }
                else if(type[i] == Type.SILENCED){
                    frameLength = silencedFrame.length;
                    texture = silencedFrame;
                    width = texture[0].getRegionWidth()*2;
                    height = texture[0].getRegionHeight()*2;
                }
                else if (type[i] == Type.BARRIER) {
                    frameLength = barrierFrame.length;
                    texture = barrierFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if (type[i] == Type.REFLECTION) {
                    frameLength = reflectDmgFrame.length;
                    texture = reflectDmgFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if (type[i] == Type.HEROATTACK) {
                    frameLength = heroAttackFrame.length;
                    texture = heroAttackFrame;
                    width = texture[0].getRegionWidth()*2;
                    height = texture[0].getRegionHeight()*2;
                }
                else if (type[i] == Type.TIGERATTACK) {
                    frameLength = tigerAttackFrame.length;
                    texture = tigerAttackFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() * 1.5);
                }
                else if (type[i] == Type.INVIS) {
                    frameLength = invisFrame.length;
                    texture = invisFrame;
                    width = (int) (texture[0].getRegionWidth() * 1.5);
                    height = (int) (texture[0].getRegionHeight() *1.5);
                }

                int frameNo = (int) (lifeTime[i] / (PARTICLE_LIFETIME)
                        * frameLength);

                if(frameNo > -1 && frameNo < frameLength){
                    batch.begin();
                    batch.draw(texture[texture.length -frameNo-1], position[i].x, position[i].y,width,height);
                    batch.end();
                }
            }
        }
    }

    public Vector2[] getPosition() {
        return position;
    }

}
