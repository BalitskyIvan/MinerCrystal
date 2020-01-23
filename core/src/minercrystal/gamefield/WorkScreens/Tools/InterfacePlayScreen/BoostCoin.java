package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.Animation.BoostButtonFrame;


public class BoostCoin implements Boost {

    public int COINS_X = 1;
    private final static float TIMER = 6f;
    private Rectangle r;
    private int count, INDEX = 1;
    private boolean isDeploy, isGOTo = false, isReverse = false, isTimerDeploy;
    private BoostButtonFrame boostButtonFrame;
    private Array<Texture> buttonTexture;
    private float accumulator = 0, yGoTo, deployTimer = 0;
    private static final float deltaDeploy = 0.065f;
    private Array<Texture> coinTexure;
    private Texture flag;
    private BitmapFont bitmapFont;
    public BoostCoin(int count, Rectangle r, Array<Texture> buttonTexture, BitmapFont font) {
        this.r = r;
        this.count = count;
        this.bitmapFont = font;
        this.buttonTexture = buttonTexture;
        isDeploy = true;
        boostButtonFrame = new BoostButtonFrame(r.width, r.height);
        coinTexure = new Array<>();
        for (int i = 2; i <= 5; i++)
            coinTexure.add(new Texture("boost/x" + i + ".png"));
        flag = new Texture("boost/boostButton/flag.png");

    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        boostButtonFrame.render(spriteBatch, delta);
        spriteBatch.draw(buttonTexture.get(INDEX), r.x, r.y, r.width, r.height);
        spriteBatch.draw(flag, r.x + 40, r.y + 47, 25, 25);
        bitmapFont.draw(spriteBatch, String.valueOf(count), r.x + 48, r.y + 63);
        if (isGOTo) {
            if (r.y > yGoTo) {
                r.y -= 105 * delta;
                boostButtonFrame.changePos(r.y);
            } else {
                isGOTo = false;
            }
        }
        if(isTimerDeploy){
            deployTimer -= delta;
            if(deployTimer < 0){
                isTimerDeploy = false;
                isDeploy = true;
                isReverse = true;
            }
        }
        if (isDeploy) {
            accumulator += delta;
            if (accumulator > deltaDeploy) {
                accumulator = 0;
                if (!isReverse) {
                    INDEX++;
                    if (INDEX == 11) {
                        isDeploy = false;
                    }
                } else if (!boostButtonFrame.isActive()) {
                    INDEX--;
                    if (INDEX == 0) {
                        isDeploy = false;
                        count = -1;
                    }
                }

            }
        } else {
            spriteBatch.draw(coinTexure.get(COINS_X - 1), r.x, r.y, r.width, r.height);

        }
        if (boostButtonFrame.isActive())
            spriteBatch.draw(coinTexure.get(COINS_X - 1), r.getX(), r.getY(), r.getWidth(), r.getHeight());
        else
            COINS_X = 1;

    }

    public int getCoinBoost() {
        return COINS_X;
    }

    @Override
    public boolean touchDown(float X, float Y) {
        if (COINS_X < 4)
            if (r.contains(X, Y)) {
               setActive(TIMER, getCount() - 1);
            }
        if (getCount() == 0) {
            isReverse = true;
            isDeploy = true;
            return true;
        } else
            return false;
    }

    @Override
    public void destroy() {

    }
    @Override
    public Rectangle getElementRect(String s) {
        return r;
    }

    @Override
    public void plusElement(String element, int count) {
        if(element == "delete"){
            deployTimer = 0.8f;
            isTimerDeploy = true;
        }else
            setActive(0.8f, getCount()+count);
    }
    private void setActive(float time, int count){
        this.count = count;
        deployTimer = time;
        isTimerDeploy = true;
        COINS_X += 1;
        boostButtonFrame.addFrame(r.getX(), r.getY(), time-0.1f);
    }
    @Override
    public boolean isActive() {
        return boostButtonFrame.isActive();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean setGoToY(float Y) {
        this.yGoTo = Y;
        isGOTo = true;
        return false;
    }

}
