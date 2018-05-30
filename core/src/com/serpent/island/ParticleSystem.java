package com.serpent.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



/**
 * Created by toyty004 on 8/5/18.
 */

public class ParticleSystem {
    public static final int MAX_PARTICLES = 128;
    public static final float PARTICLE_LIFETIME = 0.5f;
    enum Type {NONE, DAMAGE_BUFF, HEAL, DEF_BUFF, COOLDOWN, ENTANGLE, LEECH, TAUNT, INVIS, REVIVE, STUN, SILENCED, GUST, FIRENOVA, EARTHQUAKE, TIDECALLING}

    private Texture defBuffSprite;
    private Texture damageBuffSprite;
    private Texture entangleSprite;
    private Texture healSprite;
    private Texture cooldownSprite;
    private Texture tauntSprite;
    private Texture leechSprite;
    private Texture invisSprite;
    private Texture reviveSprite;
    private Texture stunSprite;
    private Texture silencedSprite;
    private Texture gustSprite;
    private Texture firenovaSprite;
    private Texture earthquakeSprite;
    private Texture tidecallingSprite;

    private TextureRegion[] defBuffFrame = new TextureRegion[10];
    private TextureRegion[] cooldownFrame = new TextureRegion[10];
    private TextureRegion[] damageBuffFrame = new TextureRegion[10];
    private TextureRegion[] entangleFrame = new TextureRegion[6];
    private TextureRegion[] healFrame = new TextureRegion[10];
    private TextureRegion[] tauntFrame = new TextureRegion[6];
    private TextureRegion[] leechFrame = new TextureRegion[6];
    private TextureRegion[] invisFrame = new TextureRegion[6];
    private TextureRegion[] reviveFrame = new TextureRegion[6];
    private TextureRegion[] stunFrame = new TextureRegion[14];
    private TextureRegion[] silencedFrame = new TextureRegion[15];
    private TextureRegion[] gustFrame = new TextureRegion[15];
    private TextureRegion[] firenovaFrame = new TextureRegion[10];
    private TextureRegion[] earthquakeFrame = new TextureRegion[18];
    private TextureRegion[] tidecallingFrame = new TextureRegion[14];

    private Vector2[] position;
    private float[] lifeTime = new float[MAX_PARTICLES];
    private Type[] type ;

