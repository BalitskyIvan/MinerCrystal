package minercrystal.gamefield.WorkScreens.Tools.Frames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.I18NBundle;

import minercrystal.gamefield.AdsController;
import minercrystal.gamefield.Buttons.FrameRedButton;
import minercrystal.gamefield.FrameInterface;

public class CoinPlusFrame implements FrameInterface {

    private Texture frame, closeBtn1, closeBtn2;
    private boolean isFrameActive = false, isCloseButtonPressed, isFrameDeploy = false, isFrameDestroyDeploy = false;
    private Rectangle staticFrameRectangle, closeBtn, frameRectangle;
    private float ACCELERATION = 1.3f, VELOCITY = 0.5f;
    private static final float INDENT = 37, HW_CLOSEBUTTON = 35, STATIC_VELOCITY = 0.5f, DELTA_FRAME = 60;
    private FrameRedButton frameRedButton;
    AdsController adsController;
    public CoinPlusFrame(Rectangle frameRectangle, BitmapFont bitmapFont, I18NBundle myBundle, AdsController adsController) {
        this.frameRectangle = new Rectangle(frameRectangle.x + DELTA_FRAME, frameRectangle.y + DELTA_FRAME, frameRectangle.width - DELTA_FRAME * 2, frameRectangle.height - DELTA_FRAME * 2);
        this.staticFrameRectangle = frameRectangle;
        this.adsController = adsController;
        frame = new Texture("tools/Frame.png");
        closeBtn1 = new Texture("Buttons/StandartBtn/closeBtn1.png");
        closeBtn2 = new Texture("Buttons/StandartBtn/closeBtn2.png");
        frameRedButton = new FrameRedButton(bitmapFont, myBundle, new Rectangle(100, 200, 256, 128));
        closeBtn = new Rectangle(frameRectangle.x + frameRectangle.width - INDENT, frameRectangle.y + frameRectangle.height - INDENT, HW_CLOSEBUTTON, HW_CLOSEBUTTON);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        spriteBatch.begin();
        if (isFrameActive) {
            spriteBatch.draw(frame, frameRectangle.x, frameRectangle.y, frameRectangle.width, frameRectangle.height);
            if (isCloseButtonPressed)
                spriteBatch.draw(closeBtn2, closeBtn.x, closeBtn.y, closeBtn.width, closeBtn.height);
            else
                spriteBatch.draw(closeBtn1, closeBtn.x, closeBtn.y, closeBtn.width, closeBtn.height);
            spriteBatch.end();
            frameRedButton.render(delta, spriteBatch, "PLUS");
            spriteBatch.begin();
            if(frameRedButton.isPlayDead()){
                frameRedButton.setPlayDeadFalse();
                action();
            }
        } else {
            if (isFrameDeploy) {
                spriteBatch.draw(frame, frameRectangle.x, frameRectangle.y, frameRectangle.width, frameRectangle.height);
                if (frameRectangle.width < staticFrameRectangle.width) {
                    VELOCITY *= ACCELERATION;
                    frameRectangle.x -= VELOCITY;
                    frameRectangle.y -= VELOCITY;
                    frameRectangle.width += VELOCITY * 2;
                    frameRectangle.height += VELOCITY * 2;
                } else {
                    isFrameActive = true;
                    isFrameDeploy = false;
                    VELOCITY = STATIC_VELOCITY;
                }
            }
            if (isFrameDestroyDeploy){
                spriteBatch.draw(frame, frameRectangle.x, frameRectangle.y, frameRectangle.width, frameRectangle.height);
                if (frameRectangle.width > staticFrameRectangle.width - DELTA_FRAME) {
                    VELOCITY *= ACCELERATION;
                    frameRectangle.x += VELOCITY;
                    frameRectangle.y += VELOCITY;
                    frameRectangle.width -= VELOCITY * 2;
                    frameRectangle.height -= VELOCITY * 2;
                } else {
                    isFrameDestroyDeploy = false;
                    isFrameActive = false;
                    isCloseButtonPressed = false;

                    VELOCITY = STATIC_VELOCITY;
                }
            }
        }
        spriteBatch.end();
    }

    private void action(){
        //  adsController.showBannerAd();
    }
    @Override
    public void touchDown(float x, float y) {
        if (closeBtn.contains(x, y))
            closeFrame();
        frameRedButton.isContains((int)x,(int) y);
    }

    @Override
    public void touchDragged(float x, float y) {
        if (!closeBtn.contains(x, y))
            isCloseButtonPressed = false;
    }

    @Override
    public void touchUP(float x, float y) {
        frameRedButton.reset();
        isCloseButtonPressed = false;

    }

    @Override
    public void setFrameDeploy(boolean frameDeploy) {
        isFrameDeploy = frameDeploy;

    }

    @Override
    public void closeFrame() {
        isCloseButtonPressed = true;
        isFrameDestroyDeploy = true;
        isFrameActive = false;
    }

    @Override
    public boolean isFrameActive() {
        return isFrameActive;
    }

    @Override
    public boolean isRedBTNPressed() {
        return false;
    }
}
