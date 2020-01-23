package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.Animation.BoostButtonFrame;
import minercrystal.gamefield.MovingHandler;
import minercrystal.gamefield.Objects.Item;
import minercrystal.gamefield.ScreenWindows.EndScreen;


public class BoostRow implements Boost {

    private final static float TIMER = 6f;
    private Rectangle r;
    private int count, INDEX = 1, lesrIndex = 0, dx, dy;
    private float accumulator = 0, yGoTo, laserX, laserY, laserAccumulator, deployTimer = 0;
    private static final float deltaDeploy = 0.065f, laserDelta = 0.025f;
    private boolean isDeploy, isReverse = false, isGOTo = false, isLaserDeploy = false, isCanBlow = true, isTimerDeploy = false;
    private BoostButtonFrame boostButtonFrame;
    private Texture rowTexture, laserM, flag;
    private Array<Texture> buttonTexture;
    private Array<TextureRegion> lasers;
    private Array<Array<Item>> Items;
    private MovingHandler handler;
    private BitmapFont bitmapFont;

    public BoostRow(int count, Array<Array<Item>> Items, MovingHandler handler, Rectangle r, Array<Texture> buttonTexture, BitmapFont font) {
        this.r = r;
        this.bitmapFont = font;
        this.Items = Items;
        this.handler = handler;
        this.count = count;
        this.buttonTexture = buttonTexture;
        isDeploy = true;
        boostButtonFrame = new BoostButtonFrame(r.width, r.height);
        rowTexture = new Texture("boost/row.png");
        laserM = new Texture("boost/laser.png");
        lasers = new Array<>();

        for (int i = 0; i < 12; i++) {
            for (int i2 = 0; i2 < 3; i2++)
                lasers.add(new TextureRegion(laserM, i * 512, 682 * i2, 512, 682));
        }
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
            spriteBatch.draw(rowTexture, r.x, r.y, r.width, r.height);
        }

        if (boostButtonFrame.isActive())
            spriteBatch.draw(rowTexture, r.x, r.y, r.width, r.height);

        if (isLaserDeploy) {
            laserAccumulator += delta;
            if (laserAccumulator > laserDelta) {
                lesrIndex++;
                laserAccumulator = 0;
                if (lesrIndex == 13) {
                    lesrIndex = 0;
                    setDestroybyLaser(dx, dy);
                    isLaserDeploy = false;
                }
            }
            spriteBatch.draw(lasers.get(lesrIndex), 35, laserY - 250, 412 / 2, 550 / 2, 412, 550, 1, 1, 90);
            spriteBatch.draw(lasers.get(lesrIndex), laserX - 177, -45, 412, 490);
        }
    }

    public void setLaserDeploy(Vector2 elementDestroy) {
        this.dx = (int) elementDestroy.x;
        this.dy = (int) elementDestroy.y;
        this.laserX = Items.get((int) elementDestroy.x).get((int) elementDestroy.y).getRectangle().getX();
        this.laserY = Items.get((int) elementDestroy.x).get((int) elementDestroy.y).getRectangle().getY();
        isLaserDeploy = true;
        isCanBlow = false;
    }

    private void setDestroybyLaser(int dx, int dy) {
        for (Item y : Items.get(dx))
            y.setNeedToDestroy(true);//destroy xxxx

        for (Array<Item> x : Items) {
            for (Item y : x)
                if (x.indexOf(y, true) == dy)
                    y.setNeedToDestroy(true);

        }///destroy YYYYYY
        isCanBlow = true;
    }

    public boolean isLaserDeploy() {
        return isLaserDeploy;
    }


    @Override
    public boolean touchDown(float X, float Y) {
        if (r.contains(X, Y)) {
            if (!boostButtonFrame.isActive()) {
                setActive(TIMER, getCount() - 1);
            }
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

    private void setActive(float time, int count) {
        this.count = count;
        deployTimer = time;
        isTimerDeploy = true;
        boostButtonFrame.addFrame(r.getX(), r.getY(), time-0.1f);
    }

    public boolean isCanBlow() {
        return isCanBlow;
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
        setActive(0.8f, getCount() + count);

    }
}