    public void init(){

        //TODO:insert image
        defBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/defense_buff.png"));
        damageBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/attack_buff.png"));
        healSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png"));
        entangleSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: To be changed
        tauntSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: To be changed
        defBuffSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png"));
        leechSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: TO be changed
        invisSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: To be REMOVED
        reviveSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: TO be changed
        stunSprite = new Texture(Gdx.files.internal("particle_Spritesheet/stun.png"));
        silencedSprite = new Texture(Gdx.files.internal("particle_Spritesheet/silence.png"));
        cooldownSprite = new Texture(Gdx.files.internal("particle_Spritesheet/heal.png")); //TODO: To be changed
        gustSprite = new Texture(Gdx.files.internal("particle_Spritesheet/gust_ss.png"));
        firenovaSprite = new Texture(Gdx.files.internal("particle_Spritesheet/gust_ss.png"));
        earthquakeSprite = new Texture(Gdx.files.internal("particle_Spritesheet/earthquake.png"));
        tidecallingSprite = new Texture(Gdx.files.internal("particle_Spritesheet/tide_calling.png"));

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

        //TODO: change x, y , width, height
        cooldownFrame[0] = new TextureRegion(cooldownSprite,2, 306,45,45);
        cooldownFrame[1] = new TextureRegion(cooldownSprite,52, 306,45,45);
        cooldownFrame[2] = new TextureRegion(cooldownSprite,102, 306,45,45);
        cooldownFrame[3] = new TextureRegion(cooldownSprite,152, 306,45,45);
        cooldownFrame[4] = new TextureRegion(cooldownSprite,202, 306,45,45);
        cooldownFrame[5] = new TextureRegion(cooldownSprite,252, 306,45,45);

        //TODO: change x, y , width, height
        entangleFrame[0] = new TextureRegion(entangleSprite,2, 306,45,45);
        entangleFrame[1] = new TextureRegion(entangleSprite,52, 306,45,45);
        entangleFrame[2] = new TextureRegion(entangleSprite,102, 306,45,45);
        entangleFrame[3] = new TextureRegion(entangleSprite,152, 306,45,45);
        entangleFrame[4] = new TextureRegion(entangleSprite,202, 306,45,45);
        entangleFrame[5] = new TextureRegion(entangleSprite,252, 306,45,45);

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

        //TODO: change x, y , width, height
        tauntFrame[0] = new TextureRegion(tauntSprite,2, 306,45,45);
        tauntFrame[1] = new TextureRegion(tauntSprite,52, 306,45,45);
        tauntFrame[2] = new TextureRegion(tauntSprite,102, 306,45,45);
        tauntFrame[3] = new TextureRegion(tauntSprite,152, 306,45,45);
        tauntFrame[4] = new TextureRegion(tauntSprite,202, 306,45,45);
        tauntFrame[5] = new TextureRegion(tauntSprite,252, 306,45,45);

        //TODO: change x, y , width, height
        leechFrame[0] = new TextureRegion(leechSprite,2, 306,45,45);
        leechFrame[1] = new TextureRegion(leechSprite,52, 306,45,45);
        leechFrame[2] = new TextureRegion(leechSprite,102, 306,45,45);
        leechFrame[3] = new TextureRegion(leechSprite,152, 306,45,45);
        leechFrame[4] = new TextureRegion(leechSprite,202, 306,45,45);
        leechFrame[5] = new TextureRegion(leechSprite,252, 306,45,45);

        //TODO: TO BE REMOVED
        invisFrame[0] = new TextureRegion(invisSprite,2, 306,45,45);
        invisFrame[1] = new TextureRegion(invisSprite,52, 306,45,45);
        invisFrame[2] = new TextureRegion(invisSprite,102, 306,45,45);
        invisFrame[3] = new TextureRegion(invisSprite,152, 306,45,45);
        invisFrame[4] = new TextureRegion(invisSprite,202, 306,45,45);
        invisFrame[5] = new TextureRegion(invisSprite,252, 306,45,45);

        //TODO: change x, y , width, height
        reviveFrame[0] = new TextureRegion(reviveSprite,2, 306,45,45);
        reviveFrame[1] = new TextureRegion(reviveSprite,52, 306,45,45);
        reviveFrame[2] = new TextureRegion(reviveSprite,102, 306,45,45);
        reviveFrame[3] = new TextureRegion(reviveSprite,152, 306,45,45);
        reviveFrame[4] = new TextureRegion(reviveSprite,202, 306,45,45);
        reviveFrame[5] = new TextureRegion(reviveSprite,252, 306,45,45);

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
        earthquakeFrame[17] = new TextureRegion(earthquakeSprite,384, 576,192,192);

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
        batch.begin();
        for(int i = 0 ; i< MAX_PARTICLES; i++){
            if(type[i] == Type.NONE)
                continue;
            else{
                int frameLength = 0;
                TextureRegion[] texture = null;
                if(type[i] == Type.DAMAGE_BUFF){
                    frameLength = damageBuffFrame.length;
                    texture = damageBuffFrame;
                } else if(type[i] == Type.DEF_BUFF){
                    frameLength = defBuffFrame.length;
                    texture = defBuffFrame;
                } else if(type[i] == Type.ENTANGLE){
                    frameLength = entangleFrame.length;
                    texture = entangleFrame;
                } else if(type[i] == Type.COOLDOWN){
                    frameLength = cooldownFrame.length;
                    texture = cooldownFrame;
                }else if(type[i] == Type.TAUNT){
                    frameLength = tauntFrame.length;
                    texture = tauntFrame;
                } else if(type[i] == Type.GUST){
                    frameLength = gustFrame.length;
                    texture = gustFrame;
                } else if(type[i] == Type.FIRENOVA){
                    frameLength = firenovaFrame.length;
                    texture = firenovaFrame;
                } else if(type[i] == Type.EARTHQUAKE){
                    frameLength = earthquakeFrame.length;
                    texture = earthquakeFrame;
                } else if(type[i] == Type.TIDECALLING){
                    frameLength = tidecallingFrame.length;
                    texture = tidecallingFrame;
                } else if(type[i] == Type.HEAL){
                    frameLength = healFrame.length;
                    texture = healFrame;
                } else if(type[i] == Type.INVIS){
                    frameLength = invisFrame.length;
                    texture = invisFrame;
                }else if(type[i] == Type.LEECH){
                    frameLength = leechFrame.length;
                    texture = leechFrame;
                }else if(type[i] == Type.REVIVE){
                    frameLength = reviveFrame.length;
                    texture = reviveFrame;
                }else if(type[i] == Type.STUN){
                    frameLength = stunFrame.length;
                    texture = stunFrame;
                }else if(type[i] == Type.SILENCED){
                    frameLength = silencedFrame.length;
                    texture = silencedFrame;
                }

                int frameNo = (int) (lifeTime[i] / (PARTICLE_LIFETIME)
                        * frameLength);

                if(frameNo > -1 && frameNo < frameLength){
                    Gdx.app.log("Draw", "Drawing,j");
                    batch.draw(texture[texture.length -frameNo-1], position[i].x,position[i].y);
                }
            }
        }
        batch.end();
    }

    public Vector2[] getPosition() {
        return position;
    }

}
