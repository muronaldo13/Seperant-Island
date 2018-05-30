package com.serpent.island;

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
    enum Type {NONE, DAMAGE_BUFF, HEAL, DEF_BUFF, COOLDOWN, ENTANGLE, LEECH, TAUNT, INVIS, REVIVE, STUN, SILENCED, DAMAGE_CARD }
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
    private Texture damageCardSprite;

    private TextureRegion[] defBuffFrame = new TextureRegion[10];
    private TextureRegion[] cooldownFrame = new TextureRegion[10];
    private TextureRegion[] damageBuffFrame = new TextureRegion[10];
    private TextureRegion[] entangleFrame = new TextureRegion[6];
    private TextureRegion[] healFrame = new TextureRegion[6];
    private TextureRegion[] tauntFrame = new TextureRegion[6];
    private TextureRegion[] leechFrame = new TextureRegion[6];
    private TextureRegion[] invisFrame = new TextureRegion[6];
    private TextureRegion[] reviveFrame = new TextureRegion[6];
    private TextureRegion[] stunFrame = new TextureRegion[6];
    private TextureRegion[] silencedFrame = new TextureRegion[6];
    private TextureRegion[] damageCardFrame = new TextureRegion[6];
    private Vector2[] position;
    private float[] lifeTime = new float[MAX_PARTICLES];
    private Type[] type ;

    public void init(){

        //TODO:insert image
        defBuffSprite = new Texture("");
        damageBuffSprite = new Texture("");
        healSprite = new Texture("");
        entangleSprite = new Texture("");
        tauntSprite = new Texture("");
        defBuffSprite = new Texture("");
        leechSprite = new Texture("");
        invisSprite = new Texture("");
        reviveSprite = new Texture("");
        stunSprite = new Texture("");
        silencedSprite = new Texture("");
        damageCardSprite = new Texture("");
        cooldownSprite = new Texture("");
        //TODO: change x, y , width, height
        defBuffFrame[0] = new TextureRegion(defBuffSprite,2,2,87,87);
        defBuffFrame[1] = new TextureRegion(defBuffSprite,94,2,87,87);
        defBuffFrame[2] = new TextureRegion(defBuffSprite,186,2,87,87);
        defBuffFrame[3] = new TextureRegion(defBuffSprite,278,2,87,87);
        defBuffFrame[4] = new TextureRegion(defBuffSprite,370,2,87,87);
        defBuffFrame[5] = new TextureRegion(defBuffSprite,2,94,87,87);
        defBuffFrame[6] = new TextureRegion(defBuffSprite,94,94,87,87);
        defBuffFrame[7] = new TextureRegion(defBuffSprite,186,94,87,87);
        defBuffFrame[8] = new TextureRegion(defBuffSprite,278,94,87,87);
        defBuffFrame[9] = new TextureRegion(defBuffSprite,370,94,87,87);

        damageBuffFrame[0] = new TextureRegion(damageBuffSprite,2, 306,45,45);
        damageBuffFrame[1] = new TextureRegion(damageBuffSprite,52, 306,45,45);
        damageBuffFrame[2] = new TextureRegion(damageBuffSprite,102, 306,45,45);
        damageBuffFrame[3] = new TextureRegion(damageBuffSprite,152, 306,45,45);
        damageBuffFrame[4] = new TextureRegion(damageBuffSprite,202, 306,45,45);
        damageBuffFrame[5] = new TextureRegion(damageBuffSprite,252, 306,45,45);

        cooldownFrame[0] = new TextureRegion(cooldownSprite,2, 306,45,45);
        cooldownFrame[1] = new TextureRegion(cooldownSprite,52, 306,45,45);
        cooldownFrame[2] = new TextureRegion(cooldownSprite,102, 306,45,45);
        cooldownFrame[3] = new TextureRegion(cooldownSprite,152, 306,45,45);
        cooldownFrame[4] = new TextureRegion(cooldownSprite,202, 306,45,45);
        cooldownFrame[5] = new TextureRegion(cooldownSprite,252, 306,45,45);

        entangleFrame[0] = new TextureRegion(entangleSprite,2, 306,45,45);
        entangleFrame[1] = new TextureRegion(entangleSprite,52, 306,45,45);
        entangleFrame[2] = new TextureRegion(entangleSprite,102, 306,45,45);
        entangleFrame[3] = new TextureRegion(entangleSprite,152, 306,45,45);
        entangleFrame[4] = new TextureRegion(entangleSprite,202, 306,45,45);
        entangleFrame[5] = new TextureRegion(entangleSprite,252, 306,45,45);

        healFrame[0] = new TextureRegion(healSprite,2, 306,45,45);
        healFrame[1] = new TextureRegion(healSprite,52, 306,45,45);
        healFrame[2] = new TextureRegion(healSprite,102, 306,45,45);
        healFrame[3] = new TextureRegion(healSprite,152, 306,45,45);
        healFrame[4] = new TextureRegion(healSprite,202, 306,45,45);
        healFrame[5] = new TextureRegion(healSprite,252, 306,45,45);

        tauntFrame[0] = new TextureRegion(tauntSprite,2, 306,45,45);
        tauntFrame[1] = new TextureRegion(tauntSprite,52, 306,45,45);
        tauntFrame[2] = new TextureRegion(tauntSprite,102, 306,45,45);
        tauntFrame[3] = new TextureRegion(tauntSprite,152, 306,45,45);
        tauntFrame[4] = new TextureRegion(tauntSprite,202, 306,45,45);
        tauntFrame[5] = new TextureRegion(tauntSprite,252, 306,45,45);

        leechFrame[0] = new TextureRegion(leechSprite,2, 306,45,45);
        leechFrame[1] = new TextureRegion(leechSprite,52, 306,45,45);
        leechFrame[2] = new TextureRegion(leechSprite,102, 306,45,45);
        leechFrame[3] = new TextureRegion(leechSprite,152, 306,45,45);
        leechFrame[4] = new TextureRegion(leechSprite,202, 306,45,45);
        leechFrame[5] = new TextureRegion(leechSprite,252, 306,45,45);

        invisFrame[0] = new TextureRegion(invisSprite,2, 306,45,45);
        invisFrame[1] = new TextureRegion(invisSprite,52, 306,45,45);
        invisFrame[2] = new TextureRegion(invisSprite,102, 306,45,45);
        invisFrame[3] = new TextureRegion(invisSprite,152, 306,45,45);
        invisFrame[4] = new TextureRegion(invisSprite,202, 306,45,45);
        invisFrame[5] = new TextureRegion(invisSprite,252, 306,45,45);

        reviveFrame[0] = new TextureRegion(reviveSprite,2, 306,45,45);
        reviveFrame[1] = new TextureRegion(reviveSprite,52, 306,45,45);
        reviveFrame[2] = new TextureRegion(reviveSprite,102, 306,45,45);
        reviveFrame[3] = new TextureRegion(reviveSprite,152, 306,45,45);
        reviveFrame[4] = new TextureRegion(reviveSprite,202, 306,45,45);
        reviveFrame[5] = new TextureRegion(reviveSprite,252, 306,45,45);
        
        stunFrame[0] = new TextureRegion(stunSprite,2, 306,45,45);
        stunFrame[1] = new TextureRegion(stunSprite,52, 306,45,45);
        stunFrame[2] = new TextureRegion(stunSprite,102, 306,45,45);
        stunFrame[3] = new TextureRegion(stunSprite,152, 306,45,45);
        stunFrame[4] = new TextureRegion(stunSprite,202, 306,45,45);
        stunFrame[5] = new TextureRegion(stunSprite,252, 306,45,45);

        silencedFrame[0] = new TextureRegion(silencedSprite,2, 306,45,45);
        silencedFrame[1] = new TextureRegion(silencedSprite,52, 306,45,45);
        silencedFrame[2] = new TextureRegion(silencedSprite,102, 306,45,45);
        silencedFrame[3] = new TextureRegion(silencedSprite,152, 306,45,45);
        silencedFrame[4] = new TextureRegion(silencedSprite,202, 306,45,45);
        silencedFrame[5] = new TextureRegion(silencedSprite,252, 306,45,45);

        damageCardFrame[0] = new TextureRegion(damageCardSprite,2, 306,45,45);
        damageCardFrame[1] = new TextureRegion(damageCardSprite,52, 306,45,45);
        damageCardFrame[2] = new TextureRegion(damageCardSprite,102, 306,45,45);
        damageCardFrame[3] = new TextureRegion(damageCardSprite,152, 306,45,45);
        damageCardFrame[4] = new TextureRegion(damageCardSprite,202, 306,45,45);
        damageCardFrame[5] = new TextureRegion(damageCardSprite,252, 306,45,45);

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
                } else if(type[i] == Type.DAMAGE_CARD){
                    frameLength = damageCardFrame.length;
                    texture = damageCardFrame;
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
                    batch.draw(texture[texture.length -frameNo-1], position[i].x,position[i].y);
                }
            }
        }
    }

    public Vector2[] getPosition() {
        return position;
    }

}
