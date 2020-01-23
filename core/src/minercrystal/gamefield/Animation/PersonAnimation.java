package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class PersonAnimation {

    private Array<Texture> minerWalk, minerDigBlock1, minerDigBlock2, minerDigBlock3, minerJump;
    private Texture minerStayDig;
    float animationDigAccumulator = 0, animationWalkAccumulator = 0, animationJumpAccumulator = 0, personWidth = 187, personHeight = 210;
    int Index = 0;
    private boolean isBlow = false, isCanBlow = false, isReverse = false;
    static final float animationDigDelay = 0.16f, animationWalkDelay = 0.14f, animationJumpDelay = 0.1f;

    public PersonAnimation(boolean isLeft) {
        minerWalk = new Array<>();
        minerStayDig = new Texture("minerAnimation/minerDig/mStay.png");
        minerDigBlock1 = new Array<>();
        minerDigBlock2 = new Array<>();
        minerDigBlock3 = new Array<>();
        minerJump = new Array<>();
        if (isLeft) personWidth *= (-1); // if mine turn left
        minerJump.add(new Texture("minerAnimation/minerWalk/" + 2 + ".png"));
        for (int i = 1; i <= 3; i++)
            minerWalk.add(new Texture("minerAnimation/minerWalk/" + i + ".png"));
        for (int i = 1; i < 9; i++)
            minerDigBlock3.add(new Texture("minerAnimation/minerDig/Block1/" + i + ".png"));
        for (int i = 1; i < 7; i++)
            minerDigBlock2.add(new Texture("minerAnimation/minerDig/Block2/" + i + ".png"));
        for (int i = 1; i < 10; i++)
            minerDigBlock1.add(new Texture("minerAnimation/minerDig/Block2/" + i + ".png"));
    }

    public void drawJump(SpriteBatch spriteBatch, float delta, Vector2 person) {
        animationJumpAccumulator += delta;
        if (animationJumpAccumulator > animationJumpDelay) {
            Index = 0;
            animationJumpAccumulator = 0;
        }
        spriteBatch.draw(minerJump.get(Index), person.x, person.y, personWidth, personHeight);
    }

    public void drawWalk(SpriteBatch spriteBatch, float delta, Vector2 person) {
        animationWalkAccumulator += delta;
        if (animationWalkAccumulator > animationWalkDelay) {
            if (!isReverse)
                Index++;
            else {
                Index--;
                if (Index == -1) {
                    isReverse = false;
                    Index = 1;
                }
            }
            animationWalkAccumulator = 0;
            if (Index == 2) {
                isReverse = true;
            }
        }
        spriteBatch.draw(minerWalk.get(Index), person.x, person.y, personWidth, personHeight);
    }

    public void drawDigBlock1(SpriteBatch spriteBatch, float delta, Vector2 person) {
        animationDigAccumulator += delta;
        if (animationDigAccumulator > animationDigDelay / minerDigBlock1.size) {
            if (!isReverse)
                Index++;
            else {
                Index--;
                if (Index == -1) {
                    isReverse = false;
                    Index = 0;
                }
            }
            animationDigAccumulator = 0;
            if (Index >= minerDigBlock1.size - 1) {
                isReverse = true;
                if (isBlow) {
                    isCanBlow = true;
                    isBlow = false;
                }
            }
        }
        spriteBatch.draw(minerStayDig, person.x, person.y, personWidth, personHeight);
        spriteBatch.draw(minerDigBlock1.get(Index), person.x, person.y, personWidth, personHeight);

    }

    public void drawDigBlock2(SpriteBatch spriteBatch, float delta, Vector2 person) {
        animationDigAccumulator += delta;
        if (animationDigAccumulator > animationDigDelay / minerDigBlock2.size) {
            if (!isReverse)
                Index++;
            else {
                Index--;
                if (Index == -1) {
                    isReverse = false;
                    Index = 0;
                }
            }
            animationDigAccumulator = 0;
            if (Index >= minerDigBlock2.size - 1) {
                isReverse = true;
                if (isBlow) {
                    isCanBlow = true;
                    isBlow = false;
                }
            }
        }
        spriteBatch.draw(minerStayDig, person.x, person.y, personWidth, personHeight);
        spriteBatch.draw(minerDigBlock2.get(Index), person.x, person.y, personWidth, personHeight);

    }

    public void drawDigBlock3(SpriteBatch spriteBatch, float delta, Vector2 person) {
        animationDigAccumulator += delta;
        if (animationDigAccumulator > animationDigDelay / minerDigBlock3.size) {
            if (!isReverse)
                Index++;
            else {
                Index--;
                if (Index == -1) {
                    isReverse = false;
                    Index = 0;
                }
            }
            animationDigAccumulator = 0;
            if (Index >= minerDigBlock3.size-1) {
                isReverse = true;
                if (isBlow) {
                    isCanBlow = true;
                    isBlow = false;
                }
            }
        }
        spriteBatch.draw(minerStayDig, person.x, person.y, personWidth, personHeight);
        spriteBatch.draw(minerDigBlock3.get(Index), person.x, person.y, personWidth, personHeight);

    }

    public void setIndexToNULL() {
        Index = 0;
        isReverse = false;
    }

    public void setBlow(boolean blow) {
        isBlow = blow;
    }

    public boolean isCanBlow() {
        return isCanBlow;
    }

    public boolean isBlow() {
        return isBlow;
    }


}
