package minercrystal.gamefield.WorkScreens.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.ActiveFrameInterface;
import minercrystal.gamefield.FrameInterface;
import minercrystal.gamefield.ScreenWindows.SpinWheelWindow;


public class WorkButton implements ActiveFrameInterface {
    private Array<Texture> btnTextures;
    private Texture animFrame;
    private Rectangle btnRectangle;
    private Array<Rectangle> animFrames;
    private Array<Float> splashFrames;
    private boolean isActive, isPressed = false, isReverse = false, isDelayActive = false, isButtonDeploy = false;
    private static final float DELAY_FRAME_SPAWN = 0.35f, PAUSE_TIME_TO_SPAWN = 4, ACTIVE_TIMER = 2, STATIC_OPTION_DELTA = 3, Timer_Action = 0.05f;
    private float TIMER_ACCUMULATOR = 0, splashTimer = 1.50f, DELAY_FRAME_SPAWN_ACCUMULATOR = 0, PAUSE_TIME_TO_SPAWN_ACCUMULATOR = 0, ACTIVE_TIMER_ACCUMULATOR = 0, optionDelta = 0, IndexSize = 0.45f;
    private int ACTIVATION_INDEX = 0;

    public WorkButton(String state, float X, float Y, boolean isActive) {
        this.isActive = isActive;
        btnRectangle = new Rectangle(X, Y, 90, 90);
        btnTextures = new Array<>();
        animFrames = new Array<>();
        splashFrames = new Array<>();
        initOption(state);
    }


    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        if (isButtonDeploy) {
            spriteBatch.begin();
            if(!isReverse) {
                if (ACTIVATION_INDEX <= 14) {
                    TIMER_ACCUMULATOR += delta;
                    if (TIMER_ACCUMULATOR > Timer_Action) {
                        TIMER_ACCUMULATOR = 0;
                        ACTIVATION_INDEX++;
                        isDelayActive = false;
                        ACTIVE_TIMER_ACCUMULATOR = 1.5f;
                        DELAY_FRAME_SPAWN_ACCUMULATOR = 1;
                    }
                } else {
                    if (isActive)
                        renderActive(spriteBatch, delta);
                    if (isPressed)
                        optionDelta = STATIC_OPTION_DELTA;
                    else
                        optionDelta = 0;

                }
            }else{
                if(ACTIVATION_INDEX > 0){
                    TIMER_ACCUMULATOR += delta;
                    if (TIMER_ACCUMULATOR > Timer_Action/2) {
                        TIMER_ACCUMULATOR = 0;
                        ACTIVATION_INDEX--;

                    }
                }else{
                    ACTIVATION_INDEX = 0;
                    isReverse = false;
                    isButtonDeploy = false;
                }
            }
            spriteBatch.draw(btnTextures.get(ACTIVATION_INDEX), btnRectangle.x - optionDelta, btnRectangle.y - optionDelta, btnRectangle.width + optionDelta * 2, btnRectangle.height + optionDelta * 2);
            spriteBatch.end();
        }
    }

    private void renderActive(SpriteBatch spriteBatch, float delta) {
        if (!isDelayActive) {
            ACTIVE_TIMER_ACCUMULATOR += delta;
            if (ACTIVE_TIMER_ACCUMULATOR > ACTIVE_TIMER) {
                ACTIVE_TIMER_ACCUMULATOR = 0;
                DELAY_FRAME_SPAWN_ACCUMULATOR = 0;
                isDelayActive = true;
            }
            DELAY_FRAME_SPAWN_ACCUMULATOR += delta;
            if (DELAY_FRAME_SPAWN_ACCUMULATOR > DELAY_FRAME_SPAWN) {
                DELAY_FRAME_SPAWN_ACCUMULATOR = 0;
                addAnimFrame();
            }

        } else {
            PAUSE_TIME_TO_SPAWN_ACCUMULATOR += delta;
            if (PAUSE_TIME_TO_SPAWN_ACCUMULATOR > PAUSE_TIME_TO_SPAWN) {
                PAUSE_TIME_TO_SPAWN_ACCUMULATOR = 0;
                isDelayActive = false;
            }
        }
        for (Rectangle r : animFrames) {
            r.x -= IndexSize;
            r.y -= IndexSize;
            r.width += IndexSize * 2;
            r.height += IndexSize * 2;

            splashFrames.set(animFrames.indexOf(r, false), splashFrames.get(animFrames.indexOf(r, false)) - splashTimer * delta);
            Color c = spriteBatch.getColor();
            spriteBatch.end();
            spriteBatch.setColor(c.r, c.g, c.b, splashFrames.get(animFrames.indexOf(r, false)));
            spriteBatch.begin();
            spriteBatch.draw(animFrame, r.x, r.y, r.width, r.height);
            spriteBatch.end();
            spriteBatch.begin();
            spriteBatch.setColor(c.r, c.g, c.b, 1);
        }
        for (Rectangle r : animFrames) {
            if (splashFrames.get(animFrames.indexOf(r, false)) < 0) {
                splashFrames.removeValue(splashFrames.get(animFrames.indexOf(r, false)), false);
                animFrames.removeValue(r, false);
            }
        }
    }

    public Rectangle getBtnRectangle() {
        return btnRectangle;
    }

    public void setButtonDeploy(boolean buttonDeploy) {
        isButtonDeploy = buttonDeploy;
    }
    public boolean isButtonDeploed(){
        return isButtonDeploy;
    }
    public boolean isActive() {
        return isActive;
    }

    private void initOption(String state) {
        switch (state) {
            case "present":
                for (int i = 16; i >= 1; i--) {
                    btnTextures.add(new Texture("tools/presentButton/" + i + ".png"));
                }
                animFrame = new Texture("tools/presentButton/animFrame.png");
                break;
            case "wheel":
                for (int i = 16; i >= 1; i--) {
                    btnTextures.add(new Texture("tools/presentButton/" + i + "r.png"));
                }
                animFrame = new Texture("tools/presentButton/animFrameR.png");
                break;
            default:
                break;
        }
    }

    private void addAnimFrame() {
        animFrames.add(new Rectangle(btnRectangle.x, btnRectangle.y, btnRectangle.width, btnRectangle.height));
        splashFrames.add(1f);
    }

    public boolean touchDown(float X, float Y) {
        if (btnRectangle.contains(X, Y)) {
            setReverse(true);
            return true;
        } else return false;
    }

    public void setReverse(boolean reverse){
        isPressed = true;
        isReverse = true;
        setButtonDeploy(true);
        isDelayActive = false;
        ACTIVE_TIMER_ACCUMULATOR = 1.5f;
        DELAY_FRAME_SPAWN_ACCUMULATOR = 1;
        PAUSE_TIME_TO_SPAWN_ACCUMULATOR = 0;
    }
    public boolean touchDragged(float X, float Y) {
        if (!btnRectangle.contains(X, Y)) {
            isPressed = false;
            return false;
        }
        return true;
    }

    public void touchUp() {
        isPressed = false;
    }


    @Override
    public void setActive(float milliseconds) {

    }

    @Override
    public void changeRect(Rectangle r) {
        this.btnRectangle = r;
    }


}
