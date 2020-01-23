package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.Animation.BoostButtonFrame;
import minercrystal.gamefield.Animation.ExplosionAnimation;
import minercrystal.gamefield.MovingHandler;
import minercrystal.gamefield.Objects.Item;

public class BoostBomb implements Boost {

    private Array<Array<Item>> Items;
    private MovingHandler handler;
    private int count, INDEX = 1;
    private boolean isActive = false, isDeploy, isReverse = false, isTimerDeploy;
    private Rectangle rect, rBomb;
    private float ANIM_SPEED = 0.2f, accumulator = 0, deployTimer = 0;
    private static final float deltaDeploy = 0.065f, xToGo = 220, yToGo = 150;
    private Array<Texture> buttonTexture;
    private Texture flag;
    private Texture bomb;
    private ExplosionAnimation explosionAnimation;
    private OrthographicCamera camera;
    private BitmapFont bitmapFont;
    private BoostButtonFrame boostButtonFrame;

    public BoostBomb(int count, Array<Array<Item>> Items, MovingHandler handler, Rectangle r, Array<Texture> buttonTexture, OrthographicCamera camera, BitmapFont font) {
        this.Items = Items;
        this.handler = handler;
        this.count = count;
        this.bitmapFont = font;
        this.rBomb = r;
        this.camera = camera;
        rect = new Rectangle();
        rect.set(r);
        this.buttonTexture = buttonTexture;
        isDeploy = true;
        explosionAnimation = new ExplosionAnimation();
        boostButtonFrame = new BoostButtonFrame(rect.width, rect.height);

        bomb = new Texture("boost/bomb.png");
        flag = new Texture("boost/boostButton/flag.png");
    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        spriteBatch.draw(buttonTexture.get(INDEX), rect.x, rect.y, rect.width, rect.height);
        spriteBatch.draw(flag, rect.x + 40, rect.y + 47, 25, 25);
        bitmapFont.draw(spriteBatch, String.valueOf(count), rect.x + 48, rect.y + 63);
        if (isTimerDeploy) {
            deployTimer -= delta;
            boostButtonFrame.render(spriteBatch, delta);
            if (deployTimer < 0) {
                isTimerDeploy = false;
                isActive = false;
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
                } else {
                    INDEX--;
                    if (INDEX == 0) {
                        isDeploy = false;
                        count = -1;
                    }
                }

            }
        } else {
            if (count > 0)
                spriteBatch.draw(bomb, rect.x, rect.y, rect.width, rect.height);
        }
        if (isActive) {
            renderActive(spriteBatch, delta);
        }
        explosionAnimation.render(delta, spriteBatch, camera);
    }

    private void renderActive(SpriteBatch spriteBatch, float delta) {
        spriteBatch.draw(bomb, rBomb.x, rBomb.y, rBomb.width, rBomb.height);
        if (!isTimerDeploy) {
            if (rBomb.x > xToGo + 15 || rBomb.y > yToGo + 15) {
                rBomb.x -= ((rBomb.x - xToGo) / ANIM_SPEED) * delta;
                rBomb.y -= ((rBomb.y - yToGo) / ANIM_SPEED) * delta;
            } else {
                isActive = false;
                handler.setNeedDestroy(true);
                for (Array<Item> x : Items) {
                    for (Item y : x)
                        y.setNeedToDestroy(true);
                }
                explosionAnimation.addExplosionAnimation(rBomb.x - 30, rBomb.y - 30, rBomb.width + 160, rBomb.height + 160);
                rBomb.set(rect);
            }
        }
    }


    @Override
    public boolean touchDown(float X, float Y) {
        if (rect.contains(X, Y)) {
            setActive(deltaDeploy, getCount() - 1);
        }
        if (getCount() == 0) {
            isReverse = true;
            isDeploy = true;
            return true;
        } else
            return false;
    }

    private void setActive(float time, int count) {
        isActive = true;
        this.count = count;
        deployTimer = time;
        boostButtonFrame.addFrame(rect.getX(), rect.getY(), deployTimer - 0.1f);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isActive() {
        isActive = true;
        return false;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean setGoToY(float Y) {
        return false;
    }

    @Override
    public Rectangle getElementRect(String s) {
        return rect;
    }

    @Override
    public void plusElement(String element, int count) {
        if(element == "delete"){
            deployTimer = 0.8f;
        }else
        setActive(0.8f, getCount() + count);
        isTimerDeploy = true;

    }
}
